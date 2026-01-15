package com.moithuti.funds.security;

import android.util.Log;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT Utility - Handles JWT token operations for Google Sheets API
 * Provides token generation, validation, and refresh functionality
 */
public class JwtUtil {

    private static final String TAG = "JwtUtil";
    
    // Google Sheets API scope
    private static final String SHEETS_SCOPE = "https://www.googleapis.com/auth/spreadsheets";

    /**
     * Generate JWT token from Google credentials
     * @param credentials Google credentials
     * @return JWT token string or null if failed
     */
    public static String generateJwtToken(GoogleCredentials credentials) {
        try {
            if (credentials != null) {
                // Ensure credentials have the correct scope
                if (credentials.createScopedRequired()) {
                    credentials = credentials.createScoped(Collections.singleton(SHEETS_SCOPE));
                }
                
                // Refresh token to get new JWT
                credentials.refresh();
                
                String token = credentials.getAccessToken().getTokenValue();
                Log.d(TAG, "JWT token generated successfully");
                
                return token;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error generating JWT token", e);
        }
        
        return null;
    }

    /**
     * Generate JWT token from GoogleCredential (legacy)
     * @param credential GoogleCredential
     * @return JWT token string or null if failed
     */
    public static String generateJwtToken(GoogleCredential credential) {
        try {
            if (credential != null) {
                // Ensure credential has the correct scope
                if (credential.createScopedRequired()) {
                    credential = credential.createScoped(Collections.singleton(SHEETS_SCOPE));
                }
                
                // Refresh token to get new JWT
                credential.refreshToken();
                
                String token = credential.getAccessToken();
                Log.d(TAG, "JWT token generated successfully (legacy)");
                
                return token;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error generating JWT token (legacy)", e);
        }
        
        return null;
    }

    /**
     * Validate JWT token format
     * @param token the JWT token to validate
     * @return true if token appears valid (basic format check)
     */
    public static boolean validateJwtFormat(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        
        // Basic JWT format check: three parts separated by dots
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            Log.w(TAG, "JWT token format invalid - wrong number of parts");
            return false;
        }
        
        // Check if each part is non-empty and appears to be base64 encoded
        for (String part : parts) {
            if (part.trim().isEmpty()) {
                Log.w(TAG, "JWT token format invalid - empty part");
                return false;
            }
            
            // Basic base64 check (not comprehensive)
            if (!isValidBase64(part)) {
                Log.w(TAG, "JWT token format invalid - invalid base64 in part");
                return false;
            }
        }
        
        return true;
    }

    /**
     * Check if string is valid base64 (basic check)
     * @param base64String the string to check
     * @return true if appears to be valid base64
     */
    private static boolean isValidBase64(String base64String) {
        try {
            // Remove URL-safe base64 characters if present
            String normalized = base64String.replace("-", "+").replace("_", "/");
            
            // Check if length is divisible by 4 (base64 requirement)
            if (normalized.length() % 4 != 0) {
                return false;
            }
            
            // Try to decode (this is a basic validation)
            java.util.Base64.getDecoder().decode(normalized);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extract token expiration time (if available)
     * @param token the JWT token
     * @return expiration time in milliseconds, or 0 if not available
     */
    public static long getTokenExpirationTime(String token) {
        try {
            if (!validateJwtFormat(token)) {
                return 0;
            }
            
            // Extract payload (middle part)
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return 0;
            }
            
            String payload = parts[1];
            
            // Decode payload
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(payload);
            String payloadJson = new String(decodedBytes);
            
            // Parse JSON to get expiration time
            org.json.JSONObject json = new org.json.JSONObject(payloadJson);
            
            if (json.has("exp")) {
                long expSeconds = json.getLong("exp");
                return expSeconds * 1000; // Convert to milliseconds
            }
            
        } catch (Exception e) {
            Log.w(TAG, "Error extracting token expiration time", e);
        }
        
        return 0;
    }

    /**
     * Check if JWT token is expired
     * @param token the JWT token
     * @return true if token is expired or invalid
     */
    public static boolean isTokenExpired(String token) {
        long expirationTime = getTokenExpirationTime(token);
        if (expirationTime == 0) {
            // Cannot determine expiration, assume expired for safety
            return true;
        }
        
        long currentTime = System.currentTimeMillis();
        boolean expired = currentTime >= expirationTime;
        
        if (expired) {
            Log.d(TAG, "JWT token is expired");
        }
        
        return expired;
    }

    /**
     * Get time until token expires
     * @param token the JWT token
     * @return milliseconds until expiration, or 0 if expired/unknown
     */
    public static long getTimeUntilExpiration(String token) {
        long expirationTime = getTokenExpirationTime(token);
        if (expirationTime == 0) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long timeUntil = expirationTime - currentTime;
        
        return Math.max(0, timeUntil);
    }

    /**
     * Get token issuer (if available)
     * @param token the JWT token
     * @return issuer string or null if not available
     */
    public static String getTokenIssuer(String token) {
        try {
            if (!validateJwtFormat(token)) {
                return null;
            }
            
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return null;
            }
            
            String payload = parts[1];
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(payload);
            String payloadJson = new String(decodedBytes);
            
            org.json.JSONObject json = new org.json.JSONObject(payloadJson);
            
            if (json.has("iss")) {
                return json.getString("iss");
            }
            
        } catch (Exception e) {
            Log.w(TAG, "Error extracting token issuer", e);
        }
        
        return null;
    }

    /**
     * Get token subject (if available)
     * @param token the JWT token
     * @return subject string or null if not available
     */
    public static String getTokenSubject(String token) {
        try {
            if (!validateJwtFormat(token)) {
                return null;
            }
            
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return null;
            }
            
            String payload = parts[1];
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(payload);
            String payloadJson = new String(decodedBytes);
            
            org.json.JSONObject json = new org.json.JSONObject(payloadJson);
            
            if (json.has("sub")) {
                return json.getString("sub");
            }
            
        } catch (Exception e) {
            Log.w(TAG, "Error extracting token subject", e);
        }
        
        return null;
    }

    /**
     * Get basic token information for debugging
     * @param token the JWT token
     * @return token information string
     */
    public static String getTokenInfo(String token) {
        StringBuilder info = new StringBuilder();
        
        info.append("JWT Token Information:\n");
        info.append("Format valid: ").append(validateJwtFormat(token)).append("\n");
        info.append("Expired: ").append(isTokenExpired(token)).append("\n");
        
        long expirationTime = getTokenExpirationTime(token);
        if (expirationTime > 0) {
            info.append("Expiration time: ").append(expirationTime).append("\n");
            info.append("Time until expiration: ").append(getTimeUntilExpiration(token)).append(" ms\n");
        } else {
            info.append("Expiration time: Unknown\n");
        }
        
        String issuer = getTokenIssuer(token);
        if (issuer != null) {
            info.append("Issuer: ").append(issuer).append("\n");
        }
        
        String subject = getTokenSubject(token);
        if (subject != null) {
            info.append("Subject: ").append(subject).append("\n");
        }
        
        return info.toString();
    }

    /**
     * Create scoped credentials for Google Sheets
     * @param credentials base credentials
     * @return scoped credentials
     */
    public static GoogleCredentials createScopedCredentials(GoogleCredentials credentials) {
        if (credentials != null) {
            return credentials.createScoped(Collections.singleton(SHEETS_SCOPE));
        }
        return null;
    }

    /**
     * Create scoped credentials for Google Sheets (legacy)
     * @param credential base credential
     * @return scoped credential
     */
    public static GoogleCredential createScopedCredentials(GoogleCredential credential) {
        if (credential != null) {
            return credential.createScoped(Collections.singleton(SHEETS_SCOPE));
        }
        return null;
    }
}
