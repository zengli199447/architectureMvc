package com.nativeosproject.cn.mvcjobproject.activity.mian;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nativeosproject.cn.mvcjobproject.R;
import com.nativeosproject.cn.mvcjobproject.fragment.more.LifeFragment;
import com.nativeosproject.cn.mvcjobproject.fragment.more.RightFragment;

import base.BaseActivity;
import base.BaseFragment;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import model.event.CommonEvent;
import struct.FunctionNoPNoR;
import struct.FuntionManager;
import widget.Constants;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.radio_view)
    RadioGroup radio_view;
    @BindView(R.id.life_button)
    RadioButton life_button;
    @BindView(R.id.right_button)
    RadioButton right_button;
    private int showFragment = Constants.TYPE_LIFE;
    private int hideFragment = Constants.TYPE_LIFE;
    private LifeFragment lifeFragment;
    private RightFragment rightFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            hideFragment = showFragment;
        }
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_LIFE:
                return lifeFragment;
            case Constants.TYPE_RIGHT:
                return rightFragment;
        }
        return lifeFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initClass() {
        lifeFragment = new LifeFragment();
        rightFragment = new RightFragment();
        loadMultipleRootFragment(R.id.fl_main_content, 0, lifeFragment, rightFragment);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        radio_view.setOnCheckedChangeListener(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.life_button:
                showFragment = Constants.TYPE_LIFE;
                break;
            case R.id.right_button:
                showFragment = Constants.TYPE_RIGHT;
                break;
        }
        radio_view.check(checkedId);
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;
    }

    public void setFunctionForFragment(String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(tag);
        FuntionManager instance = FuntionManager.getInstance();
        fragment.setFuntionManager(instance.addFunction(new FunctionNoPNoR(LifeFragment.INTERFACE) {
            @Override
            public void function() {
                toastUtil.showToast("NOPNOR");
            }
        }));

    }



}
