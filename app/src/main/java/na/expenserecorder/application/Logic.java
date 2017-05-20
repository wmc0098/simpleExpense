package na.expenserecorder.application;

import android.content.Context;

import java.util.ArrayList;

import na.expenserecorder.database.CategoryDataSource;
import na.expenserecorder.database.ExpenseDataSource;
import na.expenserecorder.model.Category;
import na.expenserecorder.model.ExpenseEntry;

/**
 * Created by wmc.
 */

public class Logic {
    private ExpenseDataSource expenseDataSource;
    private CategoryDataSource categoryDataSource;
    
    public Logic(Context context) {
        expenseDataSource = new ExpenseDataSource(context);
        categoryDataSource = new CategoryDataSource(context);
        // TODO delete when category adding is done
        addCategory("testCAT1");
        addCategory("testCAT2");
        addCategory("testCAT3");
        // test step 1: each expense will have one category, saved as the category's key
        //              when category is read, string is loaded from catDb, it can be changed but not deleted
        //              category cannot be edited, for now
    }

    // expense methods
    public ExpenseDataSource getExpenseDataSource() {
        return expenseDataSource;
    }

    public void addExpense(String date, long category, float amount) {
        expenseDataSource.insertByRaw(date, category, amount);
    }

    public void clearExpenseDb() {
        expenseDataSource.deleteAll();
    }

    public ArrayList<ExpenseEntry> getExpenseFromDb() {
        return expenseDataSource.getAllEntries();
    }

    // category methods
    public CategoryDataSource getCategoryDataSource() {
        return categoryDataSource;
    }

    public void addCategory(String name) {
        categoryDataSource.addCategoryByString(name);
    }

    public void clearCategoryDb() {
        categoryDataSource.deleteAll();
    }

    public ArrayList<Category> getCategoryFromDb() {
        return categoryDataSource.getAllCategories();
    }

    public ArrayList<String> getCategoryStringsFromDb() {
        return categoryDataSource.getAllCategoryStrings();
    }

    public Category getCategoryByName(String name) {
        return categoryDataSource.getCategoryByName(name);
    }

    public String getCategoryNameByKey(long key) {
        return categoryDataSource.getNameByKey(key);
    }
}
