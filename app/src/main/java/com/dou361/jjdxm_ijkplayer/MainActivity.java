package com.dou361.jjdxm_ijkplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import androidx.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dou361.jjdxm_ijkplayer.autopark.AutoPark;
import com.dou361.jjdxm_ijkplayer.remotecontrol.RemoteControlInitial;
import com.dou361.jjdxm_ijkplayer.videomonitoring.VideoMonitor;

import butterknife.OnClick;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button button_video,button_prak,button_call,button_hand_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

        button_video= (Button)findViewById(R.id.video);
        button_prak = (Button)findViewById(R.id.au_park);
        button_call= (Button)findViewById(R.id.call);
        button_hand_remove= (Button)findViewById(R.id.RemoteControl_remove);
        button_video.setOnClickListener(this);
        button_prak.setOnClickListener(this);
        button_call.setOnClickListener(this);
        button_hand_remove.setOnClickListener(this);
    }


    @OnClick({R.id.video, R.id.au_park, R.id.call,R.id.RemoteControl_remove})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video:
                /**视频监控*/
                startActivity(VideoMonitor.class);
                break;
            case R.id.RemoteControl_remove:
                /**遥控挪车*/
                startActivity(RemoteControlInitial.class);
                break;
            case R.id.au_park:
                /**自动出泊车*/
                startActivity(AutoPark.class);
                break;

//            case R.id.call:
//                /**叫车*/
//                startActivity(PlayerActivity.class);
//                break;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
