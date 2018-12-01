package di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import model.DataManager;
import model.db.DBHelper;
import model.db.GreenDaoHelper;
import model.http.HttpHelper;
import model.http.RetrofitHelper;
import util.ToastUtil;

/**
 * Created by Administrator on 2017/10/27.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Singleton
    @Provides
    ToastUtil provideToastUtil() {
        return new ToastUtil(this.context);
    }


    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Singleton
    @Provides
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper DBHelper) {
        return new DataManager(httpHelper,DBHelper);
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(GreenDaoHelper realmHelper) {
        return realmHelper;
    }

}
