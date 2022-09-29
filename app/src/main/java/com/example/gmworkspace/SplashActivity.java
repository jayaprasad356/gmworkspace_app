package com.example.gmworkspace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.gmworkspace.helper.Session;

public class SplashActivity extends AppCompatActivity {
    ImageView imgLogo;
    Handler handler;
    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgLogo = findViewById(R.id.imgLogo);
        activity = SplashActivity.this;
        session = new Session(activity);
        handler = new Handler();

        GotoActivity();

        AlphaAnimation animation = new AlphaAnimation(0.0f,0.9f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        imgLogo.setAnimation(animation);;
    }
    private void GotoActivity()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getBoolean("is_logged_in")){
                    Intent intent=new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Intent intent=new Intent(activity,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }



            }
        },2000);
    }
}