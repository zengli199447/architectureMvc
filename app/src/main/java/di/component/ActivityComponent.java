package di.component;

import android.app.Activity;

import com.nativeosproject.cn.mvcjobproject.activity.appofice.AppOficeActivity;
import com.nativeosproject.cn.mvcjobproject.activity.live.LiveActivity;
import com.nativeosproject.cn.mvcjobproject.activity.live.LiveShowActivity;
import com.nativeosproject.cn.mvcjobproject.activity.mian.LoginActivity;
import com.nativeosproject.cn.mvcjobproject.activity.mian.MainActivity;
import com.nativeosproject.cn.mvcjobproject.activity.party.JLoginActivity;
import com.nativeosproject.cn.mvcjobproject.activity.share.JShareActivity;
import com.nativeosproject.cn.mvcjobproject.activity.pay.PayActivity;

import dagger.Component;

import di.module.ActivityModule;
import di.scope.ActivityScope;

/**
 * Created by Administrator on 2017/10/27.
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(LoginActivity loginActivity);

    void inject(PayActivity payActivity);

    void inject(JLoginActivity jLoginActivity);

    void inject(JShareActivity jShareActivity);

    void inject(LiveActivity liveActivity);

    void inject(LiveShowActivity liveShowActivity);

    void inject(MainActivity mainActivity);

    void inject(AppOficeActivity appOficeActivity);

}
