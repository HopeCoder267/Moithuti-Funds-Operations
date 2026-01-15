package com.moithuti.funds.util;

/**
 * Application Constants - Centralized constant values
 * Contains all constant values used throughout the application
 */
public class Constants {

    // Application
    public static final String APP_NAME = "Moithuti Funds Operations";
    public static final String DATABASE_NAME = "moithuti_funds_database";
    public static final int DATABASE_VERSION = 1;

    // Sync
    public static final String SYNC_WORK_NAME = "SyncWorker";
    public static final int SYNC_INTERVAL_MINUTES = 15;
    public static final int SYNC_INTERVAL_HOURS = 1;
    public static final long SYNC_INTERVAL_MILLIS = SYNC_INTERVAL_MINUTES * 60 * 1000L;
    public static final String SYNC_CHANNEL_ID = "sync_channel";
    public static final String SYNC_CHANNEL_NAME = "Sync Notifications";

    // Client Status
    public static final String CLIENT_STATUS_OWING = "OWING";
    public static final String CLIENT_STATUS_PARTIAL = "PARTIAL";
    public static final String CLIENT_STATUS_PAID = "PAID";
    public static final String CLIENT_STATUS_OVERDUE = "OVERDUE";
    public static final String CLIENT_STATUS_BLACKLISTED = "BLACKLISTED";

    // Loan Status
    public static final String LOAN_STATUS_OWING = "OWING";
    public static final String LOAN_STATUS_PARTIAL = "PARTIAL";
    public static final String LOAN_STATUS_PAID = "PAID";
    public static final String LOAN_STATUS_OVERDUE = "OVERDUE";
    public static final String LOAN_STATUS_BLACKLISTED = "BLACKLISTED";

    // Investor Transaction Types
    public static final String TRANSACTION_TYPE_INVEST = "INVEST";
    public static final String TRANSACTION_TYPE_LOAN_OUT = "LOAN_OUT";
    public static final String TRANSACTION_TYPE_REPAYMENT_IN = "REPAYMENT_IN";

    // Sync Status
    public static final String SYNC_STATUS_PENDING = "PENDING";
    public static final String SYNC_STATUS_SYNCED = "SYNCED";
    public static final String SYNC_STATUS_FAILED = "FAILED";

    // Default Values
    public static final double DEFAULT_LOAN_AMOUNT = 1000.0;
    public static final int DEFAULT_LOAN_TERM_DAYS = 30;
    public static final double DEFAULT_INTEREST_RATE = 10.0;
    public static final double MIN_LOAN_AMOUNT = 1.0;
    public static final double MAX_LOAN_AMOUNT = 100000.0;
    public static final double MIN_INVESTMENT_AMOUNT = 1.0;
    public static final double MAX_INVESTMENT_AMOUNT = 1000000.0;

    // Validation
    public static final int MAX_CLIENT_NAME_LENGTH = 100;
    public static final int MAX_PHONE_LENGTH = 15; // Botswana numbers are shorter
    public static final int MAX_NOTES_LENGTH = 1000;
    public static final int MAX_INVESTOR_NAME_LENGTH = 100;
    public static final int MIN_PHONE_LENGTH = 7; // More flexible minimum

    // Date Formats
    public static final String DATE_FORMAT_DISPLAY = "dd MMM yyyy";
    public static final String DATE_FORMAT_SHORT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_YEAR_MONTH = "yyyy-MM";
    public static final String DATE_FORMAT_MONTH_YEAR = "MMM yyyy";
    public static final String DATE_FORMAT_DATETIME = "dd MMM yyyy HH:mm";

    // Currency
    public static final String CURRENCY_SYMBOL = "P";
    public static final String CURRENCY_CODE = "BWP";
    public static final int CURRENCY_DECIMAL_PLACES = 2;

    // Google Sheets
    public static final String SHEETS_CLIENTS_TAB = "Clients";
    public static final String SHEETS_LOANS_TAB = "Loans";
    public static final String SHEETS_PAYMENTS_TAB = "Payments";
    public static final String SHEETS_INVESTORS_TAB = "Investors";
    public static final String SHEETS_INVESTOR_TRANSACTIONS_TAB = "InvestorTransactions";
    public static final String SHEETS_LOAN_FUNDING_TAB = "LoanFunding";
    public static final String SHEETS_SERVICE_ACCOUNT_FILE = "service_account.json";

    // Sheets Column Headers (must match Google Sheet exactly)
    public static final String[] SHEETS_CLIENTS_HEADERS = {
        "uuid", "name", "phone", "notes", "status", "createdDate", "lastModified", "syncStatus", "deleted"
    };

    public static final String[] SHEETS_LOANS_HEADERS = {
        "uuid", "clientId", "amount", "dateIssued", "dueDate", "status", "lastModified", "syncStatus", "deleted"
    };

    public static final String[] SHEETS_PAYMENTS_HEADERS = {
        "uuid", "loanId", "amount", "date", "lastModified", "syncStatus", "deleted"
    };

    public static final String[] SHEETS_INVESTORS_HEADERS = {
        "uuid", "name", "isMainAccount", "createdDate", "lastModified", "syncStatus", "deleted"
    };

    public static final String[] SHEETS_INVESTOR_TRANSACTIONS_HEADERS = {
        "uuid", "investorId", "type", "amount", "relatedLoanId", "timestamp", "yearMonth", "lastModified", "syncStatus", "deleted"
    };

    public static final String[] SHEETS_LOAN_FUNDING_HEADERS = {
        "uuid", "loanId", "investorId", "amount", "createdDate", "lastModified", "syncStatus", "deleted"
    };

    // Network
    public static final int NETWORK_TIMEOUT_SECONDS = 30;
    public static final int NETWORK_RETRY_COUNT = 3;
    public static final long NETWORK_RETRY_DELAY_MILLIS = 1000;

    // UI
    public static final int ANIMATION_DURATION_SHORT = 200;
    public static final int ANIMATION_DURATION_MEDIUM = 300;
    public static final int ANIMATION_DURATION_LONG = 500;
    public static final int DEBOUNCE_DELAY_MILLIS = 300;

    // Pagination
    public static final int PAGE_SIZE = 20;
    public static final int PREFETCH_DISTANCE = 5;

    // Cache
    public static final int CACHE_SIZE = 50;
    public static final long CACHE_EXPIRY_MILLIS = 5 * 60 * 1000; // 5 minutes

    // Error Messages
    public static final String ERROR_NETWORK = "Network error. Please check your connection.";
    public static final String ERROR_SYNC = "Sync failed. Please try again.";
    public static final String ERROR_VALIDATION = "Please check your input and try again.";
    public static final String ERROR_DATABASE = "Database error. Please restart the app.";
    public static final String ERROR_UNKNOWN = "An unexpected error occurred.";

    // Success Messages
    public static final String SUCCESS_SAVED = "Saved successfully";
    public static final String SUCCESS_DELETED = "Deleted successfully";
    public static final String SUCCESS_SYNCED = "Synced successfully";
    public static final String SUCCESS_CLIENT_ADDED = "Client added successfully";
    public static final String SUCCESS_LOAN_ISSUED = "Loan issued successfully";
    public static final String SUCCESS_PAYMENT_ADDED = "Payment added successfully";
    public static final String SUCCESS_INVESTOR_ADDED = "Investor added successfully";

    // Confirmation Messages
    public static final String CONFIRM_DELETE_CLIENT = "Are you sure you want to delete this client? This will also delete all associated loans and payments.";
    public static final String CONFIRM_DELETE_LOAN = "Are you sure you want to delete this loan? This will also delete all associated payments.";
    public static final String CONFIRM_DELETE_PAYMENT = "Are you sure you want to delete this payment?";
    public static final String CONFIRM_DELETE_INVESTOR = "Are you sure you want to delete this investor? This will also delete all associated transactions.";

    // SharedPreferences Keys
    public static final String PREFS_NAME = "moithuti_funds_prefs";
    public static final String PREF_LAST_SYNC_TIME = "last_sync_time";
    public static final String PREF_SYNC_ENABLED = "sync_enabled";
    public static final String PREF_FIRST_RUN = "first_run";
    public static final String PREF_MAIN_INVESTOR_ID = "main_investor_id";
    public static final String PREF_SHEET_ID = "sheet_id";

    // Intent Extras
    public static final String EXTRA_CLIENT_ID = "client_id";
    public static final String EXTRA_LOAN_ID = "loan_id";
    public static final String EXTRA_INVESTOR_ID = "investor_id";
    public static final String EXTRA_MODE = "mode";
    public static final String EXTRA_FROM_NOTIFICATION = "from_notification";

    // Modes
    public static final String MODE_ADD = "add";
    public static final String MODE_EDIT = "edit";
    public static final String MODE_VIEW = "view";

    // Chart Colors
    public static final int CHART_COLOR_PAID = 0xFF4CAF50;
    public static final int CHART_COLOR_PARTIAL = 0xFF2196F3;
    public static final int CHART_COLOR_OVERDUE = 0xFFFF9800;
    public static final int CHART_COLOR_BLACKLISTED = 0xFFF44336;
    public static final int CHART_COLOR_OWING = 0xFF9E9E9E;

    // Notification Channels
    public static final String NOTIFICATION_CHANNEL_GENERAL = "general_channel";
    public static final String NOTIFICATION_CHANNEL_SYNC = "sync_channel";
    public static final String NOTIFICATION_CHANNEL_REMINDERS = "reminders_channel";

    // Work Tags
    public static final String WORK_TAG_SYNC = "sync";
    public static final String WORK_TAG_PERIODIC = "periodic";
    public static final String WORK_TAG_ONE_TIME = "one_time";

    // Limits
    public static final int MAX_SEARCH_RESULTS = 100;
    public static final int MAX_EXPORT_ROWS = 1000;
    public static final long MAX_SYNC_AGE_MILLIS = 30L * 24 * 60 * 60 * 1000; // 30 days

    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
}
