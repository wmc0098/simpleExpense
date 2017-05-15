package na.expenserecorder.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.ParseException;
import java.util.Calendar;

import na.expenserecorder.R;

import static na.expenserecorder.util.ProjectConstants.BUNDLE_KEY_DATE_STRING;

/**
 * Created by wmc.
 * TODO Add header comment
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDateSetListener listener;
    private Calendar myCalendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        myCalendar = Calendar.getInstance();
        if (getArguments() != null) {
            String dateString = getArguments().getString(BUNDLE_KEY_DATE_STRING);
            try {
                myCalendar = TimeUtils.getCalendarFromString(dateString);
            } catch (ParseException e) {
                GeneralUtils.showShortToastMessage(getActivity(), getString(R.string.MESSAGE_invalid_date));
                e.printStackTrace();
            }
        }
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        this.myCalendar.set(Calendar.YEAR, year);
        this.myCalendar.set(Calendar.MONTH, month);
        this.myCalendar.set(Calendar.DAY_OF_MONTH, day);
        this.listener.onDateSet(this, year, month, day);
    }

    public interface OnDateSetListener {
        void onDateSet(DatePickerFragment view, int year, int month, int day);
    }

    public void setDateSetListener(OnDateSetListener listener) {
        this.listener = listener;
    }

    public Calendar getCalendar(){
        return this.myCalendar;
    }
}
