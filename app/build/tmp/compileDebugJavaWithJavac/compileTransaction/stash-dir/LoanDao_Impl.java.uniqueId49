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
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.moithuti.funds.data.local.entity.LoanEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LoanDao_Impl implements LoanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LoanEntity> __insertionAdapterOfLoanEntity;

  private final EntityDeletionOrUpdateAdapter<LoanEntity> __deletionAdapterOfLoanEntity;

  private final EntityDeletionOrUpdateAdapter<LoanEntity> __updateAdapterOfLoanEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastModified;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfRestoreLoan;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLoanStatus;

  public LoanDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoanEntity = new EntityInsertionAdapter<LoanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `loans` (`uuid`,`clientId`,`amount`,`dateIssued`,`dueDate`,`status`,`lastModified`,`syncStatus`,`deleted`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoanEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
        if (entity.clientId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.clientId);
        }
        statement.bindDouble(3, entity.amount);
        statement.bindLong(4, entity.dateIssued);
        statement.bindLong(5, entity.dueDate);
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        statement.bindLong(7, entity.lastModified);
        if (entity.syncStatus == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.syncStatus);
        }
        final int _tmp = entity.deleted ? 1 : 0;
        statement.bindLong(9, _tmp);
      }
    };
    this.__deletionAdapterOfLoanEntity = new EntityDeletionOrUpdateAdapter<LoanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `loans` WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoanEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
      }
    };
    this.__updateAdapterOfLoanEntity = new EntityDeletionOrUpdateAdapter<LoanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `loans` SET `uuid` = ?,`clientId` = ?,`amount` = ?,`dateIssued` = ?,`dueDate` = ?,`status` = ?,`lastModified` = ?,`syncStatus` = ?,`deleted` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoanEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
        if (entity.clientId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.clientId);
        }
        statement.bindDouble(3, entity.amount);
        statement.bindLong(4, entity.dateIssued);
        statement.bindLong(5, entity.dueDate);
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        statement.bindLong(7, entity.lastModified);
        if (entity.syncStatus == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.syncStatus);
        }
        final int _tmp = entity.deleted ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.uuid == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.uuid);
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM loans WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loans SET syncStatus = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastModified = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loans SET lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loans SET deleted = 1, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRestoreLoan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loans SET deleted = 0, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLoanStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loans SET status = ?, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final LoanEntity loan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfLoanEntity.insertAndReturnId(loan);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final LoanEntity loan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLoanEntity.handle(loan);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final LoanEntity loan) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total += __updateAdapterOfLoanEntity.handle(loan);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final String uuid) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
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
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public void updateSyncStatus(final String uuid, final String syncStatus) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSyncStatus.acquire();
    int _argIndex = 1;
    if (syncStatus == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, syncStatus);
    }
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
      __preparedStmtOfUpdateSyncStatus.release(_stmt);
    }
  }

  @Override
  public void updateLastModified(final String uuid, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastModified.acquire();
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
      __preparedStmtOfUpdateLastModified.release(_stmt);
    }
  }

  @Override
  public int softDelete(final String uuid, final long timestamp) {
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
        final int _result = _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfSoftDelete.release(_stmt);
    }
  }

  @Override
  public int restoreLoan(final String uuid, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRestoreLoan.acquire();
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
        final int _result = _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfRestoreLoan.release(_stmt);
    }
  }

  @Override
  public int updateLoanStatus(final String uuid, final String status, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLoanStatus.acquire();
    int _argIndex = 1;
    if (status == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, status);
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
        final int _result = _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateLoanStatus.release(_stmt);
    }
  }

  @Override
  public LoanEntity getLoanById(final String uuid) {
    final String _sql = "SELECT * FROM loans WHERE uuid = ?";
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
      final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final LoanEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new LoanEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _result.uuid = null;
        } else {
          _result.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfClientId)) {
          _result.clientId = null;
        } else {
          _result.clientId = _cursor.getString(_cursorIndexOfClientId);
        }
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _result.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
        _result.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _result.status = null;
        } else {
          _result.status = _cursor.getString(_cursorIndexOfStatus);
        }
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
  public LiveData<LoanEntity> getLoanByIdLive(final String uuid) {
    final String _sql = "SELECT * FROM loans WHERE uuid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uuid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uuid);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loans"}, false, new Callable<LoanEntity>() {
      @Override
      @Nullable
      public LoanEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final LoanEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new LoanEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _result.uuid = null;
            } else {
              _result.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfClientId)) {
              _result.clientId = null;
            } else {
              _result.clientId = _cursor.getString(_cursorIndexOfClientId);
            }
            _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
            _result.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
            _result.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _result.status = null;
            } else {
              _result.status = _cursor.getString(_cursorIndexOfStatus);
            }
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<LoanEntity>> getAllLoansLive() {
    final String _sql = "SELECT * FROM loans WHERE deleted = 0 ORDER BY dateIssued DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"loans"}, false, new Callable<List<LoanEntity>>() {
      @Override
      @Nullable
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            _item = new LoanEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfClientId)) {
              _item.clientId = null;
            } else {
              _item.clientId = _cursor.getString(_cursorIndexOfClientId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
            _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _item.status = null;
            } else {
              _item.status = _cursor.getString(_cursorIndexOfStatus);
            }
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
  public List<LoanEntity> getAllLoans() {
    final String _sql = "SELECT * FROM loans WHERE deleted = 0 ORDER BY dateIssued DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanEntity _item;
        _item = new LoanEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfClientId)) {
          _item.clientId = null;
        } else {
          _item.clientId = _cursor.getString(_cursorIndexOfClientId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
        _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
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

  @Override
  public LiveData<List<LoanEntity>> getLoansByClientLive(final String clientId) {
    final String _sql = "SELECT * FROM loans WHERE deleted = 0 AND clientId = ? ORDER BY dateIssued DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (clientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, clientId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loans"}, false, new Callable<List<LoanEntity>>() {
      @Override
      @Nullable
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            _item = new LoanEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfClientId)) {
              _item.clientId = null;
            } else {
              _item.clientId = _cursor.getString(_cursorIndexOfClientId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
            _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _item.status = null;
            } else {
              _item.status = _cursor.getString(_cursorIndexOfStatus);
            }
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
  public List<LoanEntity> getLoansByClient(final String clientId) {
    final String _sql = "SELECT * FROM loans WHERE deleted = 0 AND clientId = ? ORDER BY dateIssued DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (clientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, clientId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanEntity _item;
        _item = new LoanEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfClientId)) {
          _item.clientId = null;
        } else {
          _item.clientId = _cursor.getString(_cursorIndexOfClientId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
        _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
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

  @Override
  public LiveData<List<LoanEntity>> getLoansByStatus(final String status) {
    final String _sql = "SELECT * FROM loans WHERE deleted = 0 AND status = ? ORDER BY dateIssued DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loans"}, false, new Callable<List<LoanEntity>>() {
      @Override
      @Nullable
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            _item = new LoanEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfClientId)) {
              _item.clientId = null;
            } else {
              _item.clientId = _cursor.getString(_cursorIndexOfClientId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
            _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _item.status = null;
            } else {
              _item.status = _cursor.getString(_cursorIndexOfStatus);
            }
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
  public LiveData<List<LoanEntity>> getLoansByStatuses(final List<String> statuses) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM loans WHERE deleted = 0 AND status IN (");
    final int _inputSize = statuses == null ? 1 : statuses.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY dateIssued DESC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    if (statuses == null) {
      _statement.bindNull(_argIndex);
    } else {
      for (String _item : statuses) {
        if (_item == null) {
          _statement.bindNull(_argIndex);
        } else {
          _statement.bindString(_argIndex, _item);
        }
        _argIndex++;
      }
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loans"}, false, new Callable<List<LoanEntity>>() {
      @Override
      @Nullable
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item_1;
            _item_1 = new LoanEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item_1.uuid = null;
            } else {
              _item_1.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfClientId)) {
              _item_1.clientId = null;
            } else {
              _item_1.clientId = _cursor.getString(_cursorIndexOfClientId);
            }
            _item_1.amount = _cursor.getDouble(_cursorIndexOfAmount);
            _item_1.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
            _item_1.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _item_1.status = null;
            } else {
              _item_1.status = _cursor.getString(_cursorIndexOfStatus);
            }
            _item_1.lastModified = _cursor.getLong(_cursorIndexOfLastModified);
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _item_1.syncStatus = null;
            } else {
              _item_1.syncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _item_1.deleted = _tmp != 0;
            _result.add(_item_1);
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
  public LiveData<List<LoanEntity>> getOverdueLoans(final long currentTime) {
    final String _sql = "SELECT * FROM loans WHERE deleted = 0 AND dueDate < ? AND status != 'PAID' AND status != 'BLACKLISTED' ORDER BY dueDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    return __db.getInvalidationTracker().createLiveData(new String[] {"loans"}, false, new Callable<List<LoanEntity>>() {
      @Override
      @Nullable
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            _item = new LoanEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfClientId)) {
              _item.clientId = null;
            } else {
              _item.clientId = _cursor.getString(_cursorIndexOfClientId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
            _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _item.status = null;
            } else {
              _item.status = _cursor.getString(_cursorIndexOfStatus);
            }
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
  public List<LoanEntity> getPendingSyncLoans() {
    final String _sql = "SELECT * FROM loans WHERE syncStatus = 'PENDING' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanEntity _item;
        _item = new LoanEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfClientId)) {
          _item.clientId = null;
        } else {
          _item.clientId = _cursor.getString(_cursorIndexOfClientId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
        _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
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

  @Override
  public List<LoanEntity> getFailedSyncLoans() {
    final String _sql = "SELECT * FROM loans WHERE syncStatus = 'FAILED' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanEntity _item;
        _item = new LoanEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfClientId)) {
          _item.clientId = null;
        } else {
          _item.clientId = _cursor.getString(_cursorIndexOfClientId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
        _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
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

  @Override
  public int getLoanCount() {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getLoanCountByStatus(final String status) {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE deleted = 0 AND status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double getTotalLoanedAmount() {
    final String _sql = "SELECT SUM(amount) FROM loans WHERE deleted = 0";
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
  public double getTotalLoanedAmountByStatus(final String status) {
    final String _sql = "SELECT SUM(amount) FROM loans WHERE deleted = 0 AND status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
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
  public List<LoanEntity> getDeletedLoans() {
    final String _sql = "SELECT * FROM loans WHERE deleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfClientId = CursorUtil.getColumnIndexOrThrow(_cursor, "clientId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfDateIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIssued");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanEntity _item;
        _item = new LoanEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfClientId)) {
          _item.clientId = null;
        } else {
          _item.clientId = _cursor.getString(_cursorIndexOfClientId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        _item.dateIssued = _cursor.getLong(_cursorIndexOfDateIssued);
        _item.dueDate = _cursor.getLong(_cursorIndexOfDueDate);
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
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
