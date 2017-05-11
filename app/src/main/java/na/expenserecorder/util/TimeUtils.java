package na.expenserecorder.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wmc.
 * TODO Add header comment
 */

public class TimeUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public static String getCurrentDateString() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }
}
