package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.moithuti.funds.sync.SyncStatus;

/**
 * Investor Entity - Represents an investor in the system
 * Contains investor information and sync status for offline-first architecture
 */
@Entity(tableName = "investors", indices = {@Index(value = {"uuid"}, unique = true)})
public class InvestorEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    public String name;
    
    public boolean isMainAccount;
    
    public long createdDate;
    
    public long lastModified;
    
    public String syncStatus; // PENDING | SYNCED | FAILED
    
    public boolean deleted;

    public InvestorEntity() {
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
        this.isMainAccount = false; // Default to not main account
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isMainAccount() { return isMainAccount; }
    public void setMainAccount(boolean mainAccount) { isMainAccount = mainAccount; }

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
        return "InvestorEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", isMainAccount=" + isMainAccount +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }
}
