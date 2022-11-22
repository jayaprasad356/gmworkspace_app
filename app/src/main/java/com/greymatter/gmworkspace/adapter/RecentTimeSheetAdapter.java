package com.greymatter.gmworkspace.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greymatter.gmworkspace.R;
import com.greymatter.gmworkspace.model.ProjectInfoModel;
import com.greymatter.gmworkspace.model.RecentTimeSheetModel;

import java.util.ArrayList;

public class RecentTimeSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<RecentTimeSheetModel> recentTimeSheetModelArrayList;
    public RecentTimeSheetAdapter(Activity activity, ArrayList<RecentTimeSheetModel> recentTimeSheetModelArrayList) {
        this.activity = activity;
        this.recentTimeSheetModelArrayList = recentTimeSheetModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recenttimesheetlyt, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {


        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final  RecentTimeSheetModel recentTimeSheetModel = recentTimeSheetModelArrayList.get(position);

        holder.ProjectName.setText(recentTimeSheetModel.getName());
        holder.ProjectDes.setText(recentTimeSheetModel.getDescription());
        holder.ProjectStatus.setText(recentTimeSheetModel.getStatus());
        holder.ProjectWorkedHours.setText(recentTimeSheetModel.getHours());
        holder.ProjectDate.setText(recentTimeSheetModel.getDate());

        if (recentTimeSheetModel.getStatus().equals("1") ){

            holder.ProjectStatus.setText("Verified");
        }
        else {
            holder.ProjectStatus.setText("Pending");
        }

    }

    @Override
    public int getItemCount() {
        return recentTimeSheetModelArrayList.size();
    }

     static class ExploreItemHolder extends RecyclerView.ViewHolder
     {
         final TextView ProjectName,ProjectDes,ProjectStatus,ProjectWorkedHours,ProjectDate;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            ProjectName = itemView.findViewById(R.id.tvProjectName);
            ProjectDes = itemView.findViewById(R.id.tvProjectDescription);
            ProjectStatus = itemView.findViewById(R.id.tvVerificationStatus);
            ProjectWorkedHours = itemView.findViewById(R.id.tvProjectWorkedHours);
            ProjectDate = itemView.findViewById(R.id.tvProjectWorkedDate);
        }
    }
}
