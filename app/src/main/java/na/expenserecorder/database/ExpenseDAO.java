package na.expenserecorder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import na.expenserecorder.model.ExpenseCategory;
import na.expenserecorder.model.ExpenseEntry;

/**
 * Adapted from http://androidopentutorials.com/android-sqlite-join-multiple-tables-example/
 * TODO Add header comment
 */

public class ExpenseDAO extends MainDatabaseDAO {

    public static final String CAT_ID_IN_EXPENSE = MainDatabaseHelper.EXPENSE_TABLE + "." + MainDatabaseHelper.COLUMN_CATEGORY_ID;
    public static final String CAT_ID_IN_CATEGORY = MainDatabaseHelper.CATEGORY_TABLE + "." + MainDatabaseHelper.COLUMN_CATEGORY_ID;

    private static final String WHERE_ID_EQUALS = MainDatabaseHelper.COLUMN_EXPENSE_ID + " =?";


    public ExpenseDAO(Context context) {
        super(context);
    }

    public long insert(ExpenseEntry entry) {
        ContentValues values = new ContentValues();
        values.put(MainDatabaseHelper.COLUMN_DATE, entry.getDate());
        values.put(MainDatabaseHelper.COLUMN_AMOUNT, entry.getAmount());
        values.put(MainDatabaseHelper.COLUMN_CATEGORY_ID, entry.getCategory().getCategoryKey());

        return database.insert(MainDatabaseHelper.EXPENSE_TABLE, null, values);
    }

    // METHOD 2
    // Uses SQLiteQueryBuilder to query multiple tables
    public ArrayList<ExpenseEntry> getExpenseEntries() {
        ArrayList<ExpenseEntry> entries = new ArrayList<ExpenseEntry>();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder
                .setTables(MainDatabaseHelper.EXPENSE_TABLE
                        + " INNER JOIN "
                        + MainDatabaseHelper.CATEGORY_TABLE
                        + " ON "
                        + CAT_ID_IN_EXPENSE
                        + " = "
                        + CAT_ID_IN_CATEGORY);

        // Get cursor
        Cursor cursor = queryBuilder.query(database, new String[] {
                        MainDatabaseHelper.COLUMN_EXPENSE_ID,
                        MainDatabaseHelper.COLUMN_DATE,
                        MainDatabaseHelper.COLUMN_AMOUNT,
                        CAT_ID_IN_EXPENSE,
                        MainDatabaseHelper.COLUMN_CATEGORY_NAME },
                        null, null, null, null, null);
        int expIdIdx = cursor.getColumnIndex(MainDatabaseHelper.COLUMN_EXPENSE_ID);
        int dateIdx = cursor.getColumnIndex(MainDatabaseHelper.COLUMN_DATE);
        int amtIdx = cursor.getColumnIndex(MainDatabaseHelper.COLUMN_AMOUNT);
        int catIdIdx = cursor.getColumnIndex(MainDatabaseHelper.COLUMN_CATEGORY_ID);
        int catIdx = cursor.getColumnIndex(MainDatabaseHelper.COLUMN_CATEGORY_NAME);

        while (cursor.moveToNext()) {
            ExpenseEntry entry = new ExpenseEntry();
            entry.setEntryKey(cursor.getLong(expIdIdx));
            entry.setDate(cursor.getString(dateIdx));
            entry.setAmount(cursor.getFloat(amtIdx));

            ExpenseCategory cat = new ExpenseCategory();
            cat.setCategoryKey(cursor.getLong(catIdIdx));
            cat.setCategoryName(cursor.getString(catIdx));
            entry.setCategory(cat);

            entries.add(entry);
        }
        return entries;
    }

    public long update(ExpenseEntry entry) {
        ContentValues values = new ContentValues();
        values.put(MainDatabaseHelper.COLUMN_DATE, entry.getDate());
        values.put(MainDatabaseHelper.COLUMN_AMOUNT, entry.getAmount());
        values.put(MainDatabaseHelper.COLUMN_CATEGORY_ID, entry.getCategory().getCategoryKey());

        long result = database.update(MainDatabaseHelper.EXPENSE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(entry.getEntryKey()) });
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int delete(ExpenseEntry entry) {
        return database.delete(MainDatabaseHelper.EXPENSE_TABLE, WHERE_ID_EQUALS,
                new String[] { entry.getEntryKey() + "" });
    }

    public int clear() {
        return database.delete(MainDatabaseHelper.EXPENSE_TABLE, null, null);
    }
}
