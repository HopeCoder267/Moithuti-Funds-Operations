package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.ClientDao;
import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.sync.SyncStatus;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.UuidUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client Repository - Repository pattern for client data access
 * Provides clean API for client operations and handles sync triggers
 */
public class ClientRepository {

    private static final String TAG = "ClientRepository";
    
    private final ClientDao clientDao;
    private final ExecutorService executorService;
    private final Application application;

    // Singleton instance
    private static ClientRepository instance;

    /**
     * Get singleton instance
     * @param application application context
     * @return repository instance
     */
    public static synchronized ClientRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ClientRepository(application);
        }
        return instance;
    }

    private ClientRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.clientDao = database.clientDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Insert new client
     * @param client the client to insert
     */
    public void insertClient(ClientEntity client) {
        executorService.execute(() -> {
            try {
                // Ensure UUID is set
                if (UuidUtil.isEmpty(client.getUuid())) {
                    client.setUuid(UuidUtil.generateClientUuid());
                }
                
                // Set timestamps and sync status
                client.setCreatedDate(System.currentTimeMillis());
                client.setLastModified(System.currentTimeMillis());
                client.setSyncStatus(SyncStatus.PENDING.name());
                client.setDeleted(false);
                
                long result = clientDao.insert(client);
                Log.d(TAG, "Client inserted: " + client.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error inserting client", e);
            }
        });
    }

    /**
     * Update existing client
     * @param client the client to update
     */
    public void updateClient(ClientEntity client) {
        executorService.execute(() -> {
            try {
                // Update timestamp and sync status
                client.setLastModified(System.currentTimeMillis());
                client.setSyncStatus(SyncStatus.PENDING.name());
                
                int result = clientDao.update(client);
                Log.d(TAG, "Client updated: " + client.getUuid() + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error updating client", e);
            }
        });
    }

    /**
     * Delete client (soft delete)
     * @param clientId the client ID to delete
     */
    public void deleteClient(String clientId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = clientDao.softDelete(clientId, timestamp);
                Log.d(TAG, "Client soft deleted: " + clientId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error deleting client", e);
            }
        });
    }

    /**
     * Get client by ID
     * @param clientId the client ID
     * @return client entity
     */
    public ClientEntity getClientById(String clientId) {
        try {
            return clientDao.getClientById(clientId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting client by ID", e);
            return null;
        }
    }

    /**
     * Get client by ID (LiveData)
     * @param clientId the client ID
     * @return LiveData client
     */
    public LiveData<ClientEntity> getClientByIdLive(String clientId) {
        return clientDao.getClientByIdLive(clientId);
    }

    /**
     * Get all clients
     * @return list of all clients
     */
    public List<ClientEntity> getAllClients() {
        try {
            return clientDao.getAllClients();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all clients", e);
            return null;
        }
    }

    /**
     * Get all clients (LiveData)
     * @return LiveData list of clients
     */
    public LiveData<List<ClientEntity>> getAllClientsLive() {
        return clientDao.getAllClientsLive();
    }

    /**
     * Search clients by name
     * @param searchQuery the search query
     * @return LiveData list of matching clients
     */
    public LiveData<List<ClientEntity>> searchClients(String searchQuery) {
        return clientDao.searchClients(searchQuery);
    }

    /**
     * Get clients by status
     * @param status the client status
     * @return LiveData list of clients with specified status
     */
    public LiveData<List<ClientEntity>> getClientsByStatus(String status) {
        return clientDao.getClientsByStatus(status);
    }

    /**
     * Get clients by multiple statuses
     * @param statuses list of statuses
     * @return LiveData list of clients with specified statuses
     */
    public LiveData<List<ClientEntity>> getClientsByStatuses(List<String> statuses) {
        return clientDao.getClientsByStatuses(statuses);
    }

    /**
     * Get clients pending sync
     * @return list of clients pending sync
     */
    public List<ClientEntity> getPendingSyncClients() {
        try {
            return clientDao.getPendingSyncClients();
        } catch (Exception e) {
            Log.e(TAG, "Error getting pending sync clients", e);
            return null;
        }
    }

    /**
     * Get clients with failed sync
     * @return list of clients with failed sync
     */
    public List<ClientEntity> getFailedSyncClients() {
        try {
            return clientDao.getFailedSyncClients();
        } catch (Exception e) {
            Log.e(TAG, "Error getting failed sync clients", e);
            return null;
        }
    }

    /**
     * Update sync status for client
     * @param clientId the client ID
     * @param syncStatus the new sync status
     */
    public void updateSyncStatus(String clientId, String syncStatus) {
        executorService.execute(() -> {
            try {
                clientDao.updateSyncStatus(clientId, syncStatus);
                Log.d(TAG, "Sync status updated for client: " + clientId + " to " + syncStatus);
            } catch (Exception e) {
                Log.e(TAG, "Error updating sync status", e);
            }
        });
    }

    /**
     * Get client count
     * @return total number of clients
     */
    public int getClientCount() {
        try {
            return clientDao.getClientCount();
        } catch (Exception e) {
            Log.e(TAG, "Error getting client count", e);
            return 0;
        }
    }

    /**
     * Get client count by status
     * @param status the client status
     * @return number of clients with specified status
     */
    public int getClientCountByStatus(String status) {
        try {
            return clientDao.getClientCountByStatus(status);
        } catch (Exception e) {
            Log.e(TAG, "Error getting client count by status", e);
            return 0;
        }
    }

    /**
     * Restore deleted client
     * @param clientId the client ID to restore
     */
    public void restoreClient(String clientId) {
        executorService.execute(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                int result = clientDao.restoreClient(clientId, timestamp);
                Log.d(TAG, "Client restored: " + clientId + ", result: " + result);
                
                // Trigger sync
                triggerSync();
                
            } catch (Exception e) {
                Log.e(TAG, "Error restoring client", e);
            }
        });
    }

    /**
     * Update client status
     * @param clientId the client ID
     * @param newStatus the new status
     */
    public void updateClientStatus(String clientId, String newStatus) {
        executorService.execute(() -> {
            try {
                ClientEntity client = clientDao.getClientById(clientId);
                if (client != null) {
                    client.setStatus(newStatus);
                    client.setLastModified(System.currentTimeMillis());
                    client.setSyncStatus(SyncStatus.PENDING.name());
                    
                    int result = clientDao.update(client);
                    Log.d(TAG, "Client status updated: " + clientId + " to " + newStatus + ", result: " + result);
                    
                    // Trigger sync
                    triggerSync();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error updating client status", e);
            }
        });
    }

    /**
     * Validate client data
     * @param client the client to validate
     * @return true if valid, false otherwise
     */
    public boolean validateClient(ClientEntity client) {
        if (client == null) {
            return false;
        }
        
        // Check required fields
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            Log.w(TAG, "Client validation failed: name is empty");
            return false;
        }
        
        if (client.getName().length() > Constants.MAX_CLIENT_NAME_LENGTH) {
            Log.w(TAG, "Client validation failed: name too long");
            return false;
        }
        
        // Check phone (optional but if provided, must be valid)
        if (client.getPhone() != null && !client.getPhone().trim().isEmpty()) {
            if (client.getPhone().length() < Constants.MIN_PHONE_LENGTH || 
                client.getPhone().length() > Constants.MAX_PHONE_LENGTH) {
                Log.w(TAG, "Client validation failed: phone number invalid length");
                return false;
            }
        }
        
        // Check notes length
        if (client.getNotes() != null && client.getNotes().length() > Constants.MAX_NOTES_LENGTH) {
            Log.w(TAG, "Client validation failed: notes too long");
            return false;
        }
        
        // Check status
        if (!isValidStatus(client.getStatus())) {
            Log.w(TAG, "Client validation failed: invalid status");
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
            Constants.CLIENT_STATUS_OWING.equals(status) ||
            Constants.CLIENT_STATUS_PARTIAL.equals(status) ||
            Constants.CLIENT_STATUS_PAID.equals(status) ||
            Constants.CLIENT_STATUS_OVERDUE.equals(status) ||
            Constants.CLIENT_STATUS_BLACKLISTED.equals(status)
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
}
