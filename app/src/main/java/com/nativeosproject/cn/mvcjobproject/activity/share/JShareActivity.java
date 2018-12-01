package com.nativeosproject.cn.mvcjobproject.activity.share;

import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nativeosproject.cn.mvcjobproject.R;
import com.nativeosproject.cn.mvcjobproject.activity.share.uitool.ShareBoard;
import com.nativeosproject.cn.mvcjobproject.activity.share.uitool.ShareBoardlistener;
import com.nativeosproject.cn.mvcjobproject.activity.share.uitool.SnsPlatform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import base.BaseActivity;
import butterknife.BindView;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import model.event.CommonEvent;
import model.event.EventCode;
import rxtools.RxBus;
import util.LogUtil;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class JShareActivity extends BaseActivity implements View.OnClickListener, PlatActionListener ,ShareBoardlistener {
    @BindView(R.id.button_view)
    Button button_view;
    @BindView(R.id.button_pop)
    Button button_pop;
    private String ImagePath;
    private int mAction;
    private ShareBoard mShareBoard;
    private JShareSetting jShareSetting;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.ONSTART:
                progressDialog.dismiss();
                break;
        }

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_jshare;
    }

    @Override
    protected void initClass() {
        jShareSetting = new JShareSetting(this);
    }

    @Override
    protected void initData() {
        File imageFile =  jShareSetting.copyResurces( "jiguang_test_img.png", "test_img.png", 0);
        ImagePath = imageFile.getAbsolutePath();
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候");
    }

    @Override
    protected void initListener() {
        button_view.setOnClickListener(this);
        button_pop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_view:
                ShareParams shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_VIDEO);//分享类型
                shareParams.setTitle("标题"); //分享标题
                shareParams.setText("内容");//分享内容
                shareParams.setUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%8F%AF%E7%88%B1%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=20&spn=0&di=81947805940&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=171918920%2C1546681003&os=1619126843%2C3041949405&simid=3407526154%2C101558968&adpicid=0&lpn=0&ln=1989&fr=&fmq=1520068032787_R&fm=rs7&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fwww.qqpk.cn%2FArticle%2FUploadFiles%2F201410%2F20141015092732373.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bqqrh_z%26e3BvgAzdH3FA6ptvsjAzdH3Fp7rtwgAzdH3Fda898aAzdH3F8m89c_z%26e3Bip4&gsm=0&rpstart=0&rpnum=0");//分享链接
                shareParams.setImagePath(ImagePath);//分享链接
//                JShareInterface.share(QQ.Name, shareParams, this);
                JShareInterface.share(QZone.Name, shareParams, this);
//                JShareInterface.share(Wechat.Name, shareParams, this);
                break;

            case R.id.button_pop:
                mAction = Platform.ACTION_SHARE;
                showBroadView();

                break;

        }
    }

    private void showBroadView() {
        if (mShareBoard == null) {
            mShareBoard = new ShareBoard(this);
            List<String> platforms = JShareInterface.getPlatformList();
            if (platforms != null) {
                Iterator var2 = platforms.iterator();
                while (var2.hasNext()) {
                    String temp = (String) var2.next();
                    Log.e(TAG,"temp : " + temp);
                    SnsPlatform snsPlatform = jShareSetting.createSnsPlatform(temp);
                    Log.e(TAG,"snsPlatform.toString() : " + snsPlatform.toString());
                    mShareBoard.addPlatform(snsPlatform);
                }
            }
            mShareBoard.setShareboardclickCallback(this);
        }
        mShareBoard.show();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        RxBus.getDefault().post(new CommonEvent(EventCode.ONSTART));
        LogUtil.e(TAG, "完成");
    }

    @Override
    public void onError(Platform platform, int i, int i1, Throwable throwable) {
        RxBus.getDefault().post(new CommonEvent(EventCode.ONSTART));
        LogUtil.e(TAG, "异常");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        RxBus.getDefault().post(new CommonEvent(EventCode.ONSTART));
        LogUtil.e(TAG, "取消");
    }


    @Override
    public void onclick(SnsPlatform snsPlatform, String platform) {
        switch (mAction) {
            case Platform.ACTION_SHARE:
                progressDialog.show();
                //这里以分享链接为例
                ShareParams shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_WEBPAGE);//分享类型
                shareParams.setTitle("标题"); //分享标题
                shareParams.setText("内容");//分享内容
                shareParams.setUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%8F%AF%E7%88%B1%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=20&spn=0&di=81947805940&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=171918920%2C1546681003&os=1619126843%2C3041949405&simid=3407526154%2C101558968&adpicid=0&lpn=0&ln=1989&fr=&fmq=1520068032787_R&fm=rs7&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fwww.qqpk.cn%2FArticle%2FUploadFiles%2F201410%2F20141015092732373.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bqqrh_z%26e3BvgAzdH3FA6ptvsjAzdH3Fp7rtwgAzdH3Fda898aAzdH3F8m89c_z%26e3Bip4&gsm=0&rpstart=0&rpnum=0");//分享链接
                shareParams.setImagePath(ImagePath);//分享链接
                JShareInterface.share(platform, shareParams, this);
                break;
            default:
                break;
        }
    }
}
