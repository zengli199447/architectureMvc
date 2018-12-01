package di.component;

import android.app.Service;

import dagger.Component;
import di.module.ServerModule;
import di.scope.ServerScope;

/**
 * Created by Administrator on 2017/10/27.
 */
@ServerScope
@Component(modules = ServerModule.class, dependencies = AppComponent.class)
public interface ServerComponent {
    Service getService();

//    void inject(LoginActivity loginActivity);

}
