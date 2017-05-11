package na.expenserecorder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import na.expenserecorder.R;
import na.expenserecorder.application.ApplicationSingleton;
import na.expenserecorder.model.ExpenseEntry;
import na.expenserecorder.util.GeneralUtils;
import na.expenserecorder.util.ProjectConstants;

public class EditActivity extends AppCompatActivity {

    private ExpenseEntry entryToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // receive entry to edit from intent
        entryToEdit = getIntent().getExtras()
                .getParcelable(ProjectConstants.EXTRA_EXPENSEENTRY);

        setViewValues();

        // set button onClick actions
        Button deleteButton = (Button) findViewById(R.id.button_edit_delete);
        Button cancelButton = (Button) findViewById(R.id.button_edit_cancel);
        Button confirmButton = (Button) findViewById(R.id.button_edit_confirm);

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
    }

    private void setViewValues() {
        EditText editText = (EditText) findViewById(R.id.editText_edit_amount);
        editText.setText(String.valueOf(entryToEdit.getAmount()));
    }

    //onClick: confirm, delete, cancel

    public void onClickCancel() {
        finish();
    }

    public void onClickDelete() {
        //call logic.deletebyId
        ApplicationSingleton.getLogic().getDataSource().deleteEntry(entryToEdit);
        //jump back to main and request to refresh
        jumpToMainAndRefresh();
    }

    private void jumpToMainAndRefresh() {
//        Intent jumpToMain = new Intent(this, MainActivity.class);
//        startActivity(jumpToMain);
        setResult(RESULT_OK);
        finish();
    }

    public void onClickConfirm() {
        //edit item and update by ID inside DB
        String date = "01-01-1000";
        String category = "editedCat";
        try {
            EditText amountText = (EditText) findViewById(R.id.editText_edit_amount);
            float amount = Float.valueOf(amountText.getText().toString());
            // TODO implement edit in db
            ApplicationSingleton.getLogic().getDataSource().editEntry(entryToEdit, date, category, amount);
            //jump back to main
            jumpToMainAndRefresh();
        } catch (NumberFormatException e) {
            GeneralUtils.showShortToastMessage(getApplicationContext(), getString(R.string.MESSAGE_invalid_amount));
        }
    }
}
