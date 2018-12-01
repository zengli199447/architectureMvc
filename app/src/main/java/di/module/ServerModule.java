package di.module;

import android.app.Service;

import dagger.Module;
import dagger.Provides;
import di.scope.ServerScope;

/**
 * Created by Administrator on 2018/3/2 0002.
 */
@Module
public class ServerModule {
    private Service mErvice;

    public ServerModule(Service service) {
        this.mErvice = service;
    }

    @Provides
    @ServerScope
    public Service provideActivity() {
        return mErvice;
    }
}
