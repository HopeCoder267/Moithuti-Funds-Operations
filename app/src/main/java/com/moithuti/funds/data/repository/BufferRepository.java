package com.moithuti.funds.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.moithuti.funds.data.local.AppDatabase;
import com.moithuti.funds.data.local.dao.BufferDao;
import com.moithuti.funds.data.local.entity.BufferEntity;
import com.moithuti.funds.sync.SyncScheduler;

import java.util.List;
import java.util.UUID;

/**
 * Buffer Repository - Manages buffer data operations
 * Handles buffer CRUD operations and sync triggers
 */
public class BufferRepository {
    
    private static final String TAG = "BufferRepository";
    private static BufferRepository instance;
    
    private final BufferDao bufferDao;
    private final SyncScheduler syncScheduler;
    
    private BufferRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.bufferDao = database.bufferDao();
        this.syncScheduler = SyncScheduler.getInstance(application);
    }
    
    public static synchronized BufferRepository getInstance(Application application) {
        if (instance == null) {
            instance = new BufferRepository(application);
        }
        return instance;
    }
    
    /**
     * Create a new buffer
     */
    public String createBuffer(String investorId, double amount, String reason) {
        BufferEntity buffer = new BufferEntity();
        buffer.setUuid(UUID.randomUUID().toString());
        buffer.setInvestorId(investorId); // null for Main Account
        buffer.setAmount(amount);
        buffer.setReason(reason);
        
        bufferDao.insert(buffer);
        
        // Trigger sync
        syncScheduler.scheduleImmediateSync();
        
        Log.d(TAG, "Created buffer: " + buffer.getUuid() + " for investor: " + investorId);
        return buffer.getUuid();
    }
    
    /**
     * Update an existing buffer
     */
    public void updateBuffer(BufferEntity buffer) {
        buffer.setLastModified(System.currentTimeMillis());
        buffer.setSyncStatus("PENDING");
        
        bufferDao.update(buffer);
        
        // Trigger sync
        syncScheduler.scheduleImmediateSync();
        
        Log.d(TAG, "Updated buffer: " + buffer.getUuid());
    }
    
    /**
     * Delete a buffer
     */
    public void deleteBuffer(String bufferId) {
        long timestamp = System.currentTimeMillis();
        bufferDao.softDelete(bufferId, timestamp);
        
        // Trigger sync
        syncScheduler.scheduleImmediateSync();
        
        Log.d(TAG, "Deleted buffer: " + bufferId);
    }
    
    /**
     * Get all buffers
     */
    public LiveData<List<BufferEntity>> getAllBuffers() {
        return bufferDao.getAllBuffers();
    }
    
    /**
     * Get buffers for specific investor
     */
    public LiveData<List<BufferEntity>> getBuffersByInvestor(String investorId) {
        return bufferDao.getBuffersByInvestor(investorId);
    }
    
    /**
     * Get Main Account buffers
     */
    public LiveData<List<BufferEntity>> getMainAccountBuffers() {
        return bufferDao.getMainAccountBuffers();
    }
    
    /**
     * Get buffer by ID
     */
    public BufferEntity getBufferById(String bufferId) {
        return bufferDao.getBufferById(bufferId);
    }
    
    /**
     * Get total buffer amount for investor
     */
    public double getTotalBufferForInvestor(String investorId) {
        return bufferDao.getTotalBufferForInvestor(investorId);
    }
    
    /**
     * Get total buffer amount for Main Account
     */
    public double getTotalBufferForMainAccount() {
        return bufferDao.getTotalBufferForMainAccount();
    }
    
    /**
     * Get total buffer amount for all accounts
     */
    public double getTotalBuffers() {
        return bufferDao.getTotalBuffers();
    }
    
    /**
     * Get buffers pending sync
     */
    public List<BufferEntity> getPendingSync() {
        return bufferDao.getPendingSync();
    }
    
    /**
     * Update sync status for buffer
     */
    public void updateSyncStatus(String bufferId, String syncStatus) {
        long timestamp = System.currentTimeMillis();
        bufferDao.updateSyncStatus(bufferId, syncStatus, timestamp);
    }
}
