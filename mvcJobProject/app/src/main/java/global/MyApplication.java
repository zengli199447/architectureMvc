package global;

import android.app.Activity;
import android.app.Application;

import java.util.HashSet;
import java.util.Set;

import di.component.AppComponent;
import di.component.DaggerAppComponent;
import di.module.AppModule;
import di.module.HttpModule;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MyApplication extends Application {

    public static AppComponent appComponent;
    private static MyApplication instance;
    private Set<Activity> allActivities;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        initCrashHandler();
    }

    public static AppComponent getAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build();
        return appComponent;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    //自杀
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    //监视应用异常
    private void initCrashHandler() {
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }

}
