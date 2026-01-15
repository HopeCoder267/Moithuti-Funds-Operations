package com.moithuti.funds.util;

import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Investor Calculator - Implements investor balance calculations
 * Calculates investor balances based on transaction ledger
 * All balances are CALCULATED, never stored per requirements
 */
public class InvestorCalculator {

    // Transaction types
    public static final String TYPE_INVEST = "INVEST";
    public static final String TYPE_LOAN_OUT = "LOAN_OUT";
    public static final String TYPE_REPAYMENT_IN = "REPAYMENT_IN";

    /**
     * Calculate investor balance summary
     * 
     * RULES:
     * Invested = SUM(INVEST)
     * Rolled Out = SUM(LOAN_OUT)
     * Repaid = SUM(REPAYMENT_IN)
     * Outstanding = Rolled Out - Repaid
     * Available = Invested - Rolled Out + Repaid
     * 
     * @param transactions list of investor transactions
     * @return investor balance summary
     */
    public static InvestorBalance calculateBalance(List<InvestorTransactionEntity> transactions) {
        InvestorBalance balance = new InvestorBalance();
        
        if (transactions == null) {
            return balance;
        }

        for (InvestorTransactionEntity transaction : transactions) {
            if (transaction.isDeleted()) {
                continue; // Skip deleted transactions
            }

            switch (transaction.getType()) {
                case TYPE_INVEST:
                    balance.invested += transaction.getAmount();
                    break;
                case TYPE_LOAN_OUT:
                    balance.rolledOut += transaction.getAmount();
                    break;
                case TYPE_REPAYMENT_IN:
                    balance.repaid += transaction.getAmount();
                    break;
            }
        }

        // Calculate derived values
        balance.outstanding = balance.rolledOut - balance.repaid;
        balance.available = balance.invested - balance.rolledOut + balance.repaid;

        return balance;
    }

    /**
     * Calculate overall balance across all investors
     * @param allTransactions list of all investor transactions
     * @return overall balance summary
     */
    public static InvestorBalance calculateOverallBalance(List<InvestorTransactionEntity> allTransactions) {
        return calculateBalance(allTransactions);
    }

    /**
     * Calculate monthly totals for an investor
     * @param transactions list of investor transactions
     * @return map of yearMonth to monthly totals by transaction type
     */
    public static Map<String, MonthlyTotals> calculateMonthlyTotals(List<InvestorTransactionEntity> transactions) {
        Map<String, MonthlyTotals> monthlyMap = new HashMap<>();

        if (transactions == null) {
            return monthlyMap;
        }

        for (InvestorTransactionEntity transaction : transactions) {
            if (transaction.isDeleted()) {
                continue;
            }

            String yearMonth = transaction.getYearMonth();
            MonthlyTotals totals = monthlyMap.get(yearMonth);
            
            if (totals == null) {
                totals = new MonthlyTotals();
                monthlyMap.put(yearMonth, totals);
            }

            switch (transaction.getType()) {
                case TYPE_INVEST:
                    totals.invest += transaction.getAmount();
                    break;
                case TYPE_LOAN_OUT:
                    totals.loanOut += transaction.getAmount();
                    break;
                case TYPE_REPAYMENT_IN:
                    totals.repaymentIn += transaction.getAmount();
                    break;
            }
        }

        return monthlyMap;
    }

    /**
     * Get total invested amount
     * @param transactions list of transactions
     * @return total invested amount
     */
    public static double getTotalInvested(List<InvestorTransactionEntity> transactions) {
        return getTotalByType(transactions, TYPE_INVEST);
    }

    /**
     * Get total loaned out amount
     * @param transactions list of transactions
     * @return total loaned out amount
     */
    public static double getTotalLoanedOut(List<InvestorTransactionEntity> transactions) {
        return getTotalByType(transactions, TYPE_LOAN_OUT);
    }

    /**
     * Get total repaid amount
     * @param transactions list of transactions
     * @return total repaid amount
     */
    public static double getTotalRepaid(List<InvestorTransactionEntity> transactions) {
        return getTotalByType(transactions, TYPE_REPAYMENT_IN);
    }

    /**
     * Get total amount by transaction type
     * @param transactions list of transactions
     * @param type transaction type
     * @return total amount for the type
     */
    public static double getTotalByType(List<InvestorTransactionEntity> transactions, String type) {
        double total = 0.0;
        
        if (transactions == null) {
            return total;
        }

        for (InvestorTransactionEntity transaction : transactions) {
            if (!transaction.isDeleted() && type.equals(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        
        return total;
    }

    /**
     * Check if investor has sufficient funds for a loan
     * @param transactions list of investor transactions
     * @param loanAmount loan amount to check
     * @return true if sufficient funds, false otherwise
     */
    public static boolean hasSufficientFunds(List<InvestorTransactionEntity> transactions, double loanAmount) {
        InvestorBalance balance = calculateBalance(transactions);
        return balance.available >= loanAmount;
    }

    /**
     * Get available funds for investor
     * @param transactions list of investor transactions
     * @return available funds amount
     */
    public static double getAvailableFunds(List<InvestorTransactionEntity> transactions) {
        InvestorBalance balance = calculateBalance(transactions);
        return balance.available;
    }

    /**
     * Investor balance data model
     */
    public static class InvestorBalance {
        public double invested = 0.0;
        public double rolledOut = 0.0;
        public double repaid = 0.0;
        public double outstanding = 0.0;  // Calculated: rolledOut - repaid
        public double available = 0.0;     // Calculated: invested - rolledOut + repaid

        // Calculated properties
        public double getRepaymentRate() {
            if (rolledOut == 0) return 0.0;
            return (repaid / rolledOut) * 100;
        }

        public double getUtilizationRate() {
            if (invested == 0) return 0.0;
            return (rolledOut / invested) * 100;
        }

        @Override
        public String toString() {
            return "InvestorBalance{" +
                    "invested=" + invested +
                    ", rolledOut=" + rolledOut +
                    ", repaid=" + repaid +
                    ", outstanding=" + outstanding +
                    ", available=" + available +
                    '}';
        }
    }

    /**
     * Monthly totals data model
     */
    public static class MonthlyTotals {
        public double invest = 0.0;
        public double loanOut = 0.0;
        public double repaymentIn = 0.0;

        // Calculated properties
        public double getNetFlow() {
            return invest - loanOut + repaymentIn;
        }

        @Override
        public String toString() {
            return "MonthlyTotals{" +
                    "invest=" + invest +
                    ", loanOut=" + loanOut +
                    ", repaymentIn=" + repaymentIn +
                    ", netFlow=" + getNetFlow() +
                    '}';
        }
    }

    /**
     * Get all transaction types
     * @return array of transaction types
     */
    public static String[] getAllTransactionTypes() {
        return new String[]{
            TYPE_INVEST,
            TYPE_LOAN_OUT,
            TYPE_REPAYMENT_IN
        };
    }

    /**
     * Check if transaction type is valid
     * @param type the type to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidTransactionType(String type) {
        if (type == null) return false;
        
        for (String validType : getAllTransactionTypes()) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get transaction type display name
     * @param type the transaction type
     * @return display-friendly name
     */
    public static String getTransactionTypeDisplayName(String type) {
        switch (type) {
            case TYPE_INVEST:
                return "Investment";
            case TYPE_LOAN_OUT:
                return "Loan Issued";
            case TYPE_REPAYMENT_IN:
                return "Repayment";
            default:
                return "Unknown";
        }
    }
}
