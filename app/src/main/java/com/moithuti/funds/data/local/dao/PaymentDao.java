package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.PaymentEntity;

import java.util.List;

/**
 * Payment DAO - Data Access Object for PaymentEntity
 * Provides database operations for payment management
 */
@Dao
public interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PaymentEntity payment);

    @Update
    int update(PaymentEntity payment);

    @Delete
    void delete(PaymentEntity payment);

    @Query("DELETE FROM payments WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM payments WHERE uuid = :uuid")
    PaymentEntity getPaymentById(String uuid);

    @Query("SELECT * FROM payments WHERE uuid = :uuid")
    LiveData<PaymentEntity> getPaymentByIdLive(String uuid);

    @Query("SELECT * FROM payments WHERE deleted = 0 ORDER BY date DESC")
    LiveData<List<PaymentEntity>> getAllPaymentsLive();

    @Query("SELECT * FROM payments WHERE deleted = 0 ORDER BY date DESC")
    List<PaymentEntity> getAllPayments();

    @Query("SELECT * FROM payments WHERE deleted = 0 AND loanId = :loanId ORDER BY date DESC")
    LiveData<List<PaymentEntity>> getPaymentsByLoanLive(String loanId);

    @Query("SELECT * FROM payments WHERE deleted = 0 AND loanId = :loanId ORDER BY date DESC")
    List<PaymentEntity> getPaymentsByLoan(String loanId);

    @Query("SELECT * FROM payments WHERE deleted = 0 AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    LiveData<List<PaymentEntity>> getPaymentsByDateRange(long startDate, long endDate);

    @Query("SELECT * FROM payments WHERE deleted = 0 AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    List<PaymentEntity> getPaymentsByDateRangeSync(long startDate, long endDate);

    @Query("SELECT * FROM payments WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<PaymentEntity> getPendingSyncPayments();

    @Query("SELECT * FROM payments WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<PaymentEntity> getFailedSyncPayments();

    @Query("UPDATE payments SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    @Query("UPDATE payments SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("SELECT COUNT(*) FROM payments WHERE deleted = 0")
    int getPaymentCount();

    @Query("SELECT SUM(amount) FROM payments WHERE deleted = 0")
    double getTotalPaidAmount();

    @Query("SELECT SUM(amount) FROM payments WHERE deleted = 0 AND date >= :startDate AND date <= :endDate")
    double getTotalPaidAmountByDateRange(long startDate, long endDate);

    @Query("SELECT SUM(amount) FROM payments WHERE deleted = 0 AND loanId = :loanId")
    double getTotalPaidForLoan(String loanId);

    @Query("SELECT * FROM payments WHERE deleted = 1")
    List<PaymentEntity> getDeletedPayments();

    // Soft delete
    @Query("UPDATE payments SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int softDelete(String uuid, long timestamp);

    // Restore deleted payment
    @Query("UPDATE payments SET deleted = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    int restorePayment(String uuid, long timestamp);

    // Monthly payment totals
    @Query("SELECT SUM(amount) FROM payments WHERE deleted = 0 AND date >= :monthStart AND date < :monthEnd")
    double getMonthlyPaymentTotal(long monthStart, long monthEnd);
}
