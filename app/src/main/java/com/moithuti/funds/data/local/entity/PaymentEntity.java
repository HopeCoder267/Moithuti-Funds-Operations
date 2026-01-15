package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.moithuti.funds.sync.SyncStatus;

/**
 * Payment Entity - Represents a payment made towards a loan
 * Contains payment details and sync status for offline-first architecture
 */
@Entity(
    tableName = "payments",
    foreignKeys = @ForeignKey(
        entity = LoanEntity.class,
        parentColumns = "uuid",
        childColumns = "loanId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index(value = {"uuid"}, unique = true),
        @Index(value = {"loanId"})
    }
)
public class PaymentEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    public String loanId;
    
    public double amount;
    
    public long date;
    
    public long lastModified;
    
    public String syncStatus; // PENDING | SYNCED | FAILED
    
    public boolean deleted;

    public PaymentEntity() {
        this.date = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }

    public long getLastModified() { return lastModified; }
    public void setLastModified(long lastModified) { this.lastModified = lastModified; }

    public String getSyncStatus() { return syncStatus; }
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    @Override
    public String toString() {
        return "PaymentEntity{" +
                "uuid='" + uuid + '\'' +
                ", loanId='" + loanId + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }
}
