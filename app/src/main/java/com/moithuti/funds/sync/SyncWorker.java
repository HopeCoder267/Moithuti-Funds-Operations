package com.moithuti.funds.sync;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.moithuti.funds.data.remote.SyncRepository;
import com.moithuti.funds.util.Constants;

/**
 * Sync Worker - Background worker for synchronization
 * Handles periodic and manual sync operations using WorkManager
 */
public class SyncWorker extends Worker {

    private static final String TAG = "SyncWorker";
    
    private final Context context;
    private SyncRepository syncRepository;

    /**
     * Constructor
     * @param context application context
     * @param workerParams worker parameters
     */
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    /**
     * Perform the sync work
     * @return Result of the work
     */
    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Starting sync worker");
        
        try {
            // Initialize sync repository
            if (syncRepository == null) {
                syncRepository = SyncRepository.getInstance(context);
            }
            
            // Check if sync is ready
            if (!syncRepository.isSyncReady()) {
                Log.w(TAG, "Sync not ready - skipping sync");
                return Result.failure();
            }
            
            // Perform the synchronization
            boolean success = syncRepository.performFullSync();
            
            if (success) {
                Log.d(TAG, "Sync worker completed successfully");
                return Result.success();
            } else {
                Log.e(TAG, "Sync worker failed");
                return Result.failure();
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error in sync worker", e);
            return Result.failure();
        }
    }

    /**
     * Called when work is stopped
     */
    @Override
    public void onStopped() {
        super.onStopped();
        Log.d(TAG, "Sync worker stopped");
        
        // Cleanup resources
        if (syncRepository != null) {
            syncRepository.cleanup();
        }
    }

    /**
     * Create one-time sync worker
     * @param context application context
     * @return OneTimeWorkRequest for sync
     */
    public static androidx.work.OneTimeWorkRequest createOneTimeSyncRequest() {
        return new androidx.work.OneTimeWorkRequest.Builder(SyncWorker.class)
                .addTag(Constants.WORK_TAG_SYNC)
                .addTag(Constants.WORK_TAG_ONE_TIME)
                .build();
    }

    /**
     * Create periodic sync worker
     * @param context application context
     * @return PeriodicWorkRequest for sync
     */
    public static androidx.work.PeriodicWorkRequest createPeriodicSyncRequest() {
        return new androidx.work.PeriodicWorkRequest.Builder(
                SyncWorker.class, 
                Constants.SYNC_INTERVAL_HOURS, 
                java.util.concurrent.TimeUnit.HOURS
        )
        .addTag(Constants.WORK_TAG_SYNC)
        .addTag(Constants.WORK_TAG_PERIODIC)
        .build();
    }

    /**
     * Create constrained sync worker (only when network is available)
     * @param context application context
     * @return OneTimeWorkRequest with network constraint
     */
    public static androidx.work.OneTimeWorkRequest createConstrainedSyncRequest() {
        androidx.work.Constraints constraints = new androidx.work.Constraints.Builder()
                .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();
        
        return new androidx.work.OneTimeWorkRequest.Builder(SyncWorker.class)
                .setConstraints(constraints)
                .addTag(Constants.WORK_TAG_SYNC)
                .addTag(Constants.WORK_TAG_ONE_TIME)
                .build();
    }

    /**
     * Get sync status
     * @param context application context
     * @return sync status information
     */
    public static String getSyncStatus(Context context) {
        try {
            SyncRepository syncRepo = SyncRepository.getInstance(context);
            return syncRepo.getSyncStatus();
        } catch (Exception e) {
            return "Error getting sync status: " + e.getMessage();
        }
    }

    /**
     * Test sync connection
     * @param context application context
     * @return true if connection test passes
     */
    public static boolean testSyncConnection(Context context) {
        try {
            SyncRepository syncRepo = SyncRepository.getInstance(context);
            return syncRepo.testConnection();
        } catch (Exception e) {
            Log.e(TAG, "Error testing sync connection", e);
            return false;
        }
    }

    /**
     * Initialize sync sheets
     * @param context application context
     * @return true if initialization successful
     */
    public static boolean initializeSyncSheets(Context context) {
        try {
            SyncRepository syncRepo = SyncRepository.getInstance(context);
            return syncRepo.initializeSheets();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing sync sheets", e);
            return false;
        }
    }
}
