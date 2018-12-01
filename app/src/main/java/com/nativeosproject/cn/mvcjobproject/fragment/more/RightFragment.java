package com.nativeosproject.cn.mvcjobproject.fragment.more;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nativeosproject.cn.mvcjobproject.R;

import java.util.HashMap;
import java.util.Map;

import base.BaseFragment;
import butterknife.BindView;
import global.DataClass;
import model.bean.LoginInfoBean;
import model.event.CommonEvent;
import model.event.EventCode;
import rxtools.RxBus;
import rxtools.RxUtil;
import widget.CommonSubscriber;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class RightFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.button_view)
    Button button_view;
    private DataClass dataClass;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_right;
    }

    @Override
    protected void function() {
        initClass();
        initListener();
    }

    private void initClass() {
        dataClass = new DataClass(dataManager);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_view:
                toastUtil.showToast("right");
                initNetWork();
                break;
        }
    }

    private void initListener() {
        button_view.setOnClickListener(this);
    }

    private void initNetWork() {
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
