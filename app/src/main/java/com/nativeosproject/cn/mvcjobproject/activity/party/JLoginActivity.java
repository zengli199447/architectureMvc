package com.nativeosproject.cn.mvcjobproject.activity.party;

import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nativeosproject.cn.mvcjobproject.R;

import java.util.List;

import base.BaseActivity;
import butterknife.BindView;
import cn.jiguang.share.android.api.AuthListener;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.model.AccessTokenInfo;
import cn.jiguang.share.android.model.BaseResponseInfo;
import cn.jiguang.share.android.model.UserInfo;
import cn.jiguang.share.android.utils.Logger;
import model.event.CommonEvent;
import util.LogUtil;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class JLoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.qq_permission_view)
    Button qq_permission_view;
    @BindView(R.id.wechat_permission_view)
    Button wechat_permission_view;
    private List<String> platformList;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_jlogin;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        platformList = JShareInterface.getPlatformList();
        if (platformList == null || platformList.isEmpty()) {
            toastUtil.showToast("请检查配置文件是否正确");
            return;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        qq_permission_view.setOnClickListener(this);
        wechat_permission_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qq_permission_view:
                for (int i = 0; i < platformList.size(); i++) {
                    LogUtil.e(TAG, "platformList.get(i) :" + platformList.get(i));
                }
                initPermission(platformList.get(3));
                break;
            case R.id.wechat_permission_view:
                initPermission(platformList.get(0));
                break;
        }
    }

    private void initPermission(String permissionType){
        if (!JShareInterface.isAuthorize(permissionType)) {
            JShareInterface.authorize(permissionType, mAuthListener);
        } else {
            JShareInterface.removeAuthorize(permissionType, mAuthListener);
        }
    }


    /**
     * 授权、获取个人信息回调
     * action ：Platform.ACTION_AUTHORIZING 授权
     * Platform.ACTION_USER_INFO 获取个人信息
     */
    AuthListener mAuthListener = new AuthListener() {
        @Override
        public void onComplete(Platform platform, int action, BaseResponseInfo data) {
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    if (data instanceof AccessTokenInfo) {        //授权信息
                        String token = ((AccessTokenInfo) data).getToken();//token
                        long expiration = ((AccessTokenInfo) data).getExpiresIn();//token有效时间，时间戳
                        String refresh_token = ((AccessTokenInfo) data).getRefeshToken();//refresh_token
                        String openid = ((AccessTokenInfo) data).getOpenid();//openid
                        //授权原始数据，开发者可自行处理
                        String originData = data.getOriginData();
                        toastMsg = "授权成功:" + data.toString();
                        Logger.dd(TAG, "openid:" + openid + ",token:" + token + ",expiration:" + expiration + ",refresh_token:" + refresh_token);
                        Logger.dd(TAG, "originData:" + originData);
                    }
                    break;
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    toastMsg = "删除授权成功";
                    break;
                case Platform.ACTION_USER_INFO:
                    if (data instanceof UserInfo) {      //第三方个人信息
                        String openid = ((UserInfo) data).getOpenid();  //openid
                        String name = ((UserInfo) data).getName();  //昵称
                        String imageUrl = ((UserInfo) data).getImageUrl();  //头像url
                        int gender = ((UserInfo) data).getGender();//性别, 1表示男性；2表示女性
                        //个人信息原始数据，开发者可自行处理
                        String originData = data.getOriginData();
                        toastMsg = "获取个人信息成功:" + data.toString();
                        Logger.dd(TAG, "openid:" + openid + ",name:" + name + ",gender:" + gender + ",imageUrl:" + imageUrl);
                        Logger.dd(TAG, "originData:" + originData);
                    }
                    break;
            }
            LogUtil.e(TAG, "toastMsg : " + toastMsg);
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            Logger.dd(TAG, "onError:" + platform + ",action:" + action + ",error:" + error);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    toastMsg = "授权失败";
                    break;
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    toastMsg = "删除授权失败";
                    break;
                case Platform.ACTION_USER_INFO:
                    toastMsg = "获取个人信息失败";
                    break;
            }
            LogUtil.e(TAG,"toastMsg : " + toastMsg);
        }

        @Override
        public void onCancel(Platform platform, int action) {
            Logger.dd(TAG, "onCancel:" + platform + ",action:" + action);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    toastMsg = "取消授权";
                    break;
                // TODO: 2017/6/23 删除授权不存在取消
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    break;
                case Platform.ACTION_USER_INFO:
                    toastMsg = "取消获取个人信息";
                    break;
            }
            LogUtil.e(TAG,"toastMsg : " + toastMsg);
        }
    };


}
