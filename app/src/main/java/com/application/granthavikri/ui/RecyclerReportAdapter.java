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
import com.application.granthavikri.model.EventStockModel;

import java.util.List;

public class RecyclerReportAdapter extends RecyclerView.Adapter<RecyclerReportAdapter.ViewHolder> {

    private Context context;
    private List<Object> data;

    public RecyclerReportAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object obj = data.get(position);

        if(obj instanceof EventStockModel){
            EventStockModel stock = (EventStockModel) obj;
            int ava = stock.getQuantity_available();
            int sold = stock.getQuantity_sold();
            int taken = ava+sold;
            float price = (float) stock.getPrice();
            float total = price * sold;
            holder.bind(
                    stock.getStock().getStock_name(),
                    stock.getStock().getType(),
                    price,
                    total,
                    ava,
                    sold,
                    taken
            );
        }
        else{
            EventGroupModel group = (EventGroupModel) obj;
            int ava = group.getQuantity_available();
            int sold = group.getQuantity_sold();
            int taken = ava+sold;
            float price = (float) group.getGroup_price();
            float total = price * sold;
            holder.bind(
                    group.getGroup_name(),
                    "Group",
                    price,
                    total,
                    ava,
                    sold,
                    taken
            );
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView type_for_sale, report_item_name, quantity_taken_for_event, quantity_available_before_sale, quantity_sold_after_sale, amount_of_each_report_item, total_amount_of_each_report_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type_for_sale = itemView.findViewById(R.id.type_for_sale);
            report_item_name = itemView.findViewById(R.id.report_item_name);
            quantity_taken_for_event = itemView.findViewById(R.id.quantity_taken_for_event);
            quantity_available_before_sale = itemView.findViewById(R.id.quantity_available_before_sale);
            quantity_sold_after_sale = itemView.findViewById(R.id.quantity_sold_after_sale);
            amount_of_each_report_item = itemView.findViewById(R.id.amount_of_each_report_item);
            total_amount_of_each_report_item = itemView.findViewById(R.id.total_amount_of_each_report_item);
        }

        public void bind(String name, String type, float amount, float total, int available, int sold, int taken){
            type_for_sale.setText(type);
            report_item_name.setText(name);
            quantity_taken_for_event.setText(""+taken);
            quantity_available_before_sale.setText(""+available);
            quantity_sold_after_sale.setText(""+sold);
            amount_of_each_report_item.setText(""+amount);
            total_amount_of_each_report_item.setText(""+total);
        }
    }

    public void setData(List<Object> data){
        this.data = data;
        notifyDataSetChanged();
    }
}
