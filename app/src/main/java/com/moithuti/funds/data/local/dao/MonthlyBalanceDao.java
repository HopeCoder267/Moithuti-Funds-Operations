package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.moithuti.funds.data.local.entity.MonthlyBalanceEntity;

import java.util.List;

/**
 * Monthly Balance DAO - Data Access Object for Monthly Balance operations
 * Handles all database operations for MonthlyBalance entities
 */
@Dao
public interface MonthlyBalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MonthlyBalanceEntity monthlyBalance);

    @Update
    void update(MonthlyBalanceEntity monthlyBalance);

    @Delete
    void delete(MonthlyBalanceEntity monthlyBalance);

    @Query("SELECT * FROM monthly_balances WHERE deleted = 0 ORDER BY yearMonth DESC")
    LiveData<List<MonthlyBalanceEntity>> getAllMonthlyBalances();

    @Query("SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId = :investorId ORDER BY yearMonth DESC")
    LiveData<List<MonthlyBalanceEntity>> getMonthlyBalancesByInvestor(String investorId);

    @Query("SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC")
    LiveData<List<MonthlyBalanceEntity>> getMainAccountMonthlyBalances();

    @Query("SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId = :investorId AND yearMonth = :yearMonth")
    MonthlyBalanceEntity getMonthlyBalance(String investorId, String yearMonth);

    @Query("SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL AND yearMonth = :yearMonth")
    MonthlyBalanceEntity getMainAccountMonthlyBalance(String yearMonth);

    @Query("SELECT * FROM monthly_balances WHERE deleted = 0 AND uuid = :uuid")
    MonthlyBalanceEntity getMonthlyBalanceById(String uuid);

    @Query("SELECT COALESCE(SUM(balance), 0) FROM monthly_balances WHERE deleted = 0 AND investorId = :investorId")
    double getTotalBalanceForInvestor(String investorId);

    @Query("SELECT COALESCE(SUM(balance), 0) FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL")
    double getTotalBalanceForMainAccount();

    @Query("SELECT COALESCE(SUM(balance), 0) FROM monthly_balances WHERE deleted = 0")
    double getTotalBalance();

    @Query("SELECT COALESCE(SUM(monthlyTopUp), 0) FROM monthly_balances WHERE deleted = 0 AND investorId = :investorId")
    double getTotalMonthlyTopUpsForInvestor(String investorId);

    @Query("SELECT COALESCE(SUM(monthlyTopUp), 0) FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL")
    double getTotalMonthlyTopUpsForMainAccount();

    @Query("UPDATE monthly_balances SET deleted = 1, lastModified = :timestamp WHERE uuid = :uuid")
    void softDelete(String uuid, long timestamp);

    @Query("UPDATE monthly_balances SET syncStatus = :syncStatus, lastModified = :timestamp WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus, long timestamp);

    @Query("SELECT * FROM monthly_balances WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<MonthlyBalanceEntity> getPendingSync();
}
