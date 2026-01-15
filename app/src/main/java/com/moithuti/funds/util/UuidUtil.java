package com.moithuti.funds.util;

import java.util.UUID;

/**
 * UUID Utility - UUID generation and validation
 * Provides consistent UUID handling throughout the application
 */
public class UuidUtil {

    /**
     * Generate a new random UUID
     * @return new UUID string
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a new UUID with specific prefix
     * @param prefix the prefix to add
     * @return UUID string with prefix
     */
    public static String generateUuid(String prefix) {
        return prefix + "_" + generateUuid();
    }

    /**
     * Validate UUID format
     * @param uuid the UUID string to validate
     * @return true if valid UUID format, false otherwise
     */
    public static boolean isValidUuid(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            return false;
        }
        
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Validate UUID format with prefix
     * @param uuid the UUID string to validate
     * @param expectedPrefix the expected prefix
     * @return true if valid UUID format with expected prefix
     */
    public static boolean isValidUuid(String uuid, String expectedPrefix) {
        if (!isValidUuid(uuid)) {
            return false;
        }
        
        return uuid.startsWith(expectedPrefix + "_");
    }

    /**
     * Extract UUID without prefix
     * @param uuid the UUID string with prefix
     * @return UUID without prefix, or original if no prefix found
     */
    public static String extractUuid(String uuid) {
        if (uuid == null) {
            return null;
        }
        
        int underscoreIndex = uuid.indexOf('_');
        if (underscoreIndex != -1 && underscoreIndex < uuid.length() - 1) {
            return uuid.substring(underscoreIndex + 1);
        }
        
        return uuid;
    }

    /**
     * Extract prefix from UUID
     * @param uuid the UUID string with prefix
     * @return prefix part, or empty string if no prefix found
     */
    public static String extractPrefix(String uuid) {
        if (uuid == null) {
            return "";
        }
        
        int underscoreIndex = uuid.indexOf('_');
        if (underscoreIndex != -1) {
            return uuid.substring(0, underscoreIndex);
        }
        
        return "";
    }

    /**
     * Generate client UUID
     * @return client UUID string
     */
    public static String generateClientUuid() {
        return generateUuid("client");
    }

    /**
     * Generate loan UUID
     * @return loan UUID string
     */
    public static String generateLoanUuid() {
        return generateUuid("loan");
    }

    /**
     * Generate payment UUID
     * @return payment UUID string
     */
    public static String generatePaymentUuid() {
        return generateUuid("payment");
    }

    /**
     * Generate investor UUID
     * @return investor UUID string
     */
    public static String generateInvestorUuid() {
        return generateUuid("investor");
    }

    /**
     * Generate investor transaction UUID
     * @return investor transaction UUID string
     */
    public static String generateInvestorTransactionUuid() {
        return generateUuid("transaction");
    }

    /**
     * Generate loan funding UUID
     * @return loan funding UUID string
     */
    public static String generateLoanFundingUuid() {
        return generateUuid("funding");
    }

    /**
     * Check if UUID is a client UUID
     * @param uuid the UUID to check
     * @return true if client UUID
     */
    public static boolean isClientUuid(String uuid) {
        return isValidUuid(uuid, "client");
    }

    /**
     * Check if UUID is a loan UUID
     * @param uuid the UUID to check
     * @return true if loan UUID
     */
    public static boolean isLoanUuid(String uuid) {
        return isValidUuid(uuid, "loan");
    }

    /**
     * Check if UUID is a payment UUID
     * @param uuid the UUID to check
     * @return true if payment UUID
     */
    public static boolean isPaymentUuid(String uuid) {
        return isValidUuid(uuid, "payment");
    }

    /**
     * Check if UUID is an investor UUID
     * @param uuid the UUID to check
     * @return true if investor UUID
     */
    public static boolean isInvestorUuid(String uuid) {
        return isValidUuid(uuid, "investor");
    }

    /**
     * Check if UUID is an investor transaction UUID
     * @param uuid the UUID to check
     * @return true if investor transaction UUID
     */
    public static boolean isInvestorTransactionUuid(String uuid) {
        return isValidUuid(uuid, "transaction");
    }

    /**
     * Check if UUID is a loan funding UUID
     * @param uuid the UUID to check
     * @return true if loan funding UUID
     */
    public static boolean isLoanFundingUuid(String uuid) {
        return isValidUuid(uuid, "funding");
    }

    /**
     * Get UUID type based on prefix
     * @param uuid the UUID string
     * @return UUID type or "unknown" if not recognized
     */
    public static String getUuidType(String uuid) {
        String prefix = extractPrefix(uuid);
        
        switch (prefix) {
            case "client":
                return "client";
            case "loan":
                return "loan";
            case "payment":
                return "payment";
            case "investor":
                return "investor";
            case "transaction":
                return "investor_transaction";
            case "funding":
                return "loan_funding";
            default:
                return "unknown";
        }
    }

    /**
     * Generate a short UUID (first 8 characters)
     * @param uuid the full UUID
     * @return short UUID
     */
    public static String getShortUuid(String uuid) {
        if (uuid == null || uuid.length() < 8) {
            return uuid;
        }
        
        // Remove prefix if present
        String cleanUuid = extractUuid(uuid);
        return cleanUuid.substring(0, 8);
    }

    /**
     * Generate a display-friendly UUID (prefix + short UUID)
     * @param uuid the full UUID
     * @return display-friendly UUID
     */
    public static String getDisplayUuid(String uuid) {
        if (uuid == null) {
            return "";
        }
        
        String prefix = extractPrefix(uuid);
        String shortUuid = getShortUuid(uuid);
        
        if (!prefix.isEmpty()) {
            return prefix + "_" + shortUuid;
        } else {
            return shortUuid;
        }
    }

    /**
     * Compare two UUID strings safely
     * @param uuid1 first UUID
     * @param uuid2 second UUID
     * @return true if equal (case-insensitive)
     */
    public static boolean equals(String uuid1, String uuid2) {
        if (uuid1 == null || uuid2 == null) {
            return uuid1 == uuid2;
        }
        return uuid1.equalsIgnoreCase(uuid2);
    }

    /**
     * Check if UUID string is empty or null
     * @param uuid the UUID to check
     * @return true if empty or null
     */
    public static boolean isEmpty(String uuid) {
        return uuid == null || uuid.trim().isEmpty();
    }

    /**
     * Ensure UUID is not empty, generate if needed
     * @param uuid the UUID to check
     * @return provided UUID or new one if empty
     */
    public static String ensureUuid(String uuid) {
        return isEmpty(uuid) ? generateUuid() : uuid;
    }

    /**
     * Generate a profit tracker UUID
     * @return profit tracker UUID string
     */
    public static String generateProfitTrackerUuid() {
        return generateUuid("profit_tracker");
    }
}
