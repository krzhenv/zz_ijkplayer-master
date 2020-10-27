package com.dou361.jjdxm_ijkplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import androidx.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dou361.jjdxm_ijkplayer.bean.LiveBean;
import com.dou361.jjdxm_ijkplayer.module.ApiServiceUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button button_video,button_prak,button_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

        button_video= (Button)findViewById(R.id.video);
        button_prak = (Button)findViewById(R.id.park);
        button_call= (Button)findViewById(R.id.call);
        button_video.setOnClickListener(this);
        button_prak.setOnClickListener(this);
        button_call.setOnClickListener(this);

    }


    @OnClick({R.id.video, R.id.park, R.id.call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video:
                /**视频监控*/
                startActivity(VideoMonitoring.class);
                break;
            case R.id.park:
                /**自动出泊车*/
                startActivity(Parkin.class);
                break;
//            case R.id.call:
//                /**竖屏直播播放器*/
//                startActivity(PlayerLiveActivity.class);
//                break;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
