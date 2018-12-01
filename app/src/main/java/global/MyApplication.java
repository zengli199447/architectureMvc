package global;

import android.app.Activity;
import android.app.Application;


import com.nativeosproject.cn.mvcjobproject.livetools.fakeserver.FakeServer;
import com.nativeosproject.cn.mvcjobproject.livetools.widget.LiveKit;

import java.util.HashSet;
import java.util.Set;

import cn.jiguang.share.android.api.JShareInterface;
import cn.waps.AppConnect;
import di.component.AppComponent;
import di.component.DaggerAppComponent;
import di.module.AppModule;
import di.module.HttpModule;

/**
 * Created by Administrator on 2017/10/27.
 */

public class MyApplication extends Application {

    public static AppComponent appComponent;
    public static MyApplication instance;
    private Set<Activity> allActivities;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        initCrashHandler();
        initShare();
        initLive();
        initAppOfice();
    }

    private void initAppOfice() {
        AppConnect.getInstance("3c620831c0df010e837723d507638e8b", "waps", this);
    }

    private void initLive() {
        LiveKit.init(this);
    }

    private void initShare() {
        JShareInterface.init(this);
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
