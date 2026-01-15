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
import com.moithuti.funds.data.local.entity.MonthlyBalanceEntity;
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
public final class MonthlyBalanceDao_Impl implements MonthlyBalanceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MonthlyBalanceEntity> __insertionAdapterOfMonthlyBalanceEntity;

  private final EntityDeletionOrUpdateAdapter<MonthlyBalanceEntity> __deletionAdapterOfMonthlyBalanceEntity;

  private final EntityDeletionOrUpdateAdapter<MonthlyBalanceEntity> __updateAdapterOfMonthlyBalanceEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public MonthlyBalanceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMonthlyBalanceEntity = new EntityInsertionAdapter<MonthlyBalanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `monthly_balances` (`uuid`,`investorId`,`yearMonth`,`balance`,`monthlyTopUp`,`createdDate`,`lastModified`,`syncStatus`,`deleted`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final MonthlyBalanceEntity entity) {
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
        if (entity.yearMonth == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.yearMonth);
        }
        statement.bindDouble(4, entity.balance);
        statement.bindDouble(5, entity.monthlyTopUp);
        statement.bindLong(6, entity.createdDate);
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
    this.__deletionAdapterOfMonthlyBalanceEntity = new EntityDeletionOrUpdateAdapter<MonthlyBalanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `monthly_balances` WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final MonthlyBalanceEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
      }
    };
    this.__updateAdapterOfMonthlyBalanceEntity = new EntityDeletionOrUpdateAdapter<MonthlyBalanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `monthly_balances` SET `uuid` = ?,`investorId` = ?,`yearMonth` = ?,`balance` = ?,`monthlyTopUp` = ?,`createdDate` = ?,`lastModified` = ?,`syncStatus` = ?,`deleted` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final MonthlyBalanceEntity entity) {
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
        if (entity.yearMonth == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.yearMonth);
        }
        statement.bindDouble(4, entity.balance);
        statement.bindDouble(5, entity.monthlyTopUp);
        statement.bindLong(6, entity.createdDate);
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
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE monthly_balances SET deleted = 1, lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE monthly_balances SET syncStatus = ?, lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final MonthlyBalanceEntity monthlyBalance) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMonthlyBalanceEntity.insert(monthlyBalance);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final MonthlyBalanceEntity monthlyBalance) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMonthlyBalanceEntity.handle(monthlyBalance);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final MonthlyBalanceEntity monthlyBalance) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMonthlyBalanceEntity.handle(monthlyBalance);
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
  public LiveData<List<MonthlyBalanceEntity>> getAllMonthlyBalances() {
    final String _sql = "SELECT * FROM monthly_balances WHERE deleted = 0 ORDER BY yearMonth DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"monthly_balances"}, false, new Callable<List<MonthlyBalanceEntity>>() {
      @Override
      @Nullable
      public List<MonthlyBalanceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<MonthlyBalanceEntity> _result = new ArrayList<MonthlyBalanceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MonthlyBalanceEntity _item;
            _item = new MonthlyBalanceEntity();
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
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _item.balance = _cursor.getDouble(_cursorIndexOfBalance);
            _item.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
  public LiveData<List<MonthlyBalanceEntity>> getMonthlyBalancesByInvestor(
      final String investorId) {
    final String _sql = "SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId = ? ORDER BY yearMonth DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"monthly_balances"}, false, new Callable<List<MonthlyBalanceEntity>>() {
      @Override
      @Nullable
      public List<MonthlyBalanceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<MonthlyBalanceEntity> _result = new ArrayList<MonthlyBalanceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MonthlyBalanceEntity _item;
            _item = new MonthlyBalanceEntity();
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
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _item.balance = _cursor.getDouble(_cursorIndexOfBalance);
            _item.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
  public LiveData<List<MonthlyBalanceEntity>> getMainAccountMonthlyBalances() {
    final String _sql = "SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"monthly_balances"}, false, new Callable<List<MonthlyBalanceEntity>>() {
      @Override
      @Nullable
      public List<MonthlyBalanceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<MonthlyBalanceEntity> _result = new ArrayList<MonthlyBalanceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MonthlyBalanceEntity _item;
            _item = new MonthlyBalanceEntity();
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
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _item.yearMonth = null;
            } else {
              _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _item.balance = _cursor.getDouble(_cursorIndexOfBalance);
            _item.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
  public MonthlyBalanceEntity getMonthlyBalance(final String investorId, final String yearMonth) {
    final String _sql = "SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId = ? AND yearMonth = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    _argIndex = 2;
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
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
      final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final MonthlyBalanceEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new MonthlyBalanceEntity();
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
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _result.yearMonth = null;
        } else {
          _result.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.balance = _cursor.getDouble(_cursorIndexOfBalance);
        _result.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
  public MonthlyBalanceEntity getMainAccountMonthlyBalance(final String yearMonth) {
    final String _sql = "SELECT * FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL AND yearMonth = ?";
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
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
      final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final MonthlyBalanceEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new MonthlyBalanceEntity();
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
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _result.yearMonth = null;
        } else {
          _result.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.balance = _cursor.getDouble(_cursorIndexOfBalance);
        _result.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
  public MonthlyBalanceEntity getMonthlyBalanceById(final String uuid) {
    final String _sql = "SELECT * FROM monthly_balances WHERE deleted = 0 AND uuid = ?";
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
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
      final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final MonthlyBalanceEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new MonthlyBalanceEntity();
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
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _result.yearMonth = null;
        } else {
          _result.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.balance = _cursor.getDouble(_cursorIndexOfBalance);
        _result.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
  public double getTotalBalanceForInvestor(final String investorId) {
    final String _sql = "SELECT COALESCE(SUM(balance), 0) FROM monthly_balances WHERE deleted = 0 AND investorId = ?";
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
  public double getTotalBalanceForMainAccount() {
    final String _sql = "SELECT COALESCE(SUM(balance), 0) FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL";
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
  public double getTotalBalance() {
    final String _sql = "SELECT COALESCE(SUM(balance), 0) FROM monthly_balances WHERE deleted = 0";
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
  public double getTotalMonthlyTopUpsForInvestor(final String investorId) {
    final String _sql = "SELECT COALESCE(SUM(monthlyTopUp), 0) FROM monthly_balances WHERE deleted = 0 AND investorId = ?";
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
  public double getTotalMonthlyTopUpsForMainAccount() {
    final String _sql = "SELECT COALESCE(SUM(monthlyTopUp), 0) FROM monthly_balances WHERE deleted = 0 AND investorId IS NULL";
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
  public List<MonthlyBalanceEntity> getPendingSync() {
    final String _sql = "SELECT * FROM monthly_balances WHERE syncStatus = 'PENDING' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
      final int _cursorIndexOfMonthlyTopUp = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyTopUp");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<MonthlyBalanceEntity> _result = new ArrayList<MonthlyBalanceEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final MonthlyBalanceEntity _item;
        _item = new MonthlyBalanceEntity();
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
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.balance = _cursor.getDouble(_cursorIndexOfBalance);
        _item.monthlyTopUp = _cursor.getDouble(_cursorIndexOfMonthlyTopUp);
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
