package na.expenserecorder.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wmc.
 * TODO Add header comment
 */

public class GeneralUtils {

    public static void showShortToastMessage(Context context, String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
