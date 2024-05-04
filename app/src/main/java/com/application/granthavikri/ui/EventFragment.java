package com.application.granthavikri.ui;

import static com.application.granthavikri.Utility.tag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.EventModel;
import com.application.granthavikri.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;


public class EventFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<EventModel> eventModelList;

    private EventViewModel eventViewModel;

    public EventFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment EventFragment.
//     */
//    public static EventFragment newInstance(String param1, String param2) {
//        EventFragment fragment = new EventFragment();
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

        }
        eventModelList = new ArrayList<>();
        HomeActivity home = (HomeActivity) getActivity();
        eventViewModel = home.getEventViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        TextView actionbar_title = view.findViewById(R.id.subactionbar_card2_title);
        actionbar_title.setText("Add Event");

        recyclerView = view.findViewById(R.id.event_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Utility.OnItemClickListener eventClickListener = new Utility.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goToEventDetailsFragment(position);
            }
        };
        RecyclerEventAdapter recyclerEventAdapter = new RecyclerEventAdapter(getContext(), eventModelList, eventClickListener);
        recyclerView.setAdapter(recyclerEventAdapter);
        if(eventViewModel!=null){
            eventViewModel.getEventDataList().observe(getViewLifecycleOwner(), eventModels -> {
                eventModelList = eventModels;
                recyclerEventAdapter.setData(eventModelList);
                Log.d(tag, "size of list: "+eventModels.size());
            });
        }

        LinearLayout add_event_view = view.findViewById(R.id.add_objects_btn);

        add_event_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new AddEventFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public EventViewModel getEventViewModel() {
        return eventViewModel;
    }
    private void goToEventDetailsFragment(int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", eventModelList.get(position));
        EventDetailsFragment detailedViewFragment = new EventDetailsFragment();
        detailedViewFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detailedViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}