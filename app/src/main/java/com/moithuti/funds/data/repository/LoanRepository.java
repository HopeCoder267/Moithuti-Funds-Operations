package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.LoanDao;
import com.moithuti.funds.data.local.dao.PaymentDao;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Loan Repository - Repository pattern for loan data access
 * Provides clean API for loan operations and handles sync triggers
 */
public class LoanRepository {

    private static final String TAG = "LoanRepository";
    
    private final LoanDao loanDao;
    private final PaymentDao paymentDao;
    private final ExecutorService executorService;
    private final Application application;

    // Singleton instance
    private static LoanRepository instance;

    /**
     * Get singleton instance
     * @param application application context
     * @return repository instance
     */
    public static synchronized LoanRepository getInstance(Application application) {
        if (instance == null) {
            instance = new LoanRepository(application);
        }
        return instance;
    }

    private LoanRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.loanDao = database.loanDao();
        this.paymentDao = database.paymentDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Insert new loan
     * @param loan the loan to insert
     */
    public void insertLoan(LoanEntity loan) {
        executorService.execute(() -> {
            try {
                // Ensure UUID is set
                if (UuidUtil.isEmpty(loan.getUuid())) {
                    loan.setUuid(UuidUtil.generateLoanUuid());
                }
                
                // Set timestamps and sync status
                loan.setDateIssued(System.currentTimeMillis());
                loan.setLastModified(System.currentTimeMillis());
                loan.setSyncStatus(SyncStatus.PENDING.name());
                loan.setDeleted(false);
                
                // Set default due date if not set
                if (loan.getDueDate() <= 0) {
                    loan.setDueDate(System.currentTimeMillis() + (Constants.DEFAULT_LOAN_TERM_DAYS * 24L * 60 * 60 * 1000));
                }
                
                long result = loanDao.insert(loan);
                Log.d(TAG, "Loan inserted: " + loan.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error inserting loan", e);
            }
        });
    }

    /**
     * Update existing loan
     * @param loan the loan to update
     */
    public void updateLoan(LoanEntity loan) {
        executorService.execute(() -> {
            try {
                // Update timestamp and sync status
                loan.setLastModified(System.currentTimeMillis());
                loan.setSyncStatus(SyncStatus.PENDING.name());
                
                int result = loanDao.update(loan);
                Log.d(TAG, "Loan updated: " + loan.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error updating loan", e);
            }
        });
    }

    /**
     * Delete loan (soft delete)
     * @param loanId the loan ID to delete
     */
    public void deleteLoan(String loanId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = loanDao.softDelete(loanId, timestamp);
                Log.d(TAG, "Loan soft deleted: " + loanId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error deleting loan", e);
            }
        });
    }

    /**
     * Get loan by ID
     * @param loanId the loan ID
     * @return loan entity
     */
    public LoanEntity getLoanById(String loanId) {
        try {
            return loanDao.getLoanById(loanId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting loan by ID", e);
            return null;
        }
    }

    /**
     * Get loan by ID (LiveData)
     * @param loanId the loan ID
     * @return LiveData loan
     */
    public LiveData<LoanEntity> getLoanByIdLive(String loanId) {
        return loanDao.getLoanByIdLive(loanId);
    }

    /**
     * Get all loans
     * @return list of all loans
     */
    public List<LoanEntity> getAllLoans() {
        try {
            return loanDao.getAllLoans();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all loans", e);
            return null;
        }
    }

    /**
     * Get all loans (LiveData)
     * @return LiveData list of loans
     */
    public LiveData<List<LoanEntity>> getAllLoansLive() {
        return loanDao.getAllLoansLive();
    }

    /**
     * Get loans by client
     * @param clientId the client ID
     * @return LiveData list of client's loans
     */
    public LiveData<List<LoanEntity>> getLoansByClientLive(String clientId) {
        return loanDao.getLoansByClientLive(clientId);
    }

    /**
     * Get loans by client (synchronous)
     * @param clientId the client ID
     * @return list of client's loans
     */
    public List<LoanEntity> getLoansByClient(String clientId) {
        try {
            return loanDao.getLoansByClient(clientId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting loans by client", e);
            return null;
        }
    }

    /**
     * Get loans by status
     * @param status the loan status
     * @return LiveData list of loans with specified status
     */
    public LiveData<List<LoanEntity>> getLoansByStatus(String status) {
        return loanDao.getLoansByStatus(status);
    }

    /**
     * Get overdue loans
     * @return LiveData list of overdue loans
     */
    public LiveData<List<LoanEntity>> getOverdueLoans() {
        long currentTime = System.currentTimeMillis();
        return loanDao.getOverdueLoans(currentTime);
    }

    /**
     * Get loans pending sync
     * @return list of loans pending sync
     */
    public List<LoanEntity> getPendingSyncLoans() {
        try {
            return loanDao.getPendingSyncLoans();
        } catch (Exception e) {
            Log.e(TAG, "Error getting pending sync loans", e);
            return null;
        }
    }

    /**
     * Get loans with failed sync
     * @return list of loans with failed sync
     */
    public List<LoanEntity> getFailedSyncLoans() {
        try {
            return loanDao.getFailedSyncLoans();
        } catch (Exception e) {
            Log.e(TAG, "Error getting failed sync loans", e);
            return null;
        }
    }

    /**
     * Update loan status
     * @param loanId the loan ID
     * @param newStatus the new status
     */
    public void updateLoanStatus(String loanId, String newStatus) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = loanDao.updateLoanStatus(loanId, newStatus, timestamp);
                Log.d(TAG, "Loan status updated: " + loanId + " to " + newStatus + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error updating loan status", e);
            }
        });
    }

    /**
     * Get total loaned amount
     * @return total amount loaned
     */
    public double getTotalLoanedAmount() {
        try {
            return loanDao.getTotalLoanedAmount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting total loaned amount", e);
            return 0.0;
        }
    }

    /**
     * Get total loaned amount by status
     * @param status the loan status
     * @return total amount for loans with specified status
     */
    public double getTotalLoanedAmountByStatus(String status) {
        try {
            return loanDao.getTotalLoanedAmountByStatus(status);
        } catch (Exception e) {
            Log.e(TAG, "Error getting total loaned amount by status", e);
            return 0.0;
        }
    }

    /**
     * Get loan count
     * @return total number of loans
     */
    public int getLoanCount() {
        try {
            return loanDao.getLoanCount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting loan count", e);
            return 0;
        }
    }

    /**
     * Get loan count by status
     * @param status the loan status
     * @return number of loans with specified status
     */
    public int getLoanCountByStatus(String status) {
        try {
            return loanDao.getLoanCountByStatus(status);
        } catch (Exception e) {
            Log.e(TAG, "Error getting loan count by status", e);
            return 0;
        }
    }

    /**
     * Restore deleted loan
     * @param loanId the loan ID to restore
     */
    public void restoreLoan(String loanId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = loanDao.restoreLoan(loanId, timestamp);
                Log.d(TAG, "Loan restored: " + loanId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error restoring loan", e);
            }
        });
    }

    /**
     * Calculate loan status based on payments
     * @param loanId the loan ID
     * @return calculated loan status
     */
    public String calculateLoanStatus(String loanId) {
        try {
            LoanEntity loan = loanDao.getLoanById(loanId);
            if (loan == null) {
                return Constants.LOAN_STATUS_OWING;
            }
            
            List<PaymentEntity> payments = paymentDao.getPaymentsByLoan(loanId);
            
            // This will use the LoanStatusCalculator utility
            // For now, return the current status
            return loan.getStatus();
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating loan status", e);
            return Constants.LOAN_STATUS_OWING;
        }
    }

    /**
     * Get total paid amount for loan
     * @param loanId the loan ID
     * @return total paid amount
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
     * Get remaining amount for loan
     * @param loanId the loan ID
     * @return remaining amount
     */
    public double getRemainingAmount(String loanId) {
        try {
            LoanEntity loan = loanDao.getLoanById(loanId);
            if (loan == null) {
                return 0.0;
            }
            
            double totalPaid = paymentDao.getTotalPaidForLoan(loanId);
            return Math.max(0, loan.getAmount() - totalPaid);
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting remaining amount", e);
            return 0.0;
        }
    }

    /**
     * Validate loan data
     * @param loan the loan to validate
     * @return true if valid, false otherwise
     */
    public boolean validateLoan(LoanEntity loan) {
        if (loan == null) {
            return false;
        }
        
        // Check required fields
        if (UuidUtil.isEmpty(loan.getClientId())) {
            Log.w(TAG, "Loan validation failed: client ID is empty");
            return false;
        }
        
        if (loan.getAmount() <= 0) {
            Log.w(TAG, "Loan validation failed: amount must be positive");
            return false;
        }
        
        if (loan.getAmount() < Constants.MIN_LOAN_AMOUNT || loan.getAmount() > Constants.MAX_LOAN_AMOUNT) {
            Log.w(TAG, "Loan validation failed: amount out of range");
            return false;
        }
        
        // Check dates
        if (loan.getDateIssued() <= 0) {
            Log.w(TAG, "Loan validation failed: invalid date issued");
            return false;
        }
        
        if (loan.getDueDate() <= loan.getDateIssued()) {
            Log.w(TAG, "Loan validation failed: due date must be after issue date");
            return false;
        }
        
        // Check status
        if (!isValidStatus(loan.getStatus())) {
            Log.w(TAG, "Loan validation failed: invalid status");
            return false;
        }
        
        return true;
    }

    /**
     * Check if status is valid
     * @param status the status to check
     * @return true if valid
     */
    private boolean isValidStatus(String status) {
        return status != null && (
            Constants.LOAN_STATUS_OWING.equals(status) ||
            Constants.LOAN_STATUS_PARTIAL.equals(status) ||
            Constants.LOAN_STATUS_PAID.equals(status) ||
            Constants.LOAN_STATUS_OVERDUE.equals(status) ||
            Constants.LOAN_STATUS_BLACKLISTED.equals(status)
        );
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

    public void updateSyncStatus(String uuid, String name) {
    }
}
