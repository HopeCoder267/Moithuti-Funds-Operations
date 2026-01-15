package com.moithuti.funds.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.moithuti.funds.data.local.entity.BufferEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BufferDao_Impl implements BufferDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BufferEntity> __insertionAdapterOfBufferEntity;

  private final EntityDeletionOrUpdateAdapter<BufferEntity> __deletionAdapterOfBufferEntity;

  private final EntityDeletionOrUpdateAdapter<BufferEntity> __updateAdapterOfBufferEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public BufferDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBufferEntity = new EntityInsertionAdapter<BufferEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `buffers` (`uuid`,`investorId`,`amount`,`reason`,`createdDate`,`lastModified`,`syncStatus`,`deleted`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final BufferEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
        if (entity.investorId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.investorId);
        }
        statement.bindDouble(3, entity.amount);
        if (entity.reason == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.reason);
        }
        statement.bindLong(5, entity.createdDate);
        statement.bindLong(6, entity.lastModified);
        if (entity.syncStatus == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.syncStatus);
        }
        final int _tmp = entity.deleted ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__deletionAdapterOfBufferEntity = new EntityDeletionOrUpdateAdapter<BufferEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `buffers` WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final BufferEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
      }
    };
    this.__updateAdapterOfBufferEntity = new EntityDeletionOrUpdateAdapter<BufferEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `buffers` SET `uuid` = ?,`investorId` = ?,`amount` = ?,`reason` = ?,`createdDate` = ?,`lastModified` = ?,`syncStatus` = ?,`deleted` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final BufferEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
        if (entity.investorId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.investorId);
        }
        statement.bindDouble(3, entity.amount);
        if (entity.reason == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.reason);
        }
        statement.bindLong(5, entity.createdDate);
        statement.bindLong(6, entity.lastModified);
        if (entity.syncStatus == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.syncStatus);
        }
        final int _tmp = entity.deleted ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.uuid == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.uuid);
        }
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE buffers SET deleted = 1, lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE buffers SET syncStatus = ?, lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final BufferEntity buffer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBufferEntity.insert(buffer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final BufferEntity buffer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBufferEntity.handle(buffer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final BufferEntity buffer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBufferEntity.handle(buffer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void softDelete(final String uuid, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, timestamp);
    _argIndex = 2;
    if (uuid == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, uuid);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfSoftDelete.release(_stmt);
    }
  }

  @Override
  public void updateSyncStatus(final String uuid, final String syncStatus, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSyncStatus.acquire();
    int _argIndex = 1;
    if (syncStatus == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, syncStatus);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, timestamp);
    _argIndex = 3;
    if (uuid == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, uuid);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateSyncStatus.release(_stmt);
    }
  }

  @Override
  public LiveData<List<BufferEntity>> getAllBuffers() {
    final String _sql = "SELECT * FROM buffers WHERE deleted = 0 ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"buffers"}, false, new Callable<List<BufferEntity>>() {
      @Override
      @Nullable
      public List<BufferEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<BufferEntity> _result = new ArrayList<BufferEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BufferEntity _item;
            _item = new BufferEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _item.investorId = null;
            } else {
              _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _item.reason = null;
            } else {
              _item.reason = _cursor.getString(_cursorIndexOfReason);
            }
            _item.createdDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item.lastModified = _cursor.getLong(_cursorIndexOfLastModified);
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _item.syncStatus = null;
            } else {
              _item.syncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _item.deleted = _tmp != 0;
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<BufferEntity>> getBuffersByInvestor(final String investorId) {
    final String _sql = "SELECT * FROM buffers WHERE deleted = 0 AND investorId = ? ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"buffers"}, false, new Callable<List<BufferEntity>>() {
      @Override
      @Nullable
      public List<BufferEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<BufferEntity> _result = new ArrayList<BufferEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BufferEntity _item;
            _item = new BufferEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _item.investorId = null;
            } else {
              _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _item.reason = null;
            } else {
              _item.reason = _cursor.getString(_cursorIndexOfReason);
            }
            _item.createdDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item.lastModified = _cursor.getLong(_cursorIndexOfLastModified);
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _item.syncStatus = null;
            } else {
              _item.syncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _item.deleted = _tmp != 0;
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<BufferEntity>> getMainAccountBuffers() {
    final String _sql = "SELECT * FROM buffers WHERE deleted = 0 AND investorId IS NULL ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"buffers"}, false, new Callable<List<BufferEntity>>() {
      @Override
      @Nullable
      public List<BufferEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<BufferEntity> _result = new ArrayList<BufferEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BufferEntity _item;
            _item = new BufferEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _item.investorId = null;
            } else {
              _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _item.reason = null;
            } else {
              _item.reason = _cursor.getString(_cursorIndexOfReason);
            }
            _item.createdDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item.lastModified = _cursor.getLong(_cursorIndexOfLastModified);
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _item.syncStatus = null;
            } else {
              _item.syncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _item.deleted = _tmp != 0;
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public BufferEntity getBufferById(final String uuid) {
    final String _sql = "SELECT * FROM buffers WHERE deleted = 0 AND uuid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uuid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uuid);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final BufferEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new BufferEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _result.uuid = null;
        } else {
          _result.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _result.investorId = null;
        } else {
          _result.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfReason)) {
          _result.reason = null;
        } else {
          _result.reason = _cursor.getString(_cursorIndexOfReason);
        }
        _result.createdDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _result.lastModified = _cursor.getLong(_cursorIndexOfLastModified);
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _result.syncStatus = null;
        } else {
          _result.syncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _result.deleted = _tmp != 0;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double getTotalBufferForInvestor(final String investorId) {
    final String _sql = "SELECT COALESCE(SUM(amount), 0) FROM buffers WHERE deleted = 0 AND investorId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double getTotalBufferForMainAccount() {
    final String _sql = "SELECT COALESCE(SUM(amount), 0) FROM buffers WHERE deleted = 0 AND investorId IS NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double getTotalBuffers() {
    final String _sql = "SELECT COALESCE(SUM(amount), 0) FROM buffers WHERE deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<BufferEntity> getPendingSync() {
    final String _sql = "SELECT * FROM buffers WHERE syncStatus = 'PENDING' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<BufferEntity> _result = new ArrayList<BufferEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final BufferEntity _item;
        _item = new BufferEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfReason)) {
          _item.reason = null;
        } else {
          _item.reason = _cursor.getString(_cursorIndexOfReason);
        }
        _item.createdDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.lastModified = _cursor.getLong(_cursorIndexOfLastModified);
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _item.syncStatus = null;
        } else {
          _item.syncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _item.deleted = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
