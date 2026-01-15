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
import com.moithuti.funds.data.local.entity.ProfitTrackerEntity;
import java.lang.Class;
import java.lang.Double;
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
public final class ProfitTrackerDao_Impl implements ProfitTrackerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProfitTrackerEntity> __insertionAdapterOfProfitTrackerEntity;

  private final EntityDeletionOrUpdateAdapter<ProfitTrackerEntity> __updateAdapterOfProfitTrackerEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastModified;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ProfitTrackerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProfitTrackerEntity = new EntityInsertionAdapter<ProfitTrackerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `profit_tracker` (`uuid`,`investorId`,`yearMonth`,`monthlyProfit`,`cumulativeProfit`,`monthlyInterest`,`cumulativeInterest`,`monthlyLoansIssued`,`monthlyRepaymentsReceived`,`availableFunds`,`createdDate`,`lastModified`,`syncStatus`,`deleted`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ProfitTrackerEntity entity) {
        if (entity.getUuid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUuid());
        }
        if (entity.getInvestorId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getInvestorId());
        }
        if (entity.getYearMonth() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getYearMonth());
        }
        statement.bindDouble(4, entity.getMonthlyProfit());
        statement.bindDouble(5, entity.getCumulativeProfit());
        statement.bindDouble(6, entity.getMonthlyInterest());
        statement.bindDouble(7, entity.getCumulativeInterest());
        statement.bindDouble(8, entity.getMonthlyLoansIssued());
        statement.bindDouble(9, entity.getMonthlyRepaymentsReceived());
        statement.bindDouble(10, entity.getAvailableFunds());
        statement.bindLong(11, entity.getCreatedDate());
        statement.bindLong(12, entity.getLastModified());
        if (entity.getSyncStatus() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getSyncStatus());
        }
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(14, _tmp);
      }
    };
    this.__updateAdapterOfProfitTrackerEntity = new EntityDeletionOrUpdateAdapter<ProfitTrackerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `profit_tracker` SET `uuid` = ?,`investorId` = ?,`yearMonth` = ?,`monthlyProfit` = ?,`cumulativeProfit` = ?,`monthlyInterest` = ?,`cumulativeInterest` = ?,`monthlyLoansIssued` = ?,`monthlyRepaymentsReceived` = ?,`availableFunds` = ?,`createdDate` = ?,`lastModified` = ?,`syncStatus` = ?,`deleted` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ProfitTrackerEntity entity) {
        if (entity.getUuid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUuid());
        }
        if (entity.getInvestorId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getInvestorId());
        }
        if (entity.getYearMonth() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getYearMonth());
        }
        statement.bindDouble(4, entity.getMonthlyProfit());
        statement.bindDouble(5, entity.getCumulativeProfit());
        statement.bindDouble(6, entity.getMonthlyInterest());
        statement.bindDouble(7, entity.getCumulativeInterest());
        statement.bindDouble(8, entity.getMonthlyLoansIssued());
        statement.bindDouble(9, entity.getMonthlyRepaymentsReceived());
        statement.bindDouble(10, entity.getAvailableFunds());
        statement.bindLong(11, entity.getCreatedDate());
        statement.bindLong(12, entity.getLastModified());
        if (entity.getSyncStatus() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getSyncStatus());
        }
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(14, _tmp);
        if (entity.getUuid() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getUuid());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM profit_tracker WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastModified = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE profit_tracker SET lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE profit_tracker SET syncStatus = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE profit_tracker SET deleted = 1, lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM profit_tracker";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ProfitTrackerEntity profitTracker) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProfitTrackerEntity.insert(profitTracker);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final ProfitTrackerEntity profitTracker) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total += __updateAdapterOfProfitTrackerEntity.handle(profitTracker);
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
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public ProfitTrackerEntity getProfitTrackerById(final String uuid) {
    final String _sql = "SELECT * FROM profit_tracker WHERE uuid = ?";
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
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final ProfitTrackerEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _result.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _result.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _result.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _result.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _result.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _result.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _result.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _result.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _result.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _result.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _result.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _result.setDeleted(_tmpDeleted);
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
  public LiveData<ProfitTrackerEntity> getProfitTrackerByIdLive(final String uuid) {
    final String _sql = "SELECT * FROM profit_tracker WHERE uuid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uuid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uuid);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"profit_tracker"}, false, new Callable<ProfitTrackerEntity>() {
      @Override
      @Nullable
      public ProfitTrackerEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
          final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
          final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
          final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
          final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
          final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
          final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final ProfitTrackerEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new ProfitTrackerEntity();
            final String _tmpUuid;
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _tmpUuid = null;
            } else {
              _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            }
            _result.setUuid(_tmpUuid);
            final String _tmpInvestorId;
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _tmpInvestorId = null;
            } else {
              _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _result.setInvestorId(_tmpInvestorId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _result.setYearMonth(_tmpYearMonth);
            final double _tmpMonthlyProfit;
            _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
            _result.setMonthlyProfit(_tmpMonthlyProfit);
            final double _tmpCumulativeProfit;
            _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
            _result.setCumulativeProfit(_tmpCumulativeProfit);
            final double _tmpMonthlyInterest;
            _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
            _result.setMonthlyInterest(_tmpMonthlyInterest);
            final double _tmpCumulativeInterest;
            _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
            _result.setCumulativeInterest(_tmpCumulativeInterest);
            final double _tmpMonthlyLoansIssued;
            _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
            _result.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
            final double _tmpMonthlyRepaymentsReceived;
            _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
            _result.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
            final double _tmpAvailableFunds;
            _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
            _result.setAvailableFunds(_tmpAvailableFunds);
            final long _tmpCreatedDate;
            _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _result.setCreatedDate(_tmpCreatedDate);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result.setLastModified(_tmpLastModified);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            _result.setSyncStatus(_tmpSyncStatus);
            final boolean _tmpDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _tmpDeleted = _tmp != 0;
            _result.setDeleted(_tmpDeleted);
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
  public LiveData<List<ProfitTrackerEntity>> getAllProfitTrackersLive() {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 ORDER BY yearMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"profit_tracker"}, false, new Callable<List<ProfitTrackerEntity>>() {
      @Override
      @Nullable
      public List<ProfitTrackerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
          final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
          final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
          final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
          final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
          final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
          final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfitTrackerEntity _item;
            _item = new ProfitTrackerEntity();
            final String _tmpUuid;
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _tmpUuid = null;
            } else {
              _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            }
            _item.setUuid(_tmpUuid);
            final String _tmpInvestorId;
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _tmpInvestorId = null;
            } else {
              _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.setInvestorId(_tmpInvestorId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _item.setYearMonth(_tmpYearMonth);
            final double _tmpMonthlyProfit;
            _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
            _item.setMonthlyProfit(_tmpMonthlyProfit);
            final double _tmpCumulativeProfit;
            _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
            _item.setCumulativeProfit(_tmpCumulativeProfit);
            final double _tmpMonthlyInterest;
            _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
            _item.setMonthlyInterest(_tmpMonthlyInterest);
            final double _tmpCumulativeInterest;
            _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
            _item.setCumulativeInterest(_tmpCumulativeInterest);
            final double _tmpMonthlyLoansIssued;
            _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
            _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
            final double _tmpMonthlyRepaymentsReceived;
            _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
            _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
            final double _tmpAvailableFunds;
            _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
            _item.setAvailableFunds(_tmpAvailableFunds);
            final long _tmpCreatedDate;
            _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item.setCreatedDate(_tmpCreatedDate);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item.setLastModified(_tmpLastModified);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            _item.setSyncStatus(_tmpSyncStatus);
            final boolean _tmpDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _tmpDeleted = _tmp != 0;
            _item.setDeleted(_tmpDeleted);
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
  public List<ProfitTrackerEntity> getAllProfitTrackers() {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 ORDER BY yearMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerEntity _item;
        _item = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _item.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _item.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _item.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _item.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _item.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _item.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _item.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _item.setDeleted(_tmpDeleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ProfitTrackerEntity>> getMainAccountProfitTrackersLive() {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"profit_tracker"}, false, new Callable<List<ProfitTrackerEntity>>() {
      @Override
      @Nullable
      public List<ProfitTrackerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
          final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
          final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
          final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
          final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
          final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
          final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfitTrackerEntity _item;
            _item = new ProfitTrackerEntity();
            final String _tmpUuid;
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _tmpUuid = null;
            } else {
              _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            }
            _item.setUuid(_tmpUuid);
            final String _tmpInvestorId;
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _tmpInvestorId = null;
            } else {
              _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.setInvestorId(_tmpInvestorId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _item.setYearMonth(_tmpYearMonth);
            final double _tmpMonthlyProfit;
            _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
            _item.setMonthlyProfit(_tmpMonthlyProfit);
            final double _tmpCumulativeProfit;
            _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
            _item.setCumulativeProfit(_tmpCumulativeProfit);
            final double _tmpMonthlyInterest;
            _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
            _item.setMonthlyInterest(_tmpMonthlyInterest);
            final double _tmpCumulativeInterest;
            _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
            _item.setCumulativeInterest(_tmpCumulativeInterest);
            final double _tmpMonthlyLoansIssued;
            _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
            _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
            final double _tmpMonthlyRepaymentsReceived;
            _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
            _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
            final double _tmpAvailableFunds;
            _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
            _item.setAvailableFunds(_tmpAvailableFunds);
            final long _tmpCreatedDate;
            _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item.setCreatedDate(_tmpCreatedDate);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item.setLastModified(_tmpLastModified);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            _item.setSyncStatus(_tmpSyncStatus);
            final boolean _tmpDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _tmpDeleted = _tmp != 0;
            _item.setDeleted(_tmpDeleted);
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
  public List<ProfitTrackerEntity> getMainAccountProfitTrackers() {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerEntity _item;
        _item = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _item.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _item.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _item.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _item.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _item.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _item.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _item.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _item.setDeleted(_tmpDeleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ProfitTrackerEntity>> getInvestorProfitTrackersLive(
      final String investorId) {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId = ? ORDER BY yearMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"profit_tracker"}, false, new Callable<List<ProfitTrackerEntity>>() {
      @Override
      @Nullable
      public List<ProfitTrackerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
          final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
          final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
          final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
          final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
          final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
          final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfitTrackerEntity _item;
            _item = new ProfitTrackerEntity();
            final String _tmpUuid;
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _tmpUuid = null;
            } else {
              _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            }
            _item.setUuid(_tmpUuid);
            final String _tmpInvestorId;
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _tmpInvestorId = null;
            } else {
              _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.setInvestorId(_tmpInvestorId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            _item.setYearMonth(_tmpYearMonth);
            final double _tmpMonthlyProfit;
            _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
            _item.setMonthlyProfit(_tmpMonthlyProfit);
            final double _tmpCumulativeProfit;
            _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
            _item.setCumulativeProfit(_tmpCumulativeProfit);
            final double _tmpMonthlyInterest;
            _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
            _item.setMonthlyInterest(_tmpMonthlyInterest);
            final double _tmpCumulativeInterest;
            _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
            _item.setCumulativeInterest(_tmpCumulativeInterest);
            final double _tmpMonthlyLoansIssued;
            _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
            _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
            final double _tmpMonthlyRepaymentsReceived;
            _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
            _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
            final double _tmpAvailableFunds;
            _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
            _item.setAvailableFunds(_tmpAvailableFunds);
            final long _tmpCreatedDate;
            _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item.setCreatedDate(_tmpCreatedDate);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item.setLastModified(_tmpLastModified);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            _item.setSyncStatus(_tmpSyncStatus);
            final boolean _tmpDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeleted);
            _tmpDeleted = _tmp != 0;
            _item.setDeleted(_tmpDeleted);
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
  public List<ProfitTrackerEntity> getInvestorProfitTrackers(final String investorId) {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId = ? ORDER BY yearMonth ASC";
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
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerEntity _item;
        _item = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _item.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _item.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _item.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _item.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _item.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _item.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _item.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _item.setDeleted(_tmpDeleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ProfitTrackerEntity getMainAccountProfitTrackerByMonth(final String yearMonth) {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND yearMonth = ? AND investorId IS NULL";
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
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final ProfitTrackerEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _result.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _result.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _result.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _result.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _result.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _result.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _result.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _result.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _result.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _result.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _result.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _result.setDeleted(_tmpDeleted);
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
  public ProfitTrackerEntity getInvestorProfitTrackerByMonth(final String investorId,
      final String yearMonth) {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND yearMonth = ? AND investorId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (yearMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yearMonth);
    }
    _argIndex = 2;
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
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final ProfitTrackerEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _result.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _result.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _result.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _result.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _result.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _result.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _result.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _result.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _result.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _result.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _result.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _result.setDeleted(_tmpDeleted);
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
  public List<ProfitTrackerEntity> getAllProfitTrackersByMonth(final String yearMonth) {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND yearMonth = ?";
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
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerEntity _item;
        _item = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _item.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _item.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _item.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _item.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _item.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _item.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _item.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _item.setDeleted(_tmpDeleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ProfitTrackerEntity getLatestMainAccountProfitTracker() {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final ProfitTrackerEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _result.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _result.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _result.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _result.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _result.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _result.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _result.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _result.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _result.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _result.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _result.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _result.setDeleted(_tmpDeleted);
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
  public ProfitTrackerEntity getLatestInvestorProfitTracker(final String investorId) {
    final String _sql = "SELECT * FROM profit_tracker WHERE deleted = 0 AND investorId = ? ORDER BY yearMonth DESC LIMIT 1";
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
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final ProfitTrackerEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _result.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _result.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _result.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _result.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _result.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _result.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _result.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _result.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _result.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _result.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _result.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _result.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _result.setDeleted(_tmpDeleted);
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
  public Double getTotalMonthlyProfit() {
    final String _sql = "SELECT SUM(monthlyProfit) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
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
  public Double getCurrentCumulativeProfit() {
    final String _sql = "SELECT SUM(cumulativeProfit) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
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
  public Double getTotalInterest() {
    final String _sql = "SELECT SUM(monthlyInterest) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
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
  public Double getCurrentCumulativeInterest() {
    final String _sql = "SELECT SUM(cumulativeInterest) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
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
  public Double getCurrentAvailableFunds() {
    final String _sql = "SELECT SUM(availableFunds) FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL ORDER BY yearMonth DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
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
  public List<ProfitTrackerDao.ProfitSummary> getMonthlyProfitSummaries() {
    final String _sql = "SELECT yearMonth, SUM(monthlyProfit) as monthlyProfit, SUM(monthlyInterest) as monthlyInterest FROM profit_tracker WHERE deleted = 0 AND investorId IS NULL GROUP BY yearMonth ORDER BY yearMonth ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfYearMonth = 0;
      final int _cursorIndexOfMonthlyProfit = 1;
      final int _cursorIndexOfMonthlyInterest = 2;
      final List<ProfitTrackerDao.ProfitSummary> _result = new ArrayList<ProfitTrackerDao.ProfitSummary>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerDao.ProfitSummary _item;
        _item = new ProfitTrackerDao.ProfitSummary();
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _item.yearMonth = null;
        } else {
          _item.yearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.monthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.monthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ProfitTrackerEntity> getPendingSyncProfitTrackers() {
    final String _sql = "SELECT * FROM profit_tracker WHERE syncStatus = 'PENDING' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerEntity _item;
        _item = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _item.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _item.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _item.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _item.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _item.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _item.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _item.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _item.setDeleted(_tmpDeleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ProfitTrackerEntity> getFailedSyncProfitTrackers() {
    final String _sql = "SELECT * FROM profit_tracker WHERE syncStatus = 'FAILED' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
      final int _cursorIndexOfMonthlyProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyProfit");
      final int _cursorIndexOfCumulativeProfit = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeProfit");
      final int _cursorIndexOfMonthlyInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyInterest");
      final int _cursorIndexOfCumulativeInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulativeInterest");
      final int _cursorIndexOfMonthlyLoansIssued = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyLoansIssued");
      final int _cursorIndexOfMonthlyRepaymentsReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyRepaymentsReceived");
      final int _cursorIndexOfAvailableFunds = CursorUtil.getColumnIndexOrThrow(_cursor, "availableFunds");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<ProfitTrackerEntity> _result = new ArrayList<ProfitTrackerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ProfitTrackerEntity _item;
        _item = new ProfitTrackerEntity();
        final String _tmpUuid;
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _tmpUuid = null;
        } else {
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
        }
        _item.setUuid(_tmpUuid);
        final String _tmpInvestorId;
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _tmpInvestorId = null;
        } else {
          _tmpInvestorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.setInvestorId(_tmpInvestorId);
        final String _tmpYearMonth;
        if (_cursor.isNull(_cursorIndexOfYearMonth)) {
          _tmpYearMonth = null;
        } else {
          _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
        }
        _item.setYearMonth(_tmpYearMonth);
        final double _tmpMonthlyProfit;
        _tmpMonthlyProfit = _cursor.getDouble(_cursorIndexOfMonthlyProfit);
        _item.setMonthlyProfit(_tmpMonthlyProfit);
        final double _tmpCumulativeProfit;
        _tmpCumulativeProfit = _cursor.getDouble(_cursorIndexOfCumulativeProfit);
        _item.setCumulativeProfit(_tmpCumulativeProfit);
        final double _tmpMonthlyInterest;
        _tmpMonthlyInterest = _cursor.getDouble(_cursorIndexOfMonthlyInterest);
        _item.setMonthlyInterest(_tmpMonthlyInterest);
        final double _tmpCumulativeInterest;
        _tmpCumulativeInterest = _cursor.getDouble(_cursorIndexOfCumulativeInterest);
        _item.setCumulativeInterest(_tmpCumulativeInterest);
        final double _tmpMonthlyLoansIssued;
        _tmpMonthlyLoansIssued = _cursor.getDouble(_cursorIndexOfMonthlyLoansIssued);
        _item.setMonthlyLoansIssued(_tmpMonthlyLoansIssued);
        final double _tmpMonthlyRepaymentsReceived;
        _tmpMonthlyRepaymentsReceived = _cursor.getDouble(_cursorIndexOfMonthlyRepaymentsReceived);
        _item.setMonthlyRepaymentsReceived(_tmpMonthlyRepaymentsReceived);
        final double _tmpAvailableFunds;
        _tmpAvailableFunds = _cursor.getDouble(_cursorIndexOfAvailableFunds);
        _item.setAvailableFunds(_tmpAvailableFunds);
        final long _tmpCreatedDate;
        _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
        _item.setCreatedDate(_tmpCreatedDate);
        final long _tmpLastModified;
        _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
        _item.setLastModified(_tmpLastModified);
        final String _tmpSyncStatus;
        if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
          _tmpSyncStatus = null;
        } else {
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
        }
        _item.setSyncStatus(_tmpSyncStatus);
        final boolean _tmpDeleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp != 0;
        _item.setDeleted(_tmpDeleted);
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
