package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.moithuti.funds.sync.SyncStatus;

/**
 * Loan Entity - Represents a loan issued to a client
 * Contains loan details and sync status for offline-first architecture
 */
@Entity(
    tableName = "loans",
    foreignKeys = @ForeignKey(
        entity = ClientEntity.class,
        parentColumns = "uuid",
        childColumns = "clientId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index(value = {"uuid"}, unique = true),
        @Index(value = {"clientId"})
    }
)
public class LoanEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    public String clientId;
    
    public double amount;
    
    public long dateIssued;
    
    public long dueDate;
    
    public String status; // OWING | PARTIAL | PAID | OVERDUE | BLACKLISTED
    
    public long lastModified;
    
    public String syncStatus; // PENDING | SYNCED | FAILED
    
    public boolean deleted;

    public LoanEntity() {
        this.dateIssued = System.currentTimeMillis();
        this.dueDate = System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000); // 30 days from now
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
        this.status = "OWING"; // Default status
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public long getDateIssued() { return dateIssued; }
    public void setDateIssued(long dateIssued) { this.dateIssued = dateIssued; }

    public long getDueDate() { return dueDate; }
    public void setDueDate(long dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getLastModified() { return lastModified; }
    public void setLastModified(long lastModified) { this.lastModified = lastModified; }

    public String getSyncStatus() { return syncStatus; }
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    @Override
    public String toString() {
        return "LoanEntity{" +
                "uuid='" + uuid + '\'' +
                ", clientId='" + clientId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }
}
