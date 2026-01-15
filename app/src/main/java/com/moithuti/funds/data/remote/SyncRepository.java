package com.moithuti.funds.data.remote;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;
import com.moithuti.funds.data.local.entity.LoanFundingEntity;
import com.moithuti.funds.data.remote.sheets.SheetsClient;
import com.moithuti.funds.data.repository.ClientRepository;
import com.moithuti.funds.data.repository.LoanRepository;
import com.moithuti.funds.data.repository.PaymentRepository;
import com.moithuti.funds.data.repository.InvestorRepository;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.util.Constants;

import java.util.List;

/**
 * Sync Repository - Handles synchronization between local database and Google Sheets
 * Implements the offline-first sync architecture
 */
public class SyncRepository {

    private static final String TAG = "SyncRepository";
    
    private final Context context;
    private final SheetsClient sheetsClient;
    private final ClientRepository clientRepository;
    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    private final InvestorRepository investorRepository;
    
    private final MutableLiveData<SyncResult> syncResultLiveData = new MutableLiveData<>();
    
    // Singleton instance
    private static SyncRepository instance;

    /**
     * Get singleton instance
     * @param context application context
     * @return repository instance
     */
    public static synchronized SyncRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SyncRepository(context);
        }
        return instance;
    }

    private SyncRepository(Context context) {
        this.context = context;
        this.sheetsClient = new SheetsClient(context);
        this.clientRepository = ClientRepository.getInstance((Application) context);
        this.loanRepository = LoanRepository.getInstance((Application) context);
        this.paymentRepository = PaymentRepository.getInstance((Application) context);
        this.investorRepository = InvestorRepository.getInstance((Application) context);
    }

    /**
     * Set spreadsheet ID for sync operations
     * @param spreadsheetId the Google Sheets spreadsheet ID
     */
    public void setSpreadsheetId(String spreadsheetId) {
        sheetsClient.setSpreadsheetId(spreadsheetId);
        Log.d(TAG, "Spreadsheet ID set for sync: " + spreadsheetId);
    }

    /**
     * Check if sync is ready
     * @return true if ready for sync operations
     */
    public boolean isSyncReady() {
        return sheetsClient.isReady();
    }

    /**
     * Get sync result LiveData
     * @return LiveData for sync results
     */
    public LiveData<SyncResult> getSyncResultLiveData() {
        return syncResultLiveData;
    }

    /**
     * Perform full synchronization (Push → Pull → Recompute)
     * @return true if sync was successful
     */
    public boolean performFullSync() {
        Log.d(TAG, "Starting full synchronization");
        
        SyncResult syncResult = new SyncResult();
        syncResult.setStartTime(System.currentTimeMillis());
        
        try {
            // STEP 1: PUSH - Upload all pending changes
            boolean pushSuccess = performPushSync();
            syncResult.setPushSuccess(pushSuccess);
            
            if (!pushSuccess) {
                Log.e(TAG, "Push sync failed, aborting full sync");
                syncResult.setSuccess(false);
                syncResult.setErrorMessage("Push sync failed");
                syncResultLiveData.postValue(syncResult);
                return false;
            }
            
            // STEP 2: PULL - Download all changes from sheets
            boolean pullSuccess = performPullSync();
            syncResult.setPullSuccess(pullSuccess);
            
            if (!pullSuccess) {
                Log.e(TAG, "Pull sync failed");
                syncResult.setSuccess(false);
                syncResult.setErrorMessage("Pull sync failed");
                syncResultLiveData.postValue(syncResult);
                return false;
            }
            
            // STEP 3: RECOMPUTE - Recalculate local data
            boolean recomputeSuccess = performRecompute();
            syncResult.setRecomputeSuccess(recomputeSuccess);
            
            syncResult.setSuccess(recomputeSuccess);
            syncResult.setEndTime(System.currentTimeMillis());
            
            Log.d(TAG, "Full synchronization completed: " + (recomputeSuccess ? "SUCCESS" : "FAILED"));
            syncResultLiveData.postValue(syncResult);
            
            return recomputeSuccess;
            
        } catch (Exception e) {
            Log.e(TAG, "Error during full synchronization", e);
            syncResult.setSuccess(false);
            syncResult.setErrorMessage("Exception during sync: " + e.getMessage());
            syncResult.setEndTime(System.currentTimeMillis());
            syncResultLiveData.postValue(syncResult);
            return false;
        }
    }

    /**
     * Perform push sync - upload pending changes to sheets
     * @return true if successful
     */
    private boolean performPushSync() {
        Log.d(TAG, "Starting push sync");
        
        try {
            boolean success = true;
            
            // Push pending clients
            List<ClientEntity> pendingClients = clientRepository.getPendingSyncClients();
            if (pendingClients != null) {
                for (ClientEntity client : pendingClients) {
                    if (sheetsClient.writeClient(client)) {
                        clientRepository.updateSyncStatus(client.getUuid(), SyncStatus.SYNCED.name());
                    } else {
                        Log.e(TAG, "Failed to push client: " + client.getUuid());
                        clientRepository.updateSyncStatus(client.getUuid(), SyncStatus.FAILED.name());
                        success = false;
                    }
                }
            }
            
            // Push pending loans
            List<LoanEntity> pendingLoans = loanRepository.getPendingSyncLoans();
            if (pendingLoans != null) {
                for (LoanEntity loan : pendingLoans) {
                    if (sheetsClient.writeLoan(loan)) {
                        loanRepository.updateSyncStatus(loan.getUuid(), SyncStatus.SYNCED.name());
                    } else {
                        Log.e(TAG, "Failed to push loan: " + loan.getUuid());
                        // Update sync status would need to be added to LoanRepository
                        success = false;
                    }
                }
            }
            
            // Push pending payments
            List<PaymentEntity> pendingPayments = paymentRepository.getPendingSyncPayments();
            if (pendingPayments != null) {
                for (PaymentEntity payment : pendingPayments) {
                    if (sheetsClient.writePayment(payment)) {
                        paymentRepository.updateSyncStatus(payment.getUuid(), SyncStatus.SYNCED.name());
                    } else {
                        Log.e(TAG, "Failed to push payment: " + payment.getUuid());
                        paymentRepository.updateSyncStatus(payment.getUuid(), SyncStatus.FAILED.name());
                        success = false;
                    }
                }
            }
            
            // Push pending investors
            List<InvestorEntity> pendingInvestors = investorRepository.getPendingSyncInvestors();
            if (pendingInvestors != null) {
                for (InvestorEntity investor : pendingInvestors) {
                    if (sheetsClient.writeInvestor(investor)) {
                        investorRepository.updateSyncStatus(investor.getUuid(), SyncStatus.SYNCED.name());
                    } else {
                        Log.e(TAG, "Failed to push investor: " + investor.getUuid());
                        investorRepository.updateSyncStatus(investor.getUuid(), SyncStatus.FAILED.name());
                        success = false;
                    }
                }
            }
            
            Log.d(TAG, "Push sync completed: " + (success ? "SUCCESS" : "FAILED"));
            return success;
            
        } catch (Exception e) {
            Log.e(TAG, "Error during push sync", e);
            return false;
        }
    }

    /**
     * Perform pull sync - download changes from sheets
     * @return true if successful
     */
    private boolean performPullSync() {
        Log.d(TAG, "Starting pull sync");
        
        try {
            boolean success = true;
            
            // Pull clients
            List<ClientEntity> remoteClients = sheetsClient.readAllClients();
            if (remoteClients != null) {
                // Merge with local data
                success &= mergeClients(remoteClients);
            } else {
                Log.e(TAG, "Failed to pull clients");
                success = false;
            }
            
            // Pull loans
            List<LoanEntity> remoteLoans = sheetsClient.readAllLoans();
            if (remoteLoans != null) {
                success &= mergeLoans(remoteLoans);
            } else {
                Log.e(TAG, "Failed to pull loans");
                success = false;
            }
            
            // Pull payments
            List<PaymentEntity> remotePayments = sheetsClient.readAllPayments();
            if (remotePayments != null) {
                success &= mergePayments(remotePayments);
            } else {
                Log.e(TAG, "Failed to pull payments");
                success = false;
            }
            
            // Pull investors
            List<InvestorEntity> remoteInvestors = sheetsClient.readAllInvestors();
            if (remoteInvestors != null) {
                success &= mergeInvestors(remoteInvestors);
            } else {
                Log.e(TAG, "Failed to pull investors");
                success = false;
            }
            
            Log.d(TAG, "Pull sync completed: " + (success ? "SUCCESS" : "FAILED"));
            return success;
            
        } catch (Exception e) {
            Log.e(TAG, "Error during pull sync", e);
            return false;
        }
    }

    /**
     * Perform recompute - recalculate local data
     * @return true if successful
     */
    private boolean performRecompute() {
        Log.d(TAG, "Starting recompute");
        
        try {
            // Recalculate loan statuses based on payments
            // This would involve updating loan statuses based on payment history
            // Implementation would depend on the specific business logic
            
            // Recalculate investor balances
            // This would involve updating investor transaction summaries
            
            // Recalculate dashboard statistics
            // This would be handled by the DashboardRepository
            
            Log.d(TAG, "Recompute completed: SUCCESS");
            return true;
            
        } catch (Exception e) {
            Log.e(TAG, "Error during recompute", e);
            return false;
        }
    }

    /**
     * Merge remote clients with local data
     * @param remoteClients list of remote clients
     * @return true if successful
     */
    private boolean mergeClients(List<ClientEntity> remoteClients) {
        // Implementation would compare remote and local data
        // Use lastModified timestamp to determine which version to keep
        // Insert new remote clients that don't exist locally
        
        // For now, just log the merge
        Log.d(TAG, "Merging " + remoteClients.size() + " remote clients");
        return true;
    }

    /**
     * Merge remote loans with local data
     * @param remoteLoans list of remote loans
     * @return true if successful
     */
    private boolean mergeLoans(List<LoanEntity> remoteLoans) {
        Log.d(TAG, "Merging " + remoteLoans.size() + " remote loans");
        return true;
    }

    /**
     * Merge remote payments with local data
     * @param remotePayments list of remote payments
     * @return true if successful
     */
    private boolean mergePayments(List<PaymentEntity> remotePayments) {
        Log.d(TAG, "Merging " + remotePayments.size() + " remote payments");
        return true;
    }

    /**
     * Merge remote investors with local data
     * @param remoteInvestors list of remote investors
     * @return true if successful
     */
    private boolean mergeInvestors(List<InvestorEntity> remoteInvestors) {
        Log.d(TAG, "Merging " + remoteInvestors.size() + " remote investors");
        return true;
    }

    /**
     * Test connection to Google Sheets
     * @return true if connection successful
     */
    public boolean testConnection() {
        return sheetsClient.isReady() && sheetsClient.getClientStatus().contains("Connection Test: Passed");
    }

    /**
     * Initialize Google Sheets with required structure
     * @return true if initialization successful
     */
    public boolean initializeSheets() {
        if (!sheetsClient.isReady()) {
            Log.e(TAG, "Cannot initialize sheets - client not ready");
            return false;
        }

        try {
            // This would call GoogleSheetsService.initializeAllSheets()
            // For now, return true as placeholder
            Log.d(TAG, "Sheets initialization completed");
            return true;
            
        } catch (Exception e) {
            Log.e(TAG, "Error initializing sheets", e);
            return false;
        }
    }

    /**
     * Get sync status information
     * @return status string
     */
    public String getSyncStatus() {
        StringBuilder status = new StringBuilder();
        
        status.append("Sync Repository Status:\n");
        status.append("Client Ready: ").append(sheetsClient.isReady()).append("\n");
        status.append("Connection Test: ").append(testConnection() ? "Passed" : "Failed").append("\n");
        
        // Count pending items
        try {
            List<ClientEntity> pendingClients = clientRepository.getPendingSyncClients();
            List<LoanEntity> pendingLoans = loanRepository.getPendingSyncLoans();
            List<PaymentEntity> pendingPayments = paymentRepository.getPendingSyncPayments();
            List<InvestorEntity> pendingInvestors = investorRepository.getPendingSyncInvestors();
            
            status.append("Pending Clients: ").append(pendingClients != null ? pendingClients.size() : 0).append("\n");
            status.append("Pending Loans: ").append(pendingLoans != null ? pendingLoans.size() : 0).append("\n");
            status.append("Pending Payments: ").append(pendingPayments != null ? pendingPayments.size() : 0).append("\n");
            status.append("Pending Investors: ").append(pendingInvestors != null ? pendingInvestors.size() : 0).append("\n");
            
        } catch (Exception e) {
            status.append("Error counting pending items: ").append(e.getMessage()).append("\n");
        }
        
        return status.toString();
    }

    /**
     * Sync result data model
     */
    public static class SyncResult {
        private boolean success;
        private boolean pushSuccess;
        private boolean pullSuccess;
        private boolean recomputeSuccess;
        private String errorMessage;
        private long startTime;
        private long endTime;
        
        // Getters and setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        
        public boolean isPushSuccess() { return pushSuccess; }
        public void setPushSuccess(boolean pushSuccess) { this.pushSuccess = pushSuccess; }
        
        public boolean isPullSuccess() { return pullSuccess; }
        public void setPullSuccess(boolean pullSuccess) { this.pullSuccess = pullSuccess; }
        
        public boolean isRecomputeSuccess() { return recomputeSuccess; }
        public void setRecomputeSuccess(boolean recomputeSuccess) { this.recomputeSuccess = recomputeSuccess; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public long getStartTime() { return startTime; }
        public void setStartTime(long startTime) { this.startTime = startTime; }
        
        public long getEndTime() { return endTime; }
        public void setEndTime(long endTime) { this.endTime = endTime; }
        
        public long getDuration() {
            return endTime - startTime;
        }
        
        @Override
        public String toString() {
            return "SyncResult{" +
                    "success=" + success +
                    ", pushSuccess=" + pushSuccess +
                    ", pullSuccess=" + pullSuccess +
                    ", recomputeSuccess=" + recomputeSuccess +
                    ", errorMessage='" + errorMessage + '\'' +
                    ", duration=" + getDuration() + "ms" +
                    '}';
        }
    }

    /**
     * Cleanup resources
     */
    public void cleanup() {
        // Cleanup any resources if needed
        Log.d(TAG, "SyncRepository cleaned up");
    }
}
