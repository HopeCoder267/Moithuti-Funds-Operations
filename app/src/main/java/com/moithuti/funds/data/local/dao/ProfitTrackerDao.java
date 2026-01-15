package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.ProfitTrackerEntity;

import java.util.List;

/**
 * Profit Tracker DAO - Data Access Object for ProfitTrackerEntity
 * Provides database operations for profit tracking and calculations
 */
@Dao
public interface ProfitTrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProfitTrackerEntity profitTracker);

    @Update
    int update(ProfitTrackerEntity profitTracker);

    @Query("DELETE FROM profit_tracker WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM profit_tracker WHERE uuid = :uuid")
    ProfitTrackerEntity getProfitTrackerById(String uuid);

    @Query("SELECT * FROM profit_tracker WHERE uuid = :uuid")
    LiveData<ProfitTrackerEntity> getProfitTrackerByIdLive(String uuid);

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 ORDER BY yearMonth ASC")
    LiveData<List<ProfitTrackerEntity>> getAllProfitTrackersLive();

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 ORDER BY yearMonth ASC")
    List<ProfitTrackerEntity> getAllProfitTrackers();

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth ASC")
    LiveData<List<ProfitTrackerEntity>> getMainAccountProfitTrackersLive();

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth ASC")
    List<ProfitTrackerEntity> getMainAccountProfitTrackers();

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId = :investorId ORDER BY yearMonth ASC")
    LiveData<List<ProfitTrackerEntity>> getInvestorProfitTrackersLive(String investorId);

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId = :investorId ORDER BY yearMonth ASC")
    List<ProfitTrackerEntity> getInvestorProfitTrackers(String investorId);

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND yearMonth = :yearMonth AND investorId IS NULL")
    ProfitTrackerEntity getMainAccountProfitTrackerByMonth(String yearMonth);

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND yearMonth = :yearMonth AND investorId = :investorId")
    ProfitTrackerEntity getInvestorProfitTrackerByMonth(String investorId, String yearMonth);

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND yearMonth = :yearMonth")
    List<ProfitTrackerEntity> getAllProfitTrackersByMonth(String yearMonth);

    // Get latest profit tracker for cumulative calculations
    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1")
    ProfitTrackerEntity getLatestMainAccountProfitTracker();

    @Query("SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId = :investorId ORDER BY yearMonth DESC LIMIT 1")
    ProfitTrackerEntity getLatestInvestorProfitTracker(String investorId);

    // Aggregate queries for dashboard
    @Query("SELECT SUM(monthlyProfit) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL")
    Double getTotalMonthlyProfit();

    @Query("SELECT SUM(cumulativeProfit) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1")
    Double getCurrentCumulativeProfit();

    @Query("SELECT SUM(monthlyInterest) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL")
    Double getTotalInterest();

    @Query("SELECT SUM(cumulativeInterest) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1")
    Double getCurrentCumulativeInterest();

    @Query("SELECT SUM(availableFunds) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1")
    Double getCurrentAvailableFunds();

    // Monthly aggregates for graphs
    @Query("SELECT yearMonth, SUM(monthlyProfit) as monthlyProfit, SUM(monthlyInterest) as monthlyInterest " +
            "FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL " +
            "GROUP BY yearMonth ORDER BY yearMonth ASC")
    List<ProfitSummary> getMonthlyProfitSummaries();

    // Update operations
    @Query("UPDATE profit_tracker SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("UPDATE profit_tracker SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    // Soft delete
    @Query("UPDATE profit_tracker SET deleted = 1, lastModified = :timestamp WHERE uuid = :uuid")
    void softDelete(String uuid, long timestamp);

    // Sync status queries
    @Query("SELECT * FROM profit_tracker WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<ProfitTrackerEntity> getPendingSyncProfitTrackers();

    @Query("SELECT * FROM profit_tracker WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<ProfitTrackerEntity> getFailedSyncProfitTrackers();

    // Clear all data (for testing)
    @Query("DELETE FROM profit_tracker")
    void deleteAll();

    // Summary class for monthly aggregates
    class ProfitSummary {
        public String yearMonth;
        public double monthlyProfit;
        public double monthlyInterest;
    }
}
