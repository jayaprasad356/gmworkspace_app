package com.greymatter.gmworkspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.greymatter.gmworkspace.adapter.ProjectInfoAdapter;
import com.greymatter.gmworkspace.model.ProjectInfoModel;

import java.util.ArrayList;

public class ProjectInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProjectInfoAdapter projectInfoAdapter;
    private ArrayList<ProjectInfoModel> projectInfoModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        projectInfoModelArrayList = new ArrayList<>();
        projectInfoModelArrayList.add((new ProjectInfoModel(R.drawable.two,R.drawable.one,"GoldPlus","SOme description" +
                " about the project SOme description about the project SOme description about the project SOme description about the project")));
        projectInfoModelArrayList.add((new ProjectInfoModel(R.color.white,R.color.green,"Gurug","SOme description about the project SOme description about the project SOme description about the project SOme description about the project")));
        projectInfoModelArrayList.add((new ProjectInfoModel(R.color.white,R.color.green,"Moi","SOme description about the project SOme description about the project SOme description about the project SOme description about the project")));
        projectInfoModelArrayList.add((new ProjectInfoModel(R.color.white,R.color.green,"Telugu Calender","SOme description about the project SOme description about the project SOme description about the project SOme description about the project")));

        projectInfoAdapter = new ProjectInfoAdapter(this,projectInfoModelArrayList);
        recyclerView.setAdapter(projectInfoAdapter);
    }
}