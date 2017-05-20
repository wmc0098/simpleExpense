package na.expenserecorder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import na.expenserecorder.R;
import na.expenserecorder.application.ApplicationSingleton;
import na.expenserecorder.model.Category;
import na.expenserecorder.util.GeneralUtils;
import na.expenserecorder.util.ProjectConstants;


/**
 * Created by wmc.
 * TODO Add header comment
 */

public class CategoryDataSource {
    private SQLiteDatabase categoryDb;
    private CategoryDatabaseHelper categoryDbHelper;
    private String[] allColumns = {
            CategoryDatabaseHelper.COLUMN_ID,
            CategoryDatabaseHelper.COLUMN_NAME
    };

    public CategoryDataSource(Context context) {
        categoryDbHelper = new CategoryDatabaseHelper(context);
        openDb();
    }

    public void openDb() {
        categoryDb = categoryDbHelper.getWritableDatabase();
    }

    public void close() {
        categoryDbHelper.close();
    }

    public ArrayList<String> getAllCategoryStrings() {
        ArrayList<Category> allEntries = getAllCategories();
        ArrayList<String> allStrings = new ArrayList<String>();
        for (Category cat: allEntries) {
            allStrings.add(cat.getCategoryName());
        }
        return allStrings;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> allEntries = new ArrayList<>();
        Cursor cursor = categoryDb.query(CategoryDatabaseHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category entry = cursorToCategoryEntry(cursor);
            allEntries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return allEntries;
    }

    private Category cursorToCategoryEntry(Cursor cursor) {
        Category entry = new Category();
        int keyIndex = cursor.getColumnIndexOrThrow(CategoryDatabaseHelper.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(CategoryDatabaseHelper.COLUMN_NAME);

        entry.setCategoryKey(cursor.getLong(keyIndex));
        entry.setCategoryName(cursor.getString(nameIndex));

        return entry;
    }

    public long addCategoryByString(String name) {
        long key = getKeyByName(name);
        if (key == CategoryDatabaseHelper.KEY_EMPTY) {
            ContentValues values = createValues(name);
            key = categoryDb.insert(CategoryDatabaseHelper.TABLE_NAME, null, values);
        } else {
            Context context = ApplicationSingleton.getInstance();
            GeneralUtils.showShortToastMessage(
                    context,
                    context.getString(R.string.MESSAGE_category_already_exists));
        }
        return key;
    }

    private ContentValues createValues(String name) {
        ContentValues values = new ContentValues();
        values.put(CategoryDatabaseHelper.COLUMN_NAME, name);
        return values;
    }

    // TODO could implement a getCursorByKey, getCursorByName and process further
    public Category getCategoryByName(String name) {
        Cursor cursor = categoryDb.query(CategoryDatabaseHelper.TABLE_NAME,
                new String[]{CategoryDatabaseHelper.COLUMN_ID, CategoryDatabaseHelper.COLUMN_NAME},
                CategoryDatabaseHelper.COLUMN_NAME + " = ?",
                new String[]{name},
                null, null, null, null);
        Category cat = new Category();
        if (cursor.getCount() > 0) {
            // assume it returns only one row
            cursor.moveToFirst();
            long catKey =  cursor.getLong(cursor.getColumnIndexOrThrow(CategoryDatabaseHelper.COLUMN_ID));
            String catName = cursor.getString(cursor.getColumnIndexOrThrow(CategoryDatabaseHelper.COLUMN_NAME));
            cat.setCategoryKey(catKey);
            cat.setCategoryName(catName);
        }
        // TODO if cat name is not found ?
        cursor.close();
        return cat;
    }

    public long getKeyByName(String name) {
        Cursor cursor = categoryDb.query(CategoryDatabaseHelper.TABLE_NAME,
                new String[]{CategoryDatabaseHelper.COLUMN_ID},
                CategoryDatabaseHelper.COLUMN_NAME + " = ?",
                new String[]{name},
                null, null, null, null);
        long key = CategoryDatabaseHelper.KEY_EMPTY;
        if (cursor.getCount() > 0) {
            // assume it returns only one row
            cursor.moveToFirst();
            key =  cursor.getLong(cursor.getColumnIndexOrThrow(CategoryDatabaseHelper.COLUMN_ID));
        }
        cursor.close();
        return key;
    }

    public String getNameByKey(long key) {
        Cursor cursor = categoryDb.query(CategoryDatabaseHelper.TABLE_NAME,
                new String[]{CategoryDatabaseHelper.COLUMN_NAME},
                CategoryDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(key)},
                null, null, null, null);
        if (cursor.getCount() > 0) {
            // assume it returns only one row
            cursor.moveToFirst();
            String name =  cursor.getString(cursor.getColumnIndexOrThrow(CategoryDatabaseHelper.COLUMN_NAME));
            cursor.close();
            return name;
        } else {
            cursor.close();
            // TODO this could be caused by damaged database, should reset the DB if detected
            throw new IllegalArgumentException(ProjectConstants.MESSAGE_KEY_NOT_FOUND);
        }
    }


    public void deleteAll() {
        //TODO clear Db
    }
}
