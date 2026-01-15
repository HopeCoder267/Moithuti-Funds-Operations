package com.moithuti.funds.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Index;

import com.moithuti.funds.sync.SyncStatus;

/**
 * Client Entity - Represents a client in the local database
 * Contains client information and sync status for offline-first architecture
 */
@Entity(tableName = "clients", indices = {@Index(value = {"uuid"}, unique = true)})
public class ClientEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    public String uuid;

    public String name;
    
    public String phone;
    
    public String notes;
    
    public String status; // PAID | PARTIAL | OVERDUE | BLACKLISTED
    
    public long createdDate;
    
    public long lastModified;
    
    public String syncStatus; // PENDING | SYNCED | FAILED
    
    public boolean deleted;

    public ClientEntity() {
        this.createdDate = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING.name();
        this.deleted = false;
        this.status = "OWING"; // Default status
    }

    // Getters and setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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
        return "ClientEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }
}
