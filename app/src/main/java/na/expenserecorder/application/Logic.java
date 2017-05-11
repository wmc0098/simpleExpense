package na.expenserecorder.application;

import android.content.Context;

import java.util.ArrayList;

import na.expenserecorder.database.ExpenseDataSource;
import na.expenserecorder.model.ExpenseEntry;

/**
 * Created by wmc.
 */

public class Logic {
    private ExpenseDataSource myDataSource;
    
    public Logic(Context context) {
        myDataSource = new ExpenseDataSource(context);
    }
    
    public ExpenseDataSource getDataSource() {
        return myDataSource;
    }

    public void addExpense(String date, String category, float amount) {
        myDataSource.insertByRaw(date, category, amount);
    }

    public void clearDb() {
        myDataSource.deleteAll();
    }

    public ArrayList<ExpenseEntry> getEntriesFromDb() {
        return myDataSource.getAllEntries();
    }


}
