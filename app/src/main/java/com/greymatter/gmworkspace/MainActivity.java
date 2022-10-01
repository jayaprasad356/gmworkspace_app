package com.greymatter.gmworkspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.gmworkspace.adapter.StaffAdapter;
import com.greymatter.gmworkspace.helper.ApiConfig;
import com.greymatter.gmworkspace.helper.Constant;
import com.greymatter.gmworkspace.helper.Session;
import com.greymatter.gmworkspace.model.Staff;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    MaterialCardView btnTimesheet;
    Activity activity;
    ImageView imgStatus,imgLogout;
    Session session;
    boolean present = true;
    TextView tvTodayHours,tvMonthHours;
    RecyclerView recyclerView;
    StaffAdapter staffAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        session = new Session(activity);

        recyclerView = findViewById(R.id.recyclerView);
        btnTimesheet = findViewById(R.id.btnTimesheet);
        imgStatus = findViewById(R.id.imgStatus);
        imgLogout = findViewById(R.id.imgLogout);
        tvTodayHours = findViewById(R.id.tvTodayHours);
        tvMonthHours = findViewById(R.id.tvMonthHours);
        tvTodayHours.setText(session.getData(Constant.TODAY_HOURS) +" hrs");
        tvMonthHours.setText(session.getData(Constant.MONTH_HOURS) + " hrs");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,4);
        recyclerView.setLayoutManager(gridLayoutManager);
        stafflist();
        updateStatus();



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
                    //lslide.setVisibility(View.GONE);
                    imgPresent.setBackgroundResource(R.drawable.selectpresent);
                    imgAway.setBackgroundResource(R.drawable.unselectaway);

                }else {
                    present = false;
                    //lslide.setVisibility(View.VISIBLE);
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
                        //lslide.setVisibility(View.GONE);
                        imgPresent.setBackgroundResource(R.drawable.selectpresent);
                        imgAway.setBackgroundResource(R.drawable.unselectaway);
                    }
                });
                imgAway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        present = false;
                        //lslide.setVisibility(View.VISIBLE);
                        imgPresent.setBackgroundResource(R.drawable.unselectpresent);
                        imgAway.setBackgroundResource(R.drawable.selectaway);
                    }
                });
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String status = "0";
                        bottomSheetDialog.dismiss();
                        if (present){
                            status = "1";
                            session.setBoolean(Constant.PRESENT,true);
                            imgStatus.setBackgroundResource(R.drawable.unselectpresent);
                            imgStatus.setImageResource(R.drawable.smile);
                        }else {
                            status = "0";
                            session.setBoolean(Constant.PRESENT,false);
                            imgStatus.setBackgroundResource(R.drawable.unselectaway);
                            imgStatus.setImageResource(R.drawable.sad);
                        }
                        updateStatusApi(status);
                        stafflist();
                    }
                });

            }
        });
        dashboardApi();



    }

    private void stafflist()
    {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Staff> staffs = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Staff group = g.fromJson(jsonObject1.toString(), Staff.class);
                                staffs.add(group);
                            } else {
                                break;
                            }
                        }
                        staffAdapter = new StaffAdapter(activity, staffs);
                        recyclerView.setAdapter(staffAdapter);


                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.STAFFLIST_URL, params, true);



    }

    private void updateStatusApi(String status)
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.STAFF_ID,session.getData(Constant.ID));
        params.put(Constant.STATUS,status);
        ApiConfig.RequestToVolley((result, response) -> {
            if(result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        if (jsonArray.getJSONObject(0).getString(Constant.STATUS).equals("0")){
                            session.setBoolean(Constant.PRESENT,false);

                        }else {
                            session.setBoolean(Constant.PRESENT,true);

                        }
                        updateStatus();
                    }else{
                        //Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },activity,Constant.STATUS_URL,params,true);

    }

    private void updateStatus() {
        if (session.getBoolean(Constant.PRESENT)){
            imgStatus.setBackgroundResource(R.drawable.unselectpresent);
            imgStatus.setImageResource(R.drawable.smile);

        }else {
            imgStatus.setBackgroundResource(R.drawable.unselectaway);
            imgStatus.setImageResource(R.drawable.sad);


        }

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void dashboardApi()
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.STAFF_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("STAFF_RES",response);
            if(result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.TODAY_HOURS,jsonObject.getString(Constant.TODAY_HOURS));
                        session.setData(Constant.MONTH_HOURS,jsonObject.getString(Constant.MONTH_HOURS));
                        tvTodayHours.setText(session.getData(Constant.TODAY_HOURS) +" hrs");
                        tvMonthHours.setText(session.getData(Constant.MONTH_HOURS) + " hrs");
                        if (jsonArray.getJSONObject(0).getString(Constant.STATUS).equals("0")){
                            session.setBoolean(Constant.PRESENT,false);

                        }else {
                            session.setBoolean(Constant.PRESENT,true);

                        }
                        updateStatus();
                    }else{
                        //Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },activity,Constant.DASHBOARD_URL,params,true);

    }
}