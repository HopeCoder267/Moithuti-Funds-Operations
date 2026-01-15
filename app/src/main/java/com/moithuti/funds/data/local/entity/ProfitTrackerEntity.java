package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Profit Tracker Entity - Tracks cumulative and monthly profit
 * Maintains profit calculations across all loans and repayments
 */
@Entity(tableName = "profit_tracker",
        foreignKeys = {
            @ForeignKey(entity = InvestorEntity.class,
                    parentColumns = "uuid",
                    childColumns = "investorId",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.NO_ACTION)
        },
        indices = {
            @Index(value = {"uuid"}, unique = true),
            @Index(value = {"investorId"}),
            @Index(value = {"yearMonth"}),
            @Index(value = {"investorId", "yearMonth"}, unique = true)
        })
public class ProfitTrackerEntity {

    @PrimaryKey
    @NonNull
    private String uuid;

    // null for Main Account, investor UUID for specific investors
    private String investorId;

    // Format: "YYYY-MM" for monthly tracking
    private String yearMonth;

    // Monthly profit (repayments - loans issued)
    private double monthlyProfit;

    // Cumulative profit up to this month
    private double cumulativeProfit;

    // Monthly interest earned
    private double monthlyInterest;

    // Cumulative interest up to this month
    private double cumulativeInterest;

    // Total loans issued this month
    private double monthlyLoansIssued;

    // Total repayments received this month
    private double monthlyRepaymentsReceived;

    // Available funds at month end
    private double availableFunds;

    // Timestamps
    private long createdDate;
    private long lastModified;

    // Sync status
    private String syncStatus;

    // Soft delete flag
    private boolean deleted;

    // Default constructor
    public ProfitTrackerEntity() {
        this.deleted = false;
        this.syncStatus = "PENDING";
    }

    // Getters and Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public double getMonthlyProfit() {
        return monthlyProfit;
    }

    public void setMonthlyProfit(double monthlyProfit) {
        this.monthlyProfit = monthlyProfit;
    }

    public double getCumulativeProfit() {
        return cumulativeProfit;
    }

    public void setCumulativeProfit(double cumulativeProfit) {
        this.cumulativeProfit = cumulativeProfit;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public double getCumulativeInterest() {
        return cumulativeInterest;
    }

    public void setCumulativeInterest(double cumulativeInterest) {
        this.cumulativeInterest = cumulativeInterest;
    }

    public double getMonthlyLoansIssued() {
        return monthlyLoansIssued;
    }

    public void setMonthlyLoansIssued(double monthlyLoansIssued) {
        this.monthlyLoansIssued = monthlyLoansIssued;
    }

    public double getMonthlyRepaymentsReceived() {
        return monthlyRepaymentsReceived;
    }

    public void setMonthlyRepaymentsReceived(double monthlyRepaymentsReceived) {
        this.monthlyRepaymentsReceived = monthlyRepaymentsReceived;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(double availableFunds) {
        this.availableFunds = availableFunds;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ProfitTrackerEntity{" +
                "uuid='" + uuid + '\'' +
                ", investorId='" + investorId + '\'' +
                ", yearMonth='" + yearMonth + '\'' +
                ", monthlyProfit=" + monthlyProfit +
                ", cumulativeProfit=" + cumulativeProfit +
                ", monthlyInterest=" + monthlyInterest +
                ", cumulativeInterest=" + cumulativeInterest +
                ", monthlyLoansIssued=" + monthlyLoansIssued +
                ", monthlyRepaymentsReceived=" + monthlyRepaymentsReceived +
                ", availableFunds=" + availableFunds +
                ", createdDate=" + createdDate +
                ", lastModified=" + lastModified +
                ", syncStatus='" + syncStatus + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
