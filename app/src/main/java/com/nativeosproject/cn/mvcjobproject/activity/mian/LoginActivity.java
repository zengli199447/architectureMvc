package com.nativeosproject.cn.mvcjobproject.activity.mian;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nativeosproject.cn.mvcjobproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import butterknife.BindView;
import global.DataClass;
import model.bean.LoginInfoBean;
import model.db.entity.LoginUserInfo;
import model.event.CommonEvent;
import model.event.EventCode;
import model.http.UrlSettingTools;
import rxtools.RxBus;
import rxtools.RxUtil;
import util.LogUtil;
import widget.CommonSubscriber;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.button_view)
    Button button_view;
    private DataClass dataClass;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.ONSTART:
                toastUtil.showToast("nihao");
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initClass() {
        UrlSettingTools.UrlSetting(DataClass.URL);
        dataClass = new DataClass(dataManager);
    }

    @Override
    protected void initData() {
        LogUtil.e(TAG, "CurrentUsername() :" + dataClass.GetCurrentUser().getUsername());
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
        switch (view.getId()) {
            case R.id.button_view:
                initNetDataWork();
                break;
        }
    }

    private void initNetDataWork() {
        Map map = new HashMap();
        map.put("cmd", "CLIENT_USER_LOGIN");
        map.put("userid", "admin");
        map.put("pwd", "admin123456");
        map.put("deviceType", "mobile");
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoBean loginInfoBean) {
                        Log.e(TAG, "LoginInfoBean : " + loginInfoBean);
                        dataClass.DbCurrentUser("admin", "admin123456");
                        RxBus.getDefault().post(new CommonEvent(EventCode.ONSTART, "bingo"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);

                    }
                }));
    }


}
