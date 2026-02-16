package com.learning.quiz_api.impl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationService {
    private static class VerificationEntry {
        String code;
        long expirationTime;

        VerificationEntry(String code, long expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }

    private final Map<String, VerificationEntry> verificationMap = new ConcurrentHashMap<>();
    private final long codeExpirationMillis;

    public VerificationService() {
        this(10 * 60 * 1000); // Default: 10 minutes
    }

    public VerificationService(long expirationMillis) {
        this.codeExpirationMillis = expirationMillis;
    }

    public void storeVerificationCode(String email, String code) {
        long expirationTime = System.currentTimeMillis() + codeExpirationMillis;
        verificationMap.put(email, new VerificationEntry(code, expirationTime));

        // Clean up expired entries periodically (optional)
        if (verificationMap.size() > 100) { // Clean up when map gets large
            cleanupExpiredEntries();
        }
    }

    public boolean verifyCode(String email, String inputCode) {
        VerificationEntry entry = verificationMap.get(email);

        if (entry == null) {
            return false; // No code found for this email
        }

        if (entry.isExpired()) {
            verificationMap.remove(email); // Clean up expired entry
            return false;
        }

        boolean isValid = entry.code.equals(inputCode);
        if (isValid) {
            verificationMap.remove(email); // Remove after successful verification
        }
        return isValid;
    }

    public void removeVerification(String email) {
        verificationMap.remove(email);
    }

    public void cleanupExpiredEntries() {
        verificationMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    public int getActiveVerificationCount() {
        cleanupExpiredEntries();
        return verificationMap.size();
    }
}