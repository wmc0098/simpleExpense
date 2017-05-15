package na.expenserecorder.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import na.expenserecorder.R;
import na.expenserecorder.application.ApplicationSingleton;
import na.expenserecorder.model.ExpenseEntry;
import na.expenserecorder.util.GeneralUtils;
import na.expenserecorder.util.ProjectConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setListViewFromDb();

        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.button_main_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                onClickAdd();
            }
        });
    }

    private void setListViewFromDb() {
        // Construct the data source
        ArrayList<ExpenseEntry> arrayOfExpense = ApplicationSingleton.getLogic().getEntriesFromDb();
    // Create the adapter to convert the array to views
        final ExpenseListAdapter adapter = new ExpenseListAdapter(this, arrayOfExpense);
    // Attach the adapter to a ListView
        ListView expenseListView = (ListView) findViewById(R.id.list_content);
        expenseListView.setAdapter(adapter);

        //set onClick event for items inside the list
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseEntry itemClicked = adapter.getItem(position);
                onClickListItem(itemClicked);
            }
        });
    }

    private void onClickListItem(ExpenseEntry itemClicked) {
        Intent editIntent = new Intent(this, EditActivity.class);
        editIntent.putExtra(ProjectConstants.EXTRA_EXPENSEENTRY, itemClicked);
        startActivityForResult(editIntent, ProjectConstants.REQUEST_EDIT_ENTRY);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            ApplicationSingleton.getLogic().clearDb();
            GeneralUtils.showShortToastMessage(getApplicationContext(), getString(R.string.MESSAGE_db_cleared));
            setListViewFromDb();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAdd() {
        //getIntent : currentDate?
        //jump to AddActivity
        Intent jumpToAddActivity = new Intent(this, AddActivity.class);
        startActivityForResult(jumpToAddActivity, ProjectConstants.REQUEST_ADD_ENTRY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ProjectConstants.REQUEST_ADD_ENTRY
                || requestCode == ProjectConstants.REQUEST_EDIT_ENTRY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                setListViewFromDb();
            }
        }
    }

    public static class ExpenseListAdapter extends ArrayAdapter<ExpenseEntry> {
        public ExpenseListAdapter(Context context, ArrayList<ExpenseEntry> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ExpenseEntry entry = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_expense_list, parent, false);
            }
            // Lookup view for data population
            TextView tvTime = (TextView) convertView.findViewById(R.id.entry_time);
            TextView tvCat = (TextView) convertView.findViewById(R.id.entry_category);
            TextView tvAmount = (TextView) convertView.findViewById(R.id.entry_amount);
            // Populate the data into the template view using the data object
            tvTime.setText(entry.getTime());
            tvCat.setText(entry.getCategory());
            tvAmount.setText(Float.toString(entry.getAmount()));
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
