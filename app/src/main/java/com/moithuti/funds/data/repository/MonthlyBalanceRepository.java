package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.MonthlyBalanceDao;
import com.moithuti.funds.data.local.entity.MonthlyBalanceEntity;
import com.moithuti.funds.sync.SyncScheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Monthly Balance Repository - Manages monthly balance data operations
 * Handles monthly balance CRUD operations and sync triggers
 */
public class MonthlyBalanceRepository {
    
    private static final String TAG = "MonthlyBalanceRepository";
    private static MonthlyBalanceRepository instance;
    
    private final MonthlyBalanceDao monthlyBalanceDao;
    private final SyncScheduler syncScheduler;
    
    private MonthlyBalanceRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.monthlyBalanceDao = database.monthlyBalanceDao();
        this.syncScheduler = SyncScheduler.getInstance(application);
    }
    
    public static synchronized MonthlyBalanceRepository getInstance(Application application) {
        if (instance == null) {
            instance = new MonthlyBalanceRepository(application);
        }
        return instance;
    }
    
    /**
     * Get current year-month string
     */
    public static String getCurrentYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }
    
    /**
     * Set or update monthly balance for account
     */
    public String setMonthlyBalance(String investorId, String yearMonth, double balance, double monthlyTopUp) {
        MonthlyBalanceEntity existing = monthlyBalanceDao.getMonthlyBalance(investorId, yearMonth);
        
        if (existing != null) {
            // Update existing
            existing.setBalance(balance);
            existing.setMonthlyTopUp(monthlyTopUp);
            existing.setLastModified(System.currentTimeMillis());
            existing.setSyncStatus("PENDING");
            
            monthlyBalanceDao.update(existing);
            
            Log.d(TAG, "Updated monthly balance for investor: " + investorId + ", month: " + yearMonth);
            return existing.getUuid();
        } else {
            // Create new
            MonthlyBalanceEntity monthlyBalance = new MonthlyBalanceEntity();
            monthlyBalance.setUuid(UUID.randomUUID().toString());
            monthlyBalance.setInvestorId(investorId); // null for Main Account
            monthlyBalance.setYearMonth(yearMonth);
            monthlyBalance.setBalance(balance);
            monthlyBalance.setMonthlyTopUp(monthlyTopUp);
            
            monthlyBalanceDao.insert(monthlyBalance);
            
            Log.d(TAG, "Created monthly balance for investor: " + investorId + ", month: " + yearMonth);
            return monthlyBalance.getUuid();
        }
    }
    
    /**
     * Set Main Account monthly balance
     */
    public String setMainAccountMonthlyBalance(String yearMonth, double balance) {
        String result = setMonthlyBalance(null, yearMonth, balance, 0.0);
        
        // Trigger sync
        syncScheduler.scheduleImmediateSync();
        
        return result;
    }
    
    /**
     * Set investor monthly balance with top-up
     */
    public String setInvestorMonthlyBalance(String investorId, String yearMonth, double balance, double monthlyTopUp) {
        String result = setMonthlyBalance(investorId, yearMonth, balance, monthlyTopUp);
        
        // Trigger sync
        syncScheduler.scheduleImmediateSync();
        
        return result;
    }
    
    /**
     * Get all monthly balances
     */
    public LiveData<List<MonthlyBalanceEntity>> getAllMonthlyBalances() {
        return monthlyBalanceDao.getAllMonthlyBalances();
    }
    
    /**
     * Get monthly balances for investor
     */
    public LiveData<List<MonthlyBalanceEntity>> getMonthlyBalancesByInvestor(String investorId) {
        return monthlyBalanceDao.getMonthlyBalancesByInvestor(investorId);
    }
    
    /**
     * Get Main Account monthly balances
     */
    public LiveData<List<MonthlyBalanceEntity>> getMainAccountMonthlyBalances() {
        return monthlyBalanceDao.getMainAccountMonthlyBalances();
    }
    
    /**
     * Get monthly balance for specific month and investor
     */
    public MonthlyBalanceEntity getMonthlyBalance(String investorId, String yearMonth) {
        if (investorId == null) {
            return monthlyBalanceDao.getMainAccountMonthlyBalance(yearMonth);
        } else {
            return monthlyBalanceDao.getMonthlyBalance(investorId, yearMonth);
        }
    }
    
    /**
     * Get current month balance for investor
     */
    public MonthlyBalanceEntity getCurrentMonthBalance(String investorId) {
        return getMonthlyBalance(investorId, getCurrentYearMonth());
    }
    
    /**
     * Get current month Main Account balance
     */
    public MonthlyBalanceEntity getCurrentMonthMainAccountBalance() {
        return getMonthlyBalance(null, getCurrentYearMonth());
    }
    
    /**
     * Get total balance for investor
     */
    public double getTotalBalanceForInvestor(String investorId) {
        return monthlyBalanceDao.getTotalBalanceForInvestor(investorId);
    }
    
    /**
     * Get total Main Account balance
     */
    public double getTotalBalanceForMainAccount() {
        return monthlyBalanceDao.getTotalBalanceForMainAccount();
    }
    
    /**
     * Get total balance for all accounts
     */
    public double getTotalBalance() {
        return monthlyBalanceDao.getTotalBalance();
    }
    
    /**
     * Get total monthly top-ups for investor
     */
    public double getTotalMonthlyTopUpsForInvestor(String investorId) {
        return monthlyBalanceDao.getTotalMonthlyTopUpsForInvestor(investorId);
    }
    
    /**
     * Get total monthly top-ups for Main Account
     */
    public double getTotalMonthlyTopUpsForMainAccount() {
        return monthlyBalanceDao.getTotalMonthlyTopUpsForMainAccount();
    }
    
    /**
     * Get balances pending sync
     */
    public List<MonthlyBalanceEntity> getPendingSync() {
        return monthlyBalanceDao.getPendingSync();
    }
    
    /**
     * Update sync status for monthly balance
     */
    public void updateSyncStatus(String balanceId, String syncStatus) {
        long timestamp = System.currentTimeMillis();
        monthlyBalanceDao.updateSyncStatus(balanceId, syncStatus, timestamp);
    }
}
