package na.expenserecorder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import na.expenserecorder.model.ExpenseEntry;

/**
 * Created by wmc.
 */

public class ExpenseDataSource {
    private SQLiteDatabase expenseDb;
    private ExpenseDatabaseHelper expenseDbHelper;
    private String[] allColumns = {
            ExpenseDatabaseHelper.COLUMN_ID,
            ExpenseDatabaseHelper.COLUMN_DATE,
            ExpenseDatabaseHelper.COLUMN_CATEGORY,
            ExpenseDatabaseHelper.COLUMN_AMOUNT
    };

    public ExpenseDataSource(Context context) {
        expenseDbHelper = new ExpenseDatabaseHelper(context);
        openDb();
    }

    public void openDb() {
        expenseDb = expenseDbHelper.getWritableDatabase();
    }

    public void close() {
        expenseDbHelper.close();
    }

    public long insertByRaw(String date, long category, float amount) {
        ContentValues values = createValues(date, category, amount);
        long insertedId = expenseDb.insert(ExpenseDatabaseHelper.TABLE_NAME, null, values);
        return insertedId;
    }

    private ContentValues createValues(String date, long category, float amount) {
        ContentValues values = new ContentValues();
        values.put(ExpenseDatabaseHelper.COLUMN_DATE, date);
        // TODO enumerate category
        values.put(ExpenseDatabaseHelper.COLUMN_CATEGORY, category);
        values.put(ExpenseDatabaseHelper.COLUMN_AMOUNT, amount);
        return values;
    }

    public void deleteEntry(ExpenseEntry entry) {
        long id = entry.getEntryKey();
        deleteEntryByKey(id);
    }

    public void deleteEntryByKey(long id) {
        System.out.println("Entry deleted with id: " + id);
        String[] whereArgs = {String.valueOf(id)};
        expenseDb.delete(expenseDbHelper.TABLE_NAME, ExpenseDatabaseHelper.COLUMN_ID
                + " = ?", whereArgs);
    }

    public void editEntry(ExpenseEntry entryToEdit, String date, long category, float amount) {
        ContentValues values = createValues(date, category, amount);
        String[] selectionArgs = {String.valueOf(entryToEdit.getEntryKey())};
        expenseDb.update(expenseDbHelper.TABLE_NAME, values, ExpenseDatabaseHelper.COLUMN_ID
                + " = ?", selectionArgs);
    }

    public ArrayList<ExpenseEntry> getAllEntries() {
        ArrayList<ExpenseEntry> allEntries = new ArrayList<>();
        Cursor cursor = expenseDb.query(ExpenseDatabaseHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ExpenseEntry entry = cursorToExpenseEntry(cursor);
            allEntries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return allEntries;
    }

    private ExpenseEntry cursorToExpenseEntry(Cursor cursor) {
        ExpenseEntry entry = new ExpenseEntry();
        int keyIndex = cursor.getColumnIndexOrThrow(ExpenseDatabaseHelper.COLUMN_ID);
        int dateIndex = cursor.getColumnIndex(ExpenseDatabaseHelper.COLUMN_DATE);
        int catIndex = cursor.getColumnIndexOrThrow(ExpenseDatabaseHelper.COLUMN_CATEGORY);
        int amtIndex = cursor.getColumnIndexOrThrow(ExpenseDatabaseHelper.COLUMN_AMOUNT);

        entry.setEntryKey(cursor.getLong(keyIndex));
        entry.setTime(cursor.getString(dateIndex));
        entry.setCategory(cursor.getLong(catIndex));
        entry.setAmount(cursor.getFloat(amtIndex));

        return entry;
    }


    public int deleteAll() {
        return expenseDb.delete(ExpenseDatabaseHelper.TABLE_NAME, null, null);
    }
}
