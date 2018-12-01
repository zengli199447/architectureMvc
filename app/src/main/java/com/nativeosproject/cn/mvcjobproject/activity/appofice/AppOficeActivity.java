package com.nativeosproject.cn.mvcjobproject.activity.appofice;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nativeosproject.cn.mvcjobproject.R;
import com.nativeosproject.cn.mvcjobproject.extend.SlideWall;

import base.BaseActivity;
import butterknife.BindView;
import cn.waps.AppConnect;
import cn.waps.AppListener;
import model.event.CommonEvent;

/**
 * Created by Administrator on 2018/6/27 0027.
 */

public class AppOficeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.AdLinearLayout)
    LinearLayout AdLinearLayout;
    @BindView(R.id.miniAdLinearLayout)
    LinearLayout miniAdLinearLayout;
    private LinearLayout slidingDrawerView;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_app_ofice;
    }

    @Override
    protected void initClass() {
        // 预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
        AppConnect.getInstance(this).initAdInfo();
        // 预加载插屏广告内容（仅在使用到插屏广告的情况，才需要添加）
        AppConnect.getInstance(this).initPopAd(this);
        String showAd = AppConnect.getInstance(this).getConfig("showAd", "defaultValue");
        // 互动广告调用方式
        AppConnect.getInstance(this).showBannerAd(this, AdLinearLayout);
        LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
        AppConnect.getInstance(this).showMiniAd(this, miniLayout, 10);// 10秒刷新一次
        slidingDrawerView = SlideWall.getInstance().getView(this);
        if (slidingDrawerView != null) {
            this.addContentView(slidingDrawerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        AppConnect.getInstance(this).setBannerAdNoDataListener(new AppListener() {

            @Override
            public void onBannerNoData() {
                Log.e("debug", "banner广告暂无可用数据");
            }

        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        // 从服务器端获取当前用户的虚拟货币.
        // 返回结果在回调函数getUpdatePoints(...)中处理
//        AppConnect.getInstance(this).getPoints(this);
        super.onResume();
    }


}
