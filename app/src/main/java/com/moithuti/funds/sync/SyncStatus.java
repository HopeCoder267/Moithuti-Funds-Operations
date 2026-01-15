package com.moithuti.funds.sync;

/**
 * Sync Status Enum - Represents the synchronization status of entities
 * Used for offline-first architecture to track what needs to be synced
 */
public enum SyncStatus {
    PENDING("PENDING"),
    SYNCED("SYNCED"),
    FAILED("FAILED");

    private final String status;

    SyncStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

    /**
     * Get SyncStatus from string value
     * @param status the string status
     * @return corresponding SyncStatus enum
     */
    public static SyncStatus fromString(String status) {
        for (SyncStatus syncStatus : SyncStatus.values()) {
            if (syncStatus.status.equals(status)) {
                return syncStatus;
            }
        }
        throw new IllegalArgumentException("Unknown sync status: " + status);
    }
}
