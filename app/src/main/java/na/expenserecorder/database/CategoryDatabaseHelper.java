package na.expenserecorder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wmc.
 * TODO Add header comment
 */

public class CategoryDatabaseHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "categoryTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "categoryName";
    public static final long KEY_EMPTY = -1;

    private static final String DATABASE_NAME = "categoryValue.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER primary key autoincrement, " +
                    COLUMN_NAME + " TEXT " +
                    ");";

    public CategoryDatabaseHelper(Context context) {
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
