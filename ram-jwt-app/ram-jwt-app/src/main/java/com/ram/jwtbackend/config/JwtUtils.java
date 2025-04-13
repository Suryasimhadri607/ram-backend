package com.ram.jwtbackend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final String jwtSecret = "your-256-bit-secret-your-256-bit-secret"; // should be 32+ chars
    private final long jwtExpirationMs = 86400000; // 1 day
    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    // Extract username (email) from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Validate token using user details
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Generate token using Authentication object
    public String generateToken(org.springframework.security.core.Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Common method to extract all claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
