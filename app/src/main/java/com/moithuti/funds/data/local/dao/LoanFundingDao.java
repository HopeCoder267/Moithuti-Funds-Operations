package com.moithuti.funds.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.moithuti.funds.data.local.entity.LoanFundingEntity;

import java.util.List;

/**
 * Loan Funding DAO - Data Access Object for LoanFundingEntity
 * Provides database operations for loan funding management
 */
@Dao
public interface LoanFundingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoanFundingEntity loanFunding);

    @Update
    void update(LoanFundingEntity loanFunding);

    @Delete
    void delete(LoanFundingEntity loanFunding);

    @Query("DELETE FROM loan_funding WHERE uuid = :uuid")
    void deleteById(String uuid);

    @Query("SELECT * FROM loan_funding WHERE uuid = :uuid")
    LoanFundingEntity getLoanFundingById(String uuid);

    @Query("SELECT * FROM loan_funding WHERE uuid = :uuid")
    LiveData<LoanFundingEntity> getLoanFundingByIdLive(String uuid);

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 ORDER BY createdDate DESC")
    LiveData<List<LoanFundingEntity>> getAllLoanFundingLive();

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 ORDER BY createdDate DESC")
    List<LoanFundingEntity> getAllLoanFunding();

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 AND loanId = :loanId ORDER BY createdDate DESC")
    LiveData<List<LoanFundingEntity>> getFundingByLoanLive(String loanId);

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 AND loanId = :loanId ORDER BY createdDate DESC")
    List<LoanFundingEntity> getFundingByLoan(String loanId);

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 AND investorId = :investorId ORDER BY createdDate DESC")
    LiveData<List<LoanFundingEntity>> getFundingByInvestorLive(String investorId);

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 AND investorId = :investorId ORDER BY createdDate DESC")
    List<LoanFundingEntity> getFundingByInvestor(String investorId);

    @Query("SELECT * FROM loan_funding WHERE deleted = 0 AND loanId = :loanId AND investorId = :investorId")
    LoanFundingEntity getLoanFundingByLoanAndInvestor(String loanId, String investorId);

    @Query("SELECT SUM(amount) FROM loan_funding WHERE deleted = 0 AND loanId = :loanId")
    double getTotalFundingForLoan(String loanId);

    @Query("SELECT SUM(amount) FROM loan_funding WHERE deleted = 0 AND investorId = :investorId")
    double getTotalFundingByInvestor(String investorId);

    @Query("SELECT SUM(amount) FROM loan_funding WHERE deleted = 0")
    double getTotalFunding();

    @Query("SELECT * FROM loan_funding WHERE syncStatus = 'PENDING' AND deleted = 0")
    List<LoanFundingEntity> getPendingSyncLoanFunding();

    @Query("SELECT * FROM loan_funding WHERE syncStatus = 'FAILED' AND deleted = 0")
    List<LoanFundingEntity> getFailedSyncLoanFunding();

    @Query("UPDATE loan_funding SET syncStatus = :syncStatus WHERE uuid = :uuid")
    void updateSyncStatus(String uuid, String syncStatus);

    @Query("UPDATE loan_funding SET lastModified = :timestamp WHERE uuid = :uuid")
    void updateLastModified(String uuid, long timestamp);

    @Query("SELECT COUNT(*) FROM loan_funding WHERE deleted = 0")
    int getLoanFundingCount();

    @Query("SELECT COUNT(*) FROM loan_funding WHERE deleted = 0 AND loanId = :loanId")
    int getFundingCountForLoan(String loanId);

    @Query("SELECT COUNT(*) FROM loan_funding WHERE deleted = 0 AND investorId = :investorId")
    int getFundingCountForInvestor(String investorId);

    @Query("SELECT * FROM loan_funding WHERE deleted = 1")
    List<LoanFundingEntity> getDeletedLoanFunding();

    // Soft delete
    @Query("UPDATE loan_funding SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    void softDelete(String uuid, long timestamp);

    // Restore deleted loan funding
    @Query("UPDATE loan_funding SET deleted = 0, lastModified = :timestamp, syncStatus = 'PENDING' WHERE uuid = :uuid")
    void restoreLoanFunding(String uuid, long timestamp);

    // Delete all funding for a loan
    @Query("UPDATE loan_funding SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE loanId = :loanId")
    void softDeleteAllFundingForLoan(String loanId, long timestamp);

    // Delete all funding by an investor
    @Query("UPDATE loan_funding SET deleted = 1, lastModified = :timestamp, syncStatus = 'PENDING' WHERE investorId = :investorId")
    void softDeleteAllFundingByInvestor(String investorId, long timestamp);
}
