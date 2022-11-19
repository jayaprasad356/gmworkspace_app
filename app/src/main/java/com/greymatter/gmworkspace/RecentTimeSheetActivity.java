package com.greymatter.gmworkspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.greymatter.gmworkspace.adapter.RecentTimeSheetAdapter;
import com.greymatter.gmworkspace.model.RecentTimeSheetModel;

import java.util.ArrayList;

public class RecentTimeSheetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecentTimeSheetAdapter recentTimeSheetAdapter;
    private ArrayList<RecentTimeSheetModel> recentTimeSheetModelArrayList;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_time_sheet);
        ctx = this;
        recyclerView =findViewById(R.id.recyclerView);
        recentTimeSheetModelArrayList = new ArrayList<>();
        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
        recentTimeSheetModelArrayList.add(new RecentTimeSheetModel("Project One","Worked on Somex task and some Task","verified","16/11/2022","3"));
        recentTimeSheetAdapter = new RecentTimeSheetAdapter(this,recentTimeSheetModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        recyclerView.setAdapter(recentTimeSheetAdapter);
    }
}