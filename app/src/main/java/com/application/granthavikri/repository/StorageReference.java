package com.application.granthavikri.repository;

import static com.application.granthavikri.Utility.tag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.application.granthavikri.Utility;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;

public class StorageReference {

    private FirebaseStorage storage;
    private com.google.firebase.storage.StorageReference storageReference;

    public StorageReference() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void uploadBookCoverPhoto(Uri file_path, String file_name, Utility.OnUploadListener uploadListener){
        if (file_path != null) {
            com.google.firebase.storage.StorageReference ref = storageReference.child("images/" + file_name);
            // Handle the upload failure
            ref.putFile(file_path)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get the download URL
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageURL = uri.toString();
                            uploadListener.onSuccess(imageURL);
                            // Handle the download URL (e.g., save it to a database)
                        });
                    })
                    .addOnFailureListener(uploadListener::onFailure);
        }
    }
}
