package com.moithuti.funds.ui.home;

import java.util.List;
import java.util.Map;

/**
 * Dashboard Stats - Data model for dashboard statistics
 * Contains calculated values for display in the home dashboard
 * All values are calculated, never stored
 */
public class DashboardStats {

    // Overall finance stats
    private double totalInvested;
    private double totalLoaned;
    private double totalRepaid;
    private double totalOutstanding;
    private double totalAvailable;

    // Client stats
    private int totalClients;
    private int paidClients;
    private int partialClients;
    private int overdueClients;
    private int blacklistedClients;
    private int owingClients;

    // Loan stats
    private int totalLoans;
    private int paidLoans;
    private int partialLoans;
    private int overdueLoans;

    // Monthly stats (for charts)
    private Map<String, Double> monthlyInvestments;
    private Map<String, Double> monthlyLoans;
    private Map<String, Double> monthlyRepayments;

    // Investor summary
    private List<InvestorSummary> investorSummaries;

    // Last sync timestamp
    private long lastSyncTime;

    // Constructors
    public DashboardStats() {
        // Initialize with zeros
        this.totalInvested = 0.0;
        this.totalLoaned = 0.0;
        this.totalRepaid = 0.0;
        this.totalOutstanding = 0.0;
        this.totalAvailable = 0.0;
        this.lastSyncTime = System.currentTimeMillis();
    }

    // Getters and setters
    public double getTotalInvested() { return totalInvested; }
    public void setTotalInvested(double totalInvested) { this.totalInvested = totalInvested; }

    public double getTotalLoaned() { return totalLoaned; }
    public void setTotalLoaned(double totalLoaned) { this.totalLoaned = totalLoaned; }

    public double getTotalRepaid() { return totalRepaid; }
    public void setTotalRepaid(double totalRepaid) { this.totalRepaid = totalRepaid; }

    public double getTotalOutstanding() { return totalOutstanding; }
    public void setTotalOutstanding(double totalOutstanding) { this.totalOutstanding = totalOutstanding; }

    public double getTotalAvailable() { return totalAvailable; }
    public void setTotalAvailable(double totalAvailable) { this.totalAvailable = totalAvailable; }

    public int getTotalClients() { return totalClients; }
    public void setTotalClients(int totalClients) { this.totalClients = totalClients; }

    public int getPaidClients() { return paidClients; }
    public void setPaidClients(int paidClients) { this.paidClients = paidClients; }

    public int getPartialClients() { return partialClients; }
    public void setPartialClients(int partialClients) { this.partialClients = partialClients; }

    public int getOverdueClients() { return overdueClients; }
    public void setOverdueClients(int overdueClients) { this.overdueClients = overdueClients; }

    public int getBlacklistedClients() { return blacklistedClients; }
    public void setBlacklistedClients(int blacklistedClients) { this.blacklistedClients = blacklistedClients; }

    public int getOwingClients() { return owingClients; }
    public void setOwingClients(int owingClients) { this.owingClients = owingClients; }

    public int getTotalLoans() { return totalLoans; }
    public void setTotalLoans(int totalLoans) { this.totalLoans = totalLoans; }

    public int getPaidLoans() { return paidLoans; }
    public void setPaidLoans(int paidLoans) { this.paidLoans = paidLoans; }

    public int getPartialLoans() { return partialLoans; }
    public void setPartialLoans(int partialLoans) { this.partialLoans = partialLoans; }

    public int getOverdueLoans() { return overdueLoans; }
    public void setOverdueLoans(int overdueLoans) { this.overdueLoans = overdueLoans; }

    public Map<String, Double> getMonthlyInvestments() { return monthlyInvestments; }
    public void setMonthlyInvestments(Map<String, Double> monthlyInvestments) { this.monthlyInvestments = monthlyInvestments; }

    public Map<String, Double> getMonthlyLoans() { return monthlyLoans; }
    public void setMonthlyLoans(Map<String, Double> monthlyLoans) { this.monthlyLoans = monthlyLoans; }

    public Map<String, Double> getMonthlyRepayments() { return monthlyRepayments; }
    public void setMonthlyRepayments(Map<String, Double> monthlyRepayments) { this.monthlyRepayments = monthlyRepayments; }

    public List<InvestorSummary> getInvestorSummaries() { return investorSummaries; }
    public void setInvestorSummaries(List<InvestorSummary> investorSummaries) { this.investorSummaries = investorSummaries; }

    public long getLastSyncTime() { return lastSyncTime; }
    public void setLastSyncTime(long lastSyncTime) { this.lastSyncTime = lastSyncTime; }

    // Utility methods
    public double getRepaymentRate() {
        if (totalLoaned == 0) return 0.0;
        return (totalRepaid / totalLoaned) * 100;
    }

    public double getUtilizationRate() {
        if (totalInvested == 0) return 0.0;
        return (totalLoaned / totalInvested) * 100;
    }

    /**
     * Investor summary data model
     */
    public static class InvestorSummary {
        private String investorId;
        private String investorName;
        private boolean isMainAccount;
        private double invested;
        private double loanedOut;
        private double repaid;
        private double outstanding;
        private double available;

        public InvestorSummary() {}

        public InvestorSummary(String investorId, String investorName, boolean isMainAccount) {
            this.investorId = investorId;
            this.investorName = investorName;
            this.isMainAccount = isMainAccount;
        }

        // Getters and setters
        public String getInvestorId() { return investorId; }
        public void setInvestorId(String investorId) { this.investorId = investorId; }

        public String getInvestorName() { return investorName; }
        public void setInvestorName(String investorName) { this.investorName = investorName; }

        public boolean isMainAccount() { return isMainAccount; }
        public void setMainAccount(boolean mainAccount) { isMainAccount = mainAccount; }

        public double getInvested() { return invested; }
        public void setInvested(double invested) { this.invested = invested; }

        public double getLoanedOut() { return loanedOut; }
        public void setLoanedOut(double loanedOut) { this.loanedOut = loanedOut; }

        public double getRepaid() { return repaid; }
        public void setRepaid(double repaid) { this.repaid = repaid; }

        public double getOutstanding() { return outstanding; }
        public void setOutstanding(double outstanding) { this.outstanding = outstanding; }

        public double getAvailable() { return available; }
        public void setAvailable(double available) { this.available = available; }

        // Calculated properties
        public double getRepaymentRate() {
            if (loanedOut == 0) return 0.0;
            return (repaid / loanedOut) * 100;
        }

        public double getUtilizationRate() {
            if (invested == 0) return 0.0;
            return (loanedOut / invested) * 100;
        }
    }

    @Override
    public String toString() {
        return "DashboardStats{" +
                "totalInvested=" + totalInvested +
                ", totalLoaned=" + totalLoaned +
                ", totalRepaid=" + totalRepaid +
                ", totalOutstanding=" + totalOutstanding +
                ", totalAvailable=" + totalAvailable +
                ", totalClients=" + totalClients +
                ", lastSyncTime=" + lastSyncTime +
                '}';
    }
}
