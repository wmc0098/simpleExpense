package na.expenserecorder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import na.expenserecorder.model.ExpenseCategory;

import static android.database.DatabaseUtils.queryNumEntries;

/**
 * Adapted from http://androidopentutorials.com/android-sqlite-join-multiple-tables-example/
 * TODO Add header comment
 */

public class ExpenseCategoryDAO extends MainDatabaseDAO {

    private static final String WHERE_ID_EQUALS = MainDatabaseHelper.COLUMN_CATEGORY_ID
            + " =?";

    public static final String CATEGORY_default = "Miscellaneous";
    public static final String CATEGORY_groceries = "Groceries";
    public static final String CATEGORY_transport = "Transport";
    public static final String CATEGORY_bills = "Bills";
    public static final String CATEGORY_entertainment = "Entertainment";

    public ExpenseCategoryDAO(Context context) {
        super(context);
    }

    public long insert(ExpenseCategory category) {
        ContentValues values = new ContentValues();
        values.put(MainDatabaseHelper.COLUMN_CATEGORY_NAME, category.getCategoryName());

        return database.insert(MainDatabaseHelper.CATEGORY_TABLE, null, values);
    }

    public long update(ExpenseCategory category) {
        ContentValues values = new ContentValues();
        values.put(MainDatabaseHelper.COLUMN_CATEGORY_NAME, category.getCategoryName());

        long result = database.update(MainDatabaseHelper.CATEGORY_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(category.getCategoryKey()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int deleteCategory(ExpenseCategory category) {
        return database.delete(MainDatabaseHelper.CATEGORY_TABLE,
                WHERE_ID_EQUALS, new String[] { category.getCategoryKey() + "" });
    }

    public int clearCategories() {
        return database.delete(MainDatabaseHelper.CATEGORY_TABLE, null, null);
    }

    public boolean hasNoCategory() {
//        String sizeQuery = "SELECT count(*) FROM " + MainDatabaseHelper.CATEGORY_TABLE;
//        Cursor cursor = database.rawQuery(sizeQuery, null);
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
        return (queryNumEntries(database, MainDatabaseHelper.CATEGORY_TABLE) == 0);
    }

    public ArrayList<ExpenseCategory> getCategories() {
        ArrayList<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();
        Cursor cursor = database.query(MainDatabaseHelper.CATEGORY_TABLE,
                new String[] { MainDatabaseHelper.COLUMN_CATEGORY_ID,
                        MainDatabaseHelper.COLUMN_CATEGORY_NAME }, null, null, null, null,
                null);

        while (cursor.moveToNext()) {
            ExpenseCategory category = new ExpenseCategory();
            category.setCategoryKey(
                    cursor.getInt(cursor.getColumnIndex(MainDatabaseHelper.COLUMN_CATEGORY_ID)));
            category.setCategoryName(
                    cursor.getString(cursor.getColumnIndex(MainDatabaseHelper.COLUMN_CATEGORY_NAME)));
            categories.add(category);
        }
        return categories;
    }

    public void addTestCategories() {
        ExpenseCategory category0 = new ExpenseCategory(CATEGORY_default);
        ExpenseCategory category1 = new ExpenseCategory(CATEGORY_bills);
        ExpenseCategory category2 = new ExpenseCategory(CATEGORY_entertainment);
        ExpenseCategory category3 = new ExpenseCategory(CATEGORY_groceries);
        ExpenseCategory category4 = new ExpenseCategory(CATEGORY_transport);

        List<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();
        categories.add(category0);
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        for (ExpenseCategory cat : categories) {
            this.insert(cat);
        }
    }
}
