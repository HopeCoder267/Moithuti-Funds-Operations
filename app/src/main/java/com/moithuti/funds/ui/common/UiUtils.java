package com.moithuti.funds.ui.common;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moithuti.funds.R;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.DateUtil;
import com.moithuti.funds.util.MathUtil;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * UI Utils - Common UI utility functions
 * Provides validation, formatting, and UI helper methods
 */
public class UiUtils {

    /**
     * Show toast message
     * @param context application context
     * @param message the message to show
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show long toast message
     * @param context application context
     * @param message the message to show
     */
    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Show snackbar message
     * @param view the view to attach snackbar to
     * @param message the message to show
     */
    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Show long snackbar message
     * @param view the view to attach snackbar to
     * @param message the message to show
     */
    public static void showLongSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Format currency amount
     * @param amount the amount to format
     * @return formatted currency string
     */
    public static String formatCurrency(double amount) {
        return MathUtil.formatCurrency(amount, Constants.CURRENCY_SYMBOL);
    }

    /**
     * Format currency amount without symbol
     * @param amount the amount to format
     * @return formatted amount string
     */
    public static String formatAmount(double amount) {
        return MathUtil.formatCurrency(amount);
    }

    /**
     * Format percentage
     * @param value the percentage value (0-100)
     * @return formatted percentage string
     */
    public static String formatPercentage(double value) {
        return MathUtil.formatPercentage(value);
    }

    /**
     * Format date
     * @param timestamp the timestamp in milliseconds
     * @return formatted date string
     */
    public static String formatDate(long timestamp) {
        return DateUtil.formatDate(timestamp);
    }

    /**
     * Format date and time
     * @param timestamp the timestamp in milliseconds
     * @return formatted datetime string
     */
    public static String formatDateTime(long timestamp) {
        return DateUtil.formatDateTime(timestamp);
    }

    /**
     * Format relative time
     * @param timestamp the timestamp in milliseconds
     * @return relative time string
     */
    public static String formatRelativeTime(long timestamp) {
        return DateUtil.getRelativeTime(timestamp);
    }

    /**
     * Validate text input field
     * @param textInputLayout the text input layout
     * @param editText the edit text
     * @param required whether field is required
     * @param maxLength maximum allowed length
     * @return true if valid
     */
    public static boolean validateTextInput(TextInputLayout textInputLayout, TextInputEditText editText, boolean required, int maxLength) {
        String text = editText.getText() != null ? editText.getText().toString().trim() : "";
        
        // Check if required and empty
        if (required && TextUtils.isEmpty(text)) {
            textInputLayout.setError("This field is required");
            return false;
        }
        
        // Check max length
        if (!TextUtils.isEmpty(text) && maxLength > 0 && text.length() > maxLength) {
            textInputLayout.setError("Maximum " + maxLength + " characters allowed");
            return false;
        }
        
        // Clear error if valid
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    /**
     * Validate phone number
     * @param textInputLayout the text input layout
     * @param editText the edit text
     * @param required whether field is required
     * @return true if valid
     */
    public static boolean validatePhone(TextInputLayout textInputLayout, TextInputEditText editText, boolean required) {
        String phone = editText.getText() != null ? editText.getText().toString().trim() : "";
        
        // Check if required and empty
        if (required && TextUtils.isEmpty(phone)) {
            textInputLayout.setError("Phone number is required");
            return false;
        }
        
        // Validate phone format if not empty
        if (!TextUtils.isEmpty(phone)) {
            // Basic phone validation - should contain only digits and optional +, -, (, )
            if (!phone.matches("^[+]?[0-9\\-\\(\\)\\s]+$")) {
                textInputLayout.setError("Invalid phone number format");
                return false;
            }
            
            // Check minimum length
            if (phone.replaceAll("[^0-9]", "").length() < Constants.MIN_PHONE_LENGTH) {
                textInputLayout.setError("Phone number too short");
                return false;
            }
            
            // Check maximum length
            if (phone.length() > Constants.MAX_PHONE_LENGTH) {
                textInputLayout.setError("Phone number too long");
                return false;
            }
        }
        
        // Clear error if valid
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    /**
     * Validate amount field
     * @param textInputLayout the text input layout
     * @param editText the edit text
     * @param required whether field is required
     * @param minAmount minimum allowed amount
     * @param maxAmount maximum allowed amount
     * @return true if valid
     */
    public static boolean validateAmount(TextInputLayout textInputLayout, TextInputEditText editText, boolean required, double minAmount, double maxAmount) {
        String amountText = editText.getText() != null ? editText.getText().toString().trim() : "";
        
        // Check if required and empty
        if (required && TextUtils.isEmpty(amountText)) {
            textInputLayout.setError("Amount is required");
            return false;
        }
        
        // Validate amount if not empty
        if (!TextUtils.isEmpty(amountText)) {
            double amount = MathUtil.parseDouble(amountText, -1);
            
            if (amount < 0) {
                textInputLayout.setError("Invalid amount");
                return false;
            }
            
            if (amount < minAmount) {
                textInputLayout.setError("Minimum amount is " + formatCurrency(minAmount));
                return false;
            }
            
            if (amount > maxAmount) {
                textInputLayout.setError("Maximum amount is " + formatCurrency(maxAmount));
                return false;
            }
        }
        
        // Clear error if valid
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    /**
     * Clear all text input fields
     * @param editTexts array of edit texts to clear
     */
    public static void clearTextInputs(TextInputEditText... editTexts) {
        for (TextInputEditText editText : editTexts) {
            if (editText != null) {
                editText.setText("");
            }
        }
    }

    /**
     * Clear all text input layouts errors
     * @param textInputLayouts array of text input layouts to clear
     */
    public static void clearTextInputLayoutErrors(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            if (textInputLayout != null) {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    /**
     * Set text on text view safely
     * @param textView the text view
     * @param text the text to set
     */
    public static void setTextSafely(TextView textView, String text) {
        if (textView != null) {
            textView.setText(text != null ? text : "");
        }
    }

    /**
     * Set text on text view safely with default
     * @param textView the text view
     * @param text the text to set
     * @param defaultText default text if text is null or empty
     */
    public static void setTextSafely(TextView textView, String text, String defaultText) {
        if (textView != null) {
            String displayText = (text != null && !text.trim().isEmpty()) ? text : defaultText;
            textView.setText(displayText);
        }
    }

    /**
     * Get text from edit text safely
     * @param editText the edit text
     * @return text string or empty string
     */
    public static String getTextSafely(TextInputEditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }

    /**
     * Get double value from edit text safely
     * @param editText the edit text
     * @param defaultValue default value if parsing fails
     * @return double value
     */
    public static double getDoubleSafely(TextInputEditText editText, double defaultValue) {
        String text = getTextSafely(editText);
        return MathUtil.parseDouble(text, defaultValue);
    }

    /**
     * Hide keyboard from view
     * @param view the view
     */
    public static void hideKeyboard(View view) {
        android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) 
            view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Show keyboard for edit text
     * @param editText the edit text
     */
    public static void showKeyboard(TextInputEditText editText) {
        if (editText != null) {
            editText.requestFocus();
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) 
                editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Set view visibility
     * @param view the view
     * @param visible true to show, false to hide
     */
    public static void setViewVisibility(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Set view enabled state
     * @param view the view
     * @param enabled true to enable, false to disable
     */
    public static void setViewEnabled(View view, boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
            view.setAlpha(enabled ? 1.0f : 0.5f);
        }
    }

    /**
     * Check if fragment is attached
     * @param fragment the fragment
     * @return true if fragment is attached
     */
    public static boolean isFragmentAttached(Fragment fragment) {
        return fragment != null && fragment.isAdded() && fragment.getContext() != null;
    }

    /**
     * Check if activity is finished
     * @param activity the activity
     * @return true if activity is not finished
     */
    public static boolean isActivityActive(FragmentActivity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    /**
     * Get safe context from fragment
     * @param fragment the fragment
     * @return context or null
     */
    public static Context getSafeContext(Fragment fragment) {
        return isFragmentAttached(fragment) ? fragment.getContext() : null;
    }

    /**
     * Get safe context from activity
     * @param activity the activity
     * @return context or null
     */
    public static Context getSafeContext(FragmentActivity activity) {
        return isActivityActive(activity) ? activity : null;
    }

    /**
     * Create confirmation dialog
     * @param context the context
     * @param title dialog title
     * @param message dialog message
     * @param onConfirm callback for confirmation
     */
    public static void showConfirmationDialog(Context context, String title, String message, Runnable onConfirm) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Create info dialog
     * @param context the context
     * @param title dialog title
     * @param message dialog message
     */
    public static void showInfoDialog(Context context, String title, String message) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Create error dialog
     * @param context the context
     * @param message error message
     */
    public static void showErrorDialog(Context context, String message) {
        showInfoDialog(context, "Error", message);
    }

    /**
     * Create success dialog
     * @param context the context
     * @param message success message
     */
    public static void showSuccessDialog(Context context, String message) {
        showInfoDialog(context, "Success", message);
    }

    /**
     * Debounce runnable execution
     * @param runnable the runnable to execute
     * @param delayMillis delay in milliseconds
     */
    public static void debounce(Runnable runnable, long delayMillis) {
        android.os.Handler handler = new android.os.Handler(android.os.Looper.getMainLooper());
        handler.postDelayed(runnable, delayMillis);
    }

    /**
     * Run on UI thread
     * @param runnable the runnable to execute
     */
    public static void runOnUiThread(Runnable runnable) {
        new android.os.Handler(android.os.Looper.getMainLooper()).post(runnable);
    }

    /**
     * Run on UI thread with delay
     * @param runnable the runnable to execute
     * @param delayMillis delay in milliseconds
     */
    public static void runOnUiThread(Runnable runnable, long delayMillis) {
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(runnable, delayMillis);
    }
}
