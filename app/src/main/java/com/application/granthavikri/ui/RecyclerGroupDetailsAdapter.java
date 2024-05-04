package com.application.granthavikri.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.granthavikri.R;
import com.application.granthavikri.model.EventGroupModel;
import java.util.List;

public class RecyclerGroupDetailsAdapter extends RecyclerView.Adapter<RecyclerGroupDetailsAdapter.ViewHolder> {

    private List<EventGroupModel> eventGroupModelList;
    private Context context;

    public RecyclerGroupDetailsAdapter(List<EventGroupModel> eventGroupModelList, Context context) {
        this.eventGroupModelList = eventGroupModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_cell_layout_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventGroupModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventGroupModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView group_name, group_quantity_available, group_quantity_sold;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            group_name = itemView.findViewById(R.id.group_name);
            group_quantity_available = itemView.findViewById(R.id.group_quantity_available);
            group_quantity_sold = itemView.findViewById(R.id.group_quantity_sold);
        }

        public void bind(EventGroupModel groupModel){
            group_name.setText(groupModel.getGroup_name());
            group_quantity_sold.setText(""+groupModel.getQuantity_sold());
            group_quantity_available.setText(""+groupModel.getQuantity_available());
        }
    }

    public void setData(List<EventGroupModel> eventGroupModelList){
        this.eventGroupModelList = eventGroupModelList;
        notifyDataSetChanged();
    }
}
