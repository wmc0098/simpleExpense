package na.expenserecorder.application;

import android.content.Context;

import java.util.ArrayList;
import java.util.Locale;

import na.expenserecorder.database.ExpenseCategoryDAO;
import na.expenserecorder.database.ExpenseDAO;
import na.expenserecorder.model.ExpenseCategory;
import na.expenserecorder.model.ExpenseEntry;

/**
 * Created by wmc.
 */

public class Logic {
    private ExpenseDAO expenseDataSource;
    private ExpenseCategoryDAO categoryDataSource;
    
    public Logic(Context context) {
        expenseDataSource = new ExpenseDAO(context);
        categoryDataSource = new ExpenseCategoryDAO(context);
        if (categoryDataSource.hasNoCategory()) {
            categoryDataSource.addTestCategories();
        }
    }

    // expense methods

    public void addExpense(String date, ExpenseCategory cat, float amount) {
        ExpenseEntry entry = new ExpenseEntry(date, amount, cat);
        expenseDataSource.insert(entry);
    }

    public void editEntry(ExpenseEntry entryToEdit, String date, ExpenseCategory cat, float amount) {
        entryToEdit.setDate(date);
        entryToEdit.setCategory(cat);
        entryToEdit.setAmount(amount);
        expenseDataSource.update(entryToEdit);
    }

    public void deleteEntry(ExpenseEntry entry) {
        expenseDataSource.delete(entry);
    }

    public int clearExpenseDb() {
        return expenseDataSource.clear();
    }

    public ArrayList<ExpenseEntry> getExpenseFromDb() {
        return expenseDataSource.getExpenseEntries();
    }

    // category methods

    public void addCategory(String name) {
        ExpenseCategory newCat = new ExpenseCategory(name);
        categoryDataSource.insert(newCat);
    }

    public void clearCategoryDb() {
        categoryDataSource.clearCategories();
    }

    public ArrayList<ExpenseCategory> getCategoryFromDb() {
        return categoryDataSource.getCategories();
    }

}
