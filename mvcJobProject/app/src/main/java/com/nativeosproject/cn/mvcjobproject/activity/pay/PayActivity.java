package com.nativeosproject.cn.mvcjobproject.activity.pay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.nativeosproject.cn.mvcjobproject.R;

import java.util.Map;

import base.BaseActivity;
import butterknife.BindView;
import global.DataClass;
import model.event.CommonEvent;
import sdktools.AuthResult;
import sdktools.OrderInfoUtil2_0;
import sdktools.PayResult;
import util.ToastUtil;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.button_pay)
    Button button_pay;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DataClass.SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        toastUtil.showToast("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        toastUtil.showToast("支付失败");
                    }
                    break;
                }
                case DataClass.SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        toastUtil.showToast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        toastUtil.showToast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2() {
        if (TextUtils.isEmpty(DataClass.APPID) || (TextUtils.isEmpty(DataClass.RSA2_PRIVATE) && TextUtils.isEmpty(DataClass.RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (DataClass.RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(DataClass.APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? DataClass.RSA2_PRIVATE : DataClass.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = DataClass.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pay;
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
        button_pay.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_pay:
                PayTools();
                break;
        }

    }

    public void PayTools(){
        payV2();
        toastUtil.showToast("nihao - ");
    }



}
