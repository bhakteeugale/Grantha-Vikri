package com.application.granthavikri.ui;

import android.annotation.SuppressLint;
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
import com.application.granthavikri.model.EventModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerEventAdapter extends RecyclerView.Adapter<RecyclerEventAdapter.EventViewHolder>{

    Context context;

    List<EventModel> eventArrayList;

    Utility.OnItemClickListener listener;

    public RecyclerEventAdapter(Context context, List<EventModel> eventArrayList, Utility.OnItemClickListener listener) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card, parent, false);
        EventViewHolder viewHolder = new EventViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EventModel data = eventArrayList.get(position);
        if(data.getEvent_name()!=null) holder.event_name_text_view.setText(data.getEvent_name());
        if(data.getEvent_date()!=null) holder.event_date_textview.setText(Utility.setFormat(data.getEvent_date().toDate()));
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        TextView event_name_text_view, event_date_textview;

        LinearLayout click;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name_text_view = itemView.findViewById(R.id.event_title);
            event_date_textview = itemView.findViewById(R.id.event_date);
            click = itemView.findViewById(R.id.clickable_event_card_layout);
        }
    }

    public void setData(List<EventModel> eventModelList){
        this.eventArrayList = eventModelList;
        notifyDataSetChanged();
    }
}
