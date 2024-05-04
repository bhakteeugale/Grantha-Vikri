package com.application.granthavikri.ui;

import static com.application.granthavikri.Utility.payment_method;
import static com.application.granthavikri.Utility.tag;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.EventGroupModel;
import com.application.granthavikri.model.EventModel;
import com.application.granthavikri.model.EventStockModel;
import com.application.granthavikri.model.SaleItemModel;
import com.application.granthavikri.model.SaleModel;
import com.application.granthavikri.model.StockModel;
import com.application.granthavikri.model.UpdateEventItemsModel;
import com.application.granthavikri.repository.EventRepository;
import com.application.granthavikri.viewmodel.EventViewModel;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SaleFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SaleFragment extends Fragment {

    private EventViewModel eventViewModel;

    private Spinner event_spinner, item_type_spinner, content_spinner, payment_method_spinner;

    private List<EventModel> eventModelsList;

    private final String content_command = "Select Item";

    private final String event_command = "Select Event";

    private int event_selected = -1;

    private int type_selected = -1;

    private String doc = null;

    private int item_selected = -1;

    private int group_count=0, inventory_count=0;

    private final List<String> types = new ArrayList<>(Arrays.asList("Select Item Type", "Inventory", "Group"));

    private EventRepository eventRepository;

    private List<String> event_names = new ArrayList<>(Arrays.asList(event_command));

    private List<String> content_list = new ArrayList<>(Arrays.asList(content_command));

    private HashSet<String> items_selected = new HashSet<>();

    private List<UpdateEventItemsModel> updateEventItemsModels = new ArrayList<>();

    private EditText edit_quantity_sold;

    private TextView item_price, total_amount, total_amount_of_sale_textview;

    private double total_amount_for_sale = 0;

    private HomeActivity home;

    private int payment_selected = 0;

    private RecyclerSellAdapter recyclerSellAdapter;

    private List<SaleItemModel> saleItemModelList;

    private LinearLayout layout_view_items_button;
    private LinearLayout layout_payment_section_button;

    private LinearLayout form_add_items, form_view_items, form_payment_section;

    public SaleFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SaleFragment.
//     */
//    public static SaleFragment newInstance(String param1, String param2) {
//        SaleFragment fragment = new SaleFragment();
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
        eventViewModel = home.getEventViewModel();
        eventModelsList = new ArrayList<>();
        eventRepository = new EventRepository();
        saleItemModelList = new ArrayList<>();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sale, container, false);

        TextView action_bar_title = (TextView) view.findViewById(R.id.title_action);
        action_bar_title.setText("Add Sale");

        TextView available_amount_for_sale_textview = view.findViewById(R.id.available_amount_for_sale_textview);

        event_spinner = view.findViewById(R.id.event_spinner);
        setEventSpinner();
        eventViewModel.getEventDataList().observe(getViewLifecycleOwner(), eventModels -> {
            int sz = eventModelsList.size();
            eventModelsList = eventModels;
            if(eventModelsList.size()!=sz) setEventSpinner();
            if(item_selected!=-1) {
                for (EventModel eventModel : eventModelsList) {
                    if(Utility.areEqualStrings(eventModelsList.get(event_selected).getEvent_id(), eventModel.getEvent_id())){
                        if(type_selected==1){
                            available_amount_for_sale_textview.setText(""+eventModelsList.get(event_selected).getEvent_stocks().get(item_selected).getQuantity_available());
                        }
                        else if(type_selected==2){
                            available_amount_for_sale_textview.setText(""+eventModelsList.get(event_selected).getEvent_groups().get(item_selected).getQuantity_available());
                        }
                        break;
                    }
                }
            }
        });

        total_amount_of_sale_textview = view.findViewById(R.id.sale_total_amount);
        item_price = view.findViewById(R.id.amount_each_item);
        total_amount = view.findViewById(R.id.total_amount);

        item_type_spinner = view.findViewById(R.id.type_of_stock_spinner);
        Utility.setupSpinner(getContext(), item_type_spinner, types);
        item_type_spinner.setEnabled(false);

        content_spinner = view.findViewById(R.id.stock_spinner);
        Utility.setupSpinner(getContext(), content_spinner, content_list);
        content_spinner.setEnabled(false);

        payment_method_spinner = view.findViewById(R.id.payment_method_spinner);
        Utility.setupSpinner(getContext(), payment_method_spinner, Utility.payment_method);
        payment_method_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payment_selected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button confirm_sale_button = view.findViewById(R.id.confirm_sale_button);
        confirm_sale_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items_selected.size()==0){
                    Toast.makeText(getContext(), "No items added for sale!", Toast.LENGTH_SHORT).show();
                }
                else if(payment_selected<1){
                    Toast.makeText(getContext(), "Select Payment method!", Toast.LENGTH_SHORT).show();
                }
                else{
                    confirmSale();
                    inventory_count=0;
                    group_count=0;
                    total_amount_of_sale_textview.setText("0");
                    saleItemModelList.clear();
                    payment_method_spinner.setSelection(0);
                    recyclerSellAdapter.notifyDataSetChanged();
                }
            }
        });

        edit_quantity_sold = view.findViewById(R.id.sale_quantity);
        edit_quantity_sold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is being changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text has changed
                String text = s.toString().trim();
                if(item_selected!=-1 && !text.isEmpty()){
                    double price = Double.parseDouble(item_price.getText().toString());
                    int count = Integer.parseInt(text);
                    price = price * count;
                    price = Utility.round_off_two((float) price);
                    total_amount.setText(""+price);
                }
                else{
                    total_amount.setText("0");
                }
            }
        });

        TextView current_total = view.findViewById(R.id.current_total);

        Button add_item_button = view.findViewById(R.id.sale_add_item);
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(event_selected!=-1 && type_selected!=-1 && doc!=null && item_selected!=-1){
                    if(items_selected.contains(doc)){
                        Toast.makeText(getContext(), "Item already in sale!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        addItemToSale();
                        total_amount_of_sale_textview.setText(""+total_amount_for_sale);
                        current_total.setText(""+total_amount_for_sale);
                        Toast.makeText(getContext(), "Item successfully added in sale!", Toast.LENGTH_SHORT).show();
                    }
                    item_type_spinner.setSelection(0);
                    Utility.clear_field(edit_quantity_sold, "Enter quantity to be sold");
                    Log.d(tag, "adding item");
                }

                else{
                    Toast.makeText(getContext(), "Item not selected for sale!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView img_add_items = view.findViewById(R.id.img_expand_add_items);
        form_add_items = view.findViewById(R.id.add_items_form);
        LinearLayout layout_add_items_button = view.findViewById(R.id.button_linearlayout_for_add_items);
        setTransition(layout_add_items_button, form_add_items, img_add_items);


        ImageView img_view_items = view.findViewById(R.id.img_expand_view_items);
        form_view_items = view.findViewById(R.id.list_of_sale_items);
        layout_view_items_button = view.findViewById(R.id.button_linearlayout_for_view_sale_items);
        setTransition(layout_view_items_button, form_view_items, img_view_items);

        ImageView img_payment_section = view.findViewById(R.id.img_expand_payment_section);
        form_payment_section = view.findViewById(R.id.layout_for_payment_section);
        layout_payment_section_button = view.findViewById(R.id.button_linearlayout_for_payment_section);
        setTransition(layout_payment_section_button, form_payment_section, img_payment_section);



        event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_type_spinner.setSelection(0);
                resetSpinner(content_list, content_command);
                Utility.setupSpinner(getContext(), content_spinner, content_list);
                content_spinner.setEnabled(false);
                type_selected = -1;
                doc = null;
                item_selected = -1;
                item_price.setText("0");
                total_amount.setText("0");
                if(position==0){
                    item_type_spinner.setEnabled(false);
                    event_selected = -1;
                }
                else{
                    item_type_spinner.setEnabled(true);
                    event_selected = position-1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        item_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetSpinner(content_list, content_command);
                content_spinner.setSelection(0);
                doc = null;
                item_selected = -1;
                item_price.setText("0");
                total_amount.setText("0");
                if(position==0){
                    content_spinner.setEnabled(false);
                    type_selected = -1;
                    item_selected = -1;
                }
                else {
                    type_selected = position;
                    EventModel eventModel = eventModelsList.get(event_selected);
                    if(position==2){
                        //group
                        if(eventModel.getEvent_groups()==null || eventModel.getEvent_groups().size()==0){
                            resetSpinner(content_list, "No Groups");
                        }
                        else {
                            for (EventGroupModel eventGroupModel : eventModel.getEvent_groups()) {
                                content_list.add(eventGroupModel.getGroup_name());
                            }
                        }
                    }
                    else {
                        if(eventModel.getEvent_stocks()==null || eventModel.getEvent_stocks().size()==0){
                            resetSpinner(content_list, "No Inventory items");
                        }
                        else {
                            for (EventStockModel eventStockModel : eventModel.getEvent_stocks()) {
                                StockModel stockModel = eventStockModel.getStock();
                                content_list.add(stockModel.getStock_name()+" - "+stockModel.getType());
                            }
                        }
                    }
                    Utility.setupSpinner(getContext(), content_spinner, content_list);
                    if(content_list.size()>1) content_spinner.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        content_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    available_amount_for_sale_textview.setText("0");
                    doc = null;
                    item_selected = -1;
                    item_price.setText("0");
                    total_amount.setText("0");
                }
                else{
                    item_selected = position-1;
                    Log.d(tag, "Item selected"+item_selected);
                    EventModel eventModel= eventModelsList.get(event_selected);
                    double price = 0;
                    if(type_selected==2){
                        doc = eventModelsList.get(event_selected).getEvent_groups().get(item_selected).getGroup_id();
                        available_amount_for_sale_textview.setText(""+eventModel.getEvent_groups().get(item_selected).getQuantity_available());
                        price = eventModel.getEvent_groups().get(item_selected).getGroup_price();

                    }
                    else{
                        doc = eventModelsList.get(event_selected).getEvent_stocks().get(item_selected).getStock().getStock_id();
                        available_amount_for_sale_textview.setText(""+eventModel.getEvent_stocks().get(item_selected).getQuantity_available());
                        price = eventModel.getEvent_stocks().get(item_selected).getPrice();
                    }
                    item_price.setText(""+price);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.list_of_sold_elements);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));;
        recyclerSellAdapter = new RecyclerSellAdapter(getContext(), saleItemModelList);
        recyclerView.setAdapter(recyclerSellAdapter);

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG1", "pressed on back button");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                home.switchScreens();
                fragmentManager.beginTransaction().remove(SaleFragment.this).commit();

            }
        });


        return view;
    }

    private void setEventSpinner(){
        resetSpinner(event_names, event_command);
        for(EventModel eventModel: eventModelsList){
            String option = eventModel.getEvent_name() + " - " + Utility.setFormat(eventModel.getEvent_date().toDate());
            event_names.add(option);
        }
        Utility.setupSpinner(getContext(), event_spinner, event_names);
        if(event_names.size()>1) {
            item_type_spinner.setEnabled(true);
        }
    }

    private void resetSpinner(List<String> spinner_lst, String command){
        spinner_lst.clear();
        spinner_lst.add(command);
    }

    private void addItemToSale(){
        String str_quantity = edit_quantity_sold.getText().toString().trim();

        if(str_quantity.isEmpty()){
            Toast.makeText(getContext(), "Enter quantity to be sold!", Toast.LENGTH_SHORT).show();
            return;
        }
        total_amount_for_sale += Double.parseDouble(total_amount.getText().toString());
        int quantity_sold = Integer.parseInt(str_quantity);
        UpdateEventItemsModel updateEventItemsModel = new UpdateEventItemsModel();
        SaleItemModel saleItemModel = new SaleItemModel();

        updateEventItemsModel.setId(doc);
        saleItemModel.setId(doc);

        updateEventItemsModel.setQuantity_sold(quantity_sold);
        saleItemModel.setQuantity_sold(quantity_sold);


        float value = Float.valueOf(total_amount.getText().toString());
        updateEventItemsModel.setAmount(value);
        saleItemModel.setTotal_amount(value);

        int quantity_ava = 0;
        // inventory
        if(type_selected==1){
            quantity_ava = eventModelsList.get(event_selected).getEvent_stocks().get(item_selected).getQuantity_available();
            updateEventItemsModel.setType(eventModelsList.get(event_selected).getEvent_stocks().get(item_selected).getStock().getType());
            saleItemModel.setAmount((float) eventModelsList.get(event_selected).getEvent_stocks().get(item_selected).getPrice());
            saleItemModel.setName(eventModelsList.get(event_selected).getEvent_stocks().get(item_selected).getStock().getStock_name());
            inventory_count+=1;
        }
        //group
        else{
            quantity_ava = eventModelsList.get(event_selected).getEvent_groups().get(item_selected).getQuantity_available();
            updateEventItemsModel.setType(types.get(type_selected));
            saleItemModel.setAmount((float) eventModelsList.get(event_selected).getEvent_groups().get(item_selected).getGroup_price());
            saleItemModel.setName(eventModelsList.get(event_selected).getEvent_groups().get(item_selected).getGroup_name());
            group_count+=1;
        }
        saleItemModel.setType(updateEventItemsModel.getType());
        saleItemModelList.add(saleItemModel);
        recyclerSellAdapter.setData(saleItemModelList);
        quantity_ava -= quantity_sold;
        updateEventItemsModel.setQuantity_available(quantity_ava);
        updateEventItemsModels.add(updateEventItemsModel);
        items_selected.add(doc);
    }


    private void confirmSale(){
        SaleModel saleModel = new SaleModel();
        saleModel.setItems_sold(updateEventItemsModels);
        saleModel.setTime(Timestamp.now());
        saleModel.setTotal_amount(Double.parseDouble(total_amount_of_sale_textview.getText().toString()));
        saleModel.setGroup_count(group_count);
        saleModel.setInventory_count(inventory_count);
        saleModel.setPayment_method(payment_method.get(payment_selected));
        eventRepository.addSaleToEvent(eventModelsList.get(event_selected).getEvent_id(), saleModel, new Utility.FirestoreCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "successful ", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Successfully confirmed sale!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "fail ", Toast.LENGTH_SHORT).show();
            }
        });
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
}