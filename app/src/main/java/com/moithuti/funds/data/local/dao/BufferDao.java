package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.moithuti.funds.data.local.entity.BufferEntity;

import java.util.List;

/**
 * Buffer DAO - Data Access Object for Buffer operations
 * Handles all database operations for Buffer entities
 */
@Dao
public interface BufferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BufferEntity buffer);

    @Update
    void update(BufferEntity buffer);

    @Delete
    void delete(BufferEntity buffer);

    @Query("SELECT * FROM buffers WHERE deleted = 0 ORDER BY createdDate DESC")
    LiveData<List<BufferEntity>> getAllBuffers();

    @Query("SELECT * FROM buffers WHERE deleted = 0 AND investorId = :investorId ORDER BY createdDate DESC")
    LiveData<List<BufferEntity>> getBuffersByInvestor(String investorId);

    @Query("SELECT * FROM buffers WHERE deleted = 0 AND investorId IS NULL ORDER BY createdDate DESC")
    LiveData<List<BufferEntity>> getMainAccountBuffers();

    @Query("SELECT * FROM buffers WHERE deleted = 0 AND uuid = :uuid")
    BufferEntity getBufferById(String uuid);

    @Query("SELECT COALESCE(SUM(amount), 0) FROM buffers WHERE deleted = 0 AND investorId = :investorId")
    double getTotalBufferForInvestor(String investorId);

    @Query("SELECT COALESCE(SUM(amount), 0) FROM buffers WHERE deleted = 0 AND investorId IS NULL")
    double getTotalBufferForMainAccount();

    @Query("SELECT COALESCE(SUM(amount), 0) FROM buffers WHERE deleted = 0")
    double getTotalBuffers();

    @Query("UPDATE buffers SET deleted = 1, lastModified = :timestamp WHERE uuid = :uuid")
    void softDelete(String uuid, long timestamp);

    @Query("UPDATE buffers SET syncStatus = :syncStatus, lastModified = :timestamp WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus, long timestamp);

    @Query("SELECT * FROM buffers WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<BufferEntity> getPendingSync();
}
