package com.dou361.jjdxm_ijkplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.widget.IjkVideoView;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.dou361.jjdxm_ijkplayer.utlis.MediaUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoMonitoring extends Activity implements View.OnClickListener {

    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list;
    private PowerManager.WakeLock wakeLock;
    View rootView;
    private Button button_qian,button_hou,button_zuo,button_you,button_shangdi;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_videomonitoring, null);
        setContentView(rootView);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

        button_qian= (Button)findViewById(R.id.qian_Click);
        button_hou = (Button)findViewById(R.id.hou_Click);
        button_zuo= (Button)findViewById(R.id.zuo_Click);
        button_you= (Button)findViewById(R.id.you_Click);
        button_shangdi= (Button)findViewById(R.id.shangdi_Click);
        button_qian.setOnClickListener(this);
        button_hou.setOnClickListener(this);
        button_zuo.setOnClickListener(this);
        button_you.setOnClickListener(this);
        button_shangdi.setOnClickListener(this);


            /**虚拟按键的隐藏方法*/
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //比较Activity根布局与当前布局的大小
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 100) {
                    //大小超过100时，一般为显示虚拟键盘事件
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                } else {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                }
            }
        });

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

    }

    @OnClick({R.id.qian_Click, R.id.hou_Click, R.id.zuo_Click, R.id.you_Click,R.id.shangdi_Click})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qian_Click:
//                /**半屏播放器*/
//                startActivity(HPlayerActivity.class);
                {
                    list = new ArrayList<VideoijkBean>();
                    //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                    //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                    String url1 = "http://ivi.bupt.edu.cn/hls/cctv1.m3u8";
                    String url2 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";
                    VideoijkBean m1 = new VideoijkBean();
                    m1.setStream("原始视频");
                    m1.setUrl(url1);
                    VideoijkBean m2 = new VideoijkBean();
                    m2.setStream("融合视频");
                    m2.setUrl(url2);
                    list.add(m1);
                    list.add(m2);
                    player = new PlayerView(this, rootView) {
                        @Override
                        public PlayerView toggleProcessDurationOrientation() {
//                            hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
                        }

                        @Override
                        public PlayerView setPlaySource(List<VideoijkBean> list) {
                            return super.setPlaySource(list);
                        }
                    }
                            .setTitle("前摄像")
                            .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                            .setScaleType(PlayStateParams.fillparent) //视频界面剪裁设置
                            .forbidTouch(false)
                            .hideSteam(false)
                            .hideMenu(false)
                            .hideCenterPlayer(true)
                            .hideRotation(true) //隐藏旋转按钮
                            .setChargeTie(true,480)
//                            .showThumbnail(new OnShowThumbnailListener() {
//                                @Override
//                                public void onShowThumbnail(ImageView ivThumbnail) {
////                                                                加载前显示的缩略图
//                                    Glide.with(mContext)
//                                            .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                            .placeholder(R.color.cl_default) //加载成功之前占位图
//                                            .error(R.color.cl_error)//加载错误之后的错误图
//                                            .into(ivThumbnail);
//                                }
//                            })
                            .setPlaySource(list)
//                            .setChargeTie(true,60)
                            .startPlay();
                }
                break;
            case R.id.hou_Click:
//                /**竖屏播放器*/
//                startActivity(PlayerActivity.class);
            {
                list = new ArrayList<VideoijkBean>();
                //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                String url1 = "http://ivi.bupt.edu.cn/hls/cctv2.m3u8";
                String url2 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";
                VideoijkBean m1 = new VideoijkBean();
                m1.setStream("原始视频");
                m1.setUrl(url1);
                VideoijkBean m2 = new VideoijkBean();
                m2.setStream("融合视频");
                m2.setUrl(url2);
                list.add(m1);
                list.add(m2);
                player = new PlayerView(this, rootView) {
                    @Override
                    public PlayerView toggleProcessDurationOrientation() {
//                        hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
                    }

                    @Override
                    public PlayerView setPlaySource(List<VideoijkBean> list) {
                        return super.setPlaySource(list);
                    }
                }
                        .setTitle("后摄像")
                        .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                        .setScaleType(PlayStateParams.fillparent)
                        .forbidTouch(false)
                        .hideSteam(false)
                        .hideRotation(true) //隐藏旋转按钮
                        .hideCenterPlayer(true)
                        .setChargeTie(true,480)
//                        .showThumbnail(new OnShowThumbnailListener() {
//                            @Override
//                            public void onShowThumbnail(ImageView ivThumbnail) {
//                                Glide.with(mContext)
//                                        .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                        .placeholder(R.color.cl_default)
//                                        .error(R.color.cl_error)
//                                        .into(ivThumbnail);
//                            }
//                        })
                        .setPlaySource(list)
//                        .setChargeTie(true,60)
                        .startPlay();
            }
                break;
            case R.id.zuo_Click:
//                /**竖屏直播播放器*/
//                startActivity(PlayerLiveActivity.class);
            {
                list = new ArrayList<VideoijkBean>();
                //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                String url1 = "http://ivi.bupt.edu.cn/hls/cctv3.m3u8";
                String url2 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";
                VideoijkBean m1 = new VideoijkBean();
                m1.setStream("原始视频");
                m1.setUrl(url1);
                VideoijkBean m2 = new VideoijkBean();
                m2.setStream("融合视频");
                m2.setUrl(url2);
                list.add(m1);
                list.add(m2);
                player = new PlayerView(this, rootView) {
                    @Override
                    public PlayerView toggleProcessDurationOrientation() {
//                        hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
                    }

                    @Override
                    public PlayerView setPlaySource(List<VideoijkBean> list) {
                        return super.setPlaySource(list);
                    }
                }
                        .setTitle("左摄像")
                        .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                        .setScaleType(PlayStateParams.fillparent)
                        .forbidTouch(false)
                        .hideSteam(false)
                        .hideRotation(true) //隐藏旋转按钮
                        .hideCenterPlayer(true)
                        .setChargeTie(true,480)
//                        .showThumbnail(new OnShowThumbnailListener() {
//                            @Override
//                            public void onShowThumbnail(ImageView ivThumbnail) {
//                                Glide.with(mContext)
//                                        .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                        .placeholder(R.color.cl_default)
//                                        .error(R.color.cl_error)
//                                        .into(ivThumbnail);
//                            }
//                        })
                        .setPlaySource(list)
//                        .setChargeTie(true,60)
                        .startPlay();
            }
                break;
            case R.id.you_Click:
//                /**ijkplayer原生的播放器*/
//                startActivity(OriginPlayerActivity.class);
            {
                list = new ArrayList<VideoijkBean>();
                //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                String url1 = "http://ivi.bupt.edu.cn/hls/cctv4.m3u8";
                String url2 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";
                VideoijkBean m1 = new VideoijkBean();
                m1.setStream("原始视频");
                m1.setUrl(url1);
                VideoijkBean m2 = new VideoijkBean();
                m2.setStream("融合视频");
                m2.setUrl(url2);
                list.add(m1);
                list.add(m2);
                player = new PlayerView(this, rootView) {
                    @Override
                    public PlayerView toggleProcessDurationOrientation() {
//                        hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
                    }

                    @Override
                    public PlayerView setPlaySource(List<VideoijkBean> list) {
                        return super.setPlaySource(list);
                    }
                }
                        .setTitle("右摄像")
                        .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                        .setScaleType(PlayStateParams.fillparent)
                        .forbidTouch(false)
                        .hideSteam(false)
                        .hideRotation(true) //隐藏旋转按钮
                        .hideCenterPlayer(true)
                        .setChargeTie(true,480)
//                        .showThumbnail(new OnShowThumbnailListener() {
//                            @Override
//                            public void onShowThumbnail(ImageView ivThumbnail) {
//                                Glide.with(mContext)
//                                        .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                        .placeholder(R.color.cl_default)
//                                        .error(R.color.cl_error)
//                                        .into(ivThumbnail);
//                            }
//                        })
                        .setPlaySource(list)
//                        .setChargeTie(true,60)
                        .startPlay();
            }
                break;
            case R.id.shangdi_Click:
//                /**半屏播放器*/
//                startActivity(HPlayerActivity.class);
            {
                list = new ArrayList<VideoijkBean>();
                //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                String url1 = "http://ivi.bupt.edu.cn/hls/cctv7.m3u8";
                String url2 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";
                VideoijkBean m1 = new VideoijkBean();
                m1.setStream("原始视频");
                m1.setUrl(url1);
                VideoijkBean m2 = new VideoijkBean();
                m2.setStream("融合视频");
                m2.setUrl(url2);
                list.add(m1);
                list.add(m2);
                player = new PlayerView(this, rootView) {
                    @Override
                    public PlayerView toggleProcessDurationOrientation() {
                        hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
                    }

                    @Override
                    public PlayerView setPlaySource(List<VideoijkBean> list) {
                        return super.setPlaySource(list);
                    }
                }
                        .setTitle("上帝视角")
                        .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                        .setScaleType(PlayStateParams.fillparent)
                        .forbidTouch(false)
                        .hideMenu(false)
                        .hideSteam(true)
                        .hideRotation(true) //隐藏旋转按钮
                        .hideCenterPlayer(true)
                        .setChargeTie(true,480)
//                        .showThumbnail(new OnShowThumbnailListener() {
//                            @Override
//                            public void onShowThumbnail(ImageView ivThumbnail) {
//                                Glide.with(mContext)
//                                        .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                        .placeholder(R.color.cl_default)
//                                        .error(R.color.cl_error)
//                                        .into(ivThumbnail);
//                            }
//                        })
                        .setPlaySource(list)
//                        .setChargeTie(true,60)
                        .startPlay();
            }
            break;
        }
    }

//    private void startActivity(Class<?> cls) {
//        Intent intent = new Intent(MainActivity.this, cls);
//        startActivity(intent);
//    }



    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, false);
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }



}