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
import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;
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
public final class InvestorTransactionDao_Impl implements InvestorTransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<InvestorTransactionEntity> __insertionAdapterOfInvestorTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<InvestorTransactionEntity> __deletionAdapterOfInvestorTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<InvestorTransactionEntity> __updateAdapterOfInvestorTransactionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastModified;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfRestoreTransaction;

  public InvestorTransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInvestorTransactionEntity = new EntityInsertionAdapter<InvestorTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `investor_transactions` (`uuid`,`investorId`,`type`,`amount`,`relatedLoanId`,`timestamp`,`yearMonth`,`lastModified`,`syncStatus`,`deleted`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final InvestorTransactionEntity entity) {
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
        if (entity.type == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.type);
        }
        statement.bindDouble(4, entity.amount);
        if (entity.relatedLoanId == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.relatedLoanId);
        }
        statement.bindLong(6, entity.timestamp);
        if (entity.yearMonth == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.yearMonth);
        }
        statement.bindLong(8, entity.lastModified);
        if (entity.syncStatus == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.syncStatus);
        }
        final int _tmp = entity.deleted ? 1 : 0;
        statement.bindLong(10, _tmp);
      }
    };
    this.__deletionAdapterOfInvestorTransactionEntity = new EntityDeletionOrUpdateAdapter<InvestorTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `investor_transactions` WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final InvestorTransactionEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
      }
    };
    this.__updateAdapterOfInvestorTransactionEntity = new EntityDeletionOrUpdateAdapter<InvestorTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `investor_transactions` SET `uuid` = ?,`investorId` = ?,`type` = ?,`amount` = ?,`relatedLoanId` = ?,`timestamp` = ?,`yearMonth` = ?,`lastModified` = ?,`syncStatus` = ?,`deleted` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final InvestorTransactionEntity entity) {
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
        if (entity.type == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.type);
        }
        statement.bindDouble(4, entity.amount);
        if (entity.relatedLoanId == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.relatedLoanId);
        }
        statement.bindLong(6, entity.timestamp);
        if (entity.yearMonth == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.yearMonth);
        }
        statement.bindLong(8, entity.lastModified);
        if (entity.syncStatus == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.syncStatus);
        }
        final int _tmp = entity.deleted ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.uuid == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.uuid);
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM investor_transactions WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE investor_transactions SET syncStatus = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastModified = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE investor_transactions SET lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE investor_transactions SET deleted = 1, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRestoreTransaction = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE investor_transactions SET deleted = 0, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final InvestorTransactionEntity transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfInvestorTransactionEntity.insert(transaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final InvestorTransactionEntity transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfInvestorTransactionEntity.handle(transaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final InvestorTransactionEntity transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfInvestorTransactionEntity.handle(transaction);
      __db.setTransactionSuccessful();
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
  public void restoreTransaction(final String uuid, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRestoreTransaction.acquire();
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
      __preparedStmtOfRestoreTransaction.release(_stmt);
    }
  }

  @Override
  public InvestorTransactionEntity getTransactionById(final String uuid) {
    final String _sql = "SELECT * FROM investor_transactions WHERE uuid = ?";
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
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final InvestorTransactionEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _result.type = null;
        } else {
          _result.type = _cursor.getString(_cursorIndexOfType);
        }
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _result.relatedLoanId = null;
        } else {
          _result.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _result.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _result.yearMonth = null;
        } else {
          _result.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<InvestorTransactionEntity> getTransactionByIdLive(final String uuid) {
    final String _sql = "SELECT * FROM investor_transactions WHERE uuid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uuid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uuid);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<InvestorTransactionEntity>() {
      @Override
      @Nullable
      public InvestorTransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final InvestorTransactionEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _result.type = null;
            } else {
              _result.type = _cursor.getString(_cursorIndexOfType);
            }
            _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _result.relatedLoanId = null;
            } else {
              _result.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _result.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _result.yearMonth = null;
            } else {
              _result.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<List<InvestorTransactionEntity>> getAllTransactionsLive() {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<List<InvestorTransactionEntity>>() {
      @Override
      @Nullable
      public List<InvestorTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvestorTransactionEntity _item;
            _item = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _item.type = null;
            } else {
              _item.type = _cursor.getString(_cursorIndexOfType);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _item.relatedLoanId = null;
            } else {
              _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public List<InvestorTransactionEntity> getAllTransactions() {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<List<InvestorTransactionEntity>> getTransactionsByInvestorLive(
      final String investorId) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND investorId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<List<InvestorTransactionEntity>>() {
      @Override
      @Nullable
      public List<InvestorTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvestorTransactionEntity _item;
            _item = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _item.type = null;
            } else {
              _item.type = _cursor.getString(_cursorIndexOfType);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _item.relatedLoanId = null;
            } else {
              _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public List<InvestorTransactionEntity> getTransactionsByInvestor(final String investorId) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND investorId = ? ORDER BY timestamp DESC";
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
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<List<InvestorTransactionEntity>> getTransactionsByType(final String type) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND type = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<List<InvestorTransactionEntity>>() {
      @Override
      @Nullable
      public List<InvestorTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvestorTransactionEntity _item;
            _item = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _item.type = null;
            } else {
              _item.type = _cursor.getString(_cursorIndexOfType);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _item.relatedLoanId = null;
            } else {
              _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<List<InvestorTransactionEntity>> getTransactionsByLoanLive(
      final String relatedLoanId) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND relatedLoanId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (relatedLoanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, relatedLoanId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<List<InvestorTransactionEntity>>() {
      @Override
      @Nullable
      public List<InvestorTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvestorTransactionEntity _item;
            _item = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _item.type = null;
            } else {
              _item.type = _cursor.getString(_cursorIndexOfType);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _item.relatedLoanId = null;
            } else {
              _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public List<InvestorTransactionEntity> getTransactionsByLoan(final String relatedLoanId) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND relatedLoanId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (relatedLoanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, relatedLoanId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<List<InvestorTransactionEntity>> getTransactionsByMonthLive(
      final String yearMonth) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND yearMonth = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (yearMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yearMonth);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<List<InvestorTransactionEntity>>() {
      @Override
      @Nullable
      public List<InvestorTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvestorTransactionEntity _item;
            _item = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _item.type = null;
            } else {
              _item.type = _cursor.getString(_cursorIndexOfType);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _item.relatedLoanId = null;
            } else {
              _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public List<InvestorTransactionEntity> getTransactionsByMonth(final String yearMonth) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND yearMonth = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (yearMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yearMonth);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public LiveData<List<InvestorTransactionEntity>> getTransactionsByDateRange(final long startDate,
      final long endDate) {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 0 AND timestamp >= ? AND timestamp <= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return __db.getInvalidationTracker().createLiveData(new String[] {"investor_transactions"}, false, new Callable<List<InvestorTransactionEntity>>() {
      @Override
      @Nullable
      public List<InvestorTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvestorTransactionEntity _item;
            _item = new InvestorTransactionEntity();
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
            if (_cursor.isNull(_cursorIndexOfType)) {
              _item.type = null;
            } else {
              _item.type = _cursor.getString(_cursorIndexOfType);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
            if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
              _item.relatedLoanId = null;
            } else {
              _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
            }
            _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public List<InvestorTransactionEntity> getPendingSyncTransactions() {
    final String _sql = "SELECT * FROM investor_transactions WHERE syncStatus = 'PENDING' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public List<InvestorTransactionEntity> getFailedSyncTransactions() {
    final String _sql = "SELECT * FROM investor_transactions WHERE syncStatus = 'FAILED' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
  public int getTransactionCount() {
    final String _sql = "SELECT COUNT(*) FROM investor_transactions WHERE deleted = 0";
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
  public double getTotalInvestedByInvestor(final String investorId) {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND investorId = ? AND type = 'INVEST'";
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
  public double getTotalLoanedByInvestor(final String investorId) {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND investorId = ? AND type = 'LOAN_OUT'";
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
  public double getTotalRepaidToInvestor(final String investorId) {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND investorId = ? AND type = 'REPAYMENT_IN'";
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
  public double getTotalInvested() {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND type = 'INVEST'";
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
  public double getTotalLoaned() {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND type = 'LOAN_OUT'";
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
  public double getTotalRepaid() {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND type = 'REPAYMENT_IN'";
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
  public double getMonthlyTotalByType(final String yearMonth, final String type) {
    final String _sql = "SELECT SUM(amount) FROM investor_transactions WHERE deleted = 0 AND yearMonth = ? AND type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (yearMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yearMonth);
    }
    _argIndex = 2;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
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
  public List<InvestorTransactionEntity> getDeletedTransactions() {
    final String _sql = "SELECT * FROM investor_transactions WHERE deleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfRelatedLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "relatedLoanId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<InvestorTransactionEntity> _result = new ArrayList<InvestorTransactionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final InvestorTransactionEntity _item;
        _item = new InvestorTransactionEntity();
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
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
        if (_cursor.isNull(_cursorIndexOfRelatedLoanId)) {
          _item.relatedLoanId = null;
        } else {
          _item.relatedLoanId = _cursor.getString(_cursorIndexOfRelatedLoanId);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
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
