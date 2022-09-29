package com.example.gmworkspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gmworkspace.helper.Constant;
import com.example.gmworkspace.helper.Session;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {


    MaterialCardView btnTimesheet;
    Activity activity;
    ImageView imgStatus,imgLogout;
    Session session;
    boolean present = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        session = new Session(activity);

        btnTimesheet = findViewById(R.id.btnTimesheet);
        imgStatus = findViewById(R.id.imgStatus);
        imgLogout = findViewById(R.id.imgLogout);

        if (session.getBoolean(Constant.PRESENT)){
            imgStatus.setBackgroundResource(R.drawable.unselectpresent);
            imgStatus.setImageResource(R.drawable.smile);

        }else {
            imgStatus.setBackgroundResource(R.drawable.unselectaway);
            imgStatus.setImageResource(R.drawable.sad);


        }
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser(activity);
            }
        });

        btnTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,TimesheetActivity.class);
                startActivity(intent);
            }
        });
        imgStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
                bottomSheetDialog.setContentView(R.layout.bottomsheet_lyt);
                bottomSheetDialog.show();


                Slider sliderHours = bottomSheetDialog.findViewById(R.id.sliderHours);
                TextView tvHour = bottomSheetDialog.findViewById(R.id.tvHour);
                ImageView imgPresent = bottomSheetDialog.findViewById(R.id.imgPresent);
                ImageView imgAway = bottomSheetDialog.findViewById(R.id.imgAway);
                LinearLayout lslide = bottomSheetDialog.findViewById(R.id.lslide);
                TextView btnDone = bottomSheetDialog.findViewById(R.id.btnDone);

                if (session.getBoolean(Constant.PRESENT)){
                    present = true;
                    lslide.setVisibility(View.GONE);
                    imgPresent.setBackgroundResource(R.drawable.selectpresent);
                    imgAway.setBackgroundResource(R.drawable.unselectaway);

                }else {
                    present = false;
                    lslide.setVisibility(View.VISIBLE);
                    imgPresent.setBackgroundResource(R.drawable.unselectpresent);
                    imgAway.setBackgroundResource(R.drawable.selectaway);


                }
                sliderHours.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                    @Override
                    public void onStartTrackingTouch(@NonNull Slider slider) {

                    }

                    @Override
                    public void onStopTrackingTouch(@NonNull Slider slider) {

                        float value = sliderHours.getValue();
                        int i =  (int) value;
                        tvHour.setText(  i+"");
                    }
                });

                imgPresent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        present = true;
                        lslide.setVisibility(View.GONE);
                        imgPresent.setBackgroundResource(R.drawable.selectpresent);
                        imgAway.setBackgroundResource(R.drawable.unselectaway);
                    }
                });
                imgAway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        present = false;
                        lslide.setVisibility(View.VISIBLE);
                        imgPresent.setBackgroundResource(R.drawable.unselectpresent);
                        imgAway.setBackgroundResource(R.drawable.selectaway);
                    }
                });
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        if (present){
                            session.setBoolean(Constant.PRESENT,true);
                            imgStatus.setBackgroundResource(R.drawable.unselectpresent);
                            imgStatus.setImageResource(R.drawable.smile);
                        }else {
                            session.setBoolean(Constant.PRESENT,false);
                            imgStatus.setBackgroundResource(R.drawable.unselectaway);
                            imgStatus.setImageResource(R.drawable.sad);

                        }
                    }
                });

            }
        });



    }
}