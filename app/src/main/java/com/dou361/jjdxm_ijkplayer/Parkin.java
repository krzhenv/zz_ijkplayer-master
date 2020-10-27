package com.dou361.jjdxm_ijkplayer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import butterknife.OnClick;

public class Parkin extends Activity implements View.OnClickListener{

    ImageButton autoParking,remoteControl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkin);

//        autoParking=findViewById(R.id.autoParking);
        remoteControl=findViewById(R.id.remote_control);
//        autoParking.setOnClickListener(this);
        remoteControl.setOnClickListener(this);

    }

    @OnClick({R.id.autoParking, R.id.remote_control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remote_control:
                /**遥控泊车*/
                Intent intent1 = new Intent(Parkin.this, RemoteControlActivity.class);
                startActivity(intent1);
                break;
//            case R.id.autoParking:
//                /**竖屏播放器*/
//                Intent intent2 = new Intent(Parkin.this, RemoteControlActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.call:
//                /**竖屏直播播放器*/
//                startActivity(PlayerLiveActivity.class);
//                break;
        }
    }
}

