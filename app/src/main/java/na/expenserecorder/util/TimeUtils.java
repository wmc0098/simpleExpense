package na.expenserecorder.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wmc.
 * TODO Add header comment
 */

public class TimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String getCurrentDateString() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    public static Calendar getCalendarFromString(String dateString) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        cal.setTime(sdf.parse(dateString));
        return cal;
    }

    public static String getStringFromCalendar(Calendar dateCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(dateCalendar.getTime());
    }

}
