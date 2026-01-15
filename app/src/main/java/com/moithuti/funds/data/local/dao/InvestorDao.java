package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.InvestorEntity;

import java.util.List;

/**
 * Investor DAO - Data Access Object for InvestorEntity
 * Provides database operations for investor management
 */
@Dao
public interface InvestorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(InvestorEntity investor);

    @Update
    int update(InvestorEntity investor);

    @Delete
    void delete(InvestorEntity investor);

    @Query("DELETE FROM investors WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM investors WHERE uuid = :uuid")
    InvestorEntity getInvestorById(String uuid);

    @Query("SELECT * FROM investors WHERE uuid = :uuid")
    LiveData<InvestorEntity> getInvestorByIdLive(String uuid);

    @Query("SELECT * FROM investors WHERE deleted = 0 ORDER BY name ASC")
    LiveData<List<InvestorEntity>> getAllInvestorsLive();

    @Query("SELECT * FROM investors WHERE deleted = 0 ORDER BY name ASC")
    List<InvestorEntity> getAllInvestors();

    @Query("SELECT * FROM investors WHERE deleted = 0 AND isMainAccount = 1")
    LiveData<InvestorEntity> getMainAccountLive();

    @Query("SELECT * FROM investors WHERE deleted = 0 AND isMainAccount = 1")
    InvestorEntity getMainAccount();

    @Query("SELECT * FROM investors WHERE deleted = 0 AND isMainAccount = 0 ORDER BY name ASC")
    LiveData<List<InvestorEntity>> getOtherInvestorsLive();

    @Query("SELECT * FROM investors WHERE deleted = 0 AND isMainAccount = 0 ORDER BY name ASC")
    List<InvestorEntity> getOtherInvestors();

    @Query("SELECT * FROM investors WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<InvestorEntity> getPendingSyncInvestors();

    @Query("SELECT * FROM investors WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<InvestorEntity> getFailedSyncInvestors();

    @Query("UPDATE investors SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    @Query("UPDATE investors SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("SELECT COUNT(*) FROM investors WHERE deleted = 0")
    int getInvestorCount();

    @Query("SELECT COUNT(*) FROM investors WHERE deleted = 0 AND isMainAccount = 1")
    int getMainAccountCount();

    @Query("SELECT * FROM investors WHERE deleted = 1")
    List<InvestorEntity> getDeletedInvestors();

    // Soft delete
    @Query("UPDATE investors SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int softDelete(String uuid, long timestamp);

    // Restore deleted investor
    @Query("UPDATE investors SET deleted = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int restoreInvestor(String uuid, long timestamp);

    // Set main account (clears previous main account)
    @Query("UPDATE investors SET isMainAccount = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE isMainAccount = 1")
    void clearMainAccount(long timestamp);

    @Query("UPDATE investors SET isMainAccount = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    void setMainAccount(String uuid, long timestamp);
}
