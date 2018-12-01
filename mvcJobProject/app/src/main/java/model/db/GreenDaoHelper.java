package model.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import javax.inject.Inject;

import global.MyApplication;
import model.db.entity.DaoMaster;
import model.db.entity.DaoSession;
import model.db.entity.LoginUserInfo;
import model.db.entity.LoginUserInfoDao;
import widget.Constants;

/**
 * Created by Administrator on 2018/2/11.
 */

public class GreenDaoHelper implements DBHelper {

    private final LoginUserInfoDao loginUserInfoDao;
    private DaoSession mDaoSession;

    @Inject
    public GreenDaoHelper() {
        //初始化数据库
        setupDatabase();
        loginUserInfoDao = mDaoSession.getLoginUserInfoDao();
//        ipAndPortInfoDao = mDaoSession.getIpAndPortInfoDao();
    }

    private void setupDatabase() {
        //创建数据库,DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), Constants.DATABASE_USER_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象,DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者,DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return loginUserInfoDao.loadAll();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        LoginUserInfo loginUserInfo = loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
        if (loginUserInfo != null)
            loginUserInfoDao.delete(loginUserInfo);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }


}
