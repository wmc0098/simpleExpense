package na.expenserecorder.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Adapted from http://androidopentutorials.com/android-sqlite-join-multiple-tables-example/
 * TODO Add header comment
 */

public class MainDatabaseDAO {
    protected SQLiteDatabase database;
    private MainDatabaseHelper dbHelper;
    private Context mContext;

    public MainDatabaseDAO(Context context) {
        this.mContext = context;
        dbHelper = MainDatabaseHelper.getHelper(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = MainDatabaseHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    /*public void close() {
        dbHelper.close();
        database = null;
    }*/
}
