package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.PaymentDao;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Payment Repository - Repository pattern for payment data access
 * Provides clean API for payment operations and handles sync triggers
 */
public class PaymentRepository {

    private static final String TAG = "PaymentRepository";
    
    private final PaymentDao paymentDao;
    private final ExecutorService executorService;
    private final Application application;

    // Singleton instance
    private static PaymentRepository instance;

    /**
     * Get singleton instance
     * @param application application context
     * @return repository instance
     */
    public static synchronized PaymentRepository getInstance(Application application) {
        if (instance == null) {
            instance = new PaymentRepository(application);
        }
        return instance;
    }

    private PaymentRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.paymentDao = database.paymentDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Insert new payment
     * @param payment the payment to insert
     */
    public void insertPayment(PaymentEntity payment) {
        executorService.execute(() -> {
            try {
                // Ensure UUID is set
                if (UuidUtil.isEmpty(payment.getUuid())) {
                    payment.setUuid(UuidUtil.generatePaymentUuid());
                }
                
                // Set timestamps and sync status
                payment.setDate(System.currentTimeMillis());
                payment.setLastModified(System.currentTimeMillis());
                payment.setSyncStatus(SyncStatus.PENDING.name());
                payment.setDeleted(false);
                
                long result = paymentDao.insert(payment);
                Log.d(TAG, "Payment inserted: " + payment.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error inserting payment", e);
            }
        });
    }

    /**
     * Update existing payment
     * @param payment the payment to update
     */
    public void updatePayment(PaymentEntity payment) {
        executorService.execute(() -> {
            try {
                // Update timestamp and sync status
                payment.setLastModified(System.currentTimeMillis());
                payment.setSyncStatus(SyncStatus.PENDING.name());
                
                int result = paymentDao.update(payment);
                Log.d(TAG, "Payment updated: " + payment.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error updating payment", e);
            }
        });
    }

    /**
     * Delete payment (soft delete)
     * @param paymentId the payment ID to delete
     */
    public void deletePayment(String paymentId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = paymentDao.softDelete(paymentId, timestamp);
                Log.d(TAG, "Payment soft deleted: " + paymentId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error deleting payment", e);
            }
        });
    }

    /**
     * Get payment by ID
     * @param paymentId the payment ID
     * @return payment entity
     */
    public PaymentEntity getPaymentById(String paymentId) {
        try {
            return paymentDao.getPaymentById(paymentId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting payment by ID", e);
            return null;
        }
    }

    /**
     * Get payment by ID (LiveData)
     * @param paymentId the payment ID
     * @return LiveData payment
     */
    public LiveData<PaymentEntity> getPaymentByIdLive(String paymentId) {
        return paymentDao.getPaymentByIdLive(paymentId);
    }

    /**
     * Get all payments
     * @return list of all payments
     */
    public List<PaymentEntity> getAllPayments() {
        try {
            return paymentDao.getAllPayments();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all payments", e);
            return null;
        }
    }

    /**
     * Get all payments (LiveData)
     * @return LiveData list of payments
     */
    public LiveData<List<PaymentEntity>> getAllPaymentsLive() {
        return paymentDao.getAllPaymentsLive();
    }

    /**
     * Get payments by loan
     * @param loanId the loan ID
     * @return LiveData list of loan's payments
     */
    public LiveData<List<PaymentEntity>> getPaymentsByLoanLive(String loanId) {
        return paymentDao.getPaymentsByLoanLive(loanId);
    }

    /**
     * Get payments by loan (synchronous)
     * @param loanId the loan ID
     * @return list of loan's payments
     */
    public List<PaymentEntity> getPaymentsByLoan(String loanId) {
        try {
            return paymentDao.getPaymentsByLoan(loanId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting payments by loan", e);
            return null;
        }
    }

    /**
     * Get payments by date range
     * @param startDate start date timestamp
     * @param endDate end date timestamp
     * @return LiveData list of payments in date range
     */
    public LiveData<List<PaymentEntity>> getPaymentsByDateRange(long startDate, long endDate) {
        return paymentDao.getPaymentsByDateRange(startDate, endDate);
    }

    /**
     * Get payments by date range (synchronous)
     * @param startDate start date timestamp
     * @param endDate end date timestamp
     * @return list of payments in date range
     */
    public List<PaymentEntity> getPaymentsByDateRangeSync(long startDate, long endDate) {
        try {
            return paymentDao.getPaymentsByDateRangeSync(startDate, endDate);
        } catch (Exception e) {
            Log.e(TAG, "Error getting payments by date range", e);
            return null;
        }
    }

    /**
     * Get payments pending sync
     * @return list of payments pending sync
     */
    public List<PaymentEntity> getPendingSyncPayments() {
        try {
            return paymentDao.getPendingSyncPayments();
        } catch (Exception e) {
            Log.e(TAG, "Error getting pending sync payments", e);
            return null;
        }
    }

    /**
     * Get payments with failed sync
     * @return list of payments with failed sync
     */
    public List<PaymentEntity> getFailedSyncPayments() {
        try {
            return paymentDao.getFailedSyncPayments();
        } catch (Exception e) {
            Log.e(TAG, "Error getting failed sync payments", e);
            return null;
        }
    }

    /**
     * Update sync status for payment
     * @param paymentId the payment ID
     * @param syncStatus the new sync status
     */
    public void updateSyncStatus(String paymentId, String syncStatus) {
        executorService.execute(() -> {
            try {
                paymentDao.updateSyncStatus(paymentId, syncStatus);
                Log.d(TAG, "Sync status updated for payment: " + paymentId + " to " + syncStatus);
            } catch (Exception e) {
                Log.e(TAG, "Error updating sync status", e);
            }
        });
    }

    /**
     * Get payment count
     * @return total number of payments
     */
    public int getPaymentCount() {
        try {
            return paymentDao.getPaymentCount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting payment count", e);
            return 0;
        }
    }

    /**
     * Get total paid amount
     * @return total amount paid across all payments
     */
    public double getTotalPaidAmount() {
        try {
            return paymentDao.getTotalPaidAmount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting total paid amount", e);
            return 0.0;
        }
    }

    /**
     * Get total paid amount by date range
     * @param startDate start date timestamp
     * @param endDate end date timestamp
     * @return total amount paid in date range
     */
    public double getTotalPaidAmountByDateRange(long startDate, long endDate) {
        try {
            return paymentDao.getTotalPaidAmountByDateRange(startDate, endDate);
        } catch (Exception e) {
            Log.e(TAG, "Error getting total paid amount by date range", e);
            return 0.0;
        }
    }

    /**
     * Get total paid amount for loan
     * @param loanId the loan ID
     * @return total amount paid for the loan
     */
    public double getTotalPaidForLoan(String loanId) {
        try {
            return paymentDao.getTotalPaidForLoan(loanId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting total paid for loan", e);
            return 0.0;
        }
    }

    /**
     * Get monthly payment total
     * @param monthStart start of month timestamp
     * @param monthEnd end of month timestamp
     * @return total amount paid in the month
     */
    public double getMonthlyPaymentTotal(long monthStart, long monthEnd) {
        try {
            return paymentDao.getMonthlyPaymentTotal(monthStart, monthEnd);
        } catch (Exception e) {
            Log.e(TAG, "Error getting monthly payment total", e);
            return 0.0;
        }
    }

    /**
     * Restore deleted payment
     * @param paymentId the payment ID to restore
     */
    public void restorePayment(String paymentId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = paymentDao.restorePayment(paymentId, timestamp);
                Log.d(TAG, "Payment restored: " + paymentId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error restoring payment", e);
            }
        });
    }

    /**
     * Validate payment data
     * @param payment the payment to validate
     * @return true if valid, false otherwise
     */
    public boolean validatePayment(PaymentEntity payment) {
        if (payment == null) {
            return false;
        }
        
        // Check required fields
        if (UuidUtil.isEmpty(payment.getLoanId())) {
            Log.w(TAG, "Payment validation failed: loan ID is empty");
            return false;
        }
        
        if (payment.getAmount() <= 0) {
            Log.w(TAG, "Payment validation failed: amount must be positive");
            return false;
        }
        
        if (payment.getAmount() < Constants.MIN_LOAN_AMOUNT || payment.getAmount() > Constants.MAX_LOAN_AMOUNT) {
            Log.w(TAG, "Payment validation failed: amount out of range");
            return false;
        }
        
        // Check date
        if (payment.getDate() <= 0) {
            Log.w(TAG, "Payment validation failed: invalid date");
            return false;
        }
        
        return true;
    }

    /**
     * Check if payment amount exceeds loan amount
     * @param loanId the loan ID
     * @param paymentAmount the payment amount
     * @return true if payment would exceed loan amount
     */
    public boolean wouldExceedLoanAmount(String loanId, double paymentAmount) {
        try {
            double currentTotal = paymentDao.getTotalPaidForLoan(loanId);
            double newTotal = currentTotal + paymentAmount;
            
            // Get loan amount to check against
            // This would require access to LoanDao, for now return false
            // In a complete implementation, we'd check against the actual loan amount
            
            return false; // Placeholder
            
        } catch (Exception e) {
            Log.e(TAG, "Error checking if payment exceeds loan amount", e);
            return false;
        }
    }

    /**
     * Get payment statistics
     * @return payment statistics object
     */
    public PaymentStats getPaymentStats() {
        PaymentStats stats = new PaymentStats();
        
        try {
            stats.totalPayments = paymentDao.getPaymentCount();
            stats.totalAmount = paymentDao.getTotalPaidAmount();
            
            // Calculate monthly stats
            long currentTime = System.currentTimeMillis();
            long monthStart = getStartOfMonth(currentTime);
            long monthEnd = getEndOfMonth(currentTime);
            stats.monthlyTotal = paymentDao.getMonthlyPaymentTotal(monthStart, monthEnd);
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting payment stats", e);
        }
        
        return stats;
    }

    /**
     * Get start of month timestamp
     */
    private long getStartOfMonth(long timestamp) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Get end of month timestamp
     */
    private long getEndOfMonth(long timestamp) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
        calendar.set(java.util.Calendar.MINUTE, 59);
        calendar.set(java.util.Calendar.SECOND, 59);
        calendar.set(java.util.Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * Payment statistics data model
     */
    public static class PaymentStats {
        public int totalPayments = 0;
        public double totalAmount = 0.0;
        public double monthlyTotal = 0.0;
        
        @Override
        public String toString() {
            return "PaymentStats{" +
                    "totalPayments=" + totalPayments +
                    ", totalAmount=" + totalAmount +
                    ", monthlyTotal=" + monthlyTotal +
                    '}';
        }
    }

    /**
     * Trigger sync (placeholder for sync implementation)
     */
    private void triggerSync() {
        // This will be implemented in Phase 5 when we create the sync engine
        Log.d(TAG, "Sync triggered");
    }

    /**
     * Cleanup resources
     */
    public void cleanup() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
