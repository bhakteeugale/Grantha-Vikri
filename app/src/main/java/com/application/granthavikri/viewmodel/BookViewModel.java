package com.application.granthavikri.viewmodel;

import static com.application.granthavikri.Utility.tag;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.application.granthavikri.Utility;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.repository.InventoryRepository;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class BookViewModel extends ViewModel {

    private MutableLiveData<List<BookModel>> bookData = new MutableLiveData<>();

    private InventoryRepository inventoryRepository;

    public BookViewModel(){
        inventoryRepository = new InventoryRepository();
        fetchBookData();
    }

    private void fetchBookData(){

        inventoryRepository.fetchInventoryData(1, new Utility.OnDataFetchedListener<BookModel>(){
            @Override
            public void onDataFetched(List<BookModel> data) {
                bookData.setValue(data);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }



    public LiveData<List<BookModel>> getBookDataList(){
        return bookData;
    }

    public void addBookData(BookModel bookModel, Utility.FirestoreCallback callback){
        Log.d(tag, "reached on viewmodel class add method");
        inventoryRepository.addInventoryData(bookModel, new Utility.FirestoreCallback() {
            @Override
            public void onSuccess() {
                Log.d(tag, "Successful addition!");
                callback.onSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(tag, "Error Occurred!");
                callback.onFailure(e);
            }
        });
    }

    public BookModel getBookModel(String book_id){
        List<BookModel> data = getBookDataList().getValue();
        for(BookModel bookModel1: data){
            if(Utility.areEqualStrings(book_id, bookModel1.getBook_id())){
                return bookModel1;
            }
        }
        return null;
    }

    public void updateQuantity(String book_id, int quantity_sold){

    }
}
