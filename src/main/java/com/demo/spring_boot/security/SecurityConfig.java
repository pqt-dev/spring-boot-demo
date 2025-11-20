package com.demo.spring_boot.security;

import com.demo.spring_boot.repository.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests.requestMatchers("/css/**", "/js/**", "/images/**",
                                                                  "/plugins/**")
                                                          .permitAll()
                                                          .requestMatchers("/", "/login**", "/webjars/**", "/error")
                                                          .permitAll()
                                                          .requestMatchers("/oauth2/authorization/**",
                                                                  "/login/oauth2/code/**")
                                                          .permitAll()
                                                          .requestMatchers("/register/**")
                                                          .permitAll()
                                                          .requestMatchers("/google_login")
                                                          .permitAll()
                                                          .requestMatchers("/uploads/**")
                                                          .permitAll()
                                                          .anyRequest()
                                                          .authenticated())
            .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
                                                   .defaultSuccessUrl("/profile", true)
                                                   .failureUrl("/login")
                                                   .userInfoEndpoint(
                                                           userInfo -> userInfo.oidcUserService(customOidcUserService)))
//                .oauth2ResourceServer((oauth) -> oauth.jwt(Customizer.withDefaults()))
            .logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/login")
                                    .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173", "http://127.0.0.1:5173", "https://myapp.com" // domain production
                ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // cần nếu dùng cookie hoặc session

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Cho phép tất cả endpoint áp dụng CORS này
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
