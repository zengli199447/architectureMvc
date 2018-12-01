package base;

import javax.inject.Inject;

import di.component.ActivityComponent;
import di.component.DaggerActivityComponent;
import di.module.ActivityModule;
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
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseActivity extends SimpleActivity {

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;

    protected CompositeDisposable mCompositeDisposable;

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        initRegisterEvent();
    }

    protected abstract void registerEvent(CommonEvent commonevent);

    protected abstract void initInject();

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
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

}
