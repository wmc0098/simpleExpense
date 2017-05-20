package na.expenserecorder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wmc.
 */

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "expenseTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_AMOUNT = "amount";

    private static final String DATABASE_NAME = "expenseRecord.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                        COLUMN_ID + " INTEGER primary key autoincrement, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_CATEGORY + " LONG, " +
                        COLUMN_AMOUNT + " SINGLE" +
                ");";

    public ExpenseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ExpenseDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
