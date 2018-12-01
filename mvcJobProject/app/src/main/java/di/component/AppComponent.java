package di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import di.module.AppModule;
import di.module.HttpModule;
import model.DataManager;
import model.db.GreenDaoHelper;
import model.http.RetrofitHelper;
import util.ToastUtil;

/**
 * Created by Administrator on 2017/10/27.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext();

    ToastUtil getToastUtil();

    DataManager getDataManager(); //数据中心

    RetrofitHelper getRetrofitHelper();  //提供http的帮助类

    GreenDaoHelper greenDaoHelper();    //提供数据库帮助类

}
