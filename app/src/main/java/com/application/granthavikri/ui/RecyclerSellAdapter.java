package com.application.granthavikri.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.granthavikri.R;
import com.application.granthavikri.model.SaleItemModel;
import com.application.granthavikri.model.SaleModel;
import com.application.granthavikri.model.UpdateEventItemsModel;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerSellAdapter extends RecyclerView.Adapter<RecyclerSellAdapter.ViewHolder> {
    private Context context;

    private List<SaleItemModel> saleItemModelList;

    public RecyclerSellAdapter(Context context, List<SaleItemModel> saleItemModelList) {
        this.context = context;
        this.saleItemModelList = saleItemModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sell_items_cell_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaleItemModel data = saleItemModelList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return saleItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item_name, item_type, amount_of_current_item, item_count, total_amount_for_this_sale;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_count = itemView.findViewById(R.id.item_count);
            item_name = itemView.findViewById(R.id.item_name);
            item_type = itemView.findViewById(R.id.item_type);
            amount_of_current_item = itemView.findViewById(R.id.amount_of_current_item);
            total_amount_for_this_sale = itemView.findViewById(R.id.total_amount_for_this_sale);
        }

        public void bind(SaleItemModel data){
            item_count.setText(""+data.getQuantity_sold());
            item_name.setText(data.getName());
            item_type.setText(data.getType());
            amount_of_current_item.setText(""+data.getAmount());
            total_amount_for_this_sale.setText(""+data.getTotal_amount());
        }
    }

    public void setData(List<SaleItemModel> saleItemModelList){
        this.saleItemModelList = saleItemModelList;
        notifyDataSetChanged();
    }
}
