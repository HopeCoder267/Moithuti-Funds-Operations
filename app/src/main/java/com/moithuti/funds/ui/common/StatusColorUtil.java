package com.moithuti.funds.ui.common;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

import com.moithuti.funds.R;
import com.moithuti.funds.util.Constants;

/**
 * Status Color Utility - Provides color mapping for different statuses
 * Handles client and loan status color coding throughout the app
 */
public class StatusColorUtil {

    /**
     * Get color resource for client status
     * @param context application context
     * @param status the client status
     * @return color resource ID
     */
    public static int getClientStatusColor(Context context, String status) {
        if (status == null) {
            return R.color.status_owing;
        }

        switch (status) {
            case Constants.CLIENT_STATUS_PAID:
                return R.color.status_paid;
            case Constants.CLIENT_STATUS_PARTIAL:
                return R.color.status_partial;
            case Constants.CLIENT_STATUS_OVERDUE:
                return R.color.status_overdue;
            case Constants.CLIENT_STATUS_BLACKLISTED:
                return R.color.status_blacklisted;
            case Constants.CLIENT_STATUS_OWING:
            default:
                return R.color.status_owing;
        }
    }

    /**
     * Get color resource for loan status
     * @param context application context
     * @param status the loan status
     * @return color resource ID
     */
    public static int getLoanStatusColor(Context context, String status) {
        if (status == null) {
            return R.color.status_owing;
        }

        switch (status) {
            case Constants.LOAN_STATUS_PAID:
                return R.color.status_paid;
            case Constants.LOAN_STATUS_PARTIAL:
                return R.color.status_partial;
            case Constants.LOAN_STATUS_OVERDUE:
                return R.color.status_overdue;
            case Constants.LOAN_STATUS_BLACKLISTED:
                return R.color.status_blacklisted;
            case Constants.LOAN_STATUS_OWING:
            default:
                return R.color.status_owing;
        }
    }

    /**
     * Get actual color integer for client status
     * @param context application context
     * @param status the client status
     * @return color integer
     */
    public static int getClientStatusColorInt(Context context, String status) {
        return ContextCompat.getColor(context, getClientStatusColor(context, status));
    }

    /**
     * Get actual color integer for loan status
     * @param context application context
     * @param status the loan status
     * @return color integer
     */
    public static int getLoanStatusColorInt(Context context, String status) {
        return ContextCompat.getColor(context, getLoanStatusColor(context, status));
    }

    /**
     * Get drawable resource for status indicator
     * @param context application context
     * @param status the status
     * @return drawable resource ID
     */
    public static int getStatusIndicatorDrawable(Context context, String status) {
        if (status == null) {
            return R.drawable.status_circle;
        }

        switch (status) {
            case Constants.CLIENT_STATUS_PAID:
                return R.drawable.status_paid;
            case Constants.CLIENT_STATUS_PARTIAL:
                return R.drawable.status_partial;
            case Constants.CLIENT_STATUS_OVERDUE:
                return R.drawable.status_overdue;
            case Constants.CLIENT_STATUS_BLACKLISTED:
                return R.drawable.status_blacklisted;
            case Constants.CLIENT_STATUS_OWING:
            default:
                return R.drawable.status_circle;
        }
    }

    /**
     * Get status display name
     * @param status the status
     * @return display-friendly name
     */
    public static String getStatusDisplayName(String status) {
        if (status == null) {
            return "Unknown";
        }

        switch (status) {
            case Constants.CLIENT_STATUS_PAID:
                return "Paid";
            case Constants.CLIENT_STATUS_PARTIAL:
                return "Partial";
            case Constants.CLIENT_STATUS_OVERDUE:
                return "Overdue";
            case Constants.CLIENT_STATUS_BLACKLISTED:
                return "Blacklisted";
            case Constants.CLIENT_STATUS_OWING:
            default:
                return "Owing";
        }
    }

    /**
     * Get status description
     * @param status the status
     * @return status description
     */
    public static String getStatusDescription(String status) {
        if (status == null) {
            return "Status not specified";
        }

        switch (status) {
            case Constants.CLIENT_STATUS_PAID:
                return "All payments completed";
            case Constants.CLIENT_STATUS_PARTIAL:
                return "Some payments made";
            case Constants.CLIENT_STATUS_OVERDUE:
                return "Payment deadline passed";
            case Constants.CLIENT_STATUS_BLACKLISTED:
                return "Client blacklisted";
            case Constants.CLIENT_STATUS_OWING:
            default:
                return "No payments made yet";
        }
    }

    /**
     * Check if status indicates problem (overdue or blacklisted)
     * @param status the status
     * @return true if status indicates a problem
     */
    public static boolean isProblemStatus(String status) {
        return Constants.CLIENT_STATUS_OVERDUE.equals(status) ||
               Constants.LOAN_STATUS_OVERDUE.equals(status) ||
               Constants.CLIENT_STATUS_BLACKLISTED.equals(status) ||
               Constants.LOAN_STATUS_BLACKLISTED.equals(status);
    }

    /**
     * Check if status indicates completion (paid)
     * @param status the status
     * @return true if status indicates completion
     */
    public static boolean isCompletedStatus(String status) {
        return Constants.CLIENT_STATUS_PAID.equals(status) ||
               Constants.LOAN_STATUS_PAID.equals(status);
    }

    /**
     * Check if status indicates activity (partial)
     * @param status the status
     * @return true if status indicates activity
     */
    public static boolean isActiveStatus(String status) {
        return Constants.CLIENT_STATUS_PARTIAL.equals(status) ||
               Constants.LOAN_STATUS_PARTIAL.equals(status);
    }

    /**
     * Check if status indicates inactivity (owing)
     * @param status the status
     * @return true if status indicates inactivity
     */
    public static boolean isInactiveStatus(String status) {
        return Constants.CLIENT_STATUS_OWING.equals(status) ||
               Constants.LOAN_STATUS_OWING.equals(status);
    }

    /**
     * Get status priority for sorting
     * @param status the status
     * @return priority value (lower = higher priority)
     */
    public static int getStatusPriority(String status) {
        if (status == null) {
            return 999;
        }

        switch (status) {
            case Constants.CLIENT_STATUS_BLACKLISTED:
                return 1; // Highest priority
            case Constants.CLIENT_STATUS_OVERDUE:
                return 2;
            case Constants.CLIENT_STATUS_PARTIAL:
                return 3;
            case Constants.CLIENT_STATUS_OWING:
                return 4;
            case Constants.CLIENT_STATUS_PAID:
                return 5; // Lowest priority
            default:
                return 999;
        }
    }

    /**
     * Get chart color for status
     * @param status the status
     * @return chart color integer
     */
    public static int getChartColor(String status) {
        if (status == null) {
            return Color.GRAY;
        }

        switch (status) {
            case Constants.CLIENT_STATUS_PAID:
                return Color.parseColor("#4CAF50"); // Green
            case Constants.CLIENT_STATUS_PARTIAL:
                return Color.parseColor("#2196F3"); // Blue
            case Constants.CLIENT_STATUS_OVERDUE:
                return Color.parseColor("#FF9800"); // Orange
            case Constants.CLIENT_STATUS_BLACKLISTED:
                return Color.parseColor("#F44336"); // Red
            case Constants.CLIENT_STATUS_OWING:
                return Color.parseColor("#9E9E9E"); // Gray
            default:
                return Color.GRAY;
        }
    }

    /**
     * Get all available statuses
     * @return array of status strings
     */
    public static String[] getAllStatuses() {
        return new String[]{
            Constants.CLIENT_STATUS_PAID,
            Constants.CLIENT_STATUS_PARTIAL,
            Constants.CLIENT_STATUS_OVERDUE,
            Constants.CLIENT_STATUS_BLACKLISTED,
            Constants.CLIENT_STATUS_OWING
        };
    }

    /**
     * Get status display names array
     * @return array of display names
     */
    public static String[] getAllStatusDisplayNames() {
        String[] statuses = getAllStatuses();
        String[] displayNames = new String[statuses.length];
        
        for (int i = 0; i < statuses.length; i++) {
            displayNames[i] = getStatusDisplayName(statuses[i]);
        }
        
        return displayNames;
    }

    /**
     * Validate status string
     * @param status the status to validate
     * @return true if valid status
     */
    public static boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }

        for (String validStatus : getAllStatuses()) {
            if (validStatus.equals(status)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Create status summary text
     * @param status the status
     * @param count the count of items with this status
     * @return formatted summary text
     */
    public static String createStatusSummary(String status, int count) {
        String displayName = getStatusDisplayName(status);
        return displayName + ": " + count;
    }

    /**
     * Get status color with transparency for backgrounds
     * @param context application context
     * @param status the status
     * @param alpha transparency value (0-255)
     * @return color with transparency
     */
    public static int getClientStatusColorWithAlpha(Context context, String status, int alpha) {
        int baseColor = getClientStatusColorInt(context, status);
        return Color.argb(alpha, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor));
    }

    /**
     * Get status color with transparency for backgrounds
     * @param context application context
     * @param status the status
     * @param alpha transparency value (0-255)
     * @return color with transparency
     */
    public static int getLoanStatusColorWithAlpha(Context context, String status, int alpha) {
        int baseColor = getLoanStatusColorInt(context, status);
        return Color.argb(alpha, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor));
    }
}
