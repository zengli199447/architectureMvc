package model.http;

import java.util.Map;

import io.reactivex.Flowable;
import model.bean.LoginInfoBean;
import model.bean.OuterLayerEntity;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/27.
 */


public interface HttpHelper {

    Flowable<LoginInfoBean> fetchLogin(Map map);

    Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody);



}
