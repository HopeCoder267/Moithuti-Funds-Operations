package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.moithuti.funds.sync.SyncStatus;

/**
 * Monthly Balance Entity - Tracks monthly balance for accounts
 * Used for Main Account manual balance setting and investor tracking
 */
@Entity(
    tableName = "monthly_balances",
    foreignKeys = @ForeignKey(
        entity = InvestorEntity.class,
        parentColumns = "uuid",
        childColumns = "investorId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index(value = {"uuid"}, unique = true),
        @Index(value = {"investorId", "yearMonth"}, unique = true)
    }
)
public class MonthlyBalanceEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "investorId")
    public String investorId; // null for Main Account

    @ColumnInfo(name = "yearMonth")
    public String yearMonth; // Format: "2024-01"

    @ColumnInfo(name = "balance")
    public double balance;

    @ColumnInfo(name = "monthlyTopUp")
    public double monthlyTopUp;

    @ColumnInfo(name = "createdDate")
    public long createdDate;

    @ColumnInfo(name = "lastModified")
    public long lastModified;

    @ColumnInfo(name = "syncStatus")
    public String syncStatus; // PENDING | SYNCED | FAILED

    @ColumnInfo(name = "deleted")
    public boolean deleted;

    public MonthlyBalanceEntity() {
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
        this.monthlyTopUp = 0.0;
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getInvestorId() { return investorId; }
    public void setInvestorId(String investorId) { this.investorId = investorId; }

    public String getYearMonth() { return yearMonth; }
    public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public double getMonthlyTopUp() { return monthlyTopUp; }
    public void setMonthlyTopUp(double monthlyTopUp) { this.monthlyTopUp = monthlyTopUp; }

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
        return "MonthlyBalanceEntity{" +
                "uuid='" + uuid + '\'' +
                ", investorId='" + investorId + '\'' +
                ", yearMonth='" + yearMonth + '\'' +
                ", balance=" + balance +
                ", monthlyTopUp=" + monthlyTopUp +
                '}';
    }
}
