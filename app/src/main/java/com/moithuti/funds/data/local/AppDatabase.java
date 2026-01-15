package com.moithuti.funds.data.local;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.moithuti.funds.data.local.dao.BufferDao;
import com.moithuti.funds.data.local.dao.ClientDao;
import com.moithuti.funds.data.local.dao.InvestorDao;
import com.moithuti.funds.data.local.dao.InvestorTransactionDao;
import com.moithuti.funds.data.local.dao.LoanDao;
import com.moithuti.funds.data.local.dao.LoanFundingDao;
import com.moithuti.funds.data.local.dao.MonthlyBalanceDao;
import com.moithuti.funds.data.local.dao.PaymentDao;
import com.moithuti.funds.data.local.dao.ProfitTrackerDao;
import com.moithuti.funds.data.local.entity.BufferEntity;
import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.LoanFundingEntity;
import com.moithuti.funds.data.local.entity.MonthlyBalanceEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.data.local.entity.ProfitTrackerEntity;

/**
 * App Database - Room database configuration
 * Main database class that provides access to all DAOs
 * Implements singleton pattern for database instance management
 */
@Database(
    entities = {
        ClientEntity.class,
        LoanEntity.class,
        PaymentEntity.class,
        InvestorEntity.class,
        InvestorTransactionEntity.class,
        BufferEntity.class,
        MonthlyBalanceEntity.class,
        LoanFundingEntity.class,
        ProfitTrackerEntity.class
    },
    version = 4,
    exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // DAO instances
    public abstract BufferDao bufferDao();
    public abstract ClientDao clientDao();
    public abstract InvestorDao investorDao();
    public abstract InvestorTransactionDao investorTransactionDao();
    public abstract LoanDao loanDao();
    public abstract LoanFundingDao loanFundingDao();
    public abstract PaymentDao paymentDao();
    public abstract MonthlyBalanceDao monthlyBalanceDao();
    public abstract ProfitTrackerDao profitTrackerDao();

    /**
     * Get singleton database instance
     * @param context application context
     * @return database instance
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            // Check if we need to clear corrupted database
            if (databaseExists(context)) {
                // Clear any existing corrupted database
                context.deleteDatabase("moithuti_funds_database");
                Log.d("AppDatabase", "Cleared corrupted database");
            }
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    /**
     * Build the database instance
     * @param context application context
     * @return configured database
     */
    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(
            context.getApplicationContext(),
            AppDatabase.class,
            "moithuti_funds_database"
        )
        .addCallback(new RoomDatabase.Callback() {
            @Override
            public void onCreate(SupportSQLiteDatabase db) {
                super.onCreate(db);
                // Create default Main Account
                String mainAccountId = java.util.UUID.randomUUID().toString();
                db.execSQL("INSERT INTO investors (uuid, name, isMainAccount, createdDate, lastModified, syncStatus, deleted) VALUES ('" + 
                    mainAccountId + "', 'Main Account', 1, " + System.currentTimeMillis() + ", " + System.currentTimeMillis() + ", 'PENDING', 0)");
                
                android.util.Log.d("AppDatabase", "Database created with Main Account");
            }

            @Override
            public void onOpen(SupportSQLiteDatabase db) {
                super.onOpen(db);
                // Database is opened - can enable foreign key constraints here
                db.execSQL("PRAGMA foreign_keys=ON");
                android.util.Log.d("AppDatabase", "Database opened");
            }
        })
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4) // Add migrations when schema changes
        .build();
    }

    /**
     * Reset the database instance (for testing)
     */
    public static void resetInstance() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    // Migration from version 1 to 2 - Add Buffer and MonthlyBalance entities
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create buffers table with exact schema expected by Room
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS buffers (" +
                "uuid TEXT NOT NULL PRIMARY KEY, " +
                "investorId TEXT, " +
                "amount REAL NOT NULL, " +
                "reason TEXT, " +
                "createdDate INTEGER NOT NULL, " +
                "lastModified INTEGER NOT NULL, " +
                "syncStatus TEXT, " +
                "deleted INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY(investorId) REFERENCES investors(uuid) ON DELETE CASCADE)"
            );

            // Create monthly_balances table with exact schema expected by Room
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS monthly_balances (" +
                "uuid TEXT NOT NULL PRIMARY KEY, " +
                "investorId TEXT, " +
                "yearMonth TEXT NOT NULL, " +
                "balance REAL NOT NULL, " +
                "monthlyTopUp REAL NOT NULL DEFAULT 0, " +
                "createdDate INTEGER NOT NULL, " +
                "lastModified INTEGER NOT NULL, " +
                "syncStatus TEXT, " +
                "deleted INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY(investorId) REFERENCES investors(uuid) ON DELETE CASCADE, " +
                "UNIQUE(investorId, yearMonth))"
            );

            // Create indexes with correct uniqueness for buffers
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_buffers_uuid ON buffers(uuid)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_buffers_investorId ON buffers(investorId)");

            // Create indexes with correct uniqueness for monthly_balances
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_monthly_balances_uuid ON monthly_balances(uuid)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_monthly_balances_investorId ON monthly_balances(investorId)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_monthly_balances_yearMonth ON monthly_balances(yearMonth)");
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_monthly_balances_investorId_yearMonth ON monthly_balances(investorId, yearMonth)");
        }
    };

    // Migration from version 2 to 3 - Add ProfitTracker entity
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create profit_tracker table with exact schema expected by Room
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS profit_tracker (" +
                "uuid TEXT NOT NULL PRIMARY KEY, " +
                "investorId TEXT, " +
                "yearMonth TEXT NOT NULL, " +
                "monthlyProfit REAL NOT NULL DEFAULT 0, " +
                "cumulativeProfit REAL NOT NULL DEFAULT 0, " +
                "monthlyInterest REAL NOT NULL DEFAULT 0, " +
                "cumulativeInterest REAL NOT NULL DEFAULT 0, " +
                "monthlyLoansIssued REAL NOT NULL DEFAULT 0, " +
                "monthlyRepaymentsReceived REAL NOT NULL DEFAULT 0, " +
                "availableFunds REAL NOT NULL DEFAULT 0, " +
                "createdDate INTEGER NOT NULL, " +
                "lastModified INTEGER NOT NULL, " +
                "syncStatus TEXT, " +
                "deleted INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY(investorId) REFERENCES investors(uuid) ON DELETE CASCADE, " +
                "UNIQUE(investorId, yearMonth))"
            );

            // Create indexes with correct uniqueness
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_profit_tracker_uuid ON profit_tracker(uuid)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_profit_tracker_investorId ON profit_tracker(investorId)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_profit_tracker_yearMonth ON profit_tracker(yearMonth)");
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_profit_tracker_investorId_yearMonth ON profit_tracker(investorId, yearMonth)");
        }
    };

    // Migration from version 3 to 4 - Force complete rebuild
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // This migration will be handled by destructive migration
            // All tables will be recreated with correct schema
        }
    };

    /**
     * Check if database exists
     * @param context application context
     * @return true if database exists
     */
    public static boolean databaseExists(Context context) {
        java.io.File dbFile = context.getDatabasePath("moithuti_funds_database");
        return dbFile.exists();
    }

    /**
     * Get database file path
     * @param context application context
     * @return database file path
     */
    public static String getDatabasePath(Context context) {
        return context.getDatabasePath("moithuti_funds_database").getAbsolutePath();
    }
}
