package model.http.api;

import java.util.Map;

import io.reactivex.Flowable;
import model.bean.LoginInfoBean;
import model.bean.OuterLayerEntity;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/10/30.
 * 网络请求
 * 添加请求头可以统一添加  单一添加请查看操作符
 * 统一添加查看 di 目录下 httmModule
 */

public interface MyApis {

//    String HOST = "https://www.baidu.com/";

    String HOST = "http://192.168.14.199:8088/portal/r/";

    /**
     * 登录
     *
     * @return
     */
    @POST("jd")
    Flowable<LoginInfoBean> login(@QueryMap Map<String, String> map);

    /**
     * 上传文件
     *
     * @return
     */
    @POST("jd")
    Flowable<OuterLayerEntity> UpCasePicData(@Body MultipartBody multipartBody);


}
