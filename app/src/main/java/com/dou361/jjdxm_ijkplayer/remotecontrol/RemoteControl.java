package com.dou361.jjdxm_ijkplayer.remotecontrol;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnPlayerBackListener;
import com.dou361.ijkplayer.listener.OnPlayerStartOrPauseListener;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.dou361.jjdxm_ijkplayer.R;
import com.dou361.jjdxm_ijkplayer.videomonitoring.VideoMonitor;

import java.util.ArrayList;
import java.util.List;


//I added a line here by Github
//I added this line by Android Studio

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) by style.xml.
 */
public class RemoteControl extends Activity {

    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list;
    private PowerManager.WakeLock wakeLock;
    private View rootView;
    private Activity mActivity;

    private static final String TAG = "FullscreenActivity";
    private boolean Flag = false;
    private int flagmove = 1;
    private int flagget = 1;
    private Button LlightingButton;
    private ImageButton imageButton_forward,imageButton_backward;
    private ImageView app_video_play;
    private Spinner Video_Modul_Spinner;
    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.mActivity = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_remote_control, null);
        setContentView(rootView);


        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();


        imageButton_forward=findViewById(R.id.forward);
        imageButton_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Forward");
            }
        });

        imageButton_backward=findViewById(R.id.backward);
        imageButton_backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.d(TAG, "onTouch: backward");
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "onTouch: backwarding");
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouch: Up");
                }
                return true;
            }
        });



        //下拉单选按钮
        Video_Modul_Spinner = (Spinner)findViewById(R.id.Spinner_VIdeo_Model);
        Video_Modul_Spinner.setSelection(0);//进入不会自动播放
        Video_Modul_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    String result = parent.getItemAtPosition(position).toString();
//                    Toast.makeText(RemoteControl.this, result, Toast.LENGTH_SHORT).show();
                    switch (position) {
                        case 0: {
                            /**前摄像*/
                            list = new ArrayList<VideoijkBean>();
                            //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                            //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                            String url1 = "rtmp://202.69.69.180:443/webcast/bshdlive-pc";
                            String url2 = "http://ivi.bupt.edu.cn/hls/cctv1.m3u8";
                            VideoijkBean m1 = new VideoijkBean();
                            m1.setStream("原始视频");
                            m1.setUrl(url1);
                            VideoijkBean m2 = new VideoijkBean();
                            m2.setStream("融合视频");
                            m2.setUrl(url2);
                            list.add(m1);
                            list.add(m2);
                            player = new PlayerView(mActivity, rootView)
                                    .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                                    .setScaleType(PlayStateParams.fillparent) //视频界面剪裁设置
                                    .forbidTouch(false)
                                    .hideSteam(false)
                                    .hideMenu(false)
                                    .hideCenterPlayer(true)
                                    .hideBack(false)
                                    .setOnlyFullScreen(true)
                                    .setNetWorkTypeTie(false)
                                    .hideRotation(true)
                                    .hideFullscreen(true)
                                    .setChargeTie(true, 480)//设置最长播放时间
                                    .showThumbnail(new OnShowThumbnailListener() {
                                        @Override
                                        public void onShowThumbnail(ImageView ivThumbnail) {
//                                 加载前显示的缩略图
                                            Glide.with(mContext)
                                                    .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                                    .placeholder(R.color.cl_default) //加载成功之前占位图
                                                    .error(R.color.cl_error)//加载错误之后的错误图
                                                    .into(ivThumbnail);
                                        }
                                    })
                                    .setPlayerBackListener(new OnPlayerBackListener() {
                            @Override
                            public void onPlayerBack() {
                                //对话框
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                builder.setIcon(R.drawable.shangbackground);
                                builder.setTitle("结束挪车");//设置对话框的标题
                                builder.setMessage("您已确定车辆已经抵达目标位置并结束挪车操作吗？");//设置对话框的内容
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                        Intent intent=new Intent(RemoteControl.this, VideoMonitor.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(RemoteControl.this, "取消成功",Toast.LENGTH_SHORT).show();
                                        player.startPlay();
                                    }
                                });
                                AlertDialog b=builder.create();
                                b.show();
                                player.onPause();

                            }
                        })
                                    .setPlayerStartOrPauseListener(new OnPlayerStartOrPauseListener() {
                                        @Override
                                        public void onStartOrPause() {
                                            //对话框
                                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                            builder.setIcon(R.drawable.shangbackground);
                                            builder.setTitle("暂停挪车");//设置对话框的标题
                                            builder.setMessage("挪车已暂停，是否继续挪车？");//设置对话框的内容
                                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    player.startPlay();
                                                }
                                            });
                                            builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {  //取消按钮

                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finish();
                                                    Intent intent=new Intent(RemoteControl.this, VideoMonitor.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();
                                        }
                                    })
                                    .setPlaySource(list)
                                    .startPlay();
                        }
                        break;

                        case 1: {
                            /**后摄像*/
                            String url3 = "http://ivi.bupt.edu.cn/hls/cctv2.m3u8";
                            player = new PlayerView(mActivity, rootView)
                                    .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                                    .setScaleType(PlayStateParams.fillparent) //视频界面剪裁设置
                                    .forbidTouch(false)
                                    .hideSteam(true)
                                    .hideMenu(false)
                                    .hideCenterPlayer(true)
                                    .hideBack(false)
                                    .setOnlyFullScreen(true)
                                    .setNetWorkTypeTie(false)
                                    .hideRotation(true)
                                    .hideFullscreen(true)
                                    .setChargeTie(true, 480)//设置最长播放时间
                                    .showThumbnail(new OnShowThumbnailListener() {
                                        @Override
                                        public void onShowThumbnail(ImageView ivThumbnail) {
//                                 加载前显示的缩略图
                                            Glide.with(mContext)
                                                    .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                                    .placeholder(R.color.cl_default) //加载成功之前占位图
                                                    .error(R.color.cl_error)//加载错误之后的错误图
                                                    .into(ivThumbnail);
                                        }
                                    })
                                    .setPlayerBackListener(new OnPlayerBackListener() {
                            @Override
                            public void onPlayerBack() {
                                //对话框
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                builder.setIcon(R.drawable.shangbackground);
                                builder.setTitle("结束挪车");//设置对话框的标题
                                builder.setMessage("您已确定车辆已经抵达目标位置并结束挪车操作吗？");//设置对话框的内容
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                        Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(RemoteControl.this, "取消成功",Toast.LENGTH_SHORT).show();
                                        player.startPlay();
                                    }
                                });
                                AlertDialog b=builder.create();
                                b.show();
                                player.onPause();
                            }
                        })
                                    .setPlayerStartOrPauseListener(new OnPlayerStartOrPauseListener() {
                                        @Override
                                        public void onStartOrPause() {
                                            //对话框
                                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                            builder.setIcon(R.drawable.shangbackground);
                                            builder.setTitle("暂停挪车");//设置对话框的标题
                                            builder.setMessage("挪车已暂停，是否继续挪车？");//设置对话框的内容
                                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    player.startPlay();
                                                }
                                            });
                                            builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {  //取消按钮

                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finish();
                                                    Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();
                                        }
                                    })
                                    .setPlaySource(url3)
                                    .startPlay();

                        }
                        break;

                        case 2: {
                            /**左摄像*/
                            //有部分视频加载有问题，这个视频是有声音显示不出图像的，没有解决http://fzkt-biz.oss-cn-hangzhou.aliyuncs.com/vedio/2f58be65f43946c588ce43ea08491515.mp4
                            //这里模拟一个本地视频的播放，视频需要将testvideo文件夹的视频放到安卓设备的内置sd卡根目录中
                            String url4 = "http://ivi.bupt.edu.cn/hls/cctv3.m3u8";
                            player = new PlayerView(mActivity, rootView)
                                    .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                                    .setScaleType(PlayStateParams.fillparent) //视频界面剪裁设置
                                    .forbidTouch(false)
                                    .hideSteam(true)
                                    .hideMenu(false)
                                    .hideCenterPlayer(true)
                                    .hideBack(false)
                                    .setOnlyFullScreen(true)
                                    .setNetWorkTypeTie(false)
                                    .hideRotation(true)
                                    .hideFullscreen(true)
                                    .setChargeTie(true, 480)//设置最长播放时间
                                    .showThumbnail(new OnShowThumbnailListener() {
                                        @Override
                                        public void onShowThumbnail(ImageView ivThumbnail) {
//                                 加载前显示的缩略图
                                            Glide.with(mContext)
                                                    .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                                    .placeholder(R.color.cl_default) //加载成功之前占位图
                                                    .error(R.color.cl_error)//加载错误之后的错误图
                                                    .into(ivThumbnail);
                                        }
                                    })
                                    .setPlayerBackListener(new OnPlayerBackListener() {
                            @Override
                            public void onPlayerBack() {
                                //对话框
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                builder.setIcon(R.drawable.shangbackground);
                                builder.setTitle("结束挪车");//设置对话框的标题
                                builder.setMessage("您已确定车辆已经抵达目标位置并结束挪车操作吗？");//设置对话框的内容
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                        Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(RemoteControl.this, "取消成功",Toast.LENGTH_SHORT).show();
                                        player.startPlay();
                                    }
                                });
                                AlertDialog b=builder.create();
                                b.show();
                                player.onPause();
                            }
                        })
                                    .setPlayerStartOrPauseListener(new OnPlayerStartOrPauseListener() {
                                        @Override
                                        public void onStartOrPause() {
                                            //对话框
                                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                            builder.setIcon(R.drawable.shangbackground);
                                            builder.setTitle("暂停挪车");//设置对话框的标题
                                            builder.setMessage("挪车已暂停，是否继续挪车？");//设置对话框的内容
                                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    player.startPlay();
                                                }
                                            });
                                            builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {  //取消按钮

                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finish();
                                                    Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();
                                        }
                                    })
                                    .setPlaySource(url4)
                                    .startPlay();
                        }
                        break;

                        case 3: {
                            /**右摄像*/
                            String url5 = "http://ivi.bupt.edu.cn/hls/cctv4.m3u8";
                            player = new PlayerView(mActivity, rootView)
                                    .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                                    .setScaleType(PlayStateParams.fillparent) //视频界面剪裁设置
                                    .forbidTouch(false)
                                    .hideSteam(true)
                                    .hideMenu(false)
                                    .hideCenterPlayer(true)
                                    .hideBack(false)
                                    .setOnlyFullScreen(true)
                                    .setNetWorkTypeTie(false)
                                    .hideRotation(true)
                                    .hideFullscreen(true)
                                    .setChargeTie(true, 480)//设置最长播放时间
                                    .showThumbnail(new OnShowThumbnailListener() {
                                        @Override
                                        public void onShowThumbnail(ImageView ivThumbnail) {
//                                 加载前显示的缩略图
                                            Glide.with(mContext)
                                                    .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                                    .placeholder(R.color.cl_default) //加载成功之前占位图
                                                    .error(R.color.cl_error)//加载错误之后的错误图
                                                    .into(ivThumbnail);
                                        }
                                    })
                                    .setPlayerBackListener(new OnPlayerBackListener() {
                            @Override
                            public void onPlayerBack() {
                                //对话框
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                builder.setIcon(R.drawable.shangbackground);
                                builder.setTitle("结束挪车");//设置对话框的标题
                                builder.setMessage("您已确定车辆已经抵达目标位置并结束挪车操作吗？");//设置对话框的内容
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                        Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(RemoteControl.this, "取消成功",Toast.LENGTH_SHORT).show();
                                        player.startPlay();
                                    }
                                });
                                AlertDialog b=builder.create();
                                b.show();
                                player.onPause();
                            }
                        })
                                    .setPlayerStartOrPauseListener(new OnPlayerStartOrPauseListener() {
                                        @Override
                                        public void onStartOrPause() {
                                            //对话框
                                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                            builder.setIcon(R.drawable.shangbackground);
                                            builder.setTitle("暂停挪车");//设置对话框的标题
                                            builder.setMessage("挪车已暂停，是否继续挪车？");//设置对话框的内容
                                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    player.startPlay();
                                                }
                                            });
                                            builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {  //取消按钮

                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finish();
                                                    Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();
                                        }
                                    })

                                    .setPlaySource(url5)
                                    .startPlay();

                        }
                        break;

                        case 4: {
                            /**上帝*/

                            String url6 = "http://ivi.bupt.edu.cn/hls/cctv13.m3u8";
                            player = new PlayerView(mActivity, rootView)
                                    .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                                    .setScaleType(PlayStateParams.fillparent) //视频界面剪裁设置
                                    .forbidTouch(false)
                                    .hideSteam(true)
                                    .hideMenu(false)
                                    .hideCenterPlayer(true)
                                    .hideBack(false)
                                    .setOnlyFullScreen(true)
                                    .setNetWorkTypeTie(false)
                                    .hideRotation(true)
                                    .hideFullscreen(true)
                                    .setChargeTie(true, 480)//设置最长播放时间
                                    .showThumbnail(new OnShowThumbnailListener() {
                                        @Override
                                        public void onShowThumbnail(ImageView ivThumbnail) {
//                                 加载前显示的缩略图
                                            Glide.with(mContext)
                                                    .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                                    .placeholder(R.color.cl_default) //加载成功之前占位图
                                                    .error(R.color.cl_error)//加载错误之后的错误图
                                                    .into(ivThumbnail);
                                        }
                                    })
                                    .setPlayerBackListener(new OnPlayerBackListener() {
                            @Override
                            public void onPlayerBack() {
                                //对话框
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                builder.setIcon(R.drawable.shangbackground);
                                builder.setTitle("结束挪车");//设置对话框的标题
                                builder.setMessage("您已确定车辆已经抵达目标位置并结束挪车操作吗？");//设置对话框的内容
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                        Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(RemoteControl.this, "取消成功",Toast.LENGTH_SHORT).show();
                                        player.startPlay();
                                    }
                                });
                                AlertDialog b=builder.create();
                                b.show();
                                player.onPause();
                            }
                        })
                                    .setPlayerStartOrPauseListener(new OnPlayerStartOrPauseListener() {
                                        @Override
                                        public void onStartOrPause() {
                                            //对话框
                                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                                            builder.setIcon(R.drawable.shangbackground);
                                            builder.setTitle("暂停挪车");//设置对话框的标题
                                            builder.setMessage("挪车已暂停，是否继续挪车？");//设置对话框的内容
                                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    player.startPlay();
                                                }
                                            });
                                            builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {  //取消按钮

                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    finish();
                                                    Intent intent=new Intent(RemoteControl.this,VideoMonitor.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            AlertDialog b=builder.create();
                                            b.show();
                                        }
                                    })
                                    .setPlaySource(url6)
                                    .startPlay();

                        }
                        break;

                        default:
                            break;
                    }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
}
