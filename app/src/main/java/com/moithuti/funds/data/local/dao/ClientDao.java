package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.ClientEntity;

import java.util.List;

/**
 * Client DAO - Data Access Object for ClientEntity
 * Provides database operations for client management
 */
@Dao
public interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ClientEntity client);

    @Update
    int update(ClientEntity client);

    @Delete
    void delete(ClientEntity client);

    @Query("DELETE FROM clients WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM clients WHERE uuid = :uuid")
    ClientEntity getClientById(String uuid);

    @Query("SELECT * FROM clients WHERE uuid = :uuid")
    LiveData<ClientEntity> getClientByIdLive(String uuid);

    @Query("SELECT * FROM clients WHERE deleted = 0 ORDER BY name ASC")
    LiveData<List<ClientEntity>> getAllClientsLive();

    @Query("SELECT * FROM clients WHERE deleted = 0 ORDER BY name ASC")
    List<ClientEntity> getAllClients();

    @Query("SELECT * FROM clients WHERE deleted = 0 AND name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    LiveData<List<ClientEntity>> searchClients(String searchQuery);

    @Query("SELECT * FROM clients WHERE deleted = 0 AND status = :status ORDER BY name ASC")
    LiveData<List<ClientEntity>> getClientsByStatus(String status);

    @Query("SELECT * FROM clients WHERE deleted = 0 AND status IN (:statuses) ORDER BY name ASC")
    LiveData<List<ClientEntity>> getClientsByStatuses(List<String> statuses);

    @Query("SELECT * FROM clients WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<ClientEntity> getPendingSyncClients();

    @Query("SELECT * FROM clients WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<ClientEntity> getFailedSyncClients();

    @Query("UPDATE clients SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    @Query("UPDATE clients SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("SELECT COUNT(*) FROM clients WHERE deleted = 0")
    int getClientCount();

    @Query("SELECT COUNT(*) FROM clients WHERE deleted = 0 AND status = :status")
    int getClientCountByStatus(String status);

    @Query("SELECT * FROM clients WHERE deleted = 1")
    List<ClientEntity> getDeletedClients();

    // Soft delete
    @Query("UPDATE clients SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int softDelete(String uuid, long timestamp);

    // Restore deleted client
    @Query("UPDATE clients SET deleted = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int restoreClient(String uuid, long timestamp);
}
