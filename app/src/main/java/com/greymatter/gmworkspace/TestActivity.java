package com.greymatter.gmworkspace;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.greymatter.gmworkspace.helper.ApiConfig;
import com.greymatter.gmworkspace.helper.Constant;
import com.greymatter.gmworkspace.helper.Session;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TestActivity extends AppCompatActivity {
    private static  ArrayList<String> days = new ArrayList<>();
    private static  ArrayList<String> hours = new ArrayList<>();
    Activity activity;
    Session session;
    ValueLineSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        activity = TestActivity.this;
        session = new Session(activity);
        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);

        series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        //printDatesInMonth(2022,05);
        timesheetslist();



        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();


    }

    private void timesheetslist() {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.STAFF_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if(result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DAYS);
                        JSONArray jsonArray2 = jsonObject.getJSONArray(Constant.HOURS);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            days.add(jsonArray.get(i).toString());
                            hours.add(jsonArray2.get(i).toString());
                        }
                        for (int i = 0; i < days.size(); i++) {
                            series.addPoint(new ValueLinePoint(days.get(i), Integer.parseInt(hours.get(i))));
                        }

                    }else{
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(activity, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },activity,Constant.TIMESHEETS_SURVEY_URL,params,true);

    }

    public static void printDatesInMonth(int year, int month) {
        SimpleDateFormat fmt = new SimpleDateFormat("MMM dd,yyyy");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daysInMonth; i++) {
            days.add(fmt.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

}