package com.moithuti.funds.data.remote.sheets;

import android.content.Context;
import android.util.Log;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.moithuti.funds.data.local.entity.*;
import com.moithuti.funds.security.ServiceAccountManager;
import com.moithuti.funds.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Google Sheets Service - High-level API for Google Sheets operations
 * Provides CRUD operations for spreadsheet data management
 */
public class GoogleSheetsService {

    private static final String TAG = "GoogleSheetsService";
    
    private final Context context;
    private final ServiceAccountManager serviceAccountManager;
    private String spreadsheetId;
    
    /**
     * Constructor
     * @param context application context
     */
    public GoogleSheetsService(Context context) {
        this.context = context;
        this.serviceAccountManager = ServiceAccountManager.getInstance(context);
        this.spreadsheetId = null; // Will be set when needed
    }

    /**
     * Set spreadsheet ID
     * @param spreadsheetId the Google Sheets spreadsheet ID
     */
    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
        Log.d(TAG, "Spreadsheet ID set: " + spreadsheetId);
    }

    /**
     * Get spreadsheet ID
     * @return current spreadsheet ID
     */
    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    /**
     * Check if service is ready for operations
     * @return true if ready
     */
    public boolean isReady() {
        return serviceAccountManager.isCredentialsValid() && spreadsheetId != null && !spreadsheetId.trim().isEmpty();
    }

    /**
     * Test connection to Google Sheets
     * @return true if connection successful
     */
    public boolean testConnection() {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for connection test");
            return false;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            if (sheetsService == null) {
                Log.e(TAG, "Sheets service is null");
                return false;
            }

            // Try to get spreadsheet metadata
            Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();
            
            Log.d(TAG, "Connection test successful. Spreadsheet: " + spreadsheet.getProperties().getTitle());
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Connection test failed", e);
            
            // Try to refresh credentials and retry once
            if (serviceAccountManager.refreshCredentials()) {
                return testConnection();
            }
            
            return false;
        }
    }

    /**
     * Read data from a specific range
     * @param tabName the sheet tab name
     * @param range the range to read (e.g., "A1:Z100")
     * @return list of rows with data
     */
    public List<List<Object>> readRange(String tabName, String range) {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for read operation");
            return null;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            String fullRange = tabName + "!" + range;
            
            ValueRange result = sheetsService.spreadsheets().values()
                    .get(spreadsheetId, fullRange)
                    .execute();
            
            List<List<Object>> values = result.getValues();
            
            if (values == null || values.isEmpty()) {
                Log.d(TAG, "No data found in range: " + fullRange);
                return new ArrayList<>();
            }
            
            Log.d(TAG, "Read " + values.size() + " rows from range: " + fullRange);
            return values;
            
        } catch (IOException e) {
            Log.e(TAG, "Error reading range: " + tabName + "!" + range, e);
            return null;
        }
    }

    /**
     * Write data to a specific range
     * @param tabName the sheet tab name
     * @param range the range to write (e.g., "A1:Z100")
     * @param values the data to write
     * @return true if successful
     */
    public boolean writeRange(String tabName, String range, List<List<Object>> values) {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for write operation");
            return false;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            String fullRange = tabName + "!" + range;
            
            ValueRange body = new ValueRange().setValues(values);
            
            UpdateValuesResponse result = sheetsService.spreadsheets().values()
                    .update(spreadsheetId, fullRange, body)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            
            Log.d(TAG, "Wrote " + result.getUpdatedRows() + " rows to range: " + fullRange);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error writing range: " + tabName + "!" + range, e);
            return false;
        }
    }

    /**
     * Append data to a sheet
     * @param tabName the sheet tab name
     * @param range the range to append to (e.g., "A1")
     * @param values the data to append
     * @return true if successful
     */
    public boolean appendRange(String tabName, String range, List<List<Object>> values) {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for append operation");
            return false;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            String fullRange = tabName + "!" + range;
            
            ValueRange body = new ValueRange().setValues(values);
            
            AppendValuesResponse result = sheetsService.spreadsheets().values()
                    .append(spreadsheetId, fullRange, body)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            
            Log.d(TAG, "Appended " + values.size() + " rows to range: " + fullRange);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error appending range: " + tabName + "!" + range, e);
            return false;
        }
    }

    /**
     * Clear data from a range
     * @param tabName the sheet tab name
     * @param range the range to clear
     * @return true if successful
     */
    public boolean clearRange(String tabName, String range) {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for clear operation");
            return false;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            String fullRange = tabName + "!" + range;
            
            sheetsService.spreadsheets().values()
                    .clear(spreadsheetId, fullRange, new com.google.api.services.sheets.v4.model.ClearValuesRequest())
                    .execute();
            
            Log.d(TAG, "Cleared range: " + fullRange);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error clearing range: " + tabName + "!" + range, e);
            return false;
        }
    }

    /**
     * Create a new sheet tab
     * @param tabName the name for the new tab
     * @return true if successful
     */
    public boolean createSheet(String tabName) {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for create sheet operation");
            return false;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            
            // Create request to add sheet
            Request request = new Request()
                    .setAddSheet(new com.google.api.services.sheets.v4.model.AddSheetRequest()
                            .setProperties(new com.google.api.services.sheets.v4.model.SheetProperties()
                                    .setTitle(tabName)));
            
            BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Arrays.asList(request));
            
            BatchUpdateSpreadsheetResponse response = sheetsService.spreadsheets()
                    .batchUpdate(spreadsheetId, batchRequest)
                    .execute();
            
            Log.d(TAG, "Created sheet: " + tabName);
            return response != null;
            
        } catch (IOException e) {
            Log.e(TAG, "Error creating sheet: " + tabName, e);
            return false;
        }
    }

    /**
     * Check if sheet exists
     * @param tabName the sheet tab name
     * @return true if sheet exists
     */
    public boolean sheetExists(String tabName) {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for sheet existence check");
            return false;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();
            
            for (com.google.api.services.sheets.v4.model.Sheet sheet : spreadsheet.getSheets()) {
                if (tabName.equals(sheet.getProperties().getTitle())) {
                    return true;
                }
            }
            
            return false;
            
        } catch (IOException e) {
            Log.e(TAG, "Error checking sheet existence: " + tabName, e);
            return false;
        }
    }

    /**
     * Initialize sheet with headers
     * @param tabName the sheet tab name
     * @param headers the header row
     * @return true if successful
     */
    public boolean initializeSheet(String tabName, List<Object> headers) {
        if (!sheetExists(tabName)) {
            if (!createSheet(tabName)) {
                Log.e(TAG, "Failed to create sheet: " + tabName);
                return false;
            }
        }
        
        // Write headers
        List<List<Object>> headerData = Arrays.asList(headers);
        return writeRange(tabName, "A1", headerData);
    }

    /**
     * Get sheet metadata
     * @return spreadsheet information
     */
    public Spreadsheet getSpreadsheetInfo() {
        if (!isReady()) {
            Log.w(TAG, "Service not ready for getting spreadsheet info");
            return null;
        }

        try {
            Sheets sheetsService = serviceAccountManager.getSheetsService();
            return sheetsService.spreadsheets().get(spreadsheetId).execute();
            
        } catch (IOException e) {
            Log.e(TAG, "Error getting spreadsheet info", e);
            return null;
        }
    }

    /**
     * Get all sheet names
     * @return list of sheet names
     */
    public List<String> getSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        
        Spreadsheet spreadsheet = getSpreadsheetInfo();
        if (spreadsheet != null) {
            for (com.google.api.services.sheets.v4.model.Sheet sheet : spreadsheet.getSheets()) {
                sheetNames.add(sheet.getProperties().getTitle());
            }
        }
        
        return sheetNames;
    }

    /**
     * Get service status information
     * @return status string
     */
    public String getServiceStatus() {
        StringBuilder status = new StringBuilder();
        
        status.append("Google Sheets Service Status:\n");
        status.append("Service Account Manager: ").append(serviceAccountManager.getCredentialStatus()).append("\n");
        status.append("Spreadsheet ID: ").append(spreadsheetId != null ? spreadsheetId : "Not set").append("\n");
        status.append("Service Ready: ").append(isReady()).append("\n");
        
        if (isReady()) {
            status.append("Connection Test: ").append(testConnection() ? "Passed" : "Failed").append("\n");
            
            List<String> sheetNames = getSheetNames();
            status.append("Available Sheets: ").append(sheetNames.size()).append("\n");
            for (String sheetName : sheetNames) {
                status.append("  - ").append(sheetName).append("\n");
            }
        }
        
        return status.toString();
    }

    /**
     * Initialize all required sheets with headers
     * @return true if all sheets initialized successfully
     */
    public boolean initializeAllSheets() {
        boolean success = true;
        
        // Initialize each sheet with its headers
        success &= initializeSheet(Constants.SHEETS_CLIENTS_TAB, 
                Arrays.asList((Object[]) Constants.SHEETS_CLIENTS_HEADERS));
        
        success &= initializeSheet(Constants.SHEETS_LOANS_TAB, 
                Arrays.asList((Object[]) Constants.SHEETS_LOANS_HEADERS));
        
        success &= initializeSheet(Constants.SHEETS_PAYMENTS_TAB, 
                Arrays.asList((Object[]) Constants.SHEETS_PAYMENTS_HEADERS));
        
        success &= initializeSheet(Constants.SHEETS_INVESTORS_TAB, 
                Arrays.asList((Object[]) Constants.SHEETS_INVESTORS_HEADERS));
        
        success &= initializeSheet(Constants.SHEETS_INVESTOR_TRANSACTIONS_TAB, 
                Arrays.asList((Object[]) Constants.SHEETS_INVESTOR_TRANSACTIONS_HEADERS));
        
        success &= initializeSheet(Constants.SHEETS_LOAN_FUNDING_TAB, 
                Arrays.asList((Object[]) Constants.SHEETS_LOAN_FUNDING_HEADERS));
        
        if (success) {
            Log.d(TAG, "All sheets initialized successfully");
        } else {
            Log.e(TAG, "Failed to initialize some sheets");
        }
        
        return success;
    }
}
