package com.application.granthavikri.ui;

import com.application.granthavikri.model.StockModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData(List<StockModel> stockModelArrayList) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> books = new ArrayList<String>();

        List<String> cds = new ArrayList<String>();

        List<String> frames = new ArrayList<String>();

        for(StockModel stockModel: stockModelArrayList){
            if(stockModel.getType().equals("Book")){
                books.add(stockModel.getStock_name());
            }
            else if (stockModel.getType().equals("CD")){
                cds.add(stockModel.getStock_name());
            }
            else if(stockModel.getType().equals("Frame")){
                frames.add(stockModel.getStock_name());
            }
        }

        expandableListDetail.put("BOOKS", books);
        expandableListDetail.put("CDS", cds);
        expandableListDetail.put("FRAMES", frames);
        return expandableListDetail;
    }
}
