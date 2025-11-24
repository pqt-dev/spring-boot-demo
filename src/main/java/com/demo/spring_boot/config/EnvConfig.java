package com.demo.spring_boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Configuration
public class EnvConfig {

    final private Environment environment;

    public EnvConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public boolean isProd() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        return profiles.contains("prod");
    }

}
