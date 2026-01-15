package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;

import java.util.List;

/**
 * Investor Transaction DAO - Data Access Object for InvestorTransactionEntity
 * Provides database operations for investor transaction management
 */
@Dao
public interface InvestorTransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InvestorTransactionEntity transaction);

    @Update
    void update(InvestorTransactionEntity transaction);

    @Delete
    void delete(InvestorTransactionEntity transaction);

    @Query("DELETE FROM investor_transactions WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM investor_transactions WHERE uuid = :uuid")
    InvestorTransactionEntity getTransactionById(String uuid);

    @Query("SELECT * FROM investor_transactions WHERE uuid = :uuid")
    LiveData<InvestorTransactionEntity> getTransactionByIdLive(String uuid);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 ORDER BY timestamp DESC")
    LiveData<List<InvestorTransactionEntity>> getAllTransactionsLive();

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 ORDER BY timestamp DESC")
    List<InvestorTransactionEntity> getAllTransactions();

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND investorId = :investorId ORDER BY timestamp DESC")
    LiveData<List<InvestorTransactionEntity>> getTransactionsByInvestorLive(String investorId);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND investorId = :investorId ORDER BY timestamp DESC")
    List<InvestorTransactionEntity> getTransactionsByInvestor(String investorId);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND type = :type ORDER BY timestamp DESC")
    LiveData<List<InvestorTransactionEntity>> getTransactionsByType(String type);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND relatedLoanId = :relatedLoanId ORDER BY timestamp DESC")
    LiveData<List<InvestorTransactionEntity>> getTransactionsByLoanLive(String relatedLoanId);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND relatedLoanId = :relatedLoanId ORDER BY timestamp DESC")
    List<InvestorTransactionEntity> getTransactionsByLoan(String relatedLoanId);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND yearMonth = :yearMonth ORDER BY timestamp DESC")
    LiveData<List<InvestorTransactionEntity>> getTransactionsByMonthLive(String yearMonth);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND yearMonth = :yearMonth ORDER BY timestamp DESC")
    List<InvestorTransactionEntity> getTransactionsByMonth(String yearMonth);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 0 AND timestamp >= :startDate AND timestamp <= :endDate ORDER BY timestamp DESC")
    LiveData<List<InvestorTransactionEntity>> getTransactionsByDateRange(long startDate, long endDate);

    @Query("SELECT * FROM investor_transactions WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<InvestorTransactionEntity> getPendingSyncTransactions();

    @Query("SELECT * FROM investor_transactions WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<InvestorTransactionEntity> getFailedSyncTransactions();

    @Query("UPDATE investor_transactions SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    @Query("UPDATE investor_transactions SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("SELECT COUNT(*) FROM investor_transactions WHERE deleted = 0")
    int getTransactionCount();

    // Balance calculations
    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND investorId = :investorId AND type = 'INVEST'")
    double getTotalInvestedByInvestor(String investorId);

    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND investorId = :investorId AND type = 'LOAN_OUT'")
    double getTotalLoanedByInvestor(String investorId);

    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND investorId = :investorId AND type = 'REPAYMENT_IN'")
    double getTotalRepaidToInvestor(String investorId);

    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND type = 'INVEST'")
    double getTotalInvested();

    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND type = 'LOAN_OUT'")
    double getTotalLoaned();

    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND type = 'REPAYMENT_IN'")
    double getTotalRepaid();

    // Monthly totals by type
    @Query("SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND yearMonth = :yearMonth AND type = :type")
    double getMonthlyTotalByType(String yearMonth, String type);

    @Query("SELECT * FROM investor_transactions WHERE deleted = 1")
    List<InvestorTransactionEntity> getDeletedTransactions();

    // Soft delete
    @Query("UPDATE investor_transactions SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    void softDelete(String uuid, long timestamp);

    // Restore deleted transaction
    @Query("UPDATE investor_transactions SET deleted = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    void restoreTransaction(String uuid, long timestamp);
}
