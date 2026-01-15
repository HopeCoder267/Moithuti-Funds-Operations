package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.moithuti.funds.sync.SyncStatus;

/**
 * Investor Transaction Entity - Represents a transaction in the investor ledger
 * Contains transaction details and sync status for offline-first architecture
 */
@Entity(
    tableName = "investor_transactions",
    foreignKeys = @ForeignKey(
        entity = InvestorEntity.class,
        parentColumns = "uuid",
        childColumns = "investorId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index(value = {"uuid"}, unique = true),
        @Index(value = {"investorId"}),
        @Index(value = {"relatedLoanId"}),
        @Index(value = {"yearMonth"})
    }
)
public class InvestorTransactionEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    public String investorId;
    
    public String type; // INVEST | LOAN_OUT | REPAYMENT_IN
    
    public double amount;
    
    public String relatedLoanId; // nullable - links to loan if applicable
    
    public long timestamp;
    
    public String yearMonth; // Format: "YYYY-MM" for monthly grouping
    
    public long lastModified;
    
    public String syncStatus; // PENDING | SYNCED | FAILED
    
    public boolean deleted;

    public InvestorTransactionEntity() {
        this.timestamp = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
        
        // Set yearMonth from timestamp
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
        this.yearMonth = sdf.format(new java.util.Date(this.timestamp));
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getInvestorId() { return investorId; }
    public void setInvestorId(String investorId) { this.investorId = investorId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getRelatedLoanId() { return relatedLoanId; }
    public void setRelatedLoanId(String relatedLoanId) { this.relatedLoanId = relatedLoanId; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { 
        this.timestamp = timestamp;
        // Update yearMonth when timestamp changes
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
        this.yearMonth = sdf.format(new java.util.Date(this.timestamp));
    }

    public String getYearMonth() { return yearMonth; }
    public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }

    public long getLastModified() { return lastModified; }
    public void setLastModified(long lastModified) { this.lastModified = lastModified; }

    public String getSyncStatus() { return syncStatus; }
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    @Override
    public String toString() {
        return "InvestorTransactionEntity{" +
                "uuid='" + uuid + '\'' +
                ", investorId='" + investorId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", relatedLoanId='" + relatedLoanId + '\'' +
                ", yearMonth='" + yearMonth + '\'' +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }
}
