package com.moithuti.funds.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.moithuti.funds.data.local.dao.ClientDao;
import com.moithuti.funds.data.local.dao.LoanDao;
import com.moithuti.funds.data.local.dao.PaymentDao;
import com.moithuti.funds.data.local.dao.InvestorDao;
import com.moithuti.funds.data.local.dao.InvestorTransactionDao;
import com.moithuti.funds.data.local.dao.LoanFundingDao;
import com.moithuti.funds.data.local.entity.ClientEntity;
import com.moithuti.funds.data.local.entity.LoanEntity;
import com.moithuti.funds.data.local.entity.PaymentEntity;
import com.moithuti.funds.data.local.entity.InvestorEntity;
import com.moithuti.funds.data.local.entity.InvestorTransactionEntity;
import com.moithuti.funds.data.local.entity.LoanFundingEntity;

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
        LoanFundingEntity.class
    },
    version = 1,
    exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // DAO instances
    public abstract ClientDao clientDao();
    public abstract LoanDao loanDao();
    public abstract PaymentDao paymentDao();
    public abstract InvestorDao investorDao();
    public abstract InvestorTransactionDao investorTransactionDao();
    public abstract LoanFundingDao loanFundingDao();

    /**
     * Get singleton database instance
     * @param context application context
     * @return database instance
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
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
                // Database is created - can add initial data here if needed
                // Note: For production, initial data should be added through the app
                android.util.Log.d("AppDatabase", "Database created");
            }

            @Override
            public void onOpen(SupportSQLiteDatabase db) {
                super.onOpen(db);
                // Database is opened - can enable foreign key constraints here
                db.execSQL("PRAGMA foreign_keys=ON");
                android.util.Log.d("AppDatabase", "Database opened");
            }
        })
        .addMigrations(MIGRATION_1_2) // Add migrations when schema changes
        .fallbackToDestructiveMigration() // For development - remove in production
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

    // Migration example (when schema changes)
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Add migration logic here when database version changes
            // Example: database.execSQL("ALTER TABLE clients ADD COLUMN new_column TEXT");
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
