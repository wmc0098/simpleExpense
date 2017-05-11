package na.expenserecorder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import na.expenserecorder.R;
import na.expenserecorder.application.ApplicationSingleton;
import na.expenserecorder.util.GeneralUtils;
import na.expenserecorder.util.ProjectConstants;
import na.expenserecorder.util.TimeUtils;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // set button onClick actions
        Button addButton = (Button) findViewById(R.id.button_add_add);
        Button cancelButton = (Button) findViewById(R.id.button_add_cancel);

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
    }

    //onClick: button_add


    //onClick: button_cancel

    public void onClickAdd() {
        EditText amountText = (EditText) findViewById(R.id.editText_add_amount);
        try {
            float amount = Float.valueOf(amountText.getText().toString());
            String time = TimeUtils.getCurrentDateString();
            //add entry to Db
            ApplicationSingleton.getLogic().addExpense(time, "DefaultCat", amount);
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
}
