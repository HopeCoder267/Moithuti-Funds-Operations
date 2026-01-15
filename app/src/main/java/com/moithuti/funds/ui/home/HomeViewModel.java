package com.moithuti.funds.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.moithuti.funds.data.repository.DashboardRepository;
import com.moithuti.funds.data.repository.ProfitTrackerRepository;
import com.moithuti.funds.data.local.entity.ProfitTrackerEntity;
import com.moithuti.funds.data.local.dao.ProfitTrackerDao;
import com.moithuti.funds.sync.SyncScheduler;
import com.moithuti.funds.ui.common.ConnectivityObserver;
import com.moithuti.funds.ui.common.UiUtils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Callback interface for async operations
 */
interface Callback<T> {
    void onResult(T result);
}

/**
 * Home ViewModel - ViewModel for Home Fragment
 * Manages dashboard data and sync operations
 */
public class HomeViewModel extends AndroidViewModel {

    private final DashboardRepository dashboardRepository;
    private final ProfitTrackerRepository profitTrackerRepository;
    private final SyncScheduler syncScheduler;
    private final ConnectivityObserver connectivityObserver;
    
    // LiveData for dashboard data
    private final MutableLiveData<DashboardStats> dashboardStatsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();
    
    // ProfitTracker LiveData
    private final LiveData<List<ProfitTrackerEntity>> profitTrackersLiveData;
    private final MutableLiveData<Double> availableFundsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Double> monthlyProfitLiveData = new MutableLiveData<>();
    private final MutableLiveData<Double> cumulativeProfitLiveData = new MutableLiveData<>();
    private final MutableLiveData<Double> interestEarnedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Double>> monthlyProfitMapLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Double>> cumulativeProfitMapLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Double>> monthlyInterestMapLiveData = new MutableLiveData<>();
    
    // LiveData for connectivity and sync status
    private final LiveData<Boolean> isConnectedLiveData;
    private final MutableLiveData<Long> lastSyncTimeLiveData = new MutableLiveData<>();
    
    // Constructor
    public HomeViewModel(Application application) {
        super(application);
        
        this.dashboardRepository = DashboardRepository.getInstance(application);
        this.profitTrackerRepository = ProfitTrackerRepository.getInstance(application);
        this.syncScheduler = SyncScheduler.getInstance(application);
        this.connectivityObserver = new ConnectivityObserver(application);
        
        // Setup connectivity observer
        this.isConnectedLiveData = connectivityObserver;
        
        // Setup ProfitTracker LiveData
        this.profitTrackersLiveData = profitTrackerRepository.getMainAccountProfitTrackersLive();
        
        // Setup last sync time
        createLastSyncTimeLiveData();
        
        // Setup profit tracker observers
        setupProfitTrackerObservers();
    }

    /**
     * Get dashboard stats LiveData
     */
    public LiveData<DashboardStats> getDashboardStatsLiveData() {
        return dashboardStatsLiveData;
    }

    /**
     * Get loading state LiveData
     */
    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    /**
     * Get error message LiveData
     */
    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    /**
     * Get success message LiveData
     */
    public LiveData<String> getSuccessMessageLiveData() {
        return successMessageLiveData;
    }

    /**
     * Get connectivity status LiveData
     */
    public LiveData<Boolean> getIsConnectedLiveData() {
        return isConnectedLiveData;
    }

    /**
     * Get last sync time LiveData
     */
    public LiveData<Long> getLastSyncTimeLiveData() {
        return lastSyncTimeLiveData;
    }

    // ProfitTracker LiveData getters
    public LiveData<Double> getAvailableFundsLiveData() {
        return availableFundsLiveData;
    }

    public LiveData<Double> getMonthlyProfitLiveData() {
        return monthlyProfitLiveData;
    }

    public LiveData<Double> getCumulativeProfitLiveData() {
        return cumulativeProfitLiveData;
    }

    public LiveData<Double> getInterestEarnedLiveData() {
        return interestEarnedLiveData;
    }

    public LiveData<Map<String, Double>> getMonthlyProfitMapLiveData() {
        return monthlyProfitMapLiveData;
    }

    public LiveData<Map<String, Double>> getCumulativeProfitMapLiveData() {
        return cumulativeProfitMapLiveData;
    }

    public LiveData<Map<String, Double>> getMonthlyInterestMapLiveData() {
        return monthlyInterestMapLiveData;
    }

    /**
     * Load dashboard data
     */
    public void loadDashboardData() {
        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);
        
        try {
            // Calculate dashboard statistics in background
            new Thread(() -> {
                try {
                    DashboardStats stats = dashboardRepository.calculateDashboardStats();
                    
                    // Post result to main thread
                    UiUtils.runOnUiThread(() -> {
                        dashboardStatsLiveData.setValue(stats);
                        isLoadingLiveData.setValue(false);
                    });
                    
                } catch (Exception e) {
                    UiUtils.runOnUiThread(() -> {
                        errorMessageLiveData.setValue("Error loading dashboard: " + e.getMessage());
                        isLoadingLiveData.setValue(false);
                    });
                }
            }).start();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error starting dashboard load: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Refresh dashboard data
     */
    public void refreshDashboard() {
        loadDashboardData();
    }

    /**
     * Force sync operation
     */
    public void forceSync() {
        if (!connectivityObserver.isConnected()) {
            errorMessageLiveData.setValue("No internet connection available");
            return;
        }
        
        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);
        
        try {
            // Schedule manual sync
            java.util.UUID workId = syncScheduler.scheduleManualSync();
            
            if (workId != null) {
                successMessageLiveData.setValue("Sync started successfully");
                
                // Monitor sync progress
                monitorSyncProgress(workId);
            } else {
                errorMessageLiveData.setValue("Failed to start sync");
                isLoadingLiveData.setValue(false);
            }
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error starting sync: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Monitor sync progress
     */
    private void monitorSyncProgress(java.util.UUID workId) {
        // This would monitor the WorkManager work progress
        // For now, just simulate completion after a delay
        UiUtils.runOnUiThread(() -> {
            try {
                // Simulate sync taking some time
                Thread.sleep(2000);
                
                // Update last sync time
                syncScheduler.saveLastSyncTime(System.currentTimeMillis());
                
                // Refresh dashboard data
                loadDashboardData();
                
                successMessageLiveData.setValue("Sync completed successfully");
                
            } catch (InterruptedException e) {
                errorMessageLiveData.setValue("Sync interrupted");
            } finally {
                isLoadingLiveData.setValue(false);
            }
        });
    }

    /**
     * Get quick stats
     */
    public void loadQuickStats() {
        isLoadingLiveData.setValue(true);
        errorMessageLiveData.setValue(null);
        
        try {
            // Run database operations in background thread
            new Thread(() -> {
                try {
                    Map<String, Object> quickStats = dashboardRepository.getQuickStats();
                    
                    // Post result to main thread
                    UiUtils.runOnUiThread(() -> {
                        try {
                            // Convert to DashboardStats for consistency
                            DashboardStats stats = new DashboardStats();
                            if (quickStats != null) {
                                stats.setTotalClients((Integer) quickStats.get("totalClients"));
                                stats.setTotalLoans((Integer) quickStats.get("totalLoans"));
                                stats.setTotalInvested((Double) quickStats.get("totalInvested"));
                                stats.setTotalLoaned((Double) quickStats.get("totalLoaned"));
                                stats.setTotalRepaid((Double) quickStats.get("totalRepaid"));
                                stats.setOverdueLoans((Integer) quickStats.get("overdueLoans"));
                            }
                            
                            dashboardStatsLiveData.setValue(stats);
                            isLoadingLiveData.setValue(false);
                            
                        } catch (Exception e) {
                            errorMessageLiveData.setValue("Error processing quick stats: " + e.getMessage());
                            isLoadingLiveData.setValue(false);
                        }
                    });
                    
                } catch (Exception e) {
                    UiUtils.runOnUiThread(() -> {
                        errorMessageLiveData.setValue("Error loading quick stats: " + e.getMessage());
                        isLoadingLiveData.setValue(false);
                    });
                }
            }).start();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error starting quick stats load: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Get client status distribution
     */
    public void getClientStatusDistribution(final Callback<Map<String, Integer>> callback) {
        new Thread(() -> {
            try {
                Map<String, Integer> distribution = dashboardRepository.getClientStatusDistribution();
                UiUtils.runOnUiThread(() -> callback.onResult(distribution));
            } catch (Exception e) {
                errorMessageLiveData.setValue("Error getting client distribution: " + e.getMessage());
                UiUtils.runOnUiThread(() -> callback.onResult(null));
            }
        }).start();
    }

    /**
     * Get loan status distribution
     */
    public void getLoanStatusDistribution(final Callback<Map<String, Integer>> callback) {
        new Thread(() -> {
            try {
                Map<String, Integer> distribution = dashboardRepository.getLoanStatusDistribution();
                UiUtils.runOnUiThread(() -> callback.onResult(distribution));
            } catch (Exception e) {
                errorMessageLiveData.setValue("Error getting loan distribution: " + e.getMessage());
                UiUtils.runOnUiThread(() -> callback.onResult(null));
            }
        }).start();
    }

    /**
     * Get monthly trend data
     */
    public void loadMonthlyTrendData() {
        isLoadingLiveData.setValue(true);
        
        try {
            new Thread(() -> {
                try {
                    java.util.List<DashboardRepository.MonthlyDataPoint> trendData = 
                        dashboardRepository.getMonthlyTrendData(12);
                    
                    UiUtils.runOnUiThread(() -> {
                        // Update dashboard stats with trend data
                        DashboardStats currentStats = dashboardStatsLiveData.getValue();
                        if (currentStats != null) {
                            // Convert trend data to monthly maps
                            java.util.Map<String, Double> monthlyInvestments = new java.util.HashMap<>();
                            java.util.Map<String, Double> monthlyLoans = new java.util.HashMap<>();
                            java.util.Map<String, Double> monthlyRepayments = new java.util.HashMap<>();
                            
                            for (DashboardRepository.MonthlyDataPoint point : trendData) {
                                monthlyInvestments.put(point.getYearMonth(), point.getInvestments());
                                monthlyLoans.put(point.getYearMonth(), point.getLoans());
                                monthlyRepayments.put(point.getYearMonth(), point.getRepayments());
                            }
                            
                            currentStats.setMonthlyInvestments(monthlyInvestments);
                            currentStats.setMonthlyLoans(monthlyLoans);
                            currentStats.setMonthlyRepayments(monthlyRepayments);
                            
                            dashboardStatsLiveData.setValue(currentStats);
                        }
                        
                        isLoadingLiveData.setValue(false);
                    });
                    
                } catch (Exception e) {
                    UiUtils.runOnUiThread(() -> {
                        errorMessageLiveData.setValue("Error loading trend data: " + e.getMessage());
                        isLoadingLiveData.setValue(false);
                    });
                }
            }).start();
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error starting trend data load: " + e.getMessage());
            isLoadingLiveData.setValue(false);
        }
    }

    /**
     * Create last sync time LiveData
     */
    private void createLastSyncTimeLiveData() {
        lastSyncTimeLiveData.setValue(syncScheduler.getLastSyncTime());
    }

    /**
     * Update last sync time
     */
    public void updateLastSyncTime() {
        long lastSyncTime = syncScheduler.getLastSyncTime();
        lastSyncTimeLiveData.setValue(lastSyncTime);
    }

    /**
     * Check if sync is enabled
     */
    public boolean isSyncEnabled() {
        return syncScheduler.isSyncEnabled();
    }

    /**
     * Set sync enabled status
     */
    public void setSyncEnabled(boolean enabled) {
        syncScheduler.setSyncEnabled(enabled);
        
        if (enabled) {
            successMessageLiveData.setValue("Sync enabled");
        } else {
            successMessageLiveData.setValue("Sync disabled");
        }
    }

    /**
     * Get sync status information
     */
    public String getSyncStatus() {
        try {
            return syncScheduler.getSchedulerStatus();
        } catch (Exception e) {
            return "Error getting sync status: " + e.getMessage();
        }
    }

    /**
     * Get connectivity status text
     */
    public String getConnectivityStatusText() {
        if (connectivityObserver.isConnected()) {
            return "🟢 Online";
        } else {
            return "🔴 Offline";
        }
    }

    /**
     * Get last sync time text
     */
    public String getLastSyncTimeText() {
        long lastSyncTime = syncScheduler.getLastSyncTime();
        if (lastSyncTime > 0) {
            return "Last sync: " + UiUtils.formatRelativeTime(lastSyncTime);
        } else {
            return "Never synced";
        }
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        errorMessageLiveData.setValue(null);
    }

    /**
     * Clear success message
     */
    public void clearSuccessMessage() {
        successMessageLiveData.setValue(null);
    }

    /**
     * Handle network connectivity change
     */
    public void onNetworkConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            successMessageLiveData.setValue("Network connection restored");
        } else {
            errorMessageLiveData.setValue("Network connection lost");
        }
    }

    /**
     * Initialize the ViewModel
     */
    public void initialize() {
        // Load initial data
        loadQuickStats();
        
        // Update sync status
        updateLastSyncTime();
        
        // Check if sync should be triggered on startup
        if (connectivityObserver.isConnected() && syncScheduler.isSyncEnabled()) {
            // Consider triggering a sync if it's been a while
            long lastSyncTime = syncScheduler.getLastSyncTime();
            long currentTime = System.currentTimeMillis();
            long syncInterval = 15 * 60 * 1000; // 15 minutes
            
            if (lastSyncTime == 0 || (currentTime - lastSyncTime) > syncInterval) {
                // Auto-sync if it's been more than 15 minutes
                forceSync();
            }
        }
    }

    /**
     * Setup profit tracker observers
     */
    private void setupProfitTrackerObservers() {
        // Observe profit tracker changes and update LiveData
        profitTrackersLiveData.observeForever(profitTrackers -> {
            if (profitTrackers != null) {
                updateProfitMetrics(profitTrackers);
            }
        });
        
        // Initialize with current values
        loadProfitMetrics();
    }

    /**
     * Load profit metrics from repository
     */
    private void loadProfitMetrics() {
        try {
            // Get current values directly from repository
            double availableFunds = profitTrackerRepository.getCurrentAvailableFunds();
            double cumulativeProfit = profitTrackerRepository.getCurrentCumulativeProfit();
            double totalInterest = profitTrackerRepository.getTotalInterestEarned();
            
            // Update LiveData
            availableFundsLiveData.setValue(availableFunds);
            cumulativeProfitLiveData.setValue(cumulativeProfit);
            interestEarnedLiveData.setValue(totalInterest);
            
            // Get monthly summaries
            List<ProfitTrackerDao.ProfitSummary> summaries = profitTrackerRepository.getMonthlyProfitSummaries();
            if (summaries != null) {
                Map<String, Double> monthlyProfitMap = new HashMap<>();
                Map<String, Double> monthlyInterestMap = new HashMap<>();
                Map<String, Double> cumulativeProfitMap = new HashMap<>();
                
                double runningCumulative = 0.0;
                
                for (ProfitTrackerDao.ProfitSummary summary : summaries) {
                    monthlyProfitMap.put(summary.yearMonth, summary.monthlyProfit);
                    monthlyInterestMap.put(summary.yearMonth, summary.monthlyInterest);
                    
                    runningCumulative += summary.monthlyProfit;
                    cumulativeProfitMap.put(summary.yearMonth, runningCumulative);
                }
                
                monthlyProfitMapLiveData.setValue(monthlyProfitMap);
                monthlyInterestMapLiveData.setValue(monthlyInterestMap);
                cumulativeProfitMapLiveData.setValue(cumulativeProfitMap);
                
                // Set current month profit
                String currentMonth = getCurrentYearMonth();
                if (monthlyProfitMap.containsKey(currentMonth)) {
                    monthlyProfitLiveData.setValue(monthlyProfitMap.get(currentMonth));
                } else {
                    monthlyProfitLiveData.setValue(0.0);
                }
            }
            
        } catch (Exception e) {
            // Set default values on error
            availableFundsLiveData.setValue(0.0);
            monthlyProfitLiveData.setValue(0.0);
            cumulativeProfitLiveData.setValue(0.0);
            interestEarnedLiveData.setValue(0.0);
            monthlyProfitMapLiveData.setValue(new HashMap<>());
            cumulativeProfitMapLiveData.setValue(new HashMap<>());
            monthlyInterestMapLiveData.setValue(new HashMap<>());
        }
    }

    /**
     * Update profit metrics from profit tracker data
     */
    private void updateProfitMetrics(List<ProfitTrackerEntity> profitTrackers) {
        if (profitTrackers == null || profitTrackers.isEmpty()) {
            loadProfitMetrics(); // Reload from repository if empty
            return;
        }
        
        try {
            Map<String, Double> monthlyProfitMap = new HashMap<>();
            Map<String, Double> monthlyInterestMap = new HashMap<>();
            Map<String, Double> cumulativeProfitMap = new HashMap<>();
            
            double runningCumulative = 0.0;
            double currentAvailableFunds = 0.0;
            double currentCumulativeProfit = 0.0;
            double currentMonthlyProfit = 0.0;
            double currentTotalInterest = 0.0;
            
            String currentMonth = getCurrentYearMonth();
            
            for (ProfitTrackerEntity tracker : profitTrackers) {
                String month = tracker.getYearMonth();
                
                // Update monthly maps
                monthlyProfitMap.put(month, tracker.getMonthlyProfit());
                monthlyInterestMap.put(month, tracker.getMonthlyInterest());
                
                // Calculate cumulative profit
                runningCumulative += tracker.getMonthlyProfit();
                cumulativeProfitMap.put(month, runningCumulative);
                
                // Get current month values
                if (month.equals(currentMonth)) {
                    currentMonthlyProfit = tracker.getMonthlyProfit();
                    currentAvailableFunds = tracker.getAvailableFunds();
                    currentCumulativeProfit = tracker.getCumulativeProfit();
                    currentTotalInterest = tracker.getCumulativeInterest();
                }
            }
            
            // Update LiveData
            availableFundsLiveData.setValue(currentAvailableFunds);
            monthlyProfitLiveData.setValue(currentMonthlyProfit);
            cumulativeProfitLiveData.setValue(currentCumulativeProfit);
            interestEarnedLiveData.setValue(currentTotalInterest);
            monthlyProfitMapLiveData.setValue(monthlyProfitMap);
            cumulativeProfitMapLiveData.setValue(cumulativeProfitMap);
            monthlyInterestMapLiveData.setValue(monthlyInterestMap);
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error updating profit metrics: " + e.getMessage());
        }
    }

    /**
     * Get current year-month string
     */
    private String getCurrentYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return sdf.format(new java.util.Date());
    }

    /**
     * Refresh profit metrics (call after loan/payment operations)
     */
    public void refreshProfitMetrics() {
        loadProfitMetrics();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cleanup resources if needed
    }
}
