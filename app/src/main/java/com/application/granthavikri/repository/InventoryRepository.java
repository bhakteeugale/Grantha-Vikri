package com.application.granthavikri.repository;

import static com.application.granthavikri.Utility.tag;
import static com.application.granthavikri.Utility.types;

import android.util.Log;
import com.application.granthavikri.Utility;
import com.application.granthavikri.data.Constants;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.FrameModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryRepository {

    private String kendra;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static String root_collection_path;

    public InventoryRepository(){
        kendra = Utility.getKendra();
        root_collection_path = kendra+"/"+Utility.kendraMap.get(kendra)+"/"+ Constants.INVENTORY_COLLECTION+"/"+Constants.INVENTORY_SUB_DATABASE+"/";
    }

    public void fetchInventoryData(int type, Utility.OnDataFetchedListener listener){
        String collection_path = root_collection_path+Utility.types.get(type);
        Log.d(tag, collection_path);
        db.collection(collection_path)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(tag, "Listen failed.", e);
                        return;
                    }
                    if(type==1){
                        List<BookModel> bookModelList = new ArrayList<>();

                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            BookModel bookModel = documentSnapshot.toObject(BookModel.class);
                            bookModel.setBook_id(documentSnapshot.getId());
                            bookModelList.add(bookModel);
                        }

                        listener.onDataFetched(bookModelList);
                    }
                    else if(type==2) {
                        List<CDModel> cdModelList = new ArrayList<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            CDModel cdModel = documentSnapshot.toObject(CDModel.class);
                            cdModel.setCd_id(documentSnapshot.getId());
                            cdModelList.add(cdModel);
                        }

                        listener.onDataFetched(cdModelList);
                    }
                    else if(type==3) {
                        List<FrameModel> frameModelList = new ArrayList<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            FrameModel frameModel = documentSnapshot.toObject(FrameModel.class);
                            frameModel.setFrame_id(documentSnapshot.getId());
                            frameModelList.add(frameModel);
                        }

                        listener.onDataFetched(frameModelList);
                    }
                });
    }

    public void addInventoryData(Object stockModel, Utility.FirestoreCallback callback){
        String collection_path = root_collection_path;
        if(stockModel instanceof BookModel){
            collection_path += "Book";
        }
        else if(stockModel instanceof CDModel){
            collection_path += "CD";
        }
        else{
            collection_path += "Frame";
        }
        db.collection(collection_path)
                .add(stockModel)
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }

    public DocumentReference getReference(String ref, String type){
        String doc_path = root_collection_path + type + "/" + ref;
        return db.document(doc_path);
    }

    public void updateQuantity(String id, String type, int quantity_sold, Utility.FirestoreCallback callback){
        DocumentReference documentReference = db.collection(root_collection_path+type).document(id);
        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()) {
                    Long quantity_available = (Long) documentSnapshot.get("available_quantity");
                    Long new_quantity_sold = (Long) documentSnapshot.get("sold_quantity");
                    quantity_available = quantity_available - (long) quantity_sold;
                    new_quantity_sold += (long) quantity_sold;
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("available_quantity", quantity_available);
                    updates.put("sold_quantity", new_quantity_sold);

                    documentReference.update(updates)
                            .addOnSuccessListener(aVoid -> {
                                callback.onSuccess();
                            })
                            .addOnFailureListener(e -> {
                                callback.onFailure(e);
                            });
                }
            }
        });

    }
}
