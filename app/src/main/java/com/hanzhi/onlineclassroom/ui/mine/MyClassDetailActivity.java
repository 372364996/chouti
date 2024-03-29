package com.hanzhi.onlineclassroom.ui.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.chewawa.baselibrary.networkutils.HttpManager;
import com.chewawa.baselibrary.networkutils.bean.ResultBean;
import com.chewawa.baselibrary.networkutils.callback.ApiCallBack;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassBean;
import com.hanzhi.onlineclassroom.network.Constants;
import com.hanzhi.onlineclassroom.ui.mine.contract.MyClassDetailContract;
import com.hanzhi.onlineclassroom.ui.mine.presenter.MyClassDetailPresenter;
import com.hanzhi.onlineclassroom.ui.selectclass.adapter.ClassDetailAdapter;
import com.hanzhi.onlineclassroom.ui.selectclass.contract.ClassDetailContract;
import com.hanzhi.onlineclassroom.ui.selectclass.presenter.ClassDetailPresenter;
import com.hanzhi.onlineclassroom.utils.CommonUtil;
import com.hanzhi.onlineclassroom.utils.Utils;
import com.hanzhi.onlineclassroom.view.SubmitAppraiseDialog;
import com.hanzhi.onlineclassroom.view.WrapContentHeightViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmFileMessage;
import io.agora.rtm.RtmImageMessage;
import io.agora.rtm.RtmMediaOperationProgress;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.SendMessageOptions;
import io.reactivex.disposables.Disposable;

import static io.agora.rtc.IRtcEngineEventHandler.UserOfflineReason.USER_OFFLINE_DROPPED;
import static io.agora.rtc.IRtcEngineEventHandler.UserOfflineReason.USER_OFFLINE_QUIT;

/**
 * @class 课程详情
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 13:58
 */
public class MyClassDetailActivity extends NBaseActivity<ClassDetailPresenter> implements ClassDetailContract.View, ViewPager.OnPageChangeListener, MyClassDetailContract.View, SubmitAppraiseDialog.OnAlertDialogListener {
    @BindView(R.id.tv_page)
    TextView tvPage;
    @BindView(R.id.progress_horizontal)
    ProgressBar progressHorizontal;
    @BindView(R.id.vp_class_detail)
    WrapContentHeightViewPager vpClassDetail;
    @BindView(R.id.remote_video_view_container)
    RelativeLayout mRemoteContainer;
    @BindView(R.id.local_video_view_container)
    RelativeLayout mLocalContainer;
    @BindView(R.id.cl_video_lay)
    ConstraintLayout clVideoLay;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.view_cut_off)
    View viewCutOff;

    ClassDetailAdapter classDetailAdapter;
    MyClassDetailPresenter myClassDetailPresenter;
    int userId;
    int classId;
    int studentUserId;
    String orderId;
    ClassBean classBean;
    CountDownTimer timer;
    SubmitAppraiseDialog submitAppraiseDialog;


    private RtcEngine mRtcEngine;
    private RtmClient mRtmClient;
    private boolean mCallEnd;
    private boolean mMuted;

    private SurfaceView mLocalView;
    private SurfaceView mRemoteView;
    private static final int PERMISSION_REQ_ID = 22;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    public static void start(Context context, int classId, String orderId, int studentUserId) {
        Intent starter = new Intent(context, MyClassDetailActivity.class);
        starter.putExtra("classId", classId);
        starter.putExtra("orderId", orderId);
        starter.putExtra("studentUserId", studentUserId);
        context.startActivity(starter);
    }

    /**
     * Event handler registered into RTC engine for RTC callbacks.
     * Note that UI operations needs to be in UI thread because RTC
     * engine deals with the events in a separate thread.
     */
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        /**
         * Occurs when the local user joins a specified channel.
         * The channel name assignment is based on channelName specified in the joinChannel method.
         * If the uid is not specified when joinChannel is called, the server automatically assigns a uid.
         *
         * @param channel Channel name.
         * @param uid User ID.
         * @param elapsed Time elapsed (ms) from the user calling joinChannel until this callback is triggered.
         */
        @Override
        public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        /**
         * Occurs when the first remote video frame is received and decoded.
         * This callback is triggered in either of the following scenarios:
         *
         *     The remote user joins the channel and sends the video stream.
         *     The remote user stops sending the video stream and re-sends it after 15 seconds. Possible reasons include:
         *         The remote user leaves channel.
         *         The remote user drops offline.
         *         The remote user calls the muteLocalVideoStream method.
         *         The remote user calls the disableVideo method.
         *
         * @param uid User ID of the remote user sending the video streams.
         * @param width Width (pixels) of the video stream.
         * @param height Height (pixels) of the video stream.
         * @param elapsed Time elapsed (ms) from the local user calling the joinChannel method until this callback is triggered.
         */
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                    //开启录制
                    startRecord(orderId,0);
                    userId = uid;
                }
            });
        }

        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         *     Leave the channel: When the user/host leaves the channel, the user/host sends a
         *     goodbye message. When this message is received, the SDK determines that the
         *     user/host leaves the channel.
         *
         *     Drop offline: When no data packet of the user or host is received for a certain
         *     period of time (20 seconds for the communication profile, and more for the live
         *     broadcast profile), the SDK assumes that the user/host drops offline. A poor
         *     network connection may lead to false detections, so we recommend using the
         *     Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who leaves the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         *     USER_OFFLINE_QUIT(0): The user left the current channel.
         *     USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         *     USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        @Override
        public void onUserOffline(final int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (reason) {
                        case USER_OFFLINE_QUIT: {
                            break;
                        }
                        case USER_OFFLINE_DROPPED: {
                            break;
                        }
                    }
                    onRemoteUserLeft();
                }
            });
        }
    };

    private void startRecord(String orderId,Integer userId) {
        String url = Constants.START_RECORD_URL;
        Map<String, Object> map = new HashMap<>();
        map.put("cname", orderId);
        Disposable disposable = HttpManager.post(url).upJsonObject(map).execute(new ApiCallBack() {
            @Override
            public void onError(int status, String message) {
            }

            @Override
            public void onSuccess(ResultBean resultBean) {
                if(!TextUtils.isEmpty(resultBean.getData())){

                }
            }
        });
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_my_class_detail;
    }

    @Override
    protected void initView() {
        classId = getIntent().getIntExtra("classId", 0);
        orderId = getIntent().getStringExtra("orderId");
        studentUserId = getIntent().getIntExtra("studentUserId", 0);
        initToolBar();
        toolbarLay.setTitle(R.string.title_class_detail);
        vpClassDetail.addOnPageChangeListener(this);

    }

    @Override
    protected void prepareData() {
        super.prepareData();
        presenter.getClassDetailData(classId);
        myClassDetailPresenter.getMyClassDetailData(orderId);
        try {
            mRtmClient = RtmClient.createInstance(getBaseContext(), getString(R.string.agora_app_id),
                    new RtmClientListener() {
                        @Override
                        public void onConnectionStateChanged(int state, int reason) {
                            Log.d("asdf", "Connection state changes to " + state + " reason: " + reason);
                        }

                        @Override
                        public void onMessageReceived(RtmMessage rtmMessage, String peerId) {
                            String msg = rtmMessage.getText();
                            Log.d("asdf", "Message received " + " from " + peerId + msg);
                            if (Utils.canParseInt(msg)) {
                                MyClassDetailActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int currentItem = Integer.parseInt(msg);
                                        vpClassDetail.setCurrentItem(currentItem);
                                    }
                                });
                            }


                        }

                        @Override
                        public void onImageMessageReceivedFromPeer(RtmImageMessage rtmImageMessage, String s) {

                        }

                        @Override
                        public void onFileMessageReceivedFromPeer(RtmFileMessage rtmFileMessage, String s) {

                        }

                        @Override
                        public void onMediaUploadingProgress(RtmMediaOperationProgress rtmMediaOperationProgress, long l) {

                        }

                        @Override
                        public void onMediaDownloadingProgress(RtmMediaOperationProgress rtmMediaOperationProgress, long l) {

                        }

                        @Override
                        public void onTokenExpired() {

                        }

                        @Override
                        public void onPeersOnlineStatusChanged(Map<String, Integer> map) {

                        }
                    });
        } catch (Exception e) {
            Log.d("asdf", "RTM SDK init fatal error!");
            throw new RuntimeException("You need to check the RTM init process.");
        }
        mRtmClient.login(null, String.valueOf(CommonUtil.getUserId()), new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.d("asdf", "login success!");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.d("asdf", "login failure!");
            }
        });
    }

    @Override
    public ClassDetailPresenter initPresenter() {
        myClassDetailPresenter = new MyClassDetailPresenter(this);
        return new ClassDetailPresenter(this);
    }

    @Override
    public void setClassDetailData(ClassBean classBean) {
        this.classBean = classBean;
        classDetailAdapter = new ClassDetailAdapter(this, classBean.getClassMaterials());
        vpClassDetail.setAdapter(classDetailAdapter);
        tvPage.setText("1/" + classBean.getClassMaterials().size());
        progressHorizontal.setMax(classBean.getClassMaterials().size());
        progressHorizontal.setProgress(1);
    }

    @Override
    public void setRemainingTime(long remainingTime) {
        if (remainingTime > 0) {
            if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                    checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
                initEngineAndJoinChannel();
            }

            clVideoLay.setVisibility(View.VISIBLE);

            timer = new CountDownTimer(remainingTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (tvCountdown != null) {
                        tvCountdown.setClickable(false);
                        tvCountdown.setEnabled(false);
                        tvCountdown.setText(Utils.ConvertTotalSecondToHourMinuteSecond(String.valueOf(millisUntilFinished / 1000)));
                        tvCountdown.setTextColor(Color.parseColor("#999999"));
                    }
                }

                @Override
                public void onFinish() {
                    submitAppraiseDialog = new SubmitAppraiseDialog(MyClassDetailActivity.this);
                    submitAppraiseDialog.setOnAlertDialogListener(MyClassDetailActivity.this);
                    submitAppraiseDialog.show();
                }
            };
            timer.start();
        } else {
            clVideoLay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (classBean == null) {
            return;
        }
        int index = position + 1;
        tvPage.setText(index + "/" + classBean.getClassMaterials().size());
        progressHorizontal.setProgress(index);
        if (CommonUtil.getTeacherId() > 0) {
            sendPeerMessage(String.valueOf(studentUserId), String.valueOf(position));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.remote_video_view_container, R.id.local_video_view_container})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.remote_video_view_container: {

                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mRemoteContainer.getLayoutParams();
                if (layoutParams.matchConstraintPercentWidth < 1) {
                    mLocalContainer.setVisibility(View.GONE);
                    layoutParams.matchConstraintPercentWidth = 1.0f;
                } else {
                    mLocalContainer.setVisibility(View.VISIBLE);
                    layoutParams.matchConstraintPercentWidth = 0.5f;
                }
                layoutParams.dimensionRatio = "h,4:3";
                mRemoteContainer.setLayoutParams(layoutParams);
                break;
            }
            case R.id.local_video_view_container: {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mLocalContainer.getLayoutParams();
                ;
                if (layoutParams.matchConstraintPercentWidth < 1) {
                    mRemoteContainer.setVisibility(View.GONE);
                    layoutParams.matchConstraintPercentWidth = 1.0f;
                } else {
                    mRemoteContainer.setVisibility(View.VISIBLE);
                    layoutParams.matchConstraintPercentWidth = 0.5f;
                }
                layoutParams.dimensionRatio = "h,4:3";
                mLocalContainer.setLayoutParams(layoutParams);
                break;
            }
        }
    }

    //发送翻页消息
    private void sendMessage(String dst, String content) {
        final RtmMessage message = mRtmClient.createMessage();
        message.setText(content);

        SendMessageOptions option = new SendMessageOptions();
        option.enableOfflineMessaging = true;

        mRtmClient.sendMessageToPeer(dst, message, option, new ResultCallback<Void>() {

            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });
    }

    private void setupRemoteVideo(int uid) {
        // Only one remote video view is available for this
        // tutorial. Here we check if there exists a surface
        // view tagged as this uid.
        int count = mRemoteContainer.getChildCount();
        View view = null;
        for (int i = 0; i < count; i++) {
            View v = mRemoteContainer.getChildAt(i);
            if (v.getTag() instanceof Integer && ((int) v.getTag()) == uid) {
                view = v;
            }
        }

        if (view != null) {
            return;
        }

        /*
          Creates the video renderer view.
          CreateRendererView returns the SurfaceView type. The operation and layout of the view
          are managed by the app, and the Agora SDK renders the view provided by the app.
          The video display view must be created using this method instead of directly
          calling SurfaceView.
         */
        mRemoteView = RtcEngine.CreateRendererView(getBaseContext());
        mRemoteContainer.addView(mRemoteView);
        // Initializes the video view of a remote user.
        mRtcEngine.setupRemoteVideo(new VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        mRemoteView.setTag(uid);
    }

    private void onRemoteUserLeft() {
        removeRemoteVideo();
    }

    private void removeRemoteVideo() {
        if (mRemoteView != null) {
            mRemoteContainer.removeView(mRemoteView);
        }
        // Destroys remote view
        mRemoteView = null;
    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ) {
                finish();
                return;
            }

            // Here we continue only if all permissions are granted.
            // The permissions can also be granted in the system settings manually.
            initEngineAndJoinChannel();
        }
    }

    private void initEngineAndJoinChannel() {
        // This is our usual steps for joining
        // a channel and starting a call.
        initializeEngine();
        setupVideoConfig();
        setupLocalVideo();
        joinChannel();
    }

    private void initializeEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }


    }

    private void setupVideoConfig() {
        // In simple use cases, we only need to enable video capturing
        // and rendering once at the initialization step.
        // Note: audio recording and playing is enabled by default.
        mRtcEngine.enableVideo();

        // Please go to this page for detailed explanation
        // https://docs.agora.io/en/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_rtc_engine.html#af5f4de754e2c1f493096641c5c5c1d8f
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }

    private void setupLocalVideo() {
        // This is used to set a local preview.
        // The steps setting local and remote view are very similar.
        // But note that if the local user do not have a uid or do
        // not care what the uid is, he can set his uid as ZERO.
        // Our server will assign one and return the uid via the event
        // handler callback function (onJoinChannelSuccess) after
        // joining the channel successfully.
        mLocalView = RtcEngine.CreateRendererView(getBaseContext());
        mLocalView.setZOrderMediaOverlay(true);
        mLocalContainer.addView(mLocalView);
        // Initializes the local video view.
        // RENDER_MODE_HIDDEN: Uniformly scale the video until it fills the visible boundaries. One dimension of the video may have clipped contents.
        mRtcEngine.setupLocalVideo(new VideoCanvas(mLocalView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }

    private void joinChannel() {
        // 1. Users can only see each other after they join the
        // same channel successfully using the same app id.
        // 2. One token is only valid for the channel name that
        // you use to generate this token.
        String token = getString(R.string.agora_access_token);
        if (TextUtils.isEmpty(token) || TextUtils.equals(token, "#YOUR ACCESS TOKEN#")) {
            token = null; // default, no token
        }
        // sendPeerMessage("1054", "3");
        mRtcEngine.joinChannel(token, orderId, "", CommonUtil.getUserId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (!mCallEnd) {
            leaveChannel();
        }
        /*
          Destroys the RtcEngine instance and releases all resources used by the Agora SDK.

          This method is useful for apps that occasionally make voice or video calls,
          to free up resources for other operations when not making calls.
         */
        RtcEngine.destroy();
    }

    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    /*静音的点击事件*/
    public void onLocalAudioMuteClicked(View view) {
//        mMuted = !mMuted;
//        // Stops/Resumes sending the local audio stream.
//        mRtcEngine.muteLocalAudioStream(mMuted);
//        int res = mMuted ? R.drawable.btn_mute : R.drawable.btn_unmute;
//        mMuteBtn.setImageResource(res);
    }

    /*切换摄像头的点击事件*/
    public void onSwitchCameraClicked(View view) {
        // Switches between front and rear cameras.
        mRtcEngine.switchCamera();
    }

    /*开始视频的点击事件*/
    public void onCallClicked(View view) {
//        if (mCallEnd) {
//            startCall();
//            mCallEnd = false;
//            mCallBtn.setImageResource(R.drawable.btn_endcall);
//        } else {
//            endCall();
//            mCallEnd = true;
//            mCallBtn.setImageResource(R.drawable.btn_startcall);
//        }
//
//        showButtons(!mCallEnd);
    }

    private void startCall() {
        setupLocalVideo();
        joinChannel();
    }

    private void endCall() {
        removeLocalVideo();
        removeRemoteVideo();
        leaveChannel();
    }

    private void removeLocalVideo() {
        if (mLocalView != null) {
            mLocalContainer.removeView(mLocalView);
        }
        mLocalView = null;
    }

    public void sendPeerMessage(String dst, String content) {

        final RtmMessage message = mRtmClient.createMessage();
        message.setText(content);

        SendMessageOptions option = new SendMessageOptions();
        option.enableOfflineMessaging = true;

        mRtmClient.sendMessageToPeer(dst, message, option, new ResultCallback<Void>() {

            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });
    }

    @Override
    public void onDialogAffirm(float ranking, String editText) {
        myClassDetailPresenter.submitAppraise(orderId, ranking, editText);
    }

}
