package di.component;

import android.app.Activity;


import com.nativeosproject.cn.mvcjobproject.fragment.more.LifeFragment;
import com.nativeosproject.cn.mvcjobproject.fragment.more.RightFragment;

import dagger.Component;
import di.module.FragmentModule;
import di.scope.FragmentScope;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(LifeFragment lifeFragment);

    void inject(RightFragment rightFragment);

}
