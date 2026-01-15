package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.moithuti.funds.sync.SyncStatus;

/**
 * Loan Funding Entity - Represents funding for a loan by an investor
 * Supports partial funding from multiple investors
 * Contains funding details and sync status for offline-first architecture
 */
@Entity(
    tableName = "loan_funding",
    foreignKeys = {
        @ForeignKey(
            entity = LoanEntity.class,
            parentColumns = "uuid",
            childColumns = "loanId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = InvestorEntity.class,
            parentColumns = "uuid",
            childColumns = "investorId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index(value = {"uuid"}, unique = true),
        @Index(value = {"loanId"}),
        @Index(value = {"investorId"})
    }
)
public class LoanFundingEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    public String loanId;
    
    public String investorId;
    
    public double amount;
    
    public long createdDate;
    
    public long lastModified;
    
    public String syncStatus; // PENDING | SYNCED | FAILED
    
    public boolean deleted;

    public LoanFundingEntity() {
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public String getInvestorId() { return investorId; }
    public void setInvestorId(String investorId) { this.investorId = investorId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public long getCreatedDate() { return createdDate; }
    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }

    public long getLastModified() { return lastModified; }
    public void setLastModified(long lastModified) { this.lastModified = lastModified; }

    public String getSyncStatus() { return syncStatus; }
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    @Override
    public String toString() {
        return "LoanFundingEntity{" +
                "uuid='" + uuid + '\'' +
                ", loanId='" + loanId + '\'' +
                ", investorId='" + investorId + '\'' +
                ", amount=" + amount +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }
}
