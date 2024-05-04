package com.application.granthavikri.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.application.granthavikri.Utility.tag;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.FrameModel;
import com.application.granthavikri.repository.StorageReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link StockDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class StockDetailsFragment extends Fragment {

    private BookModel bookModel;

    private CDModel cdModel;

    private FrameModel frameModel;

    private int type_ind;

    private String title;

    private String date;

    private String name;

    private int available_qty;

    private int sold_qty;

    private double printed_price;

    private double discounted_price;

    private String type;

    private int frame_size;

    private String edition;

    private String nickname;

    private String description;

    private TextView inventory_name_detail, inventory_type_detail, inventory_date_detail, inventory_description_detail, inventory_available_quantity_detail, inventory_sold_quantity_detail, inventory_printed_price_detail, inventory_discounted_price_detail;

    private TextView edition_textview, inventory_edition_detail, nickname_textview, inventory_nickname_detail, frame_size_textview, inventory_frame_size_detail;

    private ImageView cover_image_inventory_book;

    private String filePath;

    public StockDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment StockDetailsFragment.
//     */
//    public static StockDetailsFragment newInstance(String param1, String param2) {
//        StockDetailsFragment fragment = new StockDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            Object obj = args.get("item");
            if(obj instanceof BookModel){
                bookModel = (BookModel) obj;
                title = "Book - "+bookModel.getName();
                type = "Book";
                name = bookModel.getName();
                date = Utility.setFormat(bookModel.getDate().toDate());
                description = bookModel.getDescription();
                available_qty = bookModel.getAvailable_quantity();
                sold_qty = bookModel.getSold_quantity();
                printed_price = bookModel.getPrinted_price();
                discounted_price = bookModel.getDiscounted_price();
                edition = bookModel.getEdition();
                nickname = bookModel.getNickname();
                filePath = bookModel.getCover_photo();
                type_ind = 1;
            }
            else if(obj instanceof FrameModel){
                frameModel = (FrameModel) obj;
                title = "Frame - "+frameModel.getName();
                type = "Frame";
                name = frameModel.getName();
                description = frameModel.getDescription();
                available_qty = frameModel.getAvailable_quantity();
                sold_qty = frameModel.getSold_quantity();
                frame_size = frameModel.getFrame_size();
                printed_price = frameModel.getPrinted_price();
                discounted_price = frameModel.getDiscounted_price();
                date = Utility.setFormat(frameModel.getDate().toDate());
                type_ind = 3;
            }
            else if(obj instanceof CDModel){
                cdModel = (CDModel) obj;
                title = "CD - "+cdModel.getName();
                type = "CD";
                name = cdModel.getName();
                description = cdModel.getDescription();
                available_qty = cdModel.getAvailable_quantity();
                sold_qty = cdModel.getSold_quantity();
                date = Utility.setFormat(cdModel.getDate().toDate());
                printed_price = cdModel.getPrinted_price();
                discounted_price = cdModel.getDiscounted_price();
                type_ind = 2;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock_details, container, false);

        TextView actionbar_title = view.findViewById(R.id.title_action);
        actionbar_title.setText(title);

        TextView inventory_det_textview = view.findViewById(R.id.inventory_det_textview);
        inventory_det_textview.setText(name);

        TextView inventory_type_title = view.findViewById(R.id.inventory_type_title);
        inventory_type_title.setText(type);

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading data...");
        progressDialog.setCancelable(false); // Prevent dismiss when tapping outside or pressing back button

        inventory_name_detail = view.findViewById(R.id.inventory_name_detail);
        inventory_name_detail.setText(name);

        inventory_type_detail = view.findViewById(R.id.inventory_type_detail);
        inventory_type_detail.setText(type);

        inventory_date_detail = view.findViewById(R.id.inventory_date_detail);
        inventory_date_detail.setText(date);

        inventory_description_detail = view.findViewById(R.id.inventory_description_detail);
        inventory_description_detail.setText(description);

        inventory_available_quantity_detail = view.findViewById(R.id.inventory_available_quantity_detail);
        inventory_available_quantity_detail.setText(String.valueOf(available_qty));

        inventory_sold_quantity_detail = view.findViewById(R.id.inventory_sold_quantity_detail);
        inventory_sold_quantity_detail.setText(String.valueOf(sold_qty));

        inventory_printed_price_detail = view.findViewById(R.id.inventory_printed_price_detail);
        inventory_printed_price_detail.setText("₹ "+printed_price);

        inventory_discounted_price_detail = view.findViewById(R.id.inventory_discounted_price_detail);
        inventory_discounted_price_detail.setText("₹ "+discounted_price);

        if(type_ind==1){
            edition_textview = view.findViewById(R.id.edition_textview);
            edition_textview.setVisibility(View.VISIBLE);

            inventory_edition_detail = view.findViewById(R.id.inventory_edition_detail);
            inventory_edition_detail.setVisibility(View.VISIBLE);
            inventory_edition_detail.setText(edition);

            nickname_textview = view.findViewById(R.id.nickname_textview);
            nickname_textview.setVisibility(View.VISIBLE);

            inventory_nickname_detail = view.findViewById(R.id.inventory_nickname_detail);
            inventory_nickname_detail.setVisibility(View.VISIBLE);
            inventory_nickname_detail.setText(nickname);

            cover_image_inventory_book = view.findViewById(R.id.cover_image_inventory_book);
            cover_image_inventory_book.setVisibility(View.VISIBLE);

            cover_image_inventory_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!filePath.isEmpty()) showImageDialog(filePath);
                }
            });

            if(!filePath.isEmpty()) {
                Glide.with(getContext())
                        .load(filePath)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // Handle image load failure here
//                                Log.e(tag, "Image load failed: " + e.getMessage());
                                Toast.makeText(getContext(), "Image load failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                return false; // Return false to allow Glide to handle the error
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                // Image has been successfully loaded
                                Log.d(tag, "Image load successful");
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();
                                return false; // Return false to allow Glide to handle the resource
                            }
                        })
                        .into(cover_image_inventory_book);
            }
        }
        else if(type_ind==3){
            frame_size_textview = view.findViewById(R.id.frame_size_textview);
            frame_size_textview.setVisibility(View.VISIBLE);

            inventory_frame_size_detail = view.findViewById(R.id.inventory_frame_size_detail);
            inventory_frame_size_detail.setVisibility(View.VISIBLE);
            inventory_frame_size_detail.setText(String.valueOf(frame_size));
        }

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void showImageDialog(String imageUrl) {
        // Inflate the layout for the custom dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_image_view, null);

        // Find views in the dialog layout
        ImageView imageView = dialogView.findViewById(R.id.image_view);
        ImageButton closeButton = dialogView.findViewById(R.id.close_button);

        // Load the image into the ImageView using Glide
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Set a click listener for the close button to dismiss the dialog
        closeButton.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }

}