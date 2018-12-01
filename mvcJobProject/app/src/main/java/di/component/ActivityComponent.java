package di.component;

import android.app.Activity;

import com.nativeosproject.cn.mvcjobproject.activity.LoginActivity;
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

}
