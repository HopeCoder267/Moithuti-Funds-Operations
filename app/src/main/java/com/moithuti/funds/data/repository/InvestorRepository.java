package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.InvestorDao;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Investor Repository - Repository pattern for investor data access
 * Provides clean API for investor operations and handles sync triggers
 */
public class InvestorRepository {

    private static final String TAG = "InvestorRepository";
    
    private final InvestorDao investorDao;
    private final ExecutorService executorService;
    private final Application application;

    // Singleton instance
    private static InvestorRepository instance;

    /**
     * Get singleton instance
     * @param application application context
     * @return repository instance
     */
    public static synchronized InvestorRepository getInstance(Application application) {
        if (instance == null) {
            instance = new InvestorRepository(application);
        }
        return instance;
    }

    private InvestorRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.investorDao = database.investorDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Create a new investor with validation
     * @param name investor name (must be unique)
     * @param initialInvestment initial investment amount
     * @param monthlyTopUp optional monthly top-up amount
     * @return investor UUID if successful, null if failed
     */
    public String createInvestor(String name, double initialInvestment, double monthlyTopUp) {
        try {
            // Validate investor name uniqueness
            if (investorDao.getInvestorByName(name) != null) {
                Log.e(TAG, "Investor with name '" + name + "' already exists");
                return null;
            }
            
            // Cannot create another Main Account
            if ("Main Account".equals(name)) {
                Log.e(TAG, "Cannot create duplicate Main Account");
                return null;
            }
            
            InvestorEntity investor = new InvestorEntity();
            investor.setUuid(UuidUtil.generateInvestorUuid());
            investor.setName(name);
            investor.setMainAccount(false); // Only Main Account can be created by database
            investor.setCreatedDate(System.currentTimeMillis());
            investor.setLastModified(System.currentTimeMillis());
            investor.setSyncStatus(SyncStatus.PENDING.name());
            investor.setDeleted(false);
            
            long result = investorDao.insert(investor);
            
            if (result > 0) {
                // Trigger sync
                triggerSync();
                
                Log.d(TAG, "Created investor: " + name + " with UUID: " + investor.getUuid());
                return investor.getUuid();
            } else {
                Log.e(TAG, "Failed to create investor: " + name);
                return null;
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error creating investor", e);
            return null;
        }
    }

    /**
     * Update existing investor
     * @param investor the investor to update
     */
    public void updateInvestor(InvestorEntity investor) {
        executorService.execute(() -> {
            try {
                // Update timestamp and sync status
                investor.setLastModified(System.currentTimeMillis());
                investor.setSyncStatus(SyncStatus.PENDING.name());
                
                // If this is being set as main account, clear existing main account
                InvestorEntity existing = investorDao.getInvestorById(investor.getUuid());
                if (existing != null && !existing.isMainAccount() && investor.isMainAccount()) {
                    investorDao.clearMainAccount(System.currentTimeMillis());
                }
                
                int result = investorDao.update(investor);
                Log.d(TAG, "Investor updated: " + investor.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error updating investor", e);
            }
        });
    }

    /**
     * Delete investor (soft delete) - Main Account cannot be deleted
     * @param investorId the investor ID to delete
     * @return true if successful, false if Main Account or error
     */
    public boolean deleteInvestor(String investorId) {
        try {
            InvestorEntity investor = investorDao.getInvestorById(investorId);
            if (investor == null) {
                Log.e(TAG, "Investor not found: " + investorId);
                return false;
            }
            
            // Cannot delete Main Account
            if (investor.isMainAccount()) {
                Log.e(TAG, "Cannot delete Main Account");
                return false;
            }
            
            long timestamp = System.currentTimeMillis();
            int result = investorDao.softDelete(investorId, timestamp);
            Log.d(TAG, "Investor soft deleted: " + investorId + ", result: " + result);
            
            // Trigger sync
            triggerSync();
            
            return result > 0;
            
        } catch (Exception e) {
            Log.e(TAG, "Error deleting investor", e);
            return false;
        }
    }

    /**
     * Get investor by ID
     * @param investorId the investor ID
     * @return investor entity
     */
    public InvestorEntity getInvestorById(String investorId) {
        try {
            return investorDao.getInvestorById(investorId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting investor by ID", e);
            return null;
        }
    }

    /**
     * Get investor by ID (LiveData)
     * @param investorId the investor ID
     * @return LiveData investor
     */
    public LiveData<InvestorEntity> getInvestorByIdLive(String investorId) {
        return investorDao.getInvestorByIdLive(investorId);
    }

    /**
     * Get all investors
     * @return list of all investors
     */
    public List<InvestorEntity> getAllInvestors() {
        try {
            return investorDao.getAllInvestors();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all investors", e);
            return null;
        }
    }

    /**
     * Get all investors (LiveData)
     * @return LiveData list of investors
     */
    public LiveData<List<InvestorEntity>> getAllInvestorsLive() {
        return investorDao.getAllInvestorsLive();
    }

    /**
     * Get main account investor
     * @return main account investor (LiveData)
     */
    public LiveData<InvestorEntity> getMainAccountLive() {
        return investorDao.getMainAccountLive();
    }

    /**
     * Get main account investor (synchronous)
     * @return main account investor
     */
    public InvestorEntity getMainAccount() {
        try {
            return investorDao.getMainAccount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting main account", e);
            return null;
        }
    }

    /**
     * Get other investors (not main account)
     * @return LiveData list of other investors
     */
    public LiveData<List<InvestorEntity>> getOtherInvestorsLive() {
        return investorDao.getOtherInvestorsLive();
    }

    /**
     * Get other investors (synchronous)
     * @return list of other investors
     */
    public List<InvestorEntity> getOtherInvestors() {
        try {
            return investorDao.getOtherInvestors();
        } catch (Exception e) {
            Log.e(TAG, "Error getting other investors", e);
            return null;
        }
    }

    /**
     * Get investors pending sync
     * @return list of investors pending sync
     */
    public List<InvestorEntity> getPendingSyncInvestors() {
        try {
            return investorDao.getPendingSyncInvestors();
        } catch (Exception e) {
            Log.e(TAG, "Error getting pending sync investors", e);
            return null;
        }
    }

    /**
     * Get investors with failed sync
     * @return list of investors with failed sync
     */
    public List<InvestorEntity> getFailedSyncInvestors() {
        try {
            return investorDao.getFailedSyncInvestors();
        } catch (Exception e) {
            Log.e(TAG, "Error getting failed sync investors", e);
            return null;
        }
    }

    /**
     * Update sync status for investor
     * @param investorId the investor ID
     * @param syncStatus the new sync status
     */
    public void updateSyncStatus(String investorId, String syncStatus) {
        executorService.execute(() -> {
            try {
                investorDao.updateSyncStatus(investorId, syncStatus);
                Log.d(TAG, "Sync status updated for investor: " + investorId + " to " + syncStatus);
            } catch (Exception e) {
                Log.e(TAG, "Error updating sync status", e);
            }
        });
    }

    /**
     * Get investor count
     * @return total number of investors
     */
    public int getInvestorCount() {
        try {
            return investorDao.getInvestorCount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting investor count", e);
            return 0;
        }
    }

    /**
     * Get main account count
     * @return number of main accounts (should be 0 or 1)
     */
    public int getMainAccountCount() {
        try {
            return investorDao.getMainAccountCount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting main account count", e);
            return 0;
        }
    }

    /**
     * Set investor as main account
     * @param investorId the investor ID to set as main account
     */
    public void setMainAccount(String investorId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                
                // Clear existing main account
                investorDao.clearMainAccount(timestamp);
                
                // Set new main account
                investorDao.setMainAccount(investorId, timestamp);
                
                Log.d(TAG, "Main account set to: " + investorId);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error setting main account", e);
            }
        });
    }

    /**
     * Restore deleted investor
     * @param investorId the investor ID to restore
     */
    public void restoreInvestor(String investorId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = investorDao.restoreInvestor(investorId, timestamp);
                Log.d(TAG, "Investor restored: " + investorId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error restoring investor", e);
            }
        });
    }

    /**
     * Check if investor exists
     * @param investorId the investor ID
     * @return true if investor exists
     */
    public boolean investorExists(String investorId) {
        try {
            InvestorEntity investor = investorDao.getInvestorById(investorId);
            return investor != null && !investor.isDeleted();
        } catch (Exception e) {
            Log.e(TAG, "Error checking if investor exists", e);
            return false;
        }
    }

    /**
     * Get investor by name
     * @param name the investor name
     * @return investor entity or null
     */
    public InvestorEntity getInvestorByName(String name) {
        try {
            List<InvestorEntity> investors = investorDao.getAllInvestors();
            if (investors != null) {
                for (InvestorEntity investor : investors) {
                    if (name.equals(investor.getName()) && !investor.isDeleted()) {
                        return investor;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error getting investor by name", e);
            return null;
        }
    }

    /**
     * Get Main Account UUID
     * @return Main Account UUID or null if not found
     */
    public String getMainAccountId() {
        InvestorEntity mainAccount = getMainAccount();
        return mainAccount != null ? mainAccount.getUuid() : null;
    }

    /**
     * Validate investor data
     * @param investor the investor to validate
     * @return true if valid, false otherwise
     */
    public boolean validateInvestor(InvestorEntity investor) {
        if (investor == null) {
            return false;
        }
        
        // Check required fields
        if (investor.getName() == null || investor.getName().trim().isEmpty()) {
            Log.w(TAG, "Investor validation failed: name is empty");
            return false;
        }
        
        if (investor.getName().length() > Constants.MAX_INVESTOR_NAME_LENGTH) {
            Log.w(TAG, "Investor validation failed: name too long");
            return false;
        }
        
        // Check for duplicate names
        InvestorEntity existing = getInvestorByName(investor.getName());
        if (existing != null && !existing.getUuid().equals(investor.getUuid())) {
            Log.w(TAG, "Investor validation failed: duplicate name");
            return false;
        }
        
        return true;
    }

    /**
     * Create initial main account if none exists
     * @param mainAccountName the name for the main account
     */
    public void createMainAccountIfNotExists(String mainAccountName) {
        executorService.execute(() -> {
            try {
                InvestorEntity mainAccount = investorDao.getMainAccount();
                if (mainAccount == null) {
                    // Create main account
                    InvestorEntity newMainAccount = new InvestorEntity();
                    newMainAccount.setUuid(UuidUtil.generateInvestorUuid());
                    newMainAccount.setName(mainAccountName);
                    newMainAccount.setMainAccount(true);
                    newMainAccount.setCreatedDate(System.currentTimeMillis());
                    newMainAccount.setLastModified(System.currentTimeMillis());
                    newMainAccount.setSyncStatus(SyncStatus.PENDING.name());
                    newMainAccount.setDeleted(false);
                    
                    long result = investorDao.insert(newMainAccount);
                    Log.d(TAG, "Main account created: " + newMainAccount.getUuid() + ", result: " + result);
                    
                    // Trigger sync
                    triggerSync();
                } else {
                    Log.d(TAG, "Main account already exists: " + mainAccount.getUuid());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error creating main account", e);
            }
        });
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
