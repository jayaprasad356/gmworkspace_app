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
import com.greymatter.gmworkspace.model.Staff;


import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Staff> staffs;

    public StaffAdapter(Activity activity, ArrayList<Staff> staffs) {
        this.activity = activity;
        this.staffs = staffs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.staff_item, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Staff staff = staffs.get(position);

        Glide.with(activity).load(staff.getImage()).placeholder(R.drawable.gmlogo).into(holder.ciProfile);
        holder.tvName.setText(staff.getName());
        if (staff.getStatus().equals("1")){
            Glide.with(activity).load(R.drawable.activecircle).placeholder(R.drawable.gmlogo).into(holder.imgStatus);

        }else {
            Glide.with(activity).load(R.drawable.inactivecircle).placeholder(R.drawable.gmlogo).into(holder.imgStatus);


        }

    }

    @Override
    public int getItemCount() {
        return staffs.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {

        final ImageView ciProfile,imgStatus;
        final TextView tvName;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            ciProfile = itemView.findViewById(R.id.ciProfile);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
}
