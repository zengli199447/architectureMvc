package com.nativeosproject.cn.mvcjobproject.activity.live;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.nativeosproject.cn.mvcjobproject.R;
import com.nativeosproject.cn.mvcjobproject.fragment.live.BottomPanelFragment;
import com.nativeosproject.cn.mvcjobproject.livetools.animation.HeartLayout;
import com.nativeosproject.cn.mvcjobproject.livetools.controller.ChatListAdapter;
import com.nativeosproject.cn.mvcjobproject.livetools.message.GiftMessage;
import com.nativeosproject.cn.mvcjobproject.livetools.widget.ChatListView;
import com.nativeosproject.cn.mvcjobproject.livetools.widget.InputPanel;
import com.nativeosproject.cn.mvcjobproject.livetools.widget.LiveKit;

import java.io.IOException;
import java.util.Random;

import base.BaseActivity;
import butterknife.BindView;
import global.DataClass;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import model.event.CommonEvent;

/**
 * Created by Administrator on 2018/3/4 0004.
 */

public class LiveShowActivity extends BaseActivity implements View.OnClickListener, InputPanel.InputPanelListener, IMediaPlayer.OnPreparedListener, Handler.Callback {
    @BindView(R.id.player_surface)
    SurfaceView surfaceView;
    @BindView(R.id.background)
    ViewGroup background;
    @BindView(R.id.chat_listview)
    ChatListView chatListView;
    @BindView(R.id.heart_layout)
    HeartLayout heartLayout;
    private BottomPanelFragment bottomPanel;
    private ImageView btnGift;
    private ImageView btnHeart;
    private ChatListAdapter chatListAdapter;
    private KSYMediaPlayer ksyMediaPlayer;
    private Random random;
    private Handler handler;
    private String roomId;
    private SurfaceHolder surfaceHolder;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_show_live;
    }

    @Override
    protected void initClass() {
        random = new Random();
        handler = new Handler(this);
        LiveKit.addEventHandler(handler);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        bottomPanel = (BottomPanelFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_bar);
        btnGift = (ImageView) bottomPanel.getView().findViewById(R.id.btn_gift);
        btnHeart = (ImageView) bottomPanel.getView().findViewById(R.id.btn_heart);
        chatListAdapter = new ChatListAdapter();
        chatListView.setAdapter(chatListAdapter);

        ksyMediaPlayer = new KSYMediaPlayer.Builder(this).build();
        ksyMediaPlayer.setScreenOnWhilePlaying(true);
        ksyMediaPlayer.setBufferTimeMax(5);
        ksyMediaPlayer.setTimeout(20, 100);

        initLiveShow();

    }

    private void initLiveShow() {
        roomId = "ChatRoom01";
        String liveUrl = getIntent().getStringExtra(DataClass.LIVE_URL);
        joinChatRoom(roomId);
        playShow(liveUrl);
    }

    private void playShow(String liveUrl) {
        try {
            ksyMediaPlayer.setDataSource(liveUrl);
            ksyMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ksyMediaPlayer != null)
                    ksyMediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
                if (ksyMediaPlayer != null && ksyMediaPlayer.isPlaying())
                    ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (ksyMediaPlayer != null) {
                    ksyMediaPlayer.setDisplay(null);
                }
            }
        });
    }

    private void joinChatRoom(String roomId) {
        LiveKit.joinChatRoom(roomId, 2, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                final InformationNotificationMessage content = InformationNotificationMessage.obtain("来啦");
                LiveKit.sendMessage(content);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                toastUtil.showToast("聊天室加入失败! errorCode = " + errorCode);
            }
        });

    }

    @Override
    protected void initListener() {
        background.setOnClickListener(this);
        btnGift.setOnClickListener(this);
        btnHeart.setOnClickListener(this);
        bottomPanel.setInputPanelListener(this);
        ksyMediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.background:
                bottomPanel.onBackAction();
                break;
            case R.id.btn_gift:
                GiftMessage lMsg = new GiftMessage("2", "礼物 + 1");
                LiveKit.sendMessage(lMsg);
                break;
            case R.id.btn_heart:
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                        heartLayout.addHeart(rgb);
                    }
                });
                GiftMessage zMsg = new GiftMessage("1", "赞 + 1");
                LiveKit.sendMessage(zMsg);
                break;

        }
    }

    @Override
    public void onSendClick(String text) {
        final TextMessage content = TextMessage.obtain(text);
        LiveKit.sendMessage(content);
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        ksyMediaPlayer.start();
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case LiveKit.MESSAGE_ARRIVED: {
                MessageContent content = (MessageContent) message.obj;
                chatListAdapter.addMessage(content);
                break;
            }
            case LiveKit.MESSAGE_SENT: {
                MessageContent content = (MessageContent) message.obj;
                chatListAdapter.addMessage(content);
                break;
            }
            case LiveKit.MESSAGE_SEND_ERROR: {
                break;
            }
            default:
        }
        chatListAdapter.notifyDataSetChanged();
        return false;
    }

//    @Override
//    public void onBackPressed() {
//        if (!bottomPanel.onBackAction()) {
//            finish();
//            return;
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!bottomPanel.onBackAction()) {
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onTheCustom() {
        LiveKit.quitChatRoom(new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                LiveKit.removeEventHandler(handler);
                LiveKit.logout();
                Toast.makeText(LiveShowActivity.this, "退出聊天室成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LiveKit.removeEventHandler(handler);
                LiveKit.logout();
                Toast.makeText(LiveShowActivity.this, "退出聊天室失败! errorCode = " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
        ksyMediaPlayer.stop();
    }
}
