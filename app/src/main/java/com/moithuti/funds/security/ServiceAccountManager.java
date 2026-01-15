package com.moithuti.funds.security;

import android.content.Context;
import android.util.Log;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Service Account Manager - Manages Google Service Account authentication
 * Handles JWT token generation and Google Sheets API client creation
 */
public class ServiceAccountManager {

    private static final String TAG = "ServiceAccountManager";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_FILE = "service_account.json";
    
    // Google Sheets API scopes
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    
    private static ServiceAccountManager instance;
    private final Context context;
    private GoogleCredential credential;
    private Sheets sheetsService;
    
    /**
     * Get singleton instance
     * @param context application context
     * @return manager instance
     */
    public static synchronized ServiceAccountManager getInstance(Context context) {
        if (instance == null) {
            instance = new ServiceAccountManager(context);
        }
        return instance;
    }

    private ServiceAccountManager(Context context) {
        this.context = context.getApplicationContext();
        initializeCredentials();
    }

    /**
     * Initialize Google credentials from service account file
     */
    private void initializeCredentials() {
        try {
            InputStream inputStream = context.getAssets().open(SERVICE_ACCOUNT_FILE);
            
            // Read the service account file content to validate it
            java.util.Scanner scanner = new java.util.Scanner(inputStream, "UTF-8");
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
            scanner.close();
            inputStream.close();
            
            String serviceAccountContent = content.toString();
            
            // Validate that this is not a placeholder file
            if (serviceAccountContent.contains("REPLACE_WITH_YOUR_PRIVATE_KEY") || 
                serviceAccountContent.contains("your-project-id") ||
                serviceAccountContent.contains("your-service-account")) {
                Log.w(TAG, "Service account file contains placeholder values - skipping initialization");
                credential = null;
                sheetsService = null;
                return;
            }
            
            // Reopen the stream for GoogleCredential
            inputStream = context.getAssets().open(SERVICE_ACCOUNT_FILE);
            NetHttpTransport httpTransport = new NetHttpTransport();
            
            credential = GoogleCredential.fromStream(inputStream, httpTransport, JSON_FACTORY)
                    .createScoped(SCOPES);
            
            // Create Sheets service
            sheetsService = new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName("Moithuti Funds Operations")
                    .build();
            
            Log.d(TAG, "Google Sheets service initialized successfully");
            
        } catch (IOException e) {
            Log.e(TAG, "Error initializing Google credentials", e);
            credential = null;
            sheetsService = null;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error initializing credentials", e);
            credential = null;
            sheetsService = null;
        }
    }

    /**
     * Check if credentials are properly initialized
     * @return true if credentials are valid
     */
    public boolean isCredentialsValid() {
        return credential != null && sheetsService != null && !credential.createScopedRequired();
    }

    /**
     * Get Google Sheets service
     * @return Sheets service instance or null if not initialized
     */
    public Sheets getSheetsService() {
        return sheetsService;
    }

    /**
     * Get service account email
     * @return service account email or null
     */
    public String getServiceAccountEmail() {
        if (credential != null && credential.getServiceAccountId() != null) {
            return credential.getServiceAccountId();
        }
        return null;
    }

    /**
     * Refresh credentials if needed
     * @return true if refresh was successful
     */
    public boolean refreshCredentials() {
        try {
            if (credential != null) {
                boolean refreshed = credential.refreshToken();
                if (refreshed) {
                    Log.d(TAG, "Credentials refreshed successfully");
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error refreshing credentials", e);
        }
        
        // If refresh failed, try to reinitialize
        initializeCredentials();
        return isCredentialsValid();
    }

    /**
     * Check if service account file exists
     * @return true if service account file exists
     */
    public boolean serviceAccountFileExists() {
        try {
            InputStream inputStream = context.getAssets().open(SERVICE_ACCOUNT_FILE);
            inputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Get credential status information
     * @return status string
     */
    public String getCredentialStatus() {
        if (!serviceAccountFileExists()) {
            return "Service account file not found";
        }
        
        if (credential == null) {
            return "Credentials not initialized";
        }
        
        if (credential.createScopedRequired()) {
            return "Credentials need scoping";
        }
        
        if (sheetsService == null) {
            return "Sheets service not created";
        }
        
        return "Credentials valid";
    }

    /**
     * Test connection to Google Sheets API
     * @return true if connection test succeeds
     */
    public boolean testConnection() {
        if (!isCredentialsValid()) {
            Log.w(TAG, "Cannot test connection - credentials invalid");
            return false;
        }
        
        try {
            // Try to get spreadsheet metadata (this tests the connection)
            com.google.api.services.sheets.v4.model.Spreadsheet spreadsheet = 
                sheetsService.spreadsheets().get("test").execute();
            Log.d(TAG, "Connection test successful");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Connection test failed", e);
            return false;
        }
    }

    /**
     * Get JWT token (for debugging purposes)
     * @return JWT token or null
     */
    public String getJwtToken() {
        try {
            if (credential != null) {
                return credential.getAccessToken();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting JWT token", e);
        }
        return null;
    }

    /**
     * Check if token is expired
     * @return true if token is expired
     */
    public boolean isTokenExpired() {
        if (credential == null) {
            return true;
        }
        
        long expirationTime = credential.getExpirationTimeMilliseconds();
        if (expirationTime <= 0) {
            return true; // Unknown expiration, assume expired
        }
        
        long currentTime = System.currentTimeMillis();
        boolean expired = currentTime >= expirationTime;
        
        if (expired) {
            Log.d(TAG, "Token expired");
        }
        
        return expired;
    }

    /**
     * Get time until token expires (in milliseconds)
     * @return milliseconds until expiration, or 0 if expired/unknown
     */
    public long getTimeUntilExpiration() {
        if (credential == null) {
            return 0;
        }
        
        long expirationTime = credential.getExpirationTimeMilliseconds();
        if (expirationTime <= 0) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long timeUntil = expirationTime - currentTime;
        
        return Math.max(0, timeUntil);
    }

    /**
     * Force reinitialization of credentials
     */
    public void reinitialize() {
        Log.d(TAG, "Reinitializing credentials");
        initializeCredentials();
    }

    /**
     * Cleanup resources
     */
    public void cleanup() {
        credential = null;
        sheetsService = null;
        Log.d(TAG, "ServiceAccountManager cleaned up");
    }

    /**
     * Get detailed status information for debugging
     * @return detailed status string
     */
    public String getDetailedStatus() {
        StringBuilder status = new StringBuilder();
        
        status.append("Service Account Status:\n");
        status.append("File exists: ").append(serviceAccountFileExists()).append("\n");
        status.append("Credentials initialized: ").append(credential != null).append("\n");
        status.append("Sheets service created: ").append(sheetsService != null).append("\n");
        status.append("Credentials valid: ").append(isCredentialsValid()).append("\n");
        
        if (credential != null) {
            status.append("Service account ID: ").append(credential.getServiceAccountId()).append("\n");
            status.append("Token expired: ").append(isTokenExpired()).append("\n");
            status.append("Time until expiration: ").append(getTimeUntilExpiration()).append(" ms\n");
        }
        
        return status.toString();
    }
}
