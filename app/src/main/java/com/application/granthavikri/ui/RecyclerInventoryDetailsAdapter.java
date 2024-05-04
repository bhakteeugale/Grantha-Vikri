package com.application.granthavikri.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.granthavikri.R;
import com.application.granthavikri.model.EventStockModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerInventoryDetailsAdapter extends RecyclerView.Adapter<RecyclerInventoryDetailsAdapter.ViewHolder> {

    private Context context;

    private List<EventStockModel> eventStockModels;


    public RecyclerInventoryDetailsAdapter(Context context, List<EventStockModel> eventStockModels) {
        this.context = context;
        this.eventStockModels = eventStockModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_cell_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventStockModel data = eventStockModels.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return eventStockModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stock_name, stock_quantity_available, stock_quantity_sold, stock_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stock_price = itemView.findViewById(R.id.stock_price);
            stock_quantity_sold = itemView.findViewById(R.id.stock_quantity_sold);
            stock_quantity_available = itemView.findViewById(R.id.stock_quantity_available);
            stock_name = itemView.findViewById(R.id.stock_name);
        }

        public void bind(EventStockModel eventStockModel){
            stock_name.setText(eventStockModel.getStock().getStock_name());
            stock_price.setText(""+eventStockModel.getPrice());
            stock_quantity_available.setText(""+eventStockModel.getQuantity_available());
            stock_quantity_sold.setText(""+eventStockModel.getQuantity_sold());
        }
    }

    public void setData(List<EventStockModel> eventStockModels){
        this.eventStockModels = eventStockModels;
        notifyDataSetChanged();
    }
}
