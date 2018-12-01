package base;

import android.app.Service;

import javax.inject.Inject;

import di.component.DaggerServerComponent;
import di.component.ServerComponent;
import di.module.ServerModule;
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
 * Created by Administrator on 2018/7/18 0018.
 */

public abstract class BaseService extends Service{

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;

    protected CompositeDisposable mCompositeDisposable;

    protected ServerComponent getServerComponent() {
        return DaggerServerComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .serverModule(new ServerModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
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


}
