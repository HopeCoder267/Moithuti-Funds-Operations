package com.moithuti.funds.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Math Utility - Common mathematical operations and formatting
 * Provides consistent number formatting and calculations
 */
public class MathUtil {

    // Currency formatter
    private static final DecimalFormat CURRENCY_FORMATTER;
    private static final DecimalFormat PERCENTAGE_FORMATTER;
    
    static {
        CURRENCY_FORMATTER = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.getDefault()));
        PERCENTAGE_FORMATTER = new DecimalFormat("#,##0.0", new DecimalFormatSymbols(Locale.getDefault()));
    }

    /**
     * Format amount as currency
     * @param amount the amount to format
     * @return formatted currency string
     */
    public static String formatCurrency(double amount) {
        return CURRENCY_FORMATTER.format(amount);
    }

    /**
     * Format amount as currency with symbol
     * @param amount the amount to format
     * @param symbol currency symbol (e.g., "R", "$")
     * @return formatted currency string with symbol
     */
    public static String formatCurrency(double amount, String symbol) {
        return symbol + " " + formatCurrency(amount);
    }

    /**
     * Format percentage
     * @param value the value (0-100)
     * @return formatted percentage string
     */
    public static String formatPercentage(double value) {
        return PERCENTAGE_FORMATTER.format(value) + "%";
    }

    /**
     * Round to 2 decimal places
     * @param value the value to round
     * @return rounded value
     */
    public static double round(double value) {
        return round(value, 2);
    }

    /**
     * Round to specified decimal places
     * @param value the value to round
     * @param decimalPlaces number of decimal places
     * @return rounded value
     */
    public static double round(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

    /**
     * Check if two double values are approximately equal
     * @param a first value
     * @param b second value
     * @param epsilon tolerance
     * @return true if approximately equal
     */
    public static boolean approximatelyEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    /**
     * Check if two double values are approximately equal (default epsilon)
     * @param a first value
     * @param b second value
     * @return true if approximately equal
     */
    public static boolean approximatelyEqual(double a, double b) {
        return approximatelyEqual(a, b, 0.001);
    }

    /**
     * Clamp value between min and max
     * @param value the value to clamp
     * @param min minimum value
     * @param max maximum value
     * @return clamped value
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamp integer value between min and max
     * @param value the value to clamp
     * @param min minimum value
     * @param max maximum value
     * @return clamped value
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Calculate percentage
     * @param part the part value
     * @param total the total value
     * @return percentage (0-100)
     */
    public static double calculatePercentage(double part, double total) {
        if (total == 0) return 0.0;
        return (part / total) * 100;
    }

    /**
     * Calculate percentage change
     * @param oldValue old value
     * @param newValue new value
     * @return percentage change
     */
    public static double calculatePercentageChange(double oldValue, double newValue) {
        if (oldValue == 0) return 0.0;
        return ((newValue - oldValue) / oldValue) * 100;
    }

    /**
     * Calculate compound interest
     * @param principal principal amount
     * @param rate interest rate (as percentage)
     * @param time time period
     * @param compoundingFrequency compounding frequency per year
     * @return final amount
     */
    public static double calculateCompoundInterest(double principal, double rate, double time, int compoundingFrequency) {
        double r = rate / 100;
        double n = compoundingFrequency;
        return principal * Math.pow(1 + (r / n), n * time);
    }

    /**
     * Calculate simple interest
     * @param principal principal amount
     * @param rate interest rate (as percentage)
     * @param time time period
     * @return interest amount
     */
    public static double calculateSimpleInterest(double principal, double rate, double time) {
        return (principal * rate * time) / 100;
    }

    /**
     * Calculate loan payment (EMI)
     * @param principal loan amount
     * @param annualRate annual interest rate (as percentage)
     * @param months loan term in months
     * @return monthly payment amount
     */
    public static double calculateLoanPayment(double principal, double annualRate, int months) {
        if (annualRate == 0) {
            return principal / months;
        }
        
        double monthlyRate = annualRate / 12 / 100;
        double denominator = 1 - Math.pow(1 + monthlyRate, -months);
        return principal * monthlyRate / denominator;
    }

    /**
     * Check if value is positive
     * @param value the value to check
     * @return true if positive, false otherwise
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Check if value is negative
     * @param value the value to check
     * @return true if negative, false otherwise
     */
    public static boolean isNegative(double value) {
        return value < 0;
    }

    /**
     * Check if value is zero (approximately)
     * @param value the value to check
     * @return true if approximately zero
     */
    public static boolean isZero(double value) {
        return approximatelyEqual(value, 0);
    }

    /**
     * Get absolute value
     * @param value the value
     * @return absolute value
     */
    public static double absolute(double value) {
        return Math.abs(value);
    }

    /**
     * Get maximum of two values
     * @param a first value
     * @param b second value
     * @return maximum value
     */
    public static double max(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Get minimum of two values
     * @param a first value
     * @param b second value
     * @return minimum value
     */
    public static double min(double a, double b) {
        return Math.min(a, b);
    }

    /**
     * Check if value is within range (inclusive)
     * @param value the value to check
     * @param min minimum value
     * @param max maximum value
     * @return true if within range
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Format number with thousands separator
     * @param value the value to format
     * @return formatted number string
     */
    public static String formatNumber(double value) {
        DecimalFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault()));
        return formatter.format(value);
    }

    /**
     * Format number with specified decimal places
     * @param value the value to format
     * @param decimalPlaces number of decimal places
     * @return formatted number string
     */
    public static String formatNumber(double value, int decimalPlaces) {
        String pattern = "#,##0";
        if (decimalPlaces > 0) {
            pattern += "." + new String(new char[decimalPlaces]).replace("\0", "0");
        }
        DecimalFormat formatter = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.getDefault()));
        return formatter.format(value);
    }

    /**
     * Parse string to double safely
     * @param value the string to parse
     * @param defaultValue default value if parsing fails
     * @return parsed double or default value
     */
    public static double parseDouble(String value, double defaultValue) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return defaultValue;
            }
            return Double.parseDouble(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parse string to integer safely
     * @param value the string to parse
     * @param defaultValue default value if parsing fails
     * @return parsed integer or default value
     */
    public static int parseInt(String value, int defaultValue) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return defaultValue;
            }
            return Integer.parseInt(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Calculate sum of array
     * @param values array of values
     * @return sum of values
     */
    public static double sum(double[] values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum;
    }

    /**
     * Calculate average of array
     * @param values array of values
     * @return average of values
     */
    public static double average(double[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        return sum(values) / values.length;
    }

    /**
     * Calculate median of array
     * @param values array of values
     * @return median of values
     */
    public static double median(double[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        
        // Sort array (simple implementation)
        double[] sorted = values.clone();
        java.util.Arrays.sort(sorted);
        
        int middle = sorted.length / 2;
        if (sorted.length % 2 == 1) {
            return sorted[middle];
        } else {
            return (sorted[middle - 1] + sorted[middle]) / 2.0;
        }
    }
}
