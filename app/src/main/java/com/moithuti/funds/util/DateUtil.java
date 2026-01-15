package com.moithuti.funds.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date Utility - Common date operations and formatting
 * Provides consistent date handling throughout the application
 */
public class DateUtil {

    // Date formats
    public static final String FORMAT_DATE = "dd MMM yyyy";
    public static final String FORMAT_DATETIME = "dd MMM yyyy HH:mm";
    public static final String FORMAT_SHORT_DATE = "dd/MM/yyyy";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_YEAR_MONTH = "yyyy-MM";
    public static final String FORMAT_MONTH_YEAR = "MMM yyyy";
    public static final String FORMAT_FULL = "EEEE, dd MMMM yyyy";

    /**
     * Format timestamp to readable date string
     * @param timestamp the timestamp in milliseconds
     * @param format the date format pattern
     * @return formatted date string
     */
    public static String formatDate(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    /**
     * Format timestamp to default date format
     * @param timestamp the timestamp in milliseconds
     * @return formatted date string
     */
    public static String formatDate(long timestamp) {
        return formatDate(timestamp, FORMAT_DATE);
    }

    /**
     * Format timestamp to date and time
     * @param timestamp the timestamp in milliseconds
     * @return formatted datetime string
     */
    public static String formatDateTime(long timestamp) {
        return formatDate(timestamp, FORMAT_DATETIME);
    }

    /**
     * Format timestamp to short date format
     * @param timestamp the timestamp in milliseconds
     * @return formatted short date string
     */
    public static String formatShortDate(long timestamp) {
        return formatDate(timestamp, FORMAT_SHORT_DATE);
    }

    /**
     * Format timestamp to time only
     * @param timestamp the timestamp in milliseconds
     * @return formatted time string
     */
    public static String formatTime(long timestamp) {
        return formatDate(timestamp, FORMAT_TIME);
    }

    /**
     * Format timestamp to year-month format (for grouping)
     * @param timestamp the timestamp in milliseconds
     * @return formatted year-month string
     */
    public static String formatYearMonth(long timestamp) {
        return formatDate(timestamp, FORMAT_YEAR_MONTH);
    }

    /**
     * Format timestamp to month-year format (display)
     * @param timestamp the timestamp in milliseconds
     * @return formatted month-year string
     */
    public static String formatMonthYear(long timestamp) {
        return formatDate(timestamp, FORMAT_MONTH_YEAR);
    }

    /**
     * Get current timestamp
     * @return current time in milliseconds
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Get start of day timestamp
     * @param timestamp the timestamp
     * @return start of day timestamp
     */
    public static long getStartOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Get end of day timestamp
     * @param timestamp the timestamp
     * @return end of day timestamp
     */
    public static long getEndOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * Get start of month timestamp
     * @param timestamp the timestamp
     * @return start of month timestamp
     */
    public static long getStartOfMonth(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Get end of month timestamp
     * @param timestamp the timestamp
     * @return end of month timestamp
     */
    public static long getEndOfMonth(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * Add days to timestamp
     * @param timestamp the base timestamp
     * @param days number of days to add (negative to subtract)
     * @return new timestamp
     */
    public static long addDays(long timestamp, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTimeInMillis();
    }

    /**
     * Add months to timestamp
     * @param timestamp the base timestamp
     * @param months number of months to add (negative to subtract)
     * @return new timestamp
     */
    public static long addMonths(long timestamp, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTimeInMillis();
    }

    /**
     * Get days between two timestamps
     * @param startTimestamp start timestamp
     * @param endTimestamp end timestamp
     * @return number of days between (can be negative)
     */
    public static long getDaysBetween(long startTimestamp, long endTimestamp) {
        long diff = endTimestamp - startTimestamp;
        return diff / (24 * 60 * 60 * 1000);
    }

    /**
     * Check if timestamp is today
     * @param timestamp the timestamp to check
     * @return true if today, false otherwise
     */
    public static boolean isToday(long timestamp) {
        Calendar today = Calendar.getInstance();
        Calendar check = Calendar.getInstance();
        check.setTimeInMillis(timestamp);
        
        return today.get(Calendar.YEAR) == check.get(Calendar.YEAR) &&
               today.get(Calendar.DAY_OF_YEAR) == check.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Check if timestamp is yesterday
     * @param timestamp the timestamp to check
     * @return true if yesterday, false otherwise
     */
    public static boolean isYesterday(long timestamp) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        Calendar check = Calendar.getInstance();
        check.setTimeInMillis(timestamp);
        
        return yesterday.get(Calendar.YEAR) == check.get(Calendar.YEAR) &&
               yesterday.get(Calendar.DAY_OF_YEAR) == check.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Check if timestamp is in the past
     * @param timestamp the timestamp to check
     * @return true if in the past, false otherwise
     */
    public static boolean isPast(long timestamp) {
        return timestamp < System.currentTimeMillis();
    }

    /**
     * Check if timestamp is in the future
     * @param timestamp the timestamp to check
     * @return true if in the future, false otherwise
     */
    public static boolean isFuture(long timestamp) {
        return timestamp > System.currentTimeMillis();
    }

    /**
     * Get relative time description (e.g., "2 days ago", "in 3 days")
     * @param timestamp the timestamp
     * @return relative time description
     */
    public static String getRelativeTime(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = timestamp - now;
        long days = Math.abs(diff) / (24 * 60 * 60 * 1000);
        
        if (days == 0) {
            if (isToday(timestamp)) {
                return "Today";
            } else if (diff > 0) {
                return "Tomorrow";
            } else {
                return "Yesterday";
            }
        } else if (days == 1) {
            if (diff > 0) {
                return "Tomorrow";
            } else {
                return "Yesterday";
            }
        } else if (days < 7) {
            if (diff > 0) {
                return "In " + days + " days";
            } else {
                return days + " days ago";
            }
        } else if (days < 30) {
            long weeks = days / 7;
            if (diff > 0) {
                return "In " + weeks + " week" + (weeks > 1 ? "s" : "");
            } else {
                return weeks + " week" + (weeks > 1 ? "s" : "") + " ago";
            }
        } else {
            long months = days / 30;
            if (diff > 0) {
                return "In " + months + " month" + (months > 1 ? "s" : "");
            } else {
                return months + " month" + (months > 1 ? "s" : "") + " ago";
            }
        }
    }

    /**
     * Parse date string to timestamp
     * @param dateString the date string
     * @param format the date format
     * @return timestamp in milliseconds, or 0 if parsing fails
     */
    public static long parseDate(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            Date date = sdf.parse(dateString);
            return date != null ? date.getTime() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get age of timestamp in human readable format
     * @param timestamp the timestamp
     * @return age description (e.g., "2 hours ago", "3 days ago")
     */
    public static String getAge(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        
        if (diff < 60 * 1000) {
            long seconds = diff / 1000;
            return seconds + " second" + (seconds != 1 ? "s" : "") + " ago";
        } else if (diff < 60 * 60 * 1000) {
            long minutes = diff / (60 * 1000);
            return minutes + " minute" + (minutes != 1 ? "s" : "") + " ago";
        } else if (diff < 24 * 60 * 60 * 1000) {
            long hours = diff / (60 * 60 * 1000);
            return hours + " hour" + (hours != 1 ? "s" : "") + " ago";
        } else {
            return getRelativeTime(timestamp);
        }
    }
}
