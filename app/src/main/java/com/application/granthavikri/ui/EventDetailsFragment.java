package com.application.granthavikri.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.EventModel;
import com.application.granthavikri.model.EventStockModel;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link EventDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class EventDetailsFragment extends Fragment {

    private EventModel eventModel;


    public EventDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment EventDetailsFragment.
//     */
//    public static EventDetailsFragment newInstance(String param1, String param2) {
//        EventDetailsFragment fragment = new EventDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventModel = (EventModel) getArguments().get("event");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        TextView action_bar_title = (TextView) view.findViewById(R.id.title_action);
        action_bar_title.setText(eventModel.getEvent_name());

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG1", "pressed on back button");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        TextView main_name_title = view.findViewById(R.id.event_det_textview);
        main_name_title.setText(eventModel.getEvent_name());

        TextView main_date_title = view.findViewById(R.id.date_det_view);
        main_date_title.setText(Utility.setFormat(eventModel.getEvent_date().toDate()));

        TextView event_det_name = view.findViewById(R.id.event_det_name);
        event_det_name.setText(eventModel.getEvent_name());

        TextView event_det_place = view.findViewById(R.id.event_det_place);
        event_det_place.setText(eventModel.getEvent_place());

        TextView event_det_date = view.findViewById(R.id.event_det_date);
        event_det_date.setText(Utility.setFormat(eventModel.getEvent_date().toDate()));

        RecyclerView recyclerView_group = view.findViewById(R.id.group_items_in_event_event_det);
        recyclerView_group.setLayoutManager(new GridLayoutManager(getContext(), 1));

        RecyclerView recyclerView_inventory = view.findViewById(R.id.inventory_items_in_event_event_det);
        recyclerView_inventory.setLayoutManager(new GridLayoutManager(getContext(), 1));

        LinearLayout layout_inventory = view.findViewById(R.id.view_inventory_items_details_event_det);
        LinearLayout layout_grp = view.findViewById(R.id.view_group_items_details_event_det);

        Spinner type_of_item_spinner = view.findViewById(R.id.type_of_item_spinner_event_det);
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
                    RecyclerGroupDetailsAdapter recyclerGroupDetailsAdapter = new RecyclerGroupDetailsAdapter(eventModel.getEvent_groups(), getContext());
                    recyclerView_group.setAdapter(recyclerGroupDetailsAdapter);
                    layout_grp.setVisibility(View.VISIBLE);
                }
                else {
                    String type = Utility.types.get(position);
                    List<EventStockModel> tmp = new ArrayList<>();
                    for(EventStockModel eventStockModel: eventModel.getEvent_stocks()){
                        if(type.compareTo(eventStockModel.getStock().getType())==0) tmp.add(eventStockModel);
                    }
                    layout_grp.setVisibility(View.GONE);
                    RecyclerInventoryDetailsAdapter recyclerInventoryDetailsAdapter = new RecyclerInventoryDetailsAdapter(getContext(), tmp);
                    recyclerView_inventory.setAdapter(recyclerInventoryDetailsAdapter);
                    layout_inventory.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }
}