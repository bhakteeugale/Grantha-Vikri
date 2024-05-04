package com.application.granthavikri.viewmodel;

import static com.application.granthavikri.Utility.tag;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import com.application.granthavikri.Utility;
import com.application.granthavikri.model.EventModel;
import com.application.granthavikri.model.SaleModel;
import com.application.granthavikri.repository.EventRepository;

public class EventViewModel extends ViewModel {

    private MutableLiveData<List<EventModel>> eventData = new MutableLiveData<>();

    private EventRepository eventRepository;

    public EventViewModel(){
        eventRepository = new EventRepository();
        fetchEventData();
    }

    private void fetchEventData(){
        eventRepository.fetchEventData(new Utility.OnDataFetchedListener<EventModel>() {
            @Override
            public void onDataFetched(List<EventModel> data) {
                Log.d(tag, "event view model data fetched successfully!");
                eventData.setValue(data);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public LiveData<List<EventModel>> getEventDataList(){
        return eventData;
    }

    public void addEventData(EventModel eventModel, Utility.FirestoreCallback callback){
        eventRepository.addEventData(eventModel, new Utility.FirestoreCallback() {
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

    public void addSale(String event_id, SaleModel saleModel){
//        eventRepository.addSaleToEvent(event_id, saleModel);
    }
}
