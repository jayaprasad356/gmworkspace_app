package com.greymatter.gmworkspace.adapter;


import android.app.Activity;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greymatter.gmworkspace.R;
import com.greymatter.gmworkspace.model.ProjectInfoModel;
import com.greymatter.gmworkspace.model.Staff;

import java.util.ArrayList;

public class ProjectInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<ProjectInfoModel> projectInfoModelArrayList;
    public ProjectInfoAdapter(Activity activity, ArrayList<ProjectInfoModel> projectInfoModelArrayList) {
        this.activity = activity;
        this.projectInfoModelArrayList = projectInfoModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.projectinfomodel, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ProjectInfoModel model = projectInfoModelArrayList.get(position);
        final RecyclerView.ViewHolder holder = (ExploreItemHolder) holderParent;
        Glide.with(activity).load(model.getLeadImage()).into(((ExploreItemHolder) holderParent).LeadProfile);
        Glide.with(activity).load(model.getDeveloperImage()).into(((ExploreItemHolder) holderParent).DevProfile);
        ((ExploreItemHolder) holderParent).Title.setText(model.getProjectTile());
        ((ExploreItemHolder) holderParent).Description.setText(model.getProjectDescription());
    }

    @Override
    public int getItemCount() {
        return projectInfoModelArrayList.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final ImageView LeadProfile,DevProfile;
        final TextView Title,Description;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            LeadProfile = itemView.findViewById(R.id.imgLead);
            DevProfile = itemView.findViewById(R.id.imgDev);
            Title = itemView.findViewById(R.id.tvTitle);
            Description = itemView.findViewById(R.id.tvDescription);
        }
    }
}
