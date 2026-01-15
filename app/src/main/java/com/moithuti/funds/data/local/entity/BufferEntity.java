package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.moithuti.funds.sync.SyncStatus;

/**
 * Buffer Entity - Represents locked funds for accounts
 * Buffers reduce available balance but don't affect historical data
 */
@Entity(
    tableName = "buffers",
    foreignKeys = @ForeignKey(
        entity = InvestorEntity.class,
        parentColumns = "uuid",
        childColumns = "investorId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index(value = {"uuid"}, unique = true),
        @Index(value = {"investorId"})
    }
)
public class BufferEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "investorId")
    public String investorId; // null for Main Account

    @ColumnInfo(name = "amount")
    public double amount;

    @ColumnInfo(name = "reason")
    public String reason;

    @ColumnInfo(name = "createdDate")
    public long createdDate;

    @ColumnInfo(name = "lastModified")
    public long lastModified;

    @ColumnInfo(name = "syncStatus")
    public String syncStatus; // PENDING | SYNCED | FAILED

    @ColumnInfo(name = "deleted")
    public boolean deleted;

    public BufferEntity() {
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getInvestorId() { return investorId; }
    public void setInvestorId(String investorId) { this.investorId = investorId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

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
        return "BufferEntity{" +
                "uuid='" + uuid + '\'' +
                ", investorId='" + investorId + '\'' +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
