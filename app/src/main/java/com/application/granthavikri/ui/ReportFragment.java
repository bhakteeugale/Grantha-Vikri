package com.application.granthavikri.ui;

import static com.application.granthavikri.Utility.tag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.application.granthavikri.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ReportFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ReportFragment extends Fragment {

    private String event_command = "Select Event";

    private List<String> event_names = new ArrayList<>(Arrays.asList(event_command));

    private Spinner event_spinner;

    private EventViewModel eventViewModel;

    private HomeActivity home;

    private List<EventModel> events;

    private RecyclerView recyclerView;

    private RecyclerReportAdapter recyclerReportAdapter;

    private List<Object> dataList;

    public ReportFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ReportFragment.
//     */
//    public static ReportFragment newInstance(String param1, String param2) {
//        ReportFragment fragment = new ReportFragment();
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
        dataList = new ArrayList<>();
        events = new ArrayList<>();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        TextView action_bar_title = (TextView) view.findViewById(R.id.title_action);
        action_bar_title.setText("Reports");

        event_spinner = view.findViewById(R.id.event_spinner);

        eventViewModel.getEventDataList().observe(getViewLifecycleOwner(), eventModels -> {
            events = eventModels;
            setEventSpinner();
        });

        recyclerView = view.findViewById(R.id.report_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerReportAdapter = new RecyclerReportAdapter(getContext(), dataList);
        recyclerView.setAdapter(recyclerReportAdapter);

        event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(dataList.size()>0) {
                    dataList.clear();
                }
                else if(position!=0){
                    dataList.addAll(events.get(position-1).getEvent_groups());
                    dataList.addAll(events.get(position-1).getEvent_stocks());
                    recyclerReportAdapter.setData(dataList);
                    Log.d(tag, "size "+ dataList.size());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG1", "pressed on back button");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                home.switchScreens();
                // Remove the current fragment
                fragmentManager.beginTransaction().remove(ReportFragment.this).commit();

            }
        });
        return view;
    }

    private void setEventSpinner(){
        if(events.size()!=0 && events.size()>event_names.size()-1){
            for(int i=event_names.size()-1;i<events.size();i++){
                String option = events.get(i).getEvent_name() + " - " + Utility.setFormat(events.get(i).getEvent_date().toDate());
                event_names.add(option);
            }
        }
        Utility.setupSpinner(getContext(), event_spinner, event_names);
        event_spinner.setSelection(0);
    }
}