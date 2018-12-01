package base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import global.MyApplication;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/3/1.
 */

public  abstract class SimpleActivity extends AppCompatActivity {
    private String TAG = "SimplePermissions";
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        requestPermissions();
        mUnBinder = ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(this);
        onViewCreated();
        initClass();
        initData();
        initView();
        initListener();
    }

    protected void onViewCreated() {

    }

    protected void onUnSubscribe() {

    }

    protected abstract int getLayout();

    protected abstract void initClass();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SimpleActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.GET_TASKS,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.i(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.i(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.e(TAG, permission.name + " is denied.");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
        mUnBinder.unbind();
        onUnSubscribe();
    }

}
