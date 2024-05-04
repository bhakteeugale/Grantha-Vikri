package com.application.granthavikri.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.FrameModel;
import com.application.granthavikri.repository.InventoryRepository;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class FrameViewModel extends ViewModel {
    private MutableLiveData<List<FrameModel>> frameData = new MutableLiveData<>();

    private InventoryRepository inventoryRepository;

    public FrameViewModel(){
        inventoryRepository = new InventoryRepository();
        fetchFrameData();
    }

    public void fetchFrameData() {
        inventoryRepository.fetchInventoryData(3, new Utility.OnDataFetchedListener<FrameModel>(){
            @Override
            public void onDataFetched(List<FrameModel> data) {
                frameData.setValue(data);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public LiveData<List<FrameModel>> getFrameData(){
        return frameData;
    }

    public void addFrameData(FrameModel frameModel, Utility.FirestoreCallback callback){
        inventoryRepository.addInventoryData(frameModel, new Utility.FirestoreCallback() {
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

    public FrameModel getFrameModel(String frame_id){
        List<FrameModel> frameModelList = getFrameData().getValue();
        for(FrameModel frameModel: frameModelList){
            if(Utility.areEqualStrings(frame_id, frameModel.getFrame_id())){
                return frameModel;
            }
        }
        return null;
    }

    public void updateQuantity(String frame_id, int quantity_sold){

    }
}
