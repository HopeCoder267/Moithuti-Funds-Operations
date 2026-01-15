package com.moithuti.funds.ui.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Connectivity Observer - Monitors network connectivity status
 * Provides LiveData for connectivity changes and sync triggers
 */
public class ConnectivityObserver extends LiveData<Boolean> {

    private static final String TAG = "ConnectivityObserver";
    
    private final Context context;
    private final ConnectivityManager connectivityManager;
    private final ConnectivityManager.NetworkCallback networkCallback;
    
    /**
     * Constructor
     * @param context application context
     */
    public ConnectivityObserver(Context context) {
        this.context = context.getApplicationContext();
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        // Create network callback
        this.networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.d(TAG, "Network available: " + network);
                postValue(true);
                
                // Trigger sync when network becomes available
                triggerSyncOnNetworkAvailable();
            }
            
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.d(TAG, "Network lost: " + network);
                postValue(false);
            }
            
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                boolean hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                                   networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                Log.d(TAG, "Network capabilities changed - has internet: " + hasInternet);
                postValue(hasInternet);
            }
        };
    }

    /**
     * Check current connectivity status
     * @return true if connected to internet
     */
    private boolean isCurrentlyConnected() {
        if (connectivityManager == null) {
            return false;
        }
        
        try {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null) {
                return false;
            }
            
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            return capabilities != null && 
                   capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                   capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                   
        } catch (Exception e) {
            Log.e(TAG, "Error checking connectivity", e);
            return false;
        }
    }

    /**
     * Trigger sync when network becomes available
     */
    private void triggerSyncOnNetworkAvailable() {
        try {
            // This would trigger the sync scheduler to perform a sync
            // Implementation would depend on the sync system
            Log.d(TAG, "Network available - triggering sync");
            
            // For now, just log - actual sync trigger would be implemented here
            // SyncScheduler.getInstance(context).scheduleManualSync();
            
        } catch (Exception e) {
            Log.e(TAG, "Error triggering sync on network available", e);
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "Connectivity observer activated");
        
        // Register network callback
        if (connectivityManager != null) {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    .build();
            
            connectivityManager.registerNetworkCallback(request, networkCallback);
            
            // Set initial value
            postValue(isCurrentlyConnected());
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "Connectivity observer deactivated");
        
        // Unregister network callback
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    /**
     * Get connectivity status synchronously
     * @return true if connected
     */
    public boolean isConnected() {
        return isCurrentlyConnected();
    }

    /**
     * Get connection type
     * @return connection type string
     */
    public String getConnectionType() {
        if (!isCurrentlyConnected()) {
            return "Disconnected";
        }
        
        try {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null) {
                return "Unknown";
            }
            
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            if (capabilities == null) {
                return "Unknown";
            }
            
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return "WiFi";
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return "Cellular";
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return "Ethernet";
            } else {
                return "Other";
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting connection type", e);
            return "Error";
        }
    }

    /**
     * Check if connection is metered
     * @return true if connection is metered
     */
    public boolean isMeteredConnection() {
        if (!isCurrentlyConnected()) {
            return false;
        }
        
        try {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null) {
                return false;
            }
            
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            return capabilities != null && !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
            
        } catch (Exception e) {
            Log.e(TAG, "Error checking if connection is metered", e);
            return false;
        }
    }

    /**
     * Get detailed connectivity information
     * @return detailed status string
     */
    public String getDetailedStatus() {
        StringBuilder status = new StringBuilder();
        
        status.append("Connectivity Status:\n");
        status.append("Connected: ").append(isConnected()).append("\n");
        status.append("Connection Type: ").append(getConnectionType()).append("\n");
        status.append("Metered: ").append(isMeteredConnection()).append("\n");
        
        if (isConnected()) {
            try {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if (activeNetwork != null) {
                    status.append("Network: ").append(activeNetwork.toString()).append("\n");
                    
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                    if (capabilities != null) {
                        status.append("Capabilities:\n");
                        status.append("  - Internet: ").append(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).append("\n");
                        status.append("  - Validated: ").append(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)).append("\n");
                        status.append("  - Not Metered: ").append(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)).append("\n");
                        status.append("  - Foreground: ").append(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_FOREGROUND)).append("\n");
                    }
                }
            } catch (Exception e) {
                status.append("Error getting detailed info: ").append(e.getMessage()).append("\n");
            }
        }
        
        return status.toString();
    }

    /**
     * Check if network is suitable for sync
     * @return true if network is suitable for sync operations
     */
    public boolean isNetworkSuitableForSync() {
        return isConnected() && !isMeteredConnection();
    }

    /**
     * Get singleton instance
     * @param context application context
     * @return connectivity observer instance
     */
    public static ConnectivityObserver getInstance(Context context) {
        // This could be implemented as a singleton if needed
        return new ConnectivityObserver(context);
    }
}
