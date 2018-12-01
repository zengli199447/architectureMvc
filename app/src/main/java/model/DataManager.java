package model;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import model.bean.LoginInfoBean;
import model.bean.OuterLayerEntity;
import model.db.DBHelper;
import model.db.entity.LoginUserInfo;
import model.http.HttpHelper;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/27.
 */

public class DataManager implements HttpHelper , DBHelper {
    String TAG = "DataManager";

    private HttpHelper mHttpHelper;
    private DBHelper mDbHelper;

    public DataManager(HttpHelper mHttpHelper, DBHelper mDbHelper) {
        this.mHttpHelper = mHttpHelper;
        this.mDbHelper = mDbHelper;
    }

    //---------------------------网络请求---------------------------------------

    @Override
    public Flowable<LoginInfoBean> fetchLogin(Map map) {
        return mHttpHelper.fetchLogin(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mHttpHelper.UpCasePicData(multipartBody);
    }



    //---------------------------数据库查询---------------------------------------

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return mDbHelper.queryLoginUserInfo(mUserName);
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return mDbHelper.loadLoginUserInfo();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.insertLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        mDbHelper.deleteLoginUserInfo(mUserName);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.UpDataLoginUserInfo(mLoginUserInfo);
    }


}
