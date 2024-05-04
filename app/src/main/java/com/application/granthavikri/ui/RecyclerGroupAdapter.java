package com.application.granthavikri.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.GroupModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerGroupAdapter extends RecyclerView.Adapter<RecyclerGroupAdapter.ViewHolder> {

    Context context;

    List<GroupModel> groupArrayList;

    private Utility.OnItemClickListener listener;

    public RecyclerGroupAdapter(Context context, List<GroupModel> groupArrayList, Utility.OnItemClickListener listener) {
        this.context = context;
        this.groupArrayList = groupArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.group_title.setText(groupArrayList.get(position).getGroup_name());
        holder.view_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView group_title;

        LinearLayout view_more_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            group_title = itemView.findViewById(R.id.group_title);
            view_more_btn = itemView.findViewById(R.id.clickable_group_card_layout);
        }
    }

    // TODO: consider using DiffUtil
    public void setData(List<GroupModel> groupModelList){
        this.groupArrayList = groupModelList;
        notifyDataSetChanged();
    }
}
