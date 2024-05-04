package com.application.granthavikri.viewmodel;

import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.granthavikri.Utility;
import com.application.granthavikri.model.GroupModel;
import com.application.granthavikri.repository.GroupRepository;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<List<GroupModel>> groupData = new MutableLiveData<>();

    private GroupRepository groupRepository;

    public GroupViewModel() {
        groupRepository = new GroupRepository();
        fetchGroupData();
    }

    public void fetchGroupData(){
        groupRepository.fetchGroupData(new Utility.OnDataFetchedListener<GroupModel>() {
            @Override
            public void onDataFetched(List<GroupModel> data) {
                groupData.setValue(data);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public LiveData<List<GroupModel>> getGroupDataList(){
        return groupData;
    }

    public void addGroupData(GroupModel groupModel, Utility.FirestoreCallback callback){
        groupRepository.addGroupData(groupModel, new Utility.FirestoreCallback() {
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
