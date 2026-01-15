package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.LoanEntity;

import java.util.List;

/**
 * Loan DAO - Data Access Object for LoanEntity
 * Provides database operations for loan management
 */
@Dao
public interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LoanEntity loan);

    @Update
    int update(LoanEntity loan);

    @Delete
    void delete(LoanEntity loan);

    @Query("DELETE FROM loans WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM loans WHERE uuid = :uuid")
    LoanEntity getLoanById(String uuid);

    @Query("SELECT * FROM loans WHERE uuid = :uuid")
    LiveData<LoanEntity> getLoanByIdLive(String uuid);

    @Query("SELECT * FROM loans WHERE deleted = 0 ORDER BY dateIssued DESC")
    LiveData<List<LoanEntity>> getAllLoansLive();

    @Query("SELECT * FROM loans WHERE deleted = 0 ORDER BY dateIssued DESC")
    List<LoanEntity> getAllLoans();

    @Query("SELECT * FROM loans WHERE deleted = 0 AND clientId = :clientId ORDER BY dateIssued DESC")
    LiveData<List<LoanEntity>> getLoansByClientLive(String clientId);

    @Query("SELECT * FROM loans WHERE deleted = 0 AND clientId = :clientId ORDER BY dateIssued DESC")
    List<LoanEntity> getLoansByClient(String clientId);

    @Query("SELECT * FROM loans WHERE deleted = 0 AND status = :status ORDER BY dateIssued DESC")
    LiveData<List<LoanEntity>> getLoansByStatus(String status);

    @Query("SELECT * FROM loans WHERE deleted = 0 AND status IN (:statuses) ORDER BY dateIssued DESC")
    LiveData<List<LoanEntity>> getLoansByStatuses(List<String> statuses);

    @Query("SELECT * FROM loans WHERE deleted = 0 AND dueDate < :currentTime AND status != 'PAID' AND status != 'BLACKLISTED' ORDER BY dueDate ASC")
    LiveData<List<LoanEntity>> getOverdueLoans(long currentTime);

    @Query("SELECT * FROM loans WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<LoanEntity> getPendingSyncLoans();

    @Query("SELECT * FROM loans WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<LoanEntity> getFailedSyncLoans();

    @Query("UPDATE loans SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    @Query("UPDATE loans SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("SELECT COUNT(*) FROM loans WHERE deleted = 0")
    int getLoanCount();

    @Query("SELECT COUNT(*) FROM loans WHERE deleted = 0 AND status = :status")
    int getLoanCountByStatus(String status);

    @Query("SELECT SUM(amount) FROM loans WHERE deleted = 0")
    double getTotalLoanedAmount();

    @Query("SELECT SUM(amount) FROM loans WHERE deleted = 0 AND status = :status")
    double getTotalLoanedAmountByStatus(String status);

    @Query("SELECT * FROM loans WHERE deleted = 1")
    List<LoanEntity> getDeletedLoans();

    // Soft delete
    @Query("UPDATE loans SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int softDelete(String uuid, long timestamp);

    // Restore deleted loan
    @Query("UPDATE loans SET deleted = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int restoreLoan(String uuid, long timestamp);

    // Update loan status
    @Query("UPDATE loans SET status = :status, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int updateLoanStatus(String uuid, String status, long timestamp);
}
