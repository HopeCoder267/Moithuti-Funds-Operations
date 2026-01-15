package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.ClientDao;
import com.moithuti.funds.data.local.dao.LoanDao;
import com.moithuti.funds.data.local.dao.PaymentDao;
import com.moithuti.funds.data.local.dao.InvestorDao;
import com.moithuti.funds.data.local.dao.InvestorTransactionDao;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.ui.home.DashboardStats;
import com.moithuti.funds.util.Constants;
import com.moithuti.funds.util.DateUtil;
import com.moithuti.funds.util.InvestorCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Dashboard Repository - Repository for dashboard statistics and calculations
 * Provides calculated data for the home dashboard
 */
public class DashboardRepository {

    private static final String TAG = "DashboardRepository";
    
    private final ClientDao clientDao;
    private final LoanDao loanDao;
    private final PaymentDao paymentDao;
    private final InvestorDao investorDao;
    private final InvestorTransactionDao investorTransactionDao;
    private final ExecutorService executorService;
    private final Application application;

    // Singleton instance
    private static DashboardRepository instance;

    /**
     * Get singleton instance
     * @param application application context
     * @return repository instance
     */
    public static synchronized DashboardRepository getInstance(Application application) {
        if (instance == null) {
            instance = new DashboardRepository(application);
        }
        return instance;
    }

    private DashboardRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.clientDao = database.clientDao();
        this.loanDao = database.loanDao();
        this.paymentDao = database.paymentDao();
        this.investorDao = database.investorDao();
        this.investorTransactionDao = database.investorTransactionDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Calculate complete dashboard statistics
     * @return DashboardStats object with all calculated values
     */
    public DashboardStats calculateDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        try {
            // Calculate overall finance stats
            calculateOverallFinance(stats);
            
            // Calculate client stats
            calculateClientStats(stats);
            
            // Calculate loan stats
            calculateLoanStats(stats);
            
            // Calculate monthly stats for charts
            calculateMonthlyStats(stats);
            
            // Calculate investor summaries
            calculateInvestorSummaries(stats);
            
            // Set last sync time
            stats.setLastSyncTime(System.currentTimeMillis());
            
            Log.d(TAG, "Dashboard stats calculated: " + stats);
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating dashboard stats", e);
        }
        
        return stats;
    }

    /**
     * Calculate overall finance statistics
     */
    private void calculateOverallFinance(DashboardStats stats) {
        try {
            // Get investor transaction totals
            double totalInvested = investorTransactionDao.getTotalInvested();
            double totalLoaned = investorTransactionDao.getTotalLoaned();
            double totalRepaid = investorTransactionDao.getTotalRepaid();
            
            // Calculate derived values
            double totalOutstanding = totalLoaned - totalRepaid;
            double totalAvailable = totalInvested - totalLoaned + totalRepaid;
            
            stats.setTotalInvested(totalInvested);
            stats.setTotalLoaned(totalLoaned);
            stats.setTotalRepaid(totalRepaid);
            stats.setTotalOutstanding(totalOutstanding);
            stats.setTotalAvailable(totalAvailable);
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating overall finance", e);
        }
    }

    /**
     * Calculate client statistics
     */
    private void calculateClientStats(DashboardStats stats) {
        try {
            stats.setTotalClients(clientDao.getClientCount());
            stats.setPaidClients(clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_PAID));
            stats.setPartialClients(clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_PARTIAL));
            stats.setOverdueClients(clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_OVERDUE));
            stats.setBlacklistedClients(clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_BLACKLISTED));
            stats.setOwingClients(clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_OWING));
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating client stats", e);
        }
    }

    /**
     * Calculate loan statistics
     */
    private void calculateLoanStats(DashboardStats stats) {
        try {
            stats.setTotalLoans(loanDao.getLoanCount());
            stats.setPaidLoans(loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_PAID));
            stats.setPartialLoans(loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_PARTIAL));
            stats.setOverdueLoans(loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_OVERDUE));
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating loan stats", e);
        }
    }

    /**
     * Calculate monthly statistics for charts
     */
    private void calculateMonthlyStats(DashboardStats stats) {
        try {
            Map<String, Double> monthlyInvestments = new HashMap<>();
            Map<String, Double> monthlyLoans = new HashMap<>();
            Map<String, Double> monthlyRepayments = new HashMap<>();
            
            // Get data for last 12 months
            long currentTime = System.currentTimeMillis();
            for (int i = 11; i >= 0; i--) {
                long monthTime = DateUtil.addMonths(currentTime, -i);
                String yearMonth = DateUtil.formatYearMonth(monthTime);
                long monthStart = DateUtil.getStartOfMonth(monthTime);
                long monthEnd = DateUtil.getEndOfMonth(monthTime);
                
                // Calculate monthly totals
                double monthlyInvestTotal = investorTransactionDao.getMonthlyTotalByType(yearMonth, Constants.TRANSACTION_TYPE_INVEST);
                double monthlyLoanTotal = investorTransactionDao.getMonthlyTotalByType(yearMonth, Constants.TRANSACTION_TYPE_LOAN_OUT);
                double monthlyRepaymentTotal = investorTransactionDao.getMonthlyTotalByType(yearMonth, Constants.TRANSACTION_TYPE_REPAYMENT_IN);
                
                // Also get payment totals for the month
                double monthlyPaymentTotal = paymentDao.getMonthlyPaymentTotal(monthStart, monthEnd);
                
                monthlyInvestments.put(yearMonth, monthlyInvestTotal);
                monthlyLoans.put(yearMonth, monthlyLoanTotal);
                monthlyRepayments.put(yearMonth, monthlyRepaymentTotal);
            }
            
            stats.setMonthlyInvestments(monthlyInvestments);
            stats.setMonthlyLoans(monthlyLoans);
            stats.setMonthlyRepayments(monthlyRepayments);
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating monthly stats", e);
        }
    }

    /**
     * Calculate investor summaries
     */
    private void calculateInvestorSummaries(DashboardStats stats) {
        try {
            List<InvestorEntity> investors = investorDao.getAllInvestors();
            List<DashboardStats.InvestorSummary> summaries = new ArrayList<>();
            
            if (investors != null) {
                for (InvestorEntity investor : investors) {
                    if (investor.isDeleted()) {
                        continue;
                    }
                    
                    DashboardStats.InvestorSummary summary = new DashboardStats.InvestorSummary(
                        investor.getUuid(),
                        investor.getName(),
                        investor.isMainAccount()
                    );
                    
                    // Get investor transactions
                    List<com.moithuti.funds.data.local.entity.InvestorTransactionEntity> transactions = 
                        investorTransactionDao.getTransactionsByInvestor(investor.getUuid());
                    
                    // Calculate balance
                    InvestorCalculator.InvestorBalance balance = InvestorCalculator.calculateBalance(transactions);
                    
                    summary.setInvested(balance.invested);
                    summary.setLoanedOut(balance.rolledOut);
                    summary.setRepaid(balance.repaid);
                    summary.setOutstanding(balance.outstanding);
                    summary.setAvailable(balance.available);
                    
                    summaries.add(summary);
                }
            }
            
            stats.setInvestorSummaries(summaries);
            
        } catch (Exception e) {
            Log.e(TAG, "Error calculating investor summaries", e);
        }
    }

    /**
     * Get quick stats (simplified version for fast loading)
     * @return map of quick stat names to values
     */
    public Map<String, Object> getQuickStats() {
        Map<String, Object> quickStats = new HashMap<>();
        
        try {
            quickStats.put("totalClients", clientDao.getClientCount());
            quickStats.put("totalLoans", loanDao.getLoanCount());
            quickStats.put("totalInvested", investorTransactionDao.getTotalInvested());
            quickStats.put("totalLoaned", investorTransactionDao.getTotalLoaned());
            quickStats.put("totalRepaid", investorTransactionDao.getTotalRepaid());
            quickStats.put("overdueLoans", loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_OVERDUE));
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting quick stats", e);
        }
        
        return quickStats;
    }

    /**
     * Get client status distribution
     * @return map of status to count
     */
    public Map<String, Integer> getClientStatusDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        
        try {
            distribution.put(Constants.CLIENT_STATUS_PAID, clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_PAID));
            distribution.put(Constants.CLIENT_STATUS_PARTIAL, clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_PARTIAL));
            distribution.put(Constants.CLIENT_STATUS_OVERDUE, clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_OVERDUE));
            distribution.put(Constants.CLIENT_STATUS_BLACKLISTED, clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_BLACKLISTED));
            distribution.put(Constants.CLIENT_STATUS_OWING, clientDao.getClientCountByStatus(Constants.CLIENT_STATUS_OWING));
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting client status distribution", e);
        }
        
        return distribution;
    }

    /**
     * Get loan status distribution
     * @return map of status to count
     */
    public Map<String, Integer> getLoanStatusDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        
        try {
            distribution.put(Constants.LOAN_STATUS_PAID, loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_PAID));
            distribution.put(Constants.LOAN_STATUS_PARTIAL, loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_PARTIAL));
            distribution.put(Constants.LOAN_STATUS_OVERDUE, loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_OVERDUE));
            distribution.put(Constants.LOAN_STATUS_BLACKLISTED, loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_BLACKLISTED));
            distribution.put(Constants.LOAN_STATUS_OWING, loanDao.getLoanCountByStatus(Constants.LOAN_STATUS_OWING));
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting loan status distribution", e);
        }
        
        return distribution;
    }

    /**
     * Get monthly trend data for charts
     * @param months number of months to include
     * @return list of monthly data points
     */
    public List<MonthlyDataPoint> getMonthlyTrendData(int months) {
        List<MonthlyDataPoint> dataPoints = new ArrayList<>();
        
        try {
            long currentTime = System.currentTimeMillis();
            
            for (int i = months - 1; i >= 0; i--) {
                long monthTime = DateUtil.addMonths(currentTime, -i);
                String yearMonth = DateUtil.formatYearMonth(monthTime);
                String monthYear = DateUtil.formatMonthYear(monthTime);
                long monthStart = DateUtil.getStartOfMonth(monthTime);
                long monthEnd = DateUtil.getEndOfMonth(monthTime);
                
                MonthlyDataPoint dataPoint = new MonthlyDataPoint();
                dataPoint.setYearMonth(yearMonth);
                dataPoint.setDisplayMonth(monthYear);
                dataPoint.setInvestments(investorTransactionDao.getMonthlyTotalByType(yearMonth, Constants.TRANSACTION_TYPE_INVEST));
                dataPoint.setLoans(investorTransactionDao.getMonthlyTotalByType(yearMonth, Constants.TRANSACTION_TYPE_LOAN_OUT));
                dataPoint.setRepayments(investorTransactionDao.getMonthlyTotalByType(yearMonth, Constants.TRANSACTION_TYPE_REPAYMENT_IN));
                dataPoint.setPayments(paymentDao.getMonthlyPaymentTotal(monthStart, monthEnd));
                
                dataPoints.add(dataPoint);
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting monthly trend data", e);
        }
        
        return dataPoints;
    }

    /**
     * Monthly data point for charts
     */
    public static class MonthlyDataPoint {
        private String yearMonth;
        private String displayMonth;
        private double investments;
        private double loans;
        private double repayments;
        private double payments;

        // Getters and setters
        public String getYearMonth() { return yearMonth; }
        public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }

        public String getDisplayMonth() { return displayMonth; }
        public void setDisplayMonth(String displayMonth) { this.displayMonth = displayMonth; }

        public double getInvestments() { return investments; }
        public void setInvestments(double investments) { this.investments = investments; }

        public double getLoans() { return loans; }
        public void setLoans(double loans) { this.loans = loans; }

        public double getRepayments() { return repayments; }
        public void setRepayments(double repayments) { this.repayments = repayments; }

        public double getPayments() { return payments; }
        public void setPayments(double payments) { this.payments = payments; }

        @Override
        public String toString() {
            return "MonthlyDataPoint{" +
                    "yearMonth='" + yearMonth + '\'' +
                    ", investments=" + investments +
                    ", loans=" + loans +
                    ", repayments=" + repayments +
                    ", payments=" + payments +
                    '}';
        }
    }

    /**
     * Cleanup resources
     */
    public void cleanup() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
