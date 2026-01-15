package com.moithuti.funds.util;

import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;

import java.util.List;

/**
 * Loan Status Calculator - Implements the loan status algorithm
 * Calculates loan status based on payments, due dates, and client status
 * Status algorithm is fixed and non-negotiable per requirements
 */
public class LoanStatusCalculator {

    // Status constants
    public static final String STATUS_BLACKLISTED = "BLACKLISTED";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_OVERDUE = "OVERDUE";
    public static final String STATUS_PARTIAL = "PARTIAL";
    public static final String STATUS_OWING = "OWING";

    /**
     * Calculate loan status based on the fixed algorithm
     * 
     * ALGORITHM:
     * IF client.status == BLACKLISTED → BLACKLISTED
     * ELSE IF totalPaid >= loanAmount → PAID
     * ELSE IF today > dueDate → OVERDUE
     * ELSE IF totalPaid > 0 → PARTIAL
     * ELSE → OWING
     * 
     * @param loan the loan entity
     * @param client the client entity
     * @param payments list of payments for the loan
     * @return calculated loan status
     */
    public static String calculateLoanStatus(LoanEntity loan, ClientEntity client, List<PaymentEntity> payments) {
        long currentTime = System.currentTimeMillis();
        
        // Rule 1: If client is blacklisted, loan is blacklisted
        if (STATUS_BLACKLISTED.equals(client.getStatus())) {
            return STATUS_BLACKLISTED;
        }

        // Calculate total paid amount
        double totalPaid = 0.0;
        if (payments != null) {
            for (PaymentEntity payment : payments) {
                if (!payment.isDeleted()) {
                    totalPaid += payment.getAmount();
                }
            }
        }

        // Rule 2: If fully paid, status is PAID
        if (totalPaid >= loan.getAmount()) {
            return STATUS_PAID;
        }

        // Rule 3: If overdue and not paid, status is OVERDUE
        if (currentTime > loan.getDueDate()) {
            return STATUS_OVERDUE;
        }

        // Rule 4: If partially paid, status is PARTIAL
        if (totalPaid > 0) {
            return STATUS_PARTIAL;
        }

        // Rule 5: Default to OWING
        return STATUS_OWING;
    }

    /**
     * Calculate loan status without client info (for cases where client is not available)
     * This will not check for blacklisted status
     * 
     * @param loan the loan entity
     * @param payments list of payments for the loan
     * @return calculated loan status (excluding BLACKLISTED)
     */
    public static String calculateLoanStatusWithoutClient(LoanEntity loan, List<PaymentEntity> payments) {
        long currentTime = System.currentTimeMillis();

        // Calculate total paid amount
        double totalPaid = 0.0;
        if (payments != null) {
            for (PaymentEntity payment : payments) {
                if (!payment.isDeleted()) {
                    totalPaid += payment.getAmount();
                }
            }
        }

        // If fully paid, status is PAID
        if (totalPaid >= loan.getAmount()) {
            return STATUS_PAID;
        }

        // If overdue and not paid, status is OVERDUE
        if (currentTime > loan.getDueDate()) {
            return STATUS_OVERDUE;
        }

        // If partially paid, status is PARTIAL
        if (totalPaid > 0) {
            return STATUS_PARTIAL;
        }

        // Default to OWING
        return STATUS_OWING;
    }

    /**
     * Get total paid amount for a loan
     * @param payments list of payments
     * @return total paid amount
     */
    public static double getTotalPaid(List<PaymentEntity> payments) {
        double totalPaid = 0.0;
        if (payments != null) {
            for (PaymentEntity payment : payments) {
                if (!payment.isDeleted()) {
                    totalPaid += payment.getAmount();
                }
            }
        }
        return totalPaid;
    }

    /**
     * Get remaining amount for a loan
     * @param loan the loan entity
     * @param payments list of payments
     * @return remaining amount (0 if fully paid)
     */
    public static double getRemainingAmount(LoanEntity loan, List<PaymentEntity> payments) {
        double totalPaid = getTotalPaid(payments);
        double remaining = loan.getAmount() - totalPaid;
        return Math.max(0, remaining);
    }

    /**
     * Check if a loan is overdue
     * @param loan the loan entity
     * @return true if overdue, false otherwise
     */
    public static boolean isOverdue(LoanEntity loan) {
        return System.currentTimeMillis() > loan.getDueDate();
    }

    /**
     * Get days until due date
     * @param loan the loan entity
     * @return days until due (negative if overdue)
     */
    public static long getDaysUntilDue(LoanEntity loan) {
        long currentTime = System.currentTimeMillis();
        long timeUntilDue = loan.getDueDate() - currentTime;
        return timeUntilDue / (24 * 60 * 60 * 1000); // Convert to days
    }

    /**
     * Get all possible status values
     * @return array of status strings
     */
    public static String[] getAllStatuses() {
        return new String[]{
            STATUS_BLACKLISTED,
            STATUS_PAID,
            STATUS_OVERDUE,
            STATUS_PARTIAL,
            STATUS_OWING
        };
    }

    /**
     * Check if a status is a valid loan status
     * @param status the status to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidStatus(String status) {
        if (status == null) return false;
        
        for (String validStatus : getAllStatuses()) {
            if (validStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get status display name (for UI)
     * @param status the status
     * @return display-friendly name
     */
    public static String getStatusDisplayName(String status) {
        switch (status) {
            case STATUS_BLACKLISTED:
                return "Blacklisted";
            case STATUS_PAID:
                return "Paid";
            case STATUS_OVERDUE:
                return "Overdue";
            case STATUS_PARTIAL:
                return "Partial";
            case STATUS_OWING:
                return "Owing";
            default:
                return "Unknown";
        }
    }
}
