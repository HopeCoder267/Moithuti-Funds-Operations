package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.ProfitTrackerDao;
import com.moithuti.funds.data.local.entity.ProfitTrackerEntity;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.sync.SyncScheduler;
import com.moithuti.funds.util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Profit Tracker Repository - Repository pattern for profit tracking data access
 * Provides clean API for profit calculations and manages sync triggers
 */
public class ProfitTrackerRepository {

    private static final String TAG = "ProfitTrackerRepository";
    
    private final ProfitTrackerDao profitTrackerDao;
    private final ExecutorService executorService;
    private final SyncScheduler syncScheduler;
    private final Application application;

    // Singleton instance
    private static ProfitTrackerRepository instance;

    /**
     * Get singleton instance
     * @param application application context
     * @return repository instance
     */
    public static synchronized ProfitTrackerRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ProfitTrackerRepository(application);
        }
        return instance;
    }

    private ProfitTrackerRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.profitTrackerDao = database.profitTrackerDao();
        this.executorService = Executors.newSingleThreadExecutor();
        this.syncScheduler = SyncScheduler.getInstance(application);
    }

    /**
     * Get current year-month string
     * @return current year-month in "YYYY-MM" format
     */
    public static String getCurrentYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * Create or update profit tracker for a month
     * @param investorId investor UUID (null for Main Account)
     * @param yearMonth year-month string
     * @param monthlyLoansIssued total loans issued this month
     * @param monthlyRepaymentsReceived total repayments received this month
     * @param monthlyInterest interest earned this month
     * @return profit tracker UUID
     */
    public String updateMonthlyProfit(String investorId, String yearMonth, 
                                  double monthlyLoansIssued, double monthlyRepaymentsReceived, 
                                  double monthlyInterest) {
        try {
            // Get existing profit tracker for this month
            ProfitTrackerEntity existing = investorId == null 
                ? profitTrackerDao.getMainAccountProfitTrackerByMonth(yearMonth)
                : profitTrackerDao.getInvestorProfitTrackerByMonth(investorId, yearMonth);

            // Get previous cumulative values
            ProfitTrackerEntity previous = investorId == null
                ? profitTrackerDao.getLatestMainAccountProfitTracker()
                : profitTrackerDao.getLatestInvestorProfitTracker(investorId);

            double previousCumulativeProfit = previous != null ? previous.getCumulativeProfit() : 0.0;
            double previousCumulativeInterest = previous != null ? previous.getCumulativeInterest() : 0.0;

            // Calculate monthly profit
            double monthlyProfit = monthlyRepaymentsReceived - monthlyLoansIssued;

            // Calculate cumulative values
            double cumulativeProfit = previousCumulativeProfit + monthlyProfit;
            double cumulativeInterest = previousCumulativeInterest + monthlyInterest;

            // Calculate available funds
            double availableFunds = cumulativeProfit + cumulativeInterest;

            ProfitTrackerEntity profitTracker;
            if (existing != null) {
                // Update existing
                profitTracker = existing;
                profitTracker.setMonthlyLoansIssued(monthlyLoansIssued);
                profitTracker.setMonthlyRepaymentsReceived(monthlyRepaymentsReceived);
                profitTracker.setMonthlyProfit(monthlyProfit);
                profitTracker.setMonthlyInterest(monthlyInterest);
                profitTracker.setCumulativeProfit(cumulativeProfit);
                profitTracker.setCumulativeInterest(cumulativeInterest);
                profitTracker.setAvailableFunds(availableFunds);
                profitTracker.setLastModified(System.currentTimeMillis());
                profitTracker.setSyncStatus(SyncStatus.PENDING.name());
                
                profitTrackerDao.update(profitTracker);
                Log.d(TAG, "Updated profit tracker for month: " + yearMonth);
                
            } else {
                // Create new
                profitTracker = new ProfitTrackerEntity();
                profitTracker.setUuid(UuidUtil.generateProfitTrackerUuid());
                profitTracker.setInvestorId(investorId);
                profitTracker.setYearMonth(yearMonth);
                profitTracker.setMonthlyLoansIssued(monthlyLoansIssued);
                profitTracker.setMonthlyRepaymentsReceived(monthlyRepaymentsReceived);
                profitTracker.setMonthlyProfit(monthlyProfit);
                profitTracker.setMonthlyInterest(monthlyInterest);
                profitTracker.setCumulativeProfit(cumulativeProfit);
                profitTracker.setCumulativeInterest(cumulativeInterest);
                profitTracker.setAvailableFunds(availableFunds);
                profitTracker.setCreatedDate(System.currentTimeMillis());
                profitTracker.setLastModified(System.currentTimeMillis());
                profitTracker.setSyncStatus(SyncStatus.PENDING.name());
                profitTracker.setDeleted(false);
                
                profitTrackerDao.insert(profitTracker);
                Log.d(TAG, "Created profit tracker for month: " + yearMonth);
            }

            // Trigger sync
            syncScheduler.scheduleImmediateSync();
            
            return profitTracker.getUuid();
            
        } catch (Exception e) {
            Log.e(TAG, "Error updating monthly profit", e);
            return null;
        }
    }

    /**
     * Get all profit trackers (LiveData)
     */
    public LiveData<List<ProfitTrackerEntity>> getAllProfitTrackersLive() {
        return profitTrackerDao.getAllProfitTrackersLive();
    }

    /**
     * Get Main Account profit trackers (LiveData)
     */
    public LiveData<List<ProfitTrackerEntity>> getMainAccountProfitTrackersLive() {
        return profitTrackerDao.getMainAccountProfitTrackersLive();
    }

    /**
     * Get profit trackers for investor (LiveData)
     */
    public LiveData<List<ProfitTrackerEntity>> getInvestorProfitTrackersLive(String investorId) {
        return profitTrackerDao.getInvestorProfitTrackersLive(investorId);
    }

    /**
     * Get current cumulative profit
     */
    public double getCurrentCumulativeProfit() {
        try {
            Double result = profitTrackerDao.getCurrentCumulativeProfit();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            Log.e(TAG, "Error getting current cumulative profit", e);
            return 0.0;
        }
    }

    /**
     * Get current available funds
     */
    public double getCurrentAvailableFunds() {
        try {
            Double result = profitTrackerDao.getCurrentAvailableFunds();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            Log.e(TAG, "Error getting current available funds", e);
            return 0.0;
        }
    }

    /**
     * Get total interest earned
     */
    public double getTotalInterestEarned() {
        try {
            Double result = profitTrackerDao.getCurrentCumulativeInterest();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            Log.e(TAG, "Error getting total interest earned", e);
            return 0.0;
        }
    }

    /**
     * Get monthly profit summaries for graphs
     */
    public List<ProfitTrackerDao.ProfitSummary> getMonthlyProfitSummaries() {
        try {
            return profitTrackerDao.getMonthlyProfitSummaries();
        } catch (Exception e) {
            Log.e(TAG, "Error getting monthly profit summaries", e);
            return null;
        }
    }

    /**
     * Get profit tracker by ID
     */
    public ProfitTrackerEntity getProfitTrackerById(String profitTrackerId) {
        try {
            return profitTrackerDao.getProfitTrackerById(profitTrackerId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting profit tracker by ID", e);
            return null;
        }
    }

    /**
     * Delete profit tracker (soft delete)
     */
    public void deleteProfitTracker(String profitTrackerId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                profitTrackerDao.softDelete(profitTrackerId, timestamp);
                Log.d(TAG, "Profit tracker soft deleted: " + profitTrackerId);
                
                // Trigger sync
                syncScheduler.scheduleImmediateSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error deleting profit tracker", e);
            }
        });
    }

    /**
     * Get pending sync profit trackers
     */
    public List<ProfitTrackerEntity> getPendingSyncProfitTrackers() {
        try {
            return profitTrackerDao.getPendingSyncProfitTrackers();
        } catch (Exception e) {
            Log.e(TAG, "Error getting pending sync profit trackers", e);
            return null;
        }
    }

    /**
     * Update sync status
     */
    public void updateSyncStatus(String profitTrackerId, String syncStatus) {
        executorService.execute(() -> {
            try {
                profitTrackerDao.updateSyncStatus(profitTrackerId, syncStatus);
                Log.d(TAG, "Updated sync status for profit tracker: " + profitTrackerId + " to " + syncStatus);
            } catch (Exception e) {
                Log.e(TAG, "Error updating sync status", e);
            }
        });
    }

    /**
     * Trigger sync
     */
    private void triggerSync() {
        try {
            syncScheduler.scheduleImmediateSync();
        } catch (Exception e) {
            Log.e(TAG, "Error triggering sync", e);
        }
    }
}
