package com.nativeosproject.cn.mvcjobproject.fragment.more;

import android.view.View;
import android.widget.Button;

import com.nativeosproject.cn.mvcjobproject.R;
import com.nativeosproject.cn.mvcjobproject.view.MyProgress;

import base.BaseFragment;
import butterknife.BindView;
import model.event.CommonEvent;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class LifeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.button_view)
    Button button_view;
    @BindView(R.id.my_progress)
    MyProgress my_progress;
    public static final String INTERFACE = LifeFragment.class.getName() + "NPNR";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_life;
    }

    @Override
    protected void function() {
        initClass();
        initNetWork();
        initListener();
    }

    private void initClass() {
        my_progress.setText("60%");
        my_progress.setProgress(30);

    }

    private void initNetWork() {

    }

    private void initListener() {
        button_view.setOnClickListener(this);
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
                funtionManager.invokeFunc(INTERFACE);
//                toastUtil.showToast("life");
                break;
        }
    }

}
