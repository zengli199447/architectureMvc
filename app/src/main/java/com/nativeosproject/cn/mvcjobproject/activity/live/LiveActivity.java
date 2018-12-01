package com.nativeosproject.cn.mvcjobproject.activity.live;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nativeosproject.cn.mvcjobproject.R;
import com.nativeosproject.cn.mvcjobproject.livetools.fakeserver.FakeServer;
import com.nativeosproject.cn.mvcjobproject.livetools.fakeserver.HttpUtil;
import com.nativeosproject.cn.mvcjobproject.livetools.widget.LiveKit;

import org.json.JSONException;
import org.json.JSONObject;

import base.BaseActivity;
import butterknife.BindView;
import global.DataClass;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import model.event.CommonEvent;
import util.LogUtil;

/**
 * Created by Administrator on 2018/3/4 0004.
 */

public class LiveActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.button_view)
    Button button_view;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_live;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        button_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_view:
                FakeLogin("张三", "123456", "rtmp://live.hkstv.hk.lxdns.com/live/hks");
                break;
        }
    }

    private void FakeLogin(String id, String password, final String url){
        final UserInfo user = FakeServer.getLoginUser(id, password);
        FakeServer.getToken(user, new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                if (code != 200){
                    toastUtil.showToast("登录失败");
                    return;
                }

                String token;
                try {
                    JSONObject jsonObj = new JSONObject(body);
                    token = jsonObj.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastUtil.showToast("Token 解析失败!");
                    return;
                }

                LiveKit.connect(token, new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                        toastUtil.showToast(" 检查appKey 与token是否匹配!");
                    }

                    @Override
                    public void onSuccess(String s) {
                        LiveKit.setCurrentUser(user);
                        Intent intent = new Intent(LiveActivity.this, LiveShowActivity.class);
                        intent.putExtra(DataClass.LIVE_URL, url);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        toastUtil.showToast("errorCode ：" + errorCode.toString());
                    }
                });


            }
        });



    }


}
