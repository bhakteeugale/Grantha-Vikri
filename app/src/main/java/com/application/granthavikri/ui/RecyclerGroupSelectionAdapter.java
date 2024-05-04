package com.application.granthavikri.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.granthavikri.R;
import com.application.granthavikri.model.StockModel;

import java.util.ArrayList;

public class RecyclerGroupSelectionAdapter extends RecyclerView.Adapter<RecyclerGroupSelectionAdapter.ViewHolder> {

    Context context;

    ArrayList<StockModel> stockModelArrayList;
    public RecyclerGroupSelectionAdapter(Context context, ArrayList<StockModel> stockModelArrayList) {
        this.context = context;
        this.stockModelArrayList = stockModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item_selection_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StockModel data = stockModelArrayList.get(position);
        String type = "Type";
        if(data!=null && !data.getType().isEmpty()) type = data.getType();
        holder.type.setText(type);
        holder.name.setText(data.getStock_name());
    }

    @Override
    public int getItemCount() {
        return stockModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView type, name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_txt);
            name = itemView.findViewById(R.id.name_txt);
        }
    }
}
