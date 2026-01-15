package com.moithuti.funds.data.local;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Room Type Converters
 * Handles conversion of complex types for Room database storage
 */
public class Converters {

    @TypeConverter
    public static Long fromTimestamp(Date value) {
        return value == null ? null : value.getTime();
    }

    @TypeConverter
    public static Date dateFromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    // Note: We're using long timestamps directly in entities, so we don't need
    // Date converters for most fields. This is kept for potential future use.
}
