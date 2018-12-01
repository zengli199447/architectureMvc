package model.http;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import model.bean.LoginInfoBean;
import model.bean.OuterLayerEntity;
import model.http.api.MyApis;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/30.
 */

public class RetrofitHelper implements HttpHelper {

    private MyApis mMyApiService;

    @Inject
    public RetrofitHelper(MyApis myApiService) {
        this.mMyApiService = myApiService;
    }


    @Override
    public Flowable<LoginInfoBean> fetchLogin(Map map) {
        return mMyApiService.login(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mMyApiService.UpCasePicData(multipartBody);
    }
}
