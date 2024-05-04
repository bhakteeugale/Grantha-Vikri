package com.application.granthavikri.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.repository.InventoryRepository;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class CDViewModel extends ViewModel {
    private MutableLiveData<List<CDModel>> cdData = new MutableLiveData<>();

    private InventoryRepository inventoryRepository;

    public CDViewModel(){
        inventoryRepository = new InventoryRepository();
        fetchCDData();
    }

    public void fetchCDData(){
        inventoryRepository.fetchInventoryData(2, new Utility.OnDataFetchedListener<CDModel>(){
            @Override
            public void onDataFetched(List<CDModel> data) {
                cdData.setValue(data);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public LiveData<List<CDModel>> getCDDataList(){
        return cdData;
    }

    public void addCDData(CDModel cdModel, Utility.FirestoreCallback callback){
        inventoryRepository.addInventoryData(cdModel, new Utility.FirestoreCallback() {
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

    public DocumentReference getReference(String ref){
        return inventoryRepository.getReference(ref, "CD");
    }

    public CDModel getCdModel(String cd_id){
        List<CDModel> data = getCDDataList().getValue();
        for(CDModel cdModel: data){
            if(Utility.areEqualStrings(cd_id, cdModel.getCd_id())){
                return cdModel;
            }
        }
        return null;
    }

    public void updateQuantity(String cd_id, int quantity_sold){

    }
}
