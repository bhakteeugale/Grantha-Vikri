package com.application.granthavikri.ui;

import static android.app.Activity.RESULT_OK;

import static com.application.granthavikri.Utility.tag;
import static com.application.granthavikri.Utility.types;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.FrameModel;
import com.application.granthavikri.model.StockModel;
import com.application.granthavikri.repository.StorageReference;
import com.application.granthavikri.viewmodel.BookViewModel;
import com.application.granthavikri.viewmodel.CDViewModel;
import com.application.granthavikri.viewmodel.FrameViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.storage.FirebaseStorage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddStockFragment extends Fragment {

    private LinearLayout linearLayout;

    private EditText txt_size, edit_name, edit_description, edit_quantity, edit_printed_price, edit_discounted_prince, edit_edition, edit_nickname;


    private TextView title_view, title_photo;

    private LinearLayout linearLayout_book, form_layout;

    private int SELECT_CODE = 100;

    private int type;


    private Uri cover_photo;

    private HomeActivity home;

    private BookViewModel bookViewModel;

    private FrameViewModel frameViewModel;

    private CDViewModel cdViewModel;

    private ActivityResultLauncher<String> imagePickerLauncher;

    private ProgressDialog progressDialog;

    public AddStockFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddStockFragment.
//     */
//    public static AddStockFragment newInstance(String param1, String param2) {
//        AddStockFragment fragment = new AddStockFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home = (HomeActivity) getActivity();
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        // Upload the selected image to Firebase Storage
                        cover_photo = uri;
                        title_photo.setText("Photo Selected");
                        Log.d(tag, "still on fragment");
                    }
                }
        );

        progressDialog = new ProgressDialog(home);
        progressDialog.setTitle("Uploading data...");
        progressDialog.setCancelable(false); // Prevent dismiss when tapping outside or pressing back button
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_stock, container, false);

        form_layout = view.findViewById(R.id.form_layout);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_grp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check if any radio button is checked
                form_layout.setVisibility(View.VISIBLE);

                if (checkedId == R.id.radio_book) {
                    title_photo.setText("No Photo");
                    setForm(0);
                    type = 0;
                } else if(checkedId == R.id.radio_cd){
                    setForm(1);
                    type = 1;
                } else if(checkedId == R.id.radio_frame){
                    txt_size.setText("");
                    txt_size.setHint("Frame Size");
                    setForm(2);
                    type = 2;
                }
                else linearLayout.setVisibility(View.GONE);
            }
        });

        bookViewModel = new ViewModelProvider(home).get(BookViewModel.class);
        cdViewModel = new ViewModelProvider(home).get(CDViewModel.class);
        frameViewModel = new ViewModelProvider(home).get(FrameViewModel.class);


        edit_name = view.findViewById(R.id.edit_name);
        edit_printed_price = view.findViewById(R.id.edit_printed_price);
        edit_discounted_prince = view.findViewById(R.id.edit_discounted_price);
        edit_quantity = view.findViewById(R.id.edit_quantity);
        edit_description = view.findViewById(R.id.edit_description);
        edit_nickname = view.findViewById(R.id.book_nickname_edit);
        edit_edition = view.findViewById(R.id.book_edition_edit);

        TextView action_bar_title = view.findViewById(R.id.title_action);
        action_bar_title.setText("Add Inventory");

        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_for_form);
        linearLayout.setVisibility(View.GONE);

        linearLayout_book = view.findViewById(R.id.extra_info_book);

        Button upload_btn = view.findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerLauncher.launch("image/*");
            }
        });

        txt_size = view.findViewById(R.id.edit_size);
        title_view = view.findViewById(R.id.type_title);
        title_photo = view.findViewById(R.id.title_photo);

        Button submit_btn = view.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check =uploadStock();
                if(check==0){
                    Toast.makeText(getContext(), "Name is mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(check==2){
                    Toast.makeText(getContext(), "Printed Price is mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(check==3){
                    Toast.makeText(getContext(), "Discounted Price is mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(check==4){
                    Toast.makeText(getContext(), "Quantity is mandatory!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            cover_photo = data.getData();
//            // Upload the selected image to Firebase Storage
//
//        }
//    }

    private void setForm(int pos){
        linearLayout.setVisibility(View.VISIBLE);
        if(pos==0){
            title_view.setText("Add Book");
            linearLayout_book.setVisibility(View.VISIBLE);
            txt_size.setVisibility(View.GONE);
        }
        else if(pos==1){
            title_view.setText("Add CD");
            linearLayout_book.setVisibility(View.GONE);
            txt_size.setVisibility(View.GONE);
        }
        else if(pos==2){
            title_view.setText("Add Frame");
            linearLayout_book.setVisibility(View.GONE);
            txt_size.setVisibility(View.VISIBLE);
        }
    }

    private void clearFields(){
        Utility.clear_field(edit_name, "Name(Required)");
        Utility.clear_field(edit_description, "Description");
        Utility.clear_field(edit_quantity, "Quantity(Required)");
        Utility.clear_field(edit_discounted_prince, "Discounted Price(Required)");
        Utility.clear_field(edit_printed_price, "Printed Price(Required)");
        if(type==0){
            title_photo.setText("No Photo");
            Utility.clear_field(edit_nickname, "Nickname");
            Utility.clear_field(edit_edition, "Edition");
        }
        else if(type==2){
            Utility.clear_field(txt_size, "Frame Size");
        }
    }

    private int uploadStock(){
        StockModel stockModel = new StockModel();
        stockModel.setStock_id(null);

        String name = edit_name.getText().toString().trim();
        // if no name
        if(name.isEmpty()) {
            return 0;
        }

        String description = edit_description.getText().toString().trim();
        if(description.isEmpty()) description = "";

        String printed_price_str = edit_printed_price.getText().toString().trim();
        float printed_price = 0;
        // if no printed price
        if(printed_price_str.isEmpty()) return 2;
        printed_price = Float.parseFloat(printed_price_str);

        String discounted_price_str = edit_discounted_prince.getText().toString().trim();
        float discounted_price = 0;
        // if no discounted price
        if(discounted_price_str.isEmpty()) return 3;
        discounted_price = Float.parseFloat(discounted_price_str);

        String avialable_qty_str = edit_quantity.getText().toString().trim();
        int available_quantity = 0;
        if(avialable_qty_str.isEmpty()) return 4;
        available_quantity = Integer.parseInt(avialable_qty_str);

        int sold_quantity = 0;

        Timestamp timestamp = Timestamp.now();

        final String[] path = {""};

        Log.d(tag, "collected details of common fields!");

        progressDialog.show();

        if(type==0){
            String edition = edit_edition.getText().toString().trim();
            String nickname = edit_nickname.getText().toString().trim();
            BookModel bookModel = new BookModel();
            bookModel.setName(name);
            bookModel.setDate(timestamp);
            bookModel.setAvailable_quantity(available_quantity);
            bookModel.setDescription(description);
            bookModel.setDiscounted_price(discounted_price);
            bookModel.setPrinted_price(printed_price);
            bookModel.setNickname(nickname);
            bookModel.setEdition(edition);
            bookModel.setSold_quantity(0);
            if(cover_photo!=null){
                resizeAndSaveImage(name);
                StorageReference storageReference = new StorageReference();
                storageReference.uploadBookCoverPhoto(cover_photo, name, new Utility.OnUploadListener() {
                    @Override
                    public void onSuccess(String imageUrl) {
                        bookModel.setCover_photo(imageUrl);
                        addBook(bookModel);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), "error while uploading photo! try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                bookModel.setCover_photo("");
                addBook(bookModel);
            }

            Log.d(tag, "added book");
        }
        else if(type==1){
            CDModel cdModel = new CDModel(
                    name,
                    timestamp,
                    description,
                    available_quantity,
                    printed_price,
                    discounted_price,
                    sold_quantity
            );
            cdViewModel.addCDData(cdModel, new Utility.FirestoreCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "CD added successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    clearFields();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), "Error occurred! Try again!", Toast.LENGTH_SHORT).show();
                }
            });
            Log.d(tag, "added CD");
        }
        else if(type==2){
            String frame_size_str = txt_size.getText().toString().trim();
            int frame_size = 0;
            if(!frame_size_str.isEmpty()){
                frame_size = Integer.parseInt(frame_size_str);
            }
            FrameModel frameModel = new FrameModel(
                    name,
                    timestamp,
                    description,
                    available_quantity,
                    printed_price,
                    discounted_price,
                    sold_quantity,
                    frame_size
            );
            frameViewModel.addFrameData(frameModel, new Utility.FirestoreCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "Frame added successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    clearFields();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), "Error occurred! Try again!", Toast.LENGTH_SHORT).show();
                }
            });
            Log.d(tag, "added frame");
        }

        return -1;  // successfully added
    }

    public void addBook(BookModel bookModel){
            bookViewModel.addBookData(bookModel, new Utility.FirestoreCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    clearFields();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), "Error occurred! Try again!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("FragmentLifecycle", "onResume() called");
    }

    private void resizeAndSaveImage(String name){

        // Decode the input stream into a Bitmap
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(home.getContentResolver(), cover_photo);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float ratio = (float) width / (float) height;
            int finalHeight = 720;
            int finalWidth = (int)((float)finalHeight*ratio);
            if(height>finalHeight) bitmap = Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);

            String resized_imageUri = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, name, "Resized Image");
            cover_photo = Uri.parse(resized_imageUri);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Error occurred while accessing image!", Toast.LENGTH_SHORT).show();
        }
        finally {

        }

    }

    private void saveBitmapToGallery(Bitmap bitmap, Uri uri) {
        OutputStream outputStream = null;
        try {
            outputStream = getContext().getContentResolver().openOutputStream(cover_photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", "Description");

            getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            Toast.makeText(getContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String getFilePathFromUri(Uri uri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

}