package com.demo.spring_boot.controller.authentication;

import com.demo.spring_boot.dto.authentication.GoogleLoginRequest;
import com.demo.spring_boot.dto.authentication.LoginRequest;
import com.demo.spring_boot.dto.author.CreateAuthorRequest;
import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.response.authentication.LoginResponse;
import com.demo.spring_boot.security.JwtTokenProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
public class LoginRestController {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthorRepository repository;

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        LoginResponse response = LoginResponse.builder()
                                              .token(jwt)
                                              .build();
        response.setToken(jwt);
        return ResponseEntity.ok(ApiResponse.builder()
                                            .data(response)
                                            .build());
    }

    @PostMapping("/register")
    Author register(@RequestBody CreateAuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setAddress(request.getAddress());
        author.setPhone(request.getPhone());
        author.setJob(request.getJob());
        author.setEmail(request.getEmail());
        author.setPassword(passwordEncoder.encode(request.getPassword()));
        return repository.save(author);
    }

    @PostMapping("/google_login")
    ResponseEntity<?> verify(@RequestBody GoogleLoginRequest request) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = googleIdTokenVerifier.verify(request.getIdTokenString());
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier. This ID is unique to each Google Account, making it suitable for
            // use as a primary key during account lookup. Email is not a good choice because it can be
            // changed by the user.
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            LoginResponse response = LoginResponse.builder()
                                                  .token(jwt)
                                                  .name(name)
                                                  .email(email)
                                                  .build();
            return ResponseEntity.ok(response);

        } else {
            System.out.println("Invalid ID token.");
            return null;
        }
    }

}
