package com.moithuti.funds;

import android.app.Application;

import androidx.work.Configuration;
import androidx.work.WorkManager;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.sync.SyncWorker;

/**
 * Application class for Moithuti Funds Operations
 * Initializes database and WorkManager for background sync
 */
public class MoithutiFundsApp extends Application implements Configuration.Provider {

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize database
        database = AppDatabase.getInstance(this);
    }

    public AppDatabase getDatabase() {
        return database;
    }

    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();
    }
}
