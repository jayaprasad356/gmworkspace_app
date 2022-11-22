package com.greymatter.gmworkspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greymatter.gmworkspace.adapter.RecentTimeSheetAdapter;
import com.greymatter.gmworkspace.helper.ApiConfig;
import com.greymatter.gmworkspace.helper.Constant;
import com.greymatter.gmworkspace.helper.Session;
import com.greymatter.gmworkspace.model.RecentTimeSheetModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecentTimeSheetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecentTimeSheetAdapter recentTimeSheetAdapter;

    private Context ctx;
    ImageView imgBackbtn;
    Activity activity;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_time_sheet);
        activity = RecentTimeSheetActivity.this;
        session = new Session(activity);


        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        imgBackbtn =findViewById(R.id.imgBackbtn);

        imgBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        recentTimeSheetModelArrayList = new ArrayList<>();
//        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
//        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
//        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
//        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
//        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
//        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
//

//        recyclerView.setAdapter(recentTimeSheetAdapter);


        timesheet();
    }

    private void timesheet() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.STAFF_ID,session.getData(Constant.ID));

        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("Res",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<RecentTimeSheetModel> timeSheetModels = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                RecentTimeSheetModel group = g.fromJson(jsonObject1.toString(), RecentTimeSheetModel.class);
                                timeSheetModels.add(group);
                            } else {
                                break;
                            }
                        }

                        //important

                        recentTimeSheetAdapter = new RecentTimeSheetAdapter(activity,timeSheetModels);
                        recyclerView.setAdapter(recentTimeSheetAdapter);




                    } else {
                        Toast.makeText(activity, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.TIMESHEETS_LIST_URL, params, true);



    }


}