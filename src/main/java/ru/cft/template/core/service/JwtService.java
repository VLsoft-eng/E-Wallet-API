package ru.cft.template.core.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.cft.template.core.security.userDetails.CustomUserDetails;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(CustomUserDetails user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
