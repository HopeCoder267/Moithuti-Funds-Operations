package com.moithuti.funds.sync;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.work.WorkInfo;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkInfo;

import com.moithuti.funds.util.Constants;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Sync Scheduler - Manages WorkManager sync scheduling
 * Handles periodic, manual, and constrained sync operations
 */
public class SyncScheduler {

    private static final String TAG = "SyncScheduler";
    
    private static final String UNIQUE_PERIODIC_WORK = "periodic_sync_work";
    private static final String UNIQUE_MANUAL_WORK = "manual_sync_work";
    private static final String UNIQUE_INITIALIZER_WORK = "initializer_sync_work";
    
    private static SyncScheduler instance;
    private final Context context;
    private final WorkManager workManager;

    /**
     * Get singleton instance
     * @param context application context
     * @return scheduler instance
     */
    public static synchronized SyncScheduler getInstance(Context context) {
        if (instance == null) {
            instance = new SyncScheduler(context);
        }
        return instance;
    }

    private SyncScheduler(Context context) {
        this.context = context.getApplicationContext();
        this.workManager = WorkManager.getInstance(context);
    }

    /**
     * Schedule periodic sync work
     * @return true if scheduled successfully
     */
    public boolean schedulePeriodicSync() {
        try {
            // Create constraints for periodic sync
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .setRequiresStorageNotLow(true)
                    .build();

            // Create periodic work request
            PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(
                    SyncWorker.class,
                    Constants.SYNC_INTERVAL_HOURS,
                    TimeUnit.HOURS
            )
            .setConstraints(constraints)
            .addTag(Constants.WORK_TAG_SYNC)
            .addTag(Constants.WORK_TAG_PERIODIC)
            .build();

            // Enqueue unique periodic work
            workManager.enqueueUniquePeriodicWork(
                    UNIQUE_PERIODIC_WORK,
                    ExistingPeriodicWorkPolicy.KEEP,
                    periodicWork
            );

            Log.d(TAG, "Periodic sync scheduled successfully");
            return true;

        } catch (Exception e) {
            Log.e(TAG, "Error scheduling periodic sync", e);
            return false;
        }
    }

    /**
     * Schedule manual sync work (one-time)
     * @return UUID of the work request
     */
    public UUID scheduleManualSync() {
        try {
            // Create constraints for manual sync
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(false) // Allow manual sync even if battery is low
                    .build();

            // Create one-time work request
            OneTimeWorkRequest manualWork = new OneTimeWorkRequest.Builder(SyncWorker.class)
                    .setConstraints(constraints)
                    .addTag(Constants.WORK_TAG_SYNC)
                    .addTag(Constants.WORK_TAG_ONE_TIME)
                    .build();

            // Enqueue unique manual work
            workManager.enqueueUniqueWork(
                    UNIQUE_MANUAL_WORK,
                    ExistingWorkPolicy.REPLACE,
                    manualWork
            );

            Log.d(TAG, "Manual sync scheduled: " + manualWork.getId());
            return manualWork.getId();

        } catch (Exception e) {
            Log.e(TAG, "Error scheduling manual sync", e);
            return null;
        }
    }

    /**
     * Schedule immediate sync work (no constraints)
     * @return UUID of the work request
     */
    public UUID scheduleImmediateSync() {
        try {
            // Create one-time work request without constraints
            OneTimeWorkRequest immediateWork = new OneTimeWorkRequest.Builder(SyncWorker.class)
                    .addTag(Constants.WORK_TAG_SYNC)
                    .addTag(Constants.WORK_TAG_ONE_TIME)
                    .build();

            // Enqueue unique work
            workManager.enqueueUniqueWork(
                    UNIQUE_MANUAL_WORK,
                    ExistingWorkPolicy.REPLACE,
                    immediateWork
            );

            Log.d(TAG, "Immediate sync scheduled: " + immediateWork.getId());
            return immediateWork.getId();

        } catch (Exception e) {
            Log.e(TAG, "Error scheduling immediate sync", e);
            return null;
        }
    }

    /**
     * Schedule initialization work (runs once to setup sheets)
     * @return UUID of the work request
     */
    public UUID scheduleInitializationWork() {
        try {
            // Create constraints for initialization
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            // Create one-time work request
            OneTimeWorkRequest initWork = new OneTimeWorkRequest.Builder(SyncWorker.class)
                    .setConstraints(constraints)
                    .addTag(Constants.WORK_TAG_SYNC)
                    .addTag(Constants.WORK_TAG_ONE_TIME)
                    .build();

            // Enqueue unique initialization work
            workManager.enqueueUniqueWork(
                    UNIQUE_INITIALIZER_WORK,
                    ExistingWorkPolicy.KEEP,
                    initWork
            );

            Log.d(TAG, "Initialization work scheduled: " + initWork.getId());
            return initWork.getId();

        } catch (Exception e) {
            Log.e(TAG, "Error scheduling initialization work", e);
            return null;
        }
    }

    /**
     * Cancel all sync work
     */
    public void cancelAllSyncWork() {
        try {
            workManager.cancelAllWorkByTag(Constants.WORK_TAG_SYNC);
            Log.d(TAG, "All sync work cancelled");
        } catch (Exception e) {
            Log.e(TAG, "Error cancelling sync work", e);
        }
    }

    /**
     * Cancel periodic sync work
     */
    public void cancelPeriodicSync() {
        try {
            workManager.cancelUniqueWork(UNIQUE_PERIODIC_WORK);
            Log.d(TAG, "Periodic sync work cancelled");
        } catch (Exception e) {
            Log.e(TAG, "Error cancelling periodic sync work", e);
        }
    }

    /**
     * Cancel manual sync work
     */
    public void cancelManualSync() {
        try {
            workManager.cancelUniqueWork(UNIQUE_MANUAL_WORK);
            Log.d(TAG, "Manual sync work cancelled");
        } catch (Exception e) {
            Log.e(TAG, "Error cancelling manual sync work", e);
        }
    }

    /**
     * Get status of periodic sync work
     * @return LiveData with work info
     */
    public LiveData<List<WorkInfo>> getPeriodicSyncStatus() {
        return workManager.getWorkInfosByTagLiveData(Constants.WORK_TAG_PERIODIC);
    }

    /**
     * Get status of manual sync work
     * @return LiveData with work info
     */
    public LiveData<List<WorkInfo>> getManualSyncStatus() {
        return workManager.getWorkInfosByTagLiveData(Constants.WORK_TAG_ONE_TIME);
    }

    /**
     * Get status of all sync work
     * @return LiveData with work info
     */
    public LiveData<List<WorkInfo>> getAllSyncStatus() {
        return workManager.getWorkInfosByTagLiveData(Constants.WORK_TAG_SYNC);
    }

    /**
     * Check if periodic sync is scheduled
     * @return true if periodic sync is scheduled
     */
    public boolean isPeriodicSyncScheduled() {
        try {
            List<WorkInfo> workInfos = workManager.getWorkInfosByTag(Constants.WORK_TAG_PERIODIC).get();
            return !workInfos.isEmpty() && 
                   workInfos.get(0).getState() != WorkInfo.State.CANCELLED;
        } catch (Exception e) {
            Log.e(TAG, "Error checking periodic sync status", e);
            return false;
        }
    }

    /**
     * Check if any sync work is running
     * @return true if sync work is currently running
     */
    public boolean isSyncRunning() {
        try {
            List<WorkInfo> workInfos = workManager.getWorkInfosByTag(Constants.WORK_TAG_SYNC).get();
            for (WorkInfo workInfo : workInfos) {
                if (workInfo.getState() == WorkInfo.State.RUNNING) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error checking sync running status", e);
            return false;
        }
    }

    /**
     * Get work info by UUID
     * @param workId the work UUID
     * @return WorkInfo or null
     */
    public WorkInfo getWorkInfo(UUID workId) {
        try {
            return workManager.getWorkInfoById(workId).get();
        } catch (Exception e) {
            Log.e(TAG, "Error getting work info", e);
            return null;
        }
    }

    /**
     * Get last sync time from preferences
     * @return last sync timestamp or 0
     */
    public long getLastSyncTime() {
        try {
            android.content.SharedPreferences prefs = context.getSharedPreferences(
                    Constants.PREFS_NAME, Context.MODE_PRIVATE);
            return prefs.getLong(Constants.PREF_LAST_SYNC_TIME, 0);
        } catch (Exception e) {
            Log.e(TAG, "Error getting last sync time", e);
            return 0;
        }
    }

    /**
     * Save last sync time to preferences
     * @param timestamp the sync timestamp
     */
    public void saveLastSyncTime(long timestamp) {
        try {
            android.content.SharedPreferences prefs = context.getSharedPreferences(
                    Constants.PREFS_NAME, Context.MODE_PRIVATE);
            prefs.edit().putLong(Constants.PREF_LAST_SYNC_TIME, timestamp).apply();
            Log.d(TAG, "Last sync time saved: " + timestamp);
        } catch (Exception e) {
            Log.e(TAG, "Error saving last sync time", e);
        }
    }

    /**
     * Check if sync is enabled in preferences
     * @return true if sync is enabled
     */
    public boolean isSyncEnabled() {
        try {
            android.content.SharedPreferences prefs = context.getSharedPreferences(
                    Constants.PREFS_NAME, Context.MODE_PRIVATE);
            return prefs.getBoolean(Constants.PREF_SYNC_ENABLED, true);
        } catch (Exception e) {
            Log.e(TAG, "Error checking sync enabled status", e);
            return true; // Default to enabled
        }
    }

    /**
     * Set sync enabled status in preferences
     * @param enabled whether sync should be enabled
     */
    public void setSyncEnabled(boolean enabled) {
        try {
            android.content.SharedPreferences prefs = context.getSharedPreferences(
                    Constants.PREFS_NAME, Context.MODE_PRIVATE);
            prefs.edit().putBoolean(Constants.PREF_SYNC_ENABLED, enabled).apply();
            
            if (enabled) {
                schedulePeriodicSync();
            } else {
                cancelPeriodicSync();
            }
            
            Log.d(TAG, "Sync enabled status set to: " + enabled);
        } catch (Exception e) {
            Log.e(TAG, "Error setting sync enabled status", e);
        }
    }

    /**
     * Get scheduler status information
     * @return status string
     */
    public String getSchedulerStatus() {
        StringBuilder status = new StringBuilder();
        
        status.append("Sync Scheduler Status:\n");
        status.append("Periodic Sync Scheduled: ").append(isPeriodicSyncScheduled()).append("\n");
        status.append("Sync Currently Running: ").append(isSyncRunning()).append("\n");
        status.append("Sync Enabled: ").append(isSyncEnabled()).append("\n");
        status.append("Last Sync Time: ").append(getLastSyncTime()).append("\n");
        
        // Get work info counts
        try {
            List<WorkInfo> allWork = workManager.getWorkInfosByTag(Constants.WORK_TAG_SYNC).get();
            status.append("Total Sync Workers: ").append(allWork.size()).append("\n");
            
            int running = 0, succeeded = 0, failed = 0, cancelled = 0, enqueued = 0;
            for (WorkInfo workInfo : allWork) {
                switch (workInfo.getState()) {
                    case RUNNING: running++; break;
                    case SUCCEEDED: succeeded++; break;
                    case FAILED: failed++; break;
                    case CANCELLED: cancelled++; break;
                    case ENQUEUED: enqueued++; break;
                }
            }
            
            status.append("  - Running: ").append(running).append("\n");
            status.append("  - Succeeded: ").append(succeeded).append("\n");
            status.append("  - Failed: ").append(failed).append("\n");
            status.append("  - Cancelled: ").append(cancelled).append("\n");
            status.append("  - Enqueued: ").append(enqueued).append("\n");
            
        } catch (Exception e) {
            status.append("Error getting work info: ").append(e.getMessage()).append("\n");
        }
        
        return status.toString();
    }

    /**
     * Initialize scheduler on app startup
     */
    public void initialize() {
        try {
            Log.d(TAG, "Initializing sync scheduler");
            
            // Check if sync is enabled
            if (isSyncEnabled()) {
                schedulePeriodicSync();
            }
            
            // Schedule initialization work if this is first run
            android.content.SharedPreferences prefs = context.getSharedPreferences(
                    Constants.PREFS_NAME, Context.MODE_PRIVATE);
            boolean firstRun = prefs.getBoolean(Constants.PREF_FIRST_RUN, true);
            
            if (firstRun) {
                scheduleInitializationWork();
                prefs.edit().putBoolean(Constants.PREF_FIRST_RUN, false).apply();
                Log.d(TAG, "First run detected - initialization work scheduled");
            }
            
            Log.d(TAG, "Sync scheduler initialized successfully");
            
        } catch (Exception e) {
            Log.e(TAG, "Error initializing sync scheduler", e);
        }
    }

    /**
     * Cleanup resources
     */
    public void cleanup() {
        Log.d(TAG, "Sync scheduler cleaned up");
    }
}
