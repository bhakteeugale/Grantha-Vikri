package com.application.granthavikri.repository;

import static com.application.granthavikri.Utility.tag;
import static com.application.granthavikri.Utility.types;

import android.util.Log;

import androidx.annotation.NonNull;

import com.application.granthavikri.Utility;
import com.application.granthavikri.data.Constants;
import com.application.granthavikri.model.EventGroupModel;
import com.application.granthavikri.model.EventModel;
import com.application.granthavikri.model.EventStockModel;
import com.application.granthavikri.model.SaleModel;
import com.application.granthavikri.model.UpdateEventItemsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRepository {

    private String kendra;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String root_collection_path;

    private InventoryRepository inventoryRepository;

    private GroupRepository groupRepository;

    public EventRepository(){
        kendra = Utility.getKendra();
        root_collection_path = kendra + "/" + Utility.kendraMap.get(kendra) + "/" + Constants.EVENT_COLLECTION;
        inventoryRepository = new InventoryRepository();
        groupRepository = new GroupRepository();
    }

    public void fetchEventData(Utility.OnDataFetchedListener listener){
        Log.d(tag, root_collection_path+" path of event");
        db.collection(root_collection_path)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(tag, "Listen failed.", e);
                        return;
                    }
                    List<EventModel> eventModelList = new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        EventModel eventModel = documentSnapshot.toObject(EventModel.class);
                        eventModel.setEvent_id(documentSnapshot.getId());
                        eventModelList.add(eventModel);
                    }
                    listener.onDataFetched(eventModelList);
                });
    }

    public void addEventData(EventModel eventModel, Utility.FirestoreCallback callback){
        db.collection(root_collection_path)
                .add(eventModel)
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess();
                    Log.d(tag, "event repo data added successfully!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }

    public void updateQuantityForStock(String event_id, List<UpdateEventItemsModel> stockUpdateModels, Utility.FirestoreCallback callback){
        DocumentReference documentReference = db.collection(root_collection_path).document(event_id);
        DocumentSnapshot documentSnapshot = documentReference.get().getResult();
        if(documentSnapshot.exists()){
            List<EventStockModel> currentStocks = (List<EventStockModel>) documentSnapshot.get("event_stocks");
            for(EventStockModel eventStockModel: currentStocks){
                Log.d(tag, "id: "+eventStockModel.getStock().getStock_id());
            }
        }
    }

    public void addSaleToEvent(String event_id, SaleModel saleModel, Utility.FirestoreCallback callback){
        db.collection(root_collection_path+"/"+event_id+"/"+Constants.SALE_COLLECTION)
                .add(saleModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d(tag, "added successfully");
                    }
                });
        DocumentReference documentReference = db.collection(root_collection_path).document(event_id);
        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    List<HashMap<String, Object>> eventStockModels = (List<HashMap<String, Object>>) documentSnapshot.get("event_stocks");
                    List<HashMap<String, Object>> eventGroupModels = (List<HashMap<String, Object>>) documentSnapshot.get("event_groups");

                    Log.d(tag, "size of event stocks "+eventStockModels.size());

                    if(saleModel.getGroup_count()>0) {
                        for (HashMap<String, Object> eventGroupModel : eventGroupModels) {
                            for (UpdateEventItemsModel updateEventItemsModel : saleModel.getItems_sold()) {
                                if (Utility.areEqualStrings(updateEventItemsModel.getType(), "Group")) {
                                    String group_id = (String) eventGroupModel.get("group_id");
                                    if(Utility.areEqualStrings(group_id, updateEventItemsModel.getId())) {
                                        Long sold_quantity = (Long) eventGroupModel.get("quantity_sold");
                                        sold_quantity = sold_quantity + (long) updateEventItemsModel.getQuantity_sold();
                                        eventGroupModel.put("quantity_available", updateEventItemsModel.getQuantity_available());
                                        eventGroupModel.put("quantity_sold", sold_quantity);
                                        groupRepository.updateQuantity(group_id, sold_quantity.intValue(), new Utility.FirestoreCallback() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(tag, "Successfully updated group!");
                                            }

                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d(tag, "Failure occurred while updating group");
                                                callback.onFailure(e);

                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        }

                        updateEventItemsQuantity(documentReference, "event_groups",eventGroupModels, new Utility.FirestoreCallback() {
                            @Override
                            public void onSuccess() {
                                Log.d(tag, "successfully updated group quantity in event database!");
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d(tag, "failure occurred while updating group quantity in event database!");
                                callback.onFailure(e);
                            }
                        });
                    }

                    if(saleModel.getInventory_count()>0) {
                        for (HashMap<String, Object> eventStockModel : eventStockModels) {
                            for (UpdateEventItemsModel updateEventItemsModel : saleModel.getItems_sold()) {
                                if (!Utility.areEqualStrings(updateEventItemsModel.getType(), "Group")) {
                                    HashMap<String, Object> stock_data = (HashMap<String, Object>) eventStockModel.get("stock");
                                    String stock_id = (String) stock_data.get("stock_id");
                                    if(Utility.areEqualStrings(updateEventItemsModel.getId(), stock_id)) {
                                        Long sold_quantity = (Long) eventStockModel.get("quantity_sold");
                                        String type = updateEventItemsModel.getType();
                                        sold_quantity = sold_quantity + (long) updateEventItemsModel.getQuantity_sold();
                                        eventStockModel.put("quantity_available", updateEventItemsModel.getQuantity_available());
                                        eventStockModel.put("quantity_sold", sold_quantity);
                                        Log.d(tag, "stock id" + stock_id);
                                        inventoryRepository.updateQuantity(stock_id, type, updateEventItemsModel.getQuantity_sold(), new Utility.FirestoreCallback() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(tag, "successfully updated stock quantity!");
                                            }

                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d(tag, "error occurred while updating stock quantity");
                                                callback.onFailure(e);
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        }

                        updateEventItemsQuantity(documentReference, "event_stocks", eventStockModels, new Utility.FirestoreCallback() {
                            @Override
                            public void onSuccess() {
                                Log.d(tag, "successfully updated stock quantity in event database!");
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d(tag, "failure occurred while updating stock quantity in event database!");
                                callback.onFailure(e);
                            }
                        });
                    }

                    callback.onSuccess();
                }
            }

        });

    }

    private void updateEventItemsQuantity(DocumentReference documentReference, String list,List<HashMap<String, Object>> eventItems, Utility.FirestoreCallback callback){
        documentReference.update(list, eventItems)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }

    private void updateEventGroupQuantity(DocumentReference documentReference, List<HashMap<String, Object>> eventGroupModels, Utility.FirestoreCallback callback){
        documentReference.update("event_groups", eventGroupModels)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }
}
