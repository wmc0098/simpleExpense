package na.expenserecorder.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import na.expenserecorder.R;

public class CategoryManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.button_category_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd();
            }
        });
    }

    public void onClickAdd() {
        // opens a dialog for category name input
        // getinput and add to category db
        // show toast msg

    }

}
