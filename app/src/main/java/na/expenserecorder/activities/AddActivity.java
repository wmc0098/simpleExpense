package na.expenserecorder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import na.expenserecorder.R;
import na.expenserecorder.application.ApplicationSingleton;
import na.expenserecorder.model.ExpenseCategory;
import na.expenserecorder.util.DatePickerFragment;
import na.expenserecorder.util.GeneralUtils;
import na.expenserecorder.util.TimeUtils;

import static na.expenserecorder.R.layout.support_simple_spinner_dropdown_item;

public class AddActivity extends AppCompatActivity {

    // UI elements
    private Button addButton;
    private Button cancelButton;
    private EditText amountText;
    // TODO the category spinner
    Spinner categorySpinner;
//    protected ExpenseCategory expenseCategorySelected;
    EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // set button onClick actions
        addButton = (Button) findViewById(R.id.button_add_add);
        cancelButton = (Button) findViewById(R.id.button_add_cancel);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCancel();
            }
        });

        // set spinner
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        setCategorySpinner(); //load cat from db

        // set date editText onClick actions
        amountText = (EditText) findViewById(R.id.editText_amount);
        dateEditText = (EditText) findViewById(R.id.editText_date);
        dateEditText.setText(TimeUtils.getCurrentDateString());
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDate();
            }
        });
    }

    public void setCategorySpinner() {
        // test spinner
//        expenseCategorySelected = new ExpenseCategory();
        ArrayList<ExpenseCategory> categories = ApplicationSingleton.getLogic().getCategoryFromDb();

        ArrayAdapter<ExpenseCategory> spinnerAdapter =
                new ArrayAdapter<ExpenseCategory>(this, support_simple_spinner_dropdown_item, categories);

        categorySpinner.setAdapter(spinnerAdapter);

//        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ExpenseCategory catSelected = (ExpenseCategory) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    public void onClickAdd() {
        try {
            float amount = Float.valueOf(this.amountText.getText().toString());
            String time = this.dateEditText.getText().toString();
            ExpenseCategory cat = (ExpenseCategory) categorySpinner.getSelectedItem();
            //add entry to Db
            ApplicationSingleton.getLogic().addExpense(time, cat, amount);
            //go back to main
            setResult(RESULT_OK);
            finish();
        } catch (NumberFormatException e) {
            GeneralUtils.showShortToastMessage(getApplicationContext(), getString(R.string.MESSAGE_invalid_amount));
        }

    }

    public void onClickCancel() {
        finish();
    }

    public void onClickDate() {
        DatePickerFragment newPickerFragment = new DatePickerFragment();
        newPickerFragment.setDateSetListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerFragment view, int year, int month, int day) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                AddActivity.this.dateEditText.setText(
                        TimeUtils.getStringFromCalendar(myCalendar));
            }
        });
        // show dialog
        showDatePickerDialog(newPickerFragment);
    }

    public void showDatePickerDialog(DatePickerFragment pickerFragment) {
        pickerFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
