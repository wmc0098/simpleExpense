package na.expenserecorder.application;

import android.app.Application;

/**
 * Created by wmc.
 */

public class ApplicationSingleton extends Application {
    private static ApplicationSingleton ourInstance;
    private static Logic logic;

    public static ApplicationSingleton getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        logic = new Logic(this);
    }

    public static Logic getLogic() {
        return logic;
    }

}
