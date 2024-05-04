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
import com.application.granthavikri.model.GroupModel;
import com.application.granthavikri.viewmodel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link GroupFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class GroupFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<GroupModel> groupArrayList;

    private GroupViewModel groupViewModel;


    public GroupFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment GroupFragment.
//     */
//    public static GroupFragment newInstance(String param1, String param2) {
//        GroupFragment fragment = new GroupFragment();
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
        groupArrayList = new ArrayList<>();
        HomeActivity home = (HomeActivity) getActivity();
        groupViewModel = home.getGroupViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        TextView actionbar_title = view.findViewById(R.id.subactionbar_card2_title);
        actionbar_title.setText("Add Group");

        recyclerView = view.findViewById(R.id.group_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Utility.OnItemClickListener groupClickListener = new Utility.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goToGroupDetailsFragment(position);
            }
        };
        RecyclerGroupAdapter recyclerGroupAdapter = new RecyclerGroupAdapter(getContext(), groupArrayList, groupClickListener);
        recyclerView.setAdapter(recyclerGroupAdapter);
        if(groupViewModel!=null){
            groupViewModel.getGroupDataList().observe(getViewLifecycleOwner(), groupModels -> {
                groupArrayList = groupModels;
                recyclerGroupAdapter.setData(groupArrayList);
            });
        }

        LinearLayout add_group_view = view.findViewById(R.id.add_objects_btn);

        add_group_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new AddGroupFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public void goToGroupDetailsFragment(int position) {
        // Handle click event here
        // You can get the clicked item position from the parameter
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", groupArrayList.get(position));
        GroupDetailsFragment detailedViewFragment = new GroupDetailsFragment();
        detailedViewFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detailedViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    public GroupViewModel getGroupViewModel() {
//        return groupViewModel;
//    }
}