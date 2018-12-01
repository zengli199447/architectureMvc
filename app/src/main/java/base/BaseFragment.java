package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import di.component.DaggerFragmentComponent;
import di.component.FragmentComponent;
import di.module.FragmentModule;
import global.MyApplication;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import model.DataManager;
import model.event.CommonEvent;
import rxtools.RxBus;
import rxtools.RxUtil;
import util.ToastUtil;
import widget.CommonSubscriber;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public abstract class BaseFragment extends SimpleFragment{

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;
    private CompositeDisposable mCompositeDisposable;

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule(){
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        initRegisterEvent();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void initRegisterEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CommonEvent.class)
                .compose(RxUtil.<CommonEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonEvent>(toastUtil, null) {

                    @Override
                    public void onNext(CommonEvent commonevent) {
                        registerEvent(commonevent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }

    @Override
    protected void onUnSubscribe() {
        super.onUnSubscribe();
        unSubscribe();
    }

    protected abstract void initInject();

    protected abstract void registerEvent(CommonEvent commonevent);

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
