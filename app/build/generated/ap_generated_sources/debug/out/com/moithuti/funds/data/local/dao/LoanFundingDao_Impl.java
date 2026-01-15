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
import com.moithuti.funds.data.local.entity.LoanFundingEntity;
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
public final class LoanFundingDao_Impl implements LoanFundingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LoanFundingEntity> __insertionAdapterOfLoanFundingEntity;

  private final EntityDeletionOrUpdateAdapter<LoanFundingEntity> __deletionAdapterOfLoanFundingEntity;

  private final EntityDeletionOrUpdateAdapter<LoanFundingEntity> __updateAdapterOfLoanFundingEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastModified;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfRestoreLoanFunding;

  private final SharedSQLiteStatement __preparedStmtOfSoftDeleteAllFundingForLoan;

  private final SharedSQLiteStatement __preparedStmtOfSoftDeleteAllFundingByInvestor;

  public LoanFundingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoanFundingEntity = new EntityInsertionAdapter<LoanFundingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `loan_funding` (`uuid`,`loanId`,`investorId`,`amount`,`createdDate`,`lastModified`,`syncStatus`,`deleted`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoanFundingEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
        if (entity.loanId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.loanId);
        }
        if (entity.investorId == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.investorId);
        }
        statement.bindDouble(4, entity.amount);
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
    this.__deletionAdapterOfLoanFundingEntity = new EntityDeletionOrUpdateAdapter<LoanFundingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `loan_funding` WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoanFundingEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
      }
    };
    this.__updateAdapterOfLoanFundingEntity = new EntityDeletionOrUpdateAdapter<LoanFundingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `loan_funding` SET `uuid` = ?,`loanId` = ?,`investorId` = ?,`amount` = ?,`createdDate` = ?,`lastModified` = ?,`syncStatus` = ?,`deleted` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoanFundingEntity entity) {
        if (entity.uuid == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.uuid);
        }
        if (entity.loanId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.loanId);
        }
        if (entity.investorId == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.investorId);
        }
        statement.bindDouble(4, entity.amount);
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
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM loan_funding WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loan_funding SET syncStatus = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastModified = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loan_funding SET lastModified = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loan_funding SET deleted = 1, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRestoreLoanFunding = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loan_funding SET deleted = 0, lastModified = ?, syncStatus = 'PENDING' WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDeleteAllFundingForLoan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loan_funding SET deleted = 1, lastModified = ?, syncStatus = 'PENDING' WHERE loanId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDeleteAllFundingByInvestor = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE loan_funding SET deleted = 1, lastModified = ?, syncStatus = 'PENDING' WHERE investorId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final LoanFundingEntity loanFunding) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLoanFundingEntity.insert(loanFunding);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final LoanFundingEntity loanFunding) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLoanFundingEntity.handle(loanFunding);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final LoanFundingEntity loanFunding) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLoanFundingEntity.handle(loanFunding);
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
  public void restoreLoanFunding(final String uuid, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRestoreLoanFunding.acquire();
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
      __preparedStmtOfRestoreLoanFunding.release(_stmt);
    }
  }

  @Override
  public void softDeleteAllFundingForLoan(final String loanId, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDeleteAllFundingForLoan.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, timestamp);
    _argIndex = 2;
    if (loanId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, loanId);
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
      __preparedStmtOfSoftDeleteAllFundingForLoan.release(_stmt);
    }
  }

  @Override
  public void softDeleteAllFundingByInvestor(final String investorId, final long timestamp) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDeleteAllFundingByInvestor.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, timestamp);
    _argIndex = 2;
    if (investorId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, investorId);
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
      __preparedStmtOfSoftDeleteAllFundingByInvestor.release(_stmt);
    }
  }

  @Override
  public LoanFundingEntity getLoanFundingById(final String uuid) {
    final String _sql = "SELECT * FROM loan_funding WHERE uuid = ?";
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
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final LoanFundingEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _result.uuid = null;
        } else {
          _result.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _result.loanId = null;
        } else {
          _result.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _result.investorId = null;
        } else {
          _result.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
  public LiveData<LoanFundingEntity> getLoanFundingByIdLive(final String uuid) {
    final String _sql = "SELECT * FROM loan_funding WHERE uuid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uuid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uuid);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loan_funding"}, false, new Callable<LoanFundingEntity>() {
      @Override
      @Nullable
      public LoanFundingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final LoanFundingEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new LoanFundingEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _result.uuid = null;
            } else {
              _result.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfLoanId)) {
              _result.loanId = null;
            } else {
              _result.loanId = _cursor.getString(_cursorIndexOfLoanId);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _result.investorId = null;
            } else {
              _result.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<LoanFundingEntity>> getAllLoanFundingLive() {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"loan_funding"}, false, new Callable<List<LoanFundingEntity>>() {
      @Override
      @Nullable
      public List<LoanFundingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanFundingEntity _item;
            _item = new LoanFundingEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfLoanId)) {
              _item.loanId = null;
            } else {
              _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _item.investorId = null;
            } else {
              _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
  public List<LoanFundingEntity> getAllLoanFunding() {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanFundingEntity _item;
        _item = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _item.loanId = null;
        } else {
          _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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

  @Override
  public LiveData<List<LoanFundingEntity>> getFundingByLoanLive(final String loanId) {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 AND loanId = ? ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (loanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, loanId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loan_funding"}, false, new Callable<List<LoanFundingEntity>>() {
      @Override
      @Nullable
      public List<LoanFundingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanFundingEntity _item;
            _item = new LoanFundingEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfLoanId)) {
              _item.loanId = null;
            } else {
              _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _item.investorId = null;
            } else {
              _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
  public List<LoanFundingEntity> getFundingByLoan(final String loanId) {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 AND loanId = ? ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (loanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, loanId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanFundingEntity _item;
        _item = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _item.loanId = null;
        } else {
          _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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

  @Override
  public LiveData<List<LoanFundingEntity>> getFundingByInvestorLive(final String investorId) {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 AND investorId = ? ORDER BY createdDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (investorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, investorId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"loan_funding"}, false, new Callable<List<LoanFundingEntity>>() {
      @Override
      @Nullable
      public List<LoanFundingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanFundingEntity _item;
            _item = new LoanFundingEntity();
            if (_cursor.isNull(_cursorIndexOfUuid)) {
              _item.uuid = null;
            } else {
              _item.uuid = _cursor.getString(_cursorIndexOfUuid);
            }
            if (_cursor.isNull(_cursorIndexOfLoanId)) {
              _item.loanId = null;
            } else {
              _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
            }
            if (_cursor.isNull(_cursorIndexOfInvestorId)) {
              _item.investorId = null;
            } else {
              _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
            }
            _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
  public List<LoanFundingEntity> getFundingByInvestor(final String investorId) {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 AND investorId = ? ORDER BY createdDate DESC";
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
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanFundingEntity _item;
        _item = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _item.loanId = null;
        } else {
          _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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

  @Override
  public LoanFundingEntity getLoanFundingByLoanAndInvestor(final String loanId,
      final String investorId) {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 0 AND loanId = ? AND investorId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (loanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, loanId);
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
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final LoanFundingEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _result.uuid = null;
        } else {
          _result.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _result.loanId = null;
        } else {
          _result.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _result.investorId = null;
        } else {
          _result.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _result.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
  public double getTotalFundingForLoan(final String loanId) {
    final String _sql = "SELECT SUM(amount) FROM loan_funding WHERE deleted = 0 AND loanId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (loanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, loanId);
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
  public double getTotalFundingByInvestor(final String investorId) {
    final String _sql = "SELECT SUM(amount) FROM loan_funding WHERE deleted = 0 AND investorId = ?";
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
  public double getTotalFunding() {
    final String _sql = "SELECT SUM(amount) FROM loan_funding WHERE deleted = 0";
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
  public List<LoanFundingEntity> getPendingSyncLoanFunding() {
    final String _sql = "SELECT * FROM loan_funding WHERE syncStatus = 'PENDING' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanFundingEntity _item;
        _item = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _item.loanId = null;
        } else {
          _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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

  @Override
  public List<LoanFundingEntity> getFailedSyncLoanFunding() {
    final String _sql = "SELECT * FROM loan_funding WHERE syncStatus = 'FAILED' AND deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanFundingEntity _item;
        _item = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _item.loanId = null;
        } else {
          _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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

  @Override
  public int getLoanFundingCount() {
    final String _sql = "SELECT COUNT(*) FROM loan_funding WHERE deleted = 0";
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
  public int getFundingCountForLoan(final String loanId) {
    final String _sql = "SELECT COUNT(*) FROM loan_funding WHERE deleted = 0 AND loanId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (loanId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, loanId);
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
  public int getFundingCountForInvestor(final String investorId) {
    final String _sql = "SELECT COUNT(*) FROM loan_funding WHERE deleted = 0 AND investorId = ?";
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
  public List<LoanFundingEntity> getDeletedLoanFunding() {
    final String _sql = "SELECT * FROM loan_funding WHERE deleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
      final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
      final int _cursorIndexOfInvestorId = CursorUtil.getColumnIndexOrThrow(_cursor, "investorId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "createdDate");
      final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final List<LoanFundingEntity> _result = new ArrayList<LoanFundingEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanFundingEntity _item;
        _item = new LoanFundingEntity();
        if (_cursor.isNull(_cursorIndexOfUuid)) {
          _item.uuid = null;
        } else {
          _item.uuid = _cursor.getString(_cursorIndexOfUuid);
        }
        if (_cursor.isNull(_cursorIndexOfLoanId)) {
          _item.loanId = null;
        } else {
          _item.loanId = _cursor.getString(_cursorIndexOfLoanId);
        }
        if (_cursor.isNull(_cursorIndexOfInvestorId)) {
          _item.investorId = null;
        } else {
          _item.investorId = _cursor.getString(_cursorIndexOfInvestorId);
        }
        _item.amount = _cursor.getDouble(_cursorIndexOfAmount);
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
