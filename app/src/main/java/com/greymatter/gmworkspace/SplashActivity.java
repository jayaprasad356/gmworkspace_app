package com.greymatter.gmworkspace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.greymatter.gmworkspace.helper.ApiConfig;
import com.greymatter.gmworkspace.helper.Constant;
import com.greymatter.gmworkspace.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    ImageView imgLogo;
    Handler handler;
    Activity activity;
    Session session;
    String link,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgLogo = findViewById(R.id.imgLogo);
        activity = SplashActivity.this;
        session = new Session(activity);
        handler = new Handler();

        checkVersion();

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
    private void checkVersion() {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        link = jsonArray.getJSONObject(0).getString(Constant.LINK);
                        description = jsonArray.getJSONObject(0).getString(Constant.DESCRIPTION);
                        String latestversion = jsonArray.getJSONObject(0).getString(Constant.VERSION);
                        String currentversion = String.valueOf(BuildConfig.VERSION_CODE);
                        if (currentversion.equals(latestversion)){
                            GotoActivity();

                        }else {
                            updateAlertDialog();
                        }
                    }
                    else {
                        Log.d("MAINACTIVITY",jsonObject.getString(Constant.MESSAGE));

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, activity, Constant.APPUPDATE_URL, params,false);

    }

    private void updateAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New update Available");
        builder.setMessage(description);
        builder.setCancelable(false);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }
}