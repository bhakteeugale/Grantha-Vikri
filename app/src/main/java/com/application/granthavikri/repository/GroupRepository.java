package com.application.granthavikri.repository;

import static com.application.granthavikri.Utility.tag;

import android.util.Log;

import com.application.granthavikri.Utility;
import com.application.granthavikri.data.Constants;
import com.application.granthavikri.model.GroupModel;
import com.application.granthavikri.model.StockModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupRepository {
    private String kendra;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String root_collection_path;

    private InventoryRepository inventoryRepository;

    public GroupRepository() {
        kendra = Utility.getKendra();
        root_collection_path = kendra+"/"+Utility.kendraMap.get(kendra)+"/"+ Constants.GROUP_COLLECTION;
        inventoryRepository = new InventoryRepository();
    }

    public void fetchGroupData(Utility.OnDataFetchedListener listener){
        db.collection(root_collection_path)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(tag, "Listen failed.", e);
                        return;
                    }
                    List<GroupModel> groupModelList = new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        GroupModel groupModel = documentSnapshot.toObject(GroupModel.class);
                        groupModel.setGroup_id(documentSnapshot.getId());
                        groupModelList.add(groupModel);
                    }
                    listener.onDataFetched(groupModelList);
                });
    }

    public void addGroupData(GroupModel groupModel, Utility.FirestoreCallback callback){
        db.collection(root_collection_path)
                .add(groupModel)
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }


    public void updateQuantity(String id, int quantity_sold, Utility.FirestoreCallback callback){
        DocumentReference documentReference = db.collection(root_collection_path).document(id);
        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()) {
                    List<HashMap<String, Object>> stockModelList = (List<HashMap<String, Object>>) documentSnapshot.get("group_stocks");
                    for(HashMap<String, Object> stockModel: stockModelList){
                        String stock_id = (String) stockModel.get("stock_id");
                        String type = (String) stockModel.get("type");
                        inventoryRepository.updateQuantity(stock_id, type, quantity_sold, new Utility.FirestoreCallback() {
                            @Override
                            public void onSuccess() {
                                callback.onSuccess();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                callback.onFailure(e);
                            }
                        });
                    }

                }
            }
        });
    }

}
