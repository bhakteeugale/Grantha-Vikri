package com.application.granthavikri.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.CDModel;

import java.util.List;

public class RecyclerCDAdapter extends RecyclerView.Adapter<RecyclerCDAdapter.ViewHolder> {

    Context context;

    Utility.OnItemClickListener listener;
    List<CDModel> cdModelList;
    RecyclerCDAdapter(Context context, List<CDModel> cdModelList, Utility.OnItemClickListener listener){
        this.context = context;
        this.cdModelList = cdModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CDModel data = cdModelList.get(position);

        holder.type_view.setText("CD");
        holder.date_view.setText(Utility.setFormat(data.getDate().toDate()));
        holder.name_view.setText(data.getName());
        holder.available_quantity_view.setText(String.valueOf(data.getAvailable_quantity()));
        holder.sold_quantity_view.setText(String.valueOf(data.getSold_quantity()));

        holder.click.setOnClickListener(new View.OnClickListener() {
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
        return cdModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date_view, name_view, type_view, available_quantity_view, sold_quantity_view;

        LinearLayout click;
        public ViewHolder(View view){
            super(view);
            date_view = view.findViewById(R.id.date);
            name_view = view.findViewById(R.id.stock_name);
            type_view = view.findViewById(R.id.type_of_stock);
            available_quantity_view = view.findViewById(R.id.inventory_available);
            sold_quantity_view = view.findViewById(R.id.inventory_sold);
            click = view.findViewById(R.id.clickable_layout);
        }

    }

    // TODO: consider using DiffUtil
    public void setData(List<CDModel> cdModelList){
        this.cdModelList = cdModelList;
        notifyDataSetChanged();
    }
}
