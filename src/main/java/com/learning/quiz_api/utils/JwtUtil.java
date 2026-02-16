package com.learning.quiz_api.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // Define your secret key (preferably load from environment variables or config)
    private final String SECRET_KEY = "470dfd8100d8bc8a40d7910d45f30d92cac2e100f0ce7b2e34e9495f1a6d7faa"; // Need to have 256 bits or 256/ 8*2 = 64 digits
    private final long EXPIRATION_TIME = 3600000; // 1 hour expiration time in milliseconds

    private Key createSecretKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long userId) {
        Key secretKey = createSecretKey(SECRET_KEY); // Create a SecretKey from the string secret

        return Jwts.builder()
                .setSubject(userId.toString())  // Set the username as the subject
                .setIssuedAt(new Date())  // Set the issue date to the current time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Set expiration time
                .signWith(secretKey, SignatureAlgorithm.HS256)  // Sign the JWT with the secret key
                .compact();  // Build and return the token
    }

    public boolean validateToken(String token) {
        try {
            Key secretKey = createSecretKey(SECRET_KEY); // Create SecretKey from the string

            Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // Use the same SecretKey for validation
                    .build()
                    .parseClaimsJws(token);  // Parse and validate the token
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long extractUserIdFromToken(String token) {
        Key secretKey = createSecretKey(SECRET_KEY); // Create SecretKey from the string

        String userId = Jwts.parserBuilder()
                .setSigningKey(secretKey)  // Use the SecretKey for parsing
                .build()
                .parseClaimsJws(token)  // Parse the token
                .getBody()
                .getSubject();  // Return the username (subject) from the token

        return Long.valueOf(userId);
    }
}
