package na.expenserecorder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Adapted from http://androidopentutorials.com/android-sqlite-join-multiple-tables-example/
 * TODO Add header comment
 */

public class MainDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenseDb";
    private static final int DATABASE_VERSION = 1;

    public static final String EXPENSE_TABLE = "expenseTable";
    public static final String CATEGORY_TABLE = "categoryTable";

    public static final String COLUMN_EXPENSE_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_CATEGORY_NAME = "categoryName";

    public static final String CREATE_EXPENSE_TABLE =
            "CREATE TABLE " + EXPENSE_TABLE + "("
                    + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DATE + " TEXT, "
                    + COLUMN_AMOUNT + " SINGLE, "
                    + COLUMN_CATEGORY_ID + " INT, "
                    + "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES "
                        + CATEGORY_TABLE + "(" + COLUMN_CATEGORY_ID + "))";

    public static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE + "("
                    + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CATEGORY_NAME + " TEXT " + ")";

    private static MainDatabaseHelper instance;

    public static synchronized MainDatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new MainDatabaseHelper(context);
        return instance;
    }

    private MainDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
