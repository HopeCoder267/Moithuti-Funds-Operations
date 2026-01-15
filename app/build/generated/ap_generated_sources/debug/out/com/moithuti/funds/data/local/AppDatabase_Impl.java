package com.moithuti.funds.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.moithuti.funds.data.local.dao.ClientDao;
import com.moithuti.funds.data.local.dao.ClientDao_Impl;
import com.moithuti.funds.data.local.dao.InvestorDao;
import com.moithuti.funds.data.local.dao.InvestorDao_Impl;
import com.moithuti.funds.data.local.dao.InvestorTransactionDao;
import com.moithuti.funds.data.local.dao.InvestorTransactionDao_Impl;
import com.moithuti.funds.data.local.dao.LoanDao;
import com.moithuti.funds.data.local.dao.LoanDao_Impl;
import com.moithuti.funds.data.local.dao.LoanFundingDao;
import com.moithuti.funds.data.local.dao.LoanFundingDao_Impl;
import com.moithuti.funds.data.local.dao.PaymentDao;
import com.moithuti.funds.data.local.dao.PaymentDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ClientDao _clientDao;

  private volatile LoanDao _loanDao;

  private volatile PaymentDao _paymentDao;

  private volatile InvestorDao _investorDao;

  private volatile InvestorTransactionDao _investorTransactionDao;

  private volatile LoanFundingDao _loanFundingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `clients` (`uuid` TEXT NOT NULL, `name` TEXT, `phone` TEXT, `notes` TEXT, `status` TEXT, `createdDate` INTEGER NOT NULL, `lastModified` INTEGER NOT NULL, `syncStatus` TEXT, `deleted` INTEGER NOT NULL, PRIMARY KEY(`uuid`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_clients_uuid` ON `clients` (`uuid`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `loans` (`uuid` TEXT NOT NULL, `clientId` TEXT, `amount` REAL NOT NULL, `dateIssued` INTEGER NOT NULL, `dueDate` INTEGER NOT NULL, `status` TEXT, `lastModified` INTEGER NOT NULL, `syncStatus` TEXT, `deleted` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`clientId`) REFERENCES `clients`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_loans_uuid` ON `loans` (`uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_loans_clientId` ON `loans` (`clientId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `payments` (`uuid` TEXT NOT NULL, `loanId` TEXT, `amount` REAL NOT NULL, `date` INTEGER NOT NULL, `lastModified` INTEGER NOT NULL, `syncStatus` TEXT, `deleted` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`loanId`) REFERENCES `loans`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_payments_uuid` ON `payments` (`uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_payments_loanId` ON `payments` (`loanId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `investors` (`uuid` TEXT NOT NULL, `name` TEXT, `isMainAccount` INTEGER NOT NULL, `createdDate` INTEGER NOT NULL, `lastModified` INTEGER NOT NULL, `syncStatus` TEXT, `deleted` INTEGER NOT NULL, PRIMARY KEY(`uuid`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_investors_uuid` ON `investors` (`uuid`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `investor_transactions` (`uuid` TEXT NOT NULL, `investorId` TEXT, `type` TEXT, `amount` REAL NOT NULL, `relatedLoanId` TEXT, `timestamp` INTEGER NOT NULL, `yearMonth` TEXT, `lastModified` INTEGER NOT NULL, `syncStatus` TEXT, `deleted` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`investorId`) REFERENCES `investors`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_investor_transactions_uuid` ON `investor_transactions` (`uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_investor_transactions_investorId` ON `investor_transactions` (`investorId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_investor_transactions_relatedLoanId` ON `investor_transactions` (`relatedLoanId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_investor_transactions_yearMonth` ON `investor_transactions` (`yearMonth`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `loan_funding` (`uuid` TEXT NOT NULL, `loanId` TEXT, `investorId` TEXT, `amount` REAL NOT NULL, `createdDate` INTEGER NOT NULL, `lastModified` INTEGER NOT NULL, `syncStatus` TEXT, `deleted` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`loanId`) REFERENCES `loans`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`investorId`) REFERENCES `investors`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_loan_funding_uuid` ON `loan_funding` (`uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_loan_funding_loanId` ON `loan_funding` (`loanId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_loan_funding_investorId` ON `loan_funding` (`investorId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9357d585fbee8bb491e1a78f0682333c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `clients`");
        db.execSQL("DROP TABLE IF EXISTS `loans`");
        db.execSQL("DROP TABLE IF EXISTS `payments`");
        db.execSQL("DROP TABLE IF EXISTS `investors`");
        db.execSQL("DROP TABLE IF EXISTS `investor_transactions`");
        db.execSQL("DROP TABLE IF EXISTS `loan_funding`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsClients = new HashMap<String, TableInfo.Column>(9);
        _columnsClients.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("createdDate", new TableInfo.Column("createdDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClients = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClients = new HashSet<TableInfo.Index>(1);
        _indicesClients.add(new TableInfo.Index("index_clients_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        final TableInfo _infoClients = new TableInfo("clients", _columnsClients, _foreignKeysClients, _indicesClients);
        final TableInfo _existingClients = TableInfo.read(db, "clients");
        if (!_infoClients.equals(_existingClients)) {
          return new RoomOpenHelper.ValidationResult(false, "clients(com.moithuti.funds.data.local.entity.ClientEntity).\n"
                  + " Expected:\n" + _infoClients + "\n"
                  + " Found:\n" + _existingClients);
        }
        final HashMap<String, TableInfo.Column> _columnsLoans = new HashMap<String, TableInfo.Column>(9);
        _columnsLoans.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("clientId", new TableInfo.Column("clientId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("dateIssued", new TableInfo.Column("dateIssued", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("dueDate", new TableInfo.Column("dueDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLoans = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLoans.add(new TableInfo.ForeignKey("clients", "CASCADE", "NO ACTION", Arrays.asList("clientId"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesLoans = new HashSet<TableInfo.Index>(2);
        _indicesLoans.add(new TableInfo.Index("index_loans_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        _indicesLoans.add(new TableInfo.Index("index_loans_clientId", false, Arrays.asList("clientId"), Arrays.asList("ASC")));
        final TableInfo _infoLoans = new TableInfo("loans", _columnsLoans, _foreignKeysLoans, _indicesLoans);
        final TableInfo _existingLoans = TableInfo.read(db, "loans");
        if (!_infoLoans.equals(_existingLoans)) {
          return new RoomOpenHelper.ValidationResult(false, "loans(com.moithuti.funds.data.local.entity.LoanEntity).\n"
                  + " Expected:\n" + _infoLoans + "\n"
                  + " Found:\n" + _existingLoans);
        }
        final HashMap<String, TableInfo.Column> _columnsPayments = new HashMap<String, TableInfo.Column>(7);
        _columnsPayments.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("loanId", new TableInfo.Column("loanId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayments.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPayments = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPayments.add(new TableInfo.ForeignKey("loans", "CASCADE", "NO ACTION", Arrays.asList("loanId"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesPayments = new HashSet<TableInfo.Index>(2);
        _indicesPayments.add(new TableInfo.Index("index_payments_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        _indicesPayments.add(new TableInfo.Index("index_payments_loanId", false, Arrays.asList("loanId"), Arrays.asList("ASC")));
        final TableInfo _infoPayments = new TableInfo("payments", _columnsPayments, _foreignKeysPayments, _indicesPayments);
        final TableInfo _existingPayments = TableInfo.read(db, "payments");
        if (!_infoPayments.equals(_existingPayments)) {
          return new RoomOpenHelper.ValidationResult(false, "payments(com.moithuti.funds.data.local.entity.PaymentEntity).\n"
                  + " Expected:\n" + _infoPayments + "\n"
                  + " Found:\n" + _existingPayments);
        }
        final HashMap<String, TableInfo.Column> _columnsInvestors = new HashMap<String, TableInfo.Column>(7);
        _columnsInvestors.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestors.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestors.put("isMainAccount", new TableInfo.Column("isMainAccount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestors.put("createdDate", new TableInfo.Column("createdDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestors.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestors.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestors.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInvestors = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInvestors = new HashSet<TableInfo.Index>(1);
        _indicesInvestors.add(new TableInfo.Index("index_investors_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        final TableInfo _infoInvestors = new TableInfo("investors", _columnsInvestors, _foreignKeysInvestors, _indicesInvestors);
        final TableInfo _existingInvestors = TableInfo.read(db, "investors");
        if (!_infoInvestors.equals(_existingInvestors)) {
          return new RoomOpenHelper.ValidationResult(false, "investors(com.moithuti.funds.data.local.entity.InvestorEntity).\n"
                  + " Expected:\n" + _infoInvestors + "\n"
                  + " Found:\n" + _existingInvestors);
        }
        final HashMap<String, TableInfo.Column> _columnsInvestorTransactions = new HashMap<String, TableInfo.Column>(10);
        _columnsInvestorTransactions.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("investorId", new TableInfo.Column("investorId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("relatedLoanId", new TableInfo.Column("relatedLoanId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("yearMonth", new TableInfo.Column("yearMonth", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvestorTransactions.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInvestorTransactions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysInvestorTransactions.add(new TableInfo.ForeignKey("investors", "CASCADE", "NO ACTION", Arrays.asList("investorId"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesInvestorTransactions = new HashSet<TableInfo.Index>(4);
        _indicesInvestorTransactions.add(new TableInfo.Index("index_investor_transactions_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        _indicesInvestorTransactions.add(new TableInfo.Index("index_investor_transactions_investorId", false, Arrays.asList("investorId"), Arrays.asList("ASC")));
        _indicesInvestorTransactions.add(new TableInfo.Index("index_investor_transactions_relatedLoanId", false, Arrays.asList("relatedLoanId"), Arrays.asList("ASC")));
        _indicesInvestorTransactions.add(new TableInfo.Index("index_investor_transactions_yearMonth", false, Arrays.asList("yearMonth"), Arrays.asList("ASC")));
        final TableInfo _infoInvestorTransactions = new TableInfo("investor_transactions", _columnsInvestorTransactions, _foreignKeysInvestorTransactions, _indicesInvestorTransactions);
        final TableInfo _existingInvestorTransactions = TableInfo.read(db, "investor_transactions");
        if (!_infoInvestorTransactions.equals(_existingInvestorTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "investor_transactions(com.moithuti.funds.data.local.entity.InvestorTransactionEntity).\n"
                  + " Expected:\n" + _infoInvestorTransactions + "\n"
                  + " Found:\n" + _existingInvestorTransactions);
        }
        final HashMap<String, TableInfo.Column> _columnsLoanFunding = new HashMap<String, TableInfo.Column>(8);
        _columnsLoanFunding.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("loanId", new TableInfo.Column("loanId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("investorId", new TableInfo.Column("investorId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("createdDate", new TableInfo.Column("createdDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoanFunding.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLoanFunding = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysLoanFunding.add(new TableInfo.ForeignKey("loans", "CASCADE", "NO ACTION", Arrays.asList("loanId"), Arrays.asList("uuid")));
        _foreignKeysLoanFunding.add(new TableInfo.ForeignKey("investors", "CASCADE", "NO ACTION", Arrays.asList("investorId"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesLoanFunding = new HashSet<TableInfo.Index>(3);
        _indicesLoanFunding.add(new TableInfo.Index("index_loan_funding_uuid", true, Arrays.asList("uuid"), Arrays.asList("ASC")));
        _indicesLoanFunding.add(new TableInfo.Index("index_loan_funding_loanId", false, Arrays.asList("loanId"), Arrays.asList("ASC")));
        _indicesLoanFunding.add(new TableInfo.Index("index_loan_funding_investorId", false, Arrays.asList("investorId"), Arrays.asList("ASC")));
        final TableInfo _infoLoanFunding = new TableInfo("loan_funding", _columnsLoanFunding, _foreignKeysLoanFunding, _indicesLoanFunding);
        final TableInfo _existingLoanFunding = TableInfo.read(db, "loan_funding");
        if (!_infoLoanFunding.equals(_existingLoanFunding)) {
          return new RoomOpenHelper.ValidationResult(false, "loan_funding(com.moithuti.funds.data.local.entity.LoanFundingEntity).\n"
                  + " Expected:\n" + _infoLoanFunding + "\n"
                  + " Found:\n" + _existingLoanFunding);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "9357d585fbee8bb491e1a78f0682333c", "393384f8f94d9a019e76c840e280fbd4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "clients","loans","payments","investors","investor_transactions","loan_funding");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `clients`");
      _db.execSQL("DELETE FROM `loans`");
      _db.execSQL("DELETE FROM `payments`");
      _db.execSQL("DELETE FROM `investors`");
      _db.execSQL("DELETE FROM `investor_transactions`");
      _db.execSQL("DELETE FROM `loan_funding`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ClientDao.class, ClientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LoanDao.class, LoanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PaymentDao.class, PaymentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(InvestorDao.class, InvestorDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(InvestorTransactionDao.class, InvestorTransactionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LoanFundingDao.class, LoanFundingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ClientDao clientDao() {
    if (_clientDao != null) {
      return _clientDao;
    } else {
      synchronized(this) {
        if(_clientDao == null) {
          _clientDao = new ClientDao_Impl(this);
        }
        return _clientDao;
      }
    }
  }

  @Override
  public LoanDao loanDao() {
    if (_loanDao != null) {
      return _loanDao;
    } else {
      synchronized(this) {
        if(_loanDao == null) {
          _loanDao = new LoanDao_Impl(this);
        }
        return _loanDao;
      }
    }
  }

  @Override
  public PaymentDao paymentDao() {
    if (_paymentDao != null) {
      return _paymentDao;
    } else {
      synchronized(this) {
        if(_paymentDao == null) {
          _paymentDao = new PaymentDao_Impl(this);
        }
        return _paymentDao;
      }
    }
  }

  @Override
  public InvestorDao investorDao() {
    if (_investorDao != null) {
      return _investorDao;
    } else {
      synchronized(this) {
        if(_investorDao == null) {
          _investorDao = new InvestorDao_Impl(this);
        }
        return _investorDao;
      }
    }
  }

  @Override
  public InvestorTransactionDao investorTransactionDao() {
    if (_investorTransactionDao != null) {
      return _investorTransactionDao;
    } else {
      synchronized(this) {
        if(_investorTransactionDao == null) {
          _investorTransactionDao = new InvestorTransactionDao_Impl(this);
        }
        return _investorTransactionDao;
      }
    }
  }

  @Override
  public LoanFundingDao loanFundingDao() {
    if (_loanFundingDao != null) {
      return _loanFundingDao;
    } else {
      synchronized(this) {
        if(_loanFundingDao == null) {
          _loanFundingDao = new LoanFundingDao_Impl(this);
        }
        return _loanFundingDao;
      }
    }
  }
}
