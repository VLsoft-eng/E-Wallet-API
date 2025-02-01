package ru.cft.template.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class AppConfiguration {

    @Bean
    SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
