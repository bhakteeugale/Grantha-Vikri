package com.application.granthavikri.ui;

import static com.application.granthavikri.Utility.tag;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.EventGroupModel;
import com.application.granthavikri.model.EventModel;
import com.application.granthavikri.model.EventStockModel;
import com.application.granthavikri.model.FrameModel;
import com.application.granthavikri.model.GroupModel;
import com.application.granthavikri.model.StockModel;
import com.application.granthavikri.viewmodel.BookViewModel;
import com.application.granthavikri.viewmodel.CDViewModel;
import com.application.granthavikri.viewmodel.EventViewModel;
import com.application.granthavikri.viewmodel.FrameViewModel;
import com.application.granthavikri.viewmodel.GroupViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddEventFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddEventFragment extends Fragment {

    private List<EventStockModel> eventStockModels;

    private List<EventGroupModel> eventGroupModels;

    private EventViewModel eventViewModel;

    private GroupViewModel groupViewModel;

    private BookViewModel bookViewModel;

    private CDViewModel cdViewModel;

    private HashSet<String> hs;

    private FrameViewModel frameViewModel;

    private EditText name_event, place_event;

    private TextView date_event_textview;

    private List<String> names;

    private Spinner type_of_item_spinner, content_spinner;

    private LinearLayout clickable_event_add_details_layout, clickable_event_add_items_layout, clickable_event_view_items_layout;

    private LinearLayout view_item_list, form_add_items_layout, event_details_form_layout;

    private List<GroupModel> groupModelList;

    private List<BookModel> bookModelList;

    private List<CDModel> cdModelList;

    private List<FrameModel> frameModelList;

    private LinearLayout quantity_and_price_for_inventory, quantity_and_price_for_group_layout;

    private TextView available_quantity, price_view, group_price_details;

    private EditText quantity, quantity_for_group;

    private int type = -1;

    private int pos = -1;

    private Spinner price_spinner;

    private String member_id=null;

    private Button add_item_btn, add_event_btn;

    private Date date;

    private RecyclerGroupDetailsAdapter recyclerGroupDetailsAdapter;

    private RecyclerInventoryDetailsAdapter recyclerInventoryDetailsAdapter;

    List<EventStockModel> tmp = new ArrayList<>();

    private List<String> price_arrayList = new ArrayList<>(Arrays.asList("Select Price Type", "Printed Price(₹)", "Discounted Price(₹)"));
    public AddEventFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddEventFragment.
//     */
//    public static AddEventFragment newInstance(String param1, String param2) {
//        AddEventFragment fragment = new AddEventFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventStockModels = new ArrayList<>();
        eventGroupModels = new ArrayList<>();
        HomeActivity home = (HomeActivity) getActivity();
        eventViewModel = home.getEventViewModel();
        groupViewModel = home.getGroupViewModel();
        bookViewModel = home.getBookViewModel();
        cdViewModel = home.getCdViewModel();
        frameViewModel = home.getFrameViewModel();
        hs = new HashSet<>();
        names = new ArrayList<>();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        TextView action_bar_title = (TextView) view.findViewById(R.id.title_action);
        action_bar_title.setText("Add Event");

        name_event = view.findViewById(R.id.name_event);
        place_event = view.findViewById(R.id.place_event);
        date_event_textview = view.findViewById(R.id.date_event_textview);

        clickable_event_add_details_layout = view.findViewById(R.id.clickable_event_add_details_layout);
        event_details_form_layout = view.findViewById(R.id.event_details_form_layout);
        ImageView img1 = view.findViewById(R.id.event_add_details_img_view);
        setTransition(clickable_event_add_details_layout, event_details_form_layout, img1);

        clickable_event_add_items_layout = view.findViewById(R.id.clickable_event_add_items_layout);
        form_add_items_layout = view.findViewById(R.id.form_add_items_layout);
        ImageView img2 = view.findViewById(R.id.event_add_items_img_view);
        setTransition(clickable_event_add_items_layout, form_add_items_layout, img2);

        clickable_event_view_items_layout = view.findViewById(R.id.clickable_event_view_items_layout);
        view_item_list = view.findViewById(R.id.view_item_list);
        ImageView img3 = view.findViewById(R.id.event_view_items_img_view);
        setTransition(clickable_event_view_items_layout, view_item_list, img3);

        Button date_event_btn = view.findViewById(R.id.date_picker_btn);
        date_event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Update TextView with selected date
                                date_event_textview.setText(dayOfMonth + "." + (month + 1) + "." + year);
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                date = calendar.getTime();
                                Log.d(tag, date.toString());
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();


            }
        });

        quantity_and_price_for_inventory = view.findViewById(R.id.quantity_and_price_for_inventory);
        quantity = view.findViewById(R.id.quantity);
        available_quantity = view.findViewById(R.id.available_quantity);
        price_view = view.findViewById(R.id.price_view);
        price_spinner = view.findViewById(R.id.price_spinner);
        Utility.setupSpinner(getContext(), price_spinner, price_arrayList);
        price_spinner.setEnabled(false);
        price_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(pos==-1 || position==0){
                    price_view.setText("Price(₹)");
                    return;
                }
                String price = "₹";
                if(position==1){
                    if(type==1){
                        price += String.valueOf(bookModelList.get(pos).getPrinted_price());
                    }
                    else if(type==2){
                        price += String.valueOf(cdModelList.get(pos).getPrinted_price());
                    }
                    else if(type==3){
                        price += String.valueOf(frameModelList.get(pos).getPrinted_price());
                    }
                }
                else if(position==2){
                    if(type==1){
                        price += String.valueOf(bookModelList.get(pos).getDiscounted_price());
                    }
                    else if(type==2){
                        price += String.valueOf(cdModelList.get(pos).getDiscounted_price());
                    }
                    else if(type==3){
                        price += String.valueOf(frameModelList.get(pos).getDiscounted_price());
                    }
                }

                price_view.setText(price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantity_and_price_for_group_layout = view.findViewById(R.id.quantity_and_price_for_group_layout);
        group_price_details = view.findViewById(R.id.group_price_details);
        quantity_for_group = view.findViewById(R.id.quantity_for_group);


        bookViewModel.getBookDataList().observe(getViewLifecycleOwner(), bookModels -> {
            bookModelList = bookModels;
            if(type==1 && pos!=-1){
                available_quantity.setText(bookModelList.get(pos).getAvailable_quantity());
            }
        });

        cdViewModel.getCDDataList().observe(getViewLifecycleOwner(), cdModels -> {
            cdModelList = cdModels;
            if(type==2 && pos!=-1){
                available_quantity.setText(cdModelList.get(pos).getAvailable_quantity());
            }
        });

        frameViewModel.getFrameData().observe(getViewLifecycleOwner(), frameModels -> {
            frameModelList = frameModels;
            if(type==3 && pos!=-1){
                available_quantity.setText(frameModelList.get(pos).getAvailable_quantity());
            }
        });

        groupViewModel.getGroupDataList().observe(getViewLifecycleOwner(), groupModels -> {
            groupModelList = groupModels;
        });

        content_spinner = view.findViewById(R.id.stock_spinner);
        Utility.setupSpinner(getContext(), content_spinner, names);
        content_spinner.setEnabled(false);

        type_of_item_spinner = view.findViewById(R.id.type_of_stock_spinner);
        Utility.setupSpinner(getContext(), type_of_item_spinner, Utility.types);
        type_of_item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member_id = null;
                pos = -1;
                type = position;
                setSpinner(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_item_btn = view.findViewById(R.id.add_item_to_event_button);
        add_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hs.contains(member_id)) {
                    addItem();
                }
                else{
                    Toast.makeText(getContext(), "Item already added to event!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add_item_btn.setEnabled(false);

        add_event_btn = view.findViewById(R.id.add_event_btn);
//        add_event_btn.setEnabled(false);
        add_event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag, "clicked add event");
                if(name_event.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Event Name is mandatory! Event not added", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(place_event.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Event Place is mandatory! Event not added", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(date_event_textview.getText().toString().compareTo("Event Date")==0){
                    Toast.makeText(getContext(), "Event Date is mandatory! Event not added", Toast.LENGTH_SHORT).show();
                    return;
                }
                addEvent();
            }
        });

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG1", "pressed on back button");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        RecyclerView recyclerView_group = view.findViewById(R.id.group_items_in_event);
        recyclerView_group.setLayoutManager(new GridLayoutManager(getContext(), 1));

        RecyclerView recyclerView_inventory = view.findViewById(R.id.inventory_items_in_event);
        recyclerView_inventory.setLayoutManager(new GridLayoutManager(getContext(), 1));

        LinearLayout layout_inventory = view.findViewById(R.id.view_inventory_items_details);
        LinearLayout layout_grp = view.findViewById(R.id.view_group_items_details);

        recyclerGroupDetailsAdapter = new RecyclerGroupDetailsAdapter(eventGroupModels, getContext());
        recyclerView_group.setAdapter(recyclerGroupDetailsAdapter);

        recyclerInventoryDetailsAdapter = new RecyclerInventoryDetailsAdapter(getContext(), tmp);
        recyclerView_inventory.setAdapter(recyclerInventoryDetailsAdapter);

        Spinner type_of_item_spinner = view.findViewById(R.id.type_of_item_spinner);
        Utility.setupSpinner(getContext(), type_of_item_spinner, Utility.types);
        type_of_item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    layout_inventory.setVisibility(View.GONE);
                    layout_grp.setVisibility(View.GONE);
                }
                else if(position==4){
                    layout_inventory.setVisibility(View.GONE);
                    recyclerGroupDetailsAdapter.setData(eventGroupModels);
                    layout_grp.setVisibility(View.VISIBLE);
                }
                else {
                    String type = Utility.types.get(position);
                    tmp.clear();
                    for(EventStockModel eventStockModel: eventStockModels){
                        if(type.compareTo(eventStockModel.getStock().getType())==0) tmp.add(eventStockModel);
                    }
                    layout_grp.setVisibility(View.GONE);
                    recyclerInventoryDetailsAdapter.setData(tmp);
                    layout_inventory.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return view;
    }

    private void setTransition(LinearLayout click, LinearLayout display, ImageView img){
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vis = (display.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(click, new AutoTransition());
                if(vis==View.GONE){
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_expand_more_24);
                    img.setImageDrawable(drawable);
                }
                else{
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_expand_less_24);
                    img.setImageDrawable(drawable);
                }
                display.setVisibility(vis);
            }
        });
    }

    private void setSpinner(int type){
        resetSpinner();
        if(type==0){
            Utility.setupSpinner(getContext(), content_spinner, names);
            content_spinner.setEnabled(false);
            return;
        }
        else if(type==1){
            for(BookModel bookModel: bookModelList){
                names.add(bookModel.getName());
            }

        }
        else if(type==2){
            for(CDModel cdModel: cdModelList){
                names.add(cdModel.getName());
            }
        }
        else if(type==3){
            for (FrameModel frameModel: frameModelList){
                names.add(frameModel.getName());
            }
        }
        else if(type==4){
            for(GroupModel groupModel: groupModelList){
                names.add(groupModel.getGroup_name());
            }
        }



        if(names.size()==1){
            names.clear();
            names.add("No "+Utility.types.get(type)+"s");
        }

        content_spinner.setEnabled(true);
        Utility.setupSpinner(getContext(), content_spinner, names);

        if(names.size()>1) {
            if (type == 4) {
                setGroupQuantityForm();
            } else setInventoryQuantityForm();
            add_item_btn.setEnabled(true);
        }
        else{
            quantity_and_price_for_inventory.setVisibility(View.GONE);
            quantity_and_price_for_group_layout.setVisibility(View.GONE);
        }

        content_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
                if(position==0){
                    member_id = null;
                    pos = -1;
                    available_quantity.setText("0");
                    price_view.setText("Price(₹)");
                    price_spinner.setEnabled(false);
                    Utility.clear_field(quantity_for_group, "Quantity(Required)");
                    Utility.clear_field(quantity, "Quantity(Required)");
                }
                else{
                    price_spinner.setEnabled(true);
                    pos = position-1;
                    price_spinner.setSelection(0);
                    Utility.clear_field(quantity_for_group, "Quantity(Required)");
                    Utility.clear_field(quantity, "Quantity(Required)");
                    if(type==1) {
                        member_id = bookModelList.get(position-1).getBook_id();
                        available_quantity.setText(String.valueOf(bookModelList.get(pos).getAvailable_quantity()));
                    }
                    else if(type==2) {
                        member_id = cdModelList.get(pos).getCd_id();
                        available_quantity.setText(String.valueOf(cdModelList.get(pos).getAvailable_quantity()));
                    }
                    else if(type==3) {
                        member_id = frameModelList.get(pos).getFrame_id();
                        available_quantity.setText(String.valueOf(frameModelList.get(pos).getAvailable_quantity()));
                    }
                    else if(type==4) {
                        member_id = groupModelList.get(pos).getGroup_id();
                        group_price_details.setText("₹"+groupModelList.get(pos).getGroup_price());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void addItem(){

        if(type==4){
            EventGroupModel eventGroupModel = new EventGroupModel();
            eventGroupModel.setGroup_name(groupModelList.get(pos).getGroup_name());
            int quantity=0;
            String qty_str = quantity_for_group.getText().toString().trim();
            if(qty_str.isEmpty()){
                Toast.makeText(getContext(), "Quantity is mandatory!", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity = Integer.valueOf(qty_str);
            eventGroupModel.setQuantity_available(quantity);
            eventGroupModel.setQuantity_sold(0);
            eventGroupModel.setGroup_id(member_id);
            for(GroupModel groupModel: groupModelList){
                if(Utility.areEqualStrings(member_id, groupModel.getGroup_id())){
                    eventGroupModel.setGroup_price(groupModel.getGroup_price());
                    break;
                }
            }
            eventGroupModels.add(eventGroupModel);
            Toast.makeText(getContext(), "Group added successfully!", Toast.LENGTH_SHORT).show();
        }
        else {
            EventStockModel eventStockModel = new EventStockModel();
            int quantity_ava=0;
            String name = null;
            int quantity_sold = 0;
            double price = 0;
            if(type==1){
                name = bookModelList.get(pos).getName();
            }
            else if(type==2){
                name = cdModelList.get(pos).getName();
            }
            else if(type==3){
                name = frameModelList.get(pos).getName();
            }
            String quatity_ava_str = quantity.getText().toString().trim();
            if(quatity_ava_str.isEmpty()){
                Toast.makeText(getContext(), "Quantity is mandatory!", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity_ava = Integer.valueOf(quatity_ava_str);
            String price_str = price_view.getText().toString();
            if(price_str.compareTo("Price(₹)")==0) {
                Toast.makeText(getContext(), "Item price not selected!", Toast.LENGTH_SHORT).show();
                return;
            }
            price = Double.valueOf(price_str.substring(1));

            StockModel stockModel = new StockModel();
            stockModel.setStock_id(member_id);
            stockModel.setType(Utility.types.get(type));
            stockModel.setStock_name(name);

            eventStockModel.setPrice(price);
            eventStockModel.setStock(stockModel);
            eventStockModel.setQuantity_available(quantity_ava);
            eventStockModel.setQuantity_sold(quantity_sold);

            eventStockModels.add(eventStockModel);

            Toast.makeText(getContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
        }
        hs.add(member_id);

        clearFields_();

        if(!name_event.getText().toString().trim().isEmpty() && !place_event.getText().toString().trim().isEmpty() && date_event_textview.getText().toString().compareTo("Event Date")!=0){
            add_event_btn.setEnabled(true);
        }
        else add_event_btn.setEnabled(false);
    }

    private void resetSpinner(){
        names.clear();
        names.add("Select Item");
    }

    private void clearFields(){
        Utility.clear_field(name_event, "Event Name(Mandatory)");
        Utility.clear_field(place_event, "Event Place(Mandatory)");
    }

    private void clearFields_(){
        Utility.clear_field(quantity_for_group, "Quantity(Required)");
        Utility.clear_field(quantity, "Quantity(Required)");
        type_of_item_spinner.setSelection(0);
        clearForms();
    }

    private void addEvent(){
        Log.d(tag, "began add evnet");
        EventModel eventModel = new EventModel();
        Timestamp timestamp = new Timestamp(date);
        eventModel.setEvent_date(timestamp);
        eventModel.setEvent_name(name_event.getText().toString().trim());
        eventModel.setEvent_place(place_event.getText().toString().trim());
        eventModel.setEvent_stocks(eventStockModels);
        eventModel.setEvent_groups(eventGroupModels);

        eventViewModel.addEventData(eventModel, new Utility.FirestoreCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Event added successfully!", Toast.LENGTH_SHORT).show();
                clearFields_();
                clearForms();
                eventGroupModels.clear();
                eventStockModels.clear();
                tmp.clear();
                recyclerGroupDetailsAdapter.setData(eventGroupModels);
                recyclerInventoryDetailsAdapter.setData(eventStockModels);
                Utility.clear_field(name_event, "Event Name(Mandatory)");
                Utility.clear_field(place_event, "Event Place(Mandatory)");
                date_event_textview.setText("Event Date");
                Log.d(tag, "event added");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void setGroupQuantityForm(){
        quantity_and_price_for_inventory.setVisibility(View.GONE);
        quantity_and_price_for_group_layout.setVisibility(View.VISIBLE);
    }

    private void setInventoryQuantityForm(){
        quantity_and_price_for_group_layout.setVisibility(View.GONE);
        quantity_and_price_for_inventory.setVisibility(View.VISIBLE);
    }

    private void clearForms(){
        quantity_and_price_for_inventory.setVisibility(View.GONE);
        quantity_and_price_for_group_layout.setVisibility(View.GONE);
    }

}