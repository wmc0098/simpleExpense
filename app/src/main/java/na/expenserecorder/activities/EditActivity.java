package na.expenserecorder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import na.expenserecorder.R;
import na.expenserecorder.application.ApplicationSingleton;
import na.expenserecorder.model.ExpenseCategory;
import na.expenserecorder.model.ExpenseEntry;
import na.expenserecorder.util.DatePickerFragment;
import na.expenserecorder.util.GeneralUtils;
import na.expenserecorder.util.ProjectConstants;
import na.expenserecorder.util.TimeUtils;

import static na.expenserecorder.R.layout.support_simple_spinner_dropdown_item;
import static na.expenserecorder.util.ProjectConstants.BUNDLE_KEY_DATE_STRING;

public class EditActivity extends AppCompatActivity {

    private ExpenseEntry entryToEdit;

    // UI elements
    private Button deleteButton;
    private Button cancelButton;
    private Button confirmButton;
    private EditText amountText;
    // TODO the category spinner
    Spinner categorySpinner;
    EditText dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // receive entry to edit from intent
        entryToEdit = getIntent().getExtras()
                .getParcelable(ProjectConstants.EXTRA_EXPENSEENTRY);

        // set button onClick actions
        deleteButton = (Button) findViewById(R.id.button_edit_delete);
        cancelButton = (Button) findViewById(R.id.button_edit_cancel);
        confirmButton = (Button) findViewById(R.id.button_edit_confirm);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDelete();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCancel();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConfirm();
            }
        });

        amountText = (EditText) findViewById(R.id.editText_amount);
        dateText = (EditText) findViewById(R.id.editText_date);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDate();
            }
        });

        // set spinner
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        setCategorySpinner(); //load cat from db

        setViewValues();
    }

    private void setViewValues() {
        // display current amount and date of entryToEdit
        amountText.setText(String.valueOf(entryToEdit.getAmount()));
        dateText.setText(entryToEdit.getDate());
    }

    public void setCategorySpinner() {
        // test spinner
        ArrayList<ExpenseCategory> categories = ApplicationSingleton.getLogic().getCategoryFromDb();

        ArrayAdapter<ExpenseCategory> spinnerAdapter =
                new ArrayAdapter<ExpenseCategory>(this, support_simple_spinner_dropdown_item, categories);

        categorySpinner.setAdapter(spinnerAdapter);

        // read default category from incoming entry
        int selectedPos = spinnerAdapter.getPosition(entryToEdit.getCategory());
        categorySpinner.setSelection(selectedPos);
    }

    public void onClickCancel() {
        finish();
    }

    public void onClickDelete() {
        //call logic.deletebyId
        ApplicationSingleton.getLogic().deleteEntry(entryToEdit);
        //jump back to main and request to refresh
        jumpToMainAndRefresh();
    }

    public void onClickConfirm() {
        try {
            float newAmount = Float.valueOf(amountText.getText().toString());
            String newDate = dateText.getText().toString();
            ExpenseCategory newCat = (ExpenseCategory) categorySpinner.getSelectedItem();
            ApplicationSingleton.getLogic().editEntry(entryToEdit, newDate, newCat, newAmount);
            //jump back to main
            jumpToMainAndRefresh();
        } catch (NumberFormatException e) {
            GeneralUtils.showShortToastMessage(getApplicationContext(), getString(R.string.MESSAGE_invalid_amount));
        }
    }

    public void onClickDate() {
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_DATE_STRING, dateText.getText().toString());
        DatePickerFragment newPickerFragment = new DatePickerFragment();
        newPickerFragment.setArguments(args);

        newPickerFragment.setDateSetListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerFragment view, int year, int month, int day) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                EditActivity.this.dateText.setText(
                        TimeUtils.getStringFromCalendar(myCalendar));
            }
        });
        // show dialog
        showDatePickerDialog(newPickerFragment);
    }

    public void showDatePickerDialog(DatePickerFragment pickerFragment) {
        pickerFragment.show(getSupportFragmentManager(), "datePicker");
    }


    private void jumpToMainAndRefresh() {
        setResult(RESULT_OK);
        finish();
    }
}
