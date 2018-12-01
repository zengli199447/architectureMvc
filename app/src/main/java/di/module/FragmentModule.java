package di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import di.scope.FragmentScope;

/**
 * Created by Administrator on 2018/2/11.
 */
@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
