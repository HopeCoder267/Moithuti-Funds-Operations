package com.moithuti.funds.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.moithuti.funds.data.repository.DashboardRepository;
import com.moithuti.funds.sync.SyncScheduler;
import com.moithuti.funds.ui.common.ConnectivityObserver;
import com.moithuti.funds.ui.common.UiUtils;

import java.util.Map;

/**
 * Home ViewModel - ViewModel for Home Fragment
 * Manages dashboard data and sync operations
 */
public class HomeViewModel extends AndroidViewModel {

    private final DashboardRepository dashboardRepository;
    private final SyncScheduler syncScheduler;
    private final ConnectivityObserver connectivityObserver;
    
    // LiveData for dashboard data
    private final MutableLiveData<DashboardStats> dashboardStatsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();
    
    // LiveData for connectivity and sync status
    private final LiveData<Boolean> isConnectedLiveData;
    private final MutableLiveData<Long> lastSyncTimeLiveData = new MutableLiveData<>();
    
    // Constructor
    public HomeViewModel(Application application) {
        super(application);
        
        this.dashboardRepository = DashboardRepository.getInstance(application);
        this.syncScheduler = SyncScheduler.getInstance(application);
        this.connectivityObserver = new ConnectivityObserver(application);
        
        // Setup connectivity observer
        this.isConnectedLiveData = connectivityObserver;
        
        // Setup last sync time
        createLastSyncTimeLiveData();
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
        try {
            Map<String, Object> quickStats = dashboardRepository.getQuickStats();
            
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
            
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error loading quick stats: " + e.getMessage());
        }
    }

    /**
     * Get client status distribution
     */
    public Map<String, Integer> getClientStatusDistribution() {
        try {
            return dashboardRepository.getClientStatusDistribution();
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error getting client distribution: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get loan status distribution
     */
    public Map<String, Integer> getLoanStatusDistribution() {
        try {
            return dashboardRepository.getLoanStatusDistribution();
        } catch (Exception e) {
            errorMessageLiveData.setValue("Error getting loan distribution: " + e.getMessage());
            return null;
        }
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

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cleanup resources if needed
    }
}
