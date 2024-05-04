package com.application.granthavikri.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.GroupModel;
import com.application.granthavikri.model.StockModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link GroupDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class GroupDetailsFragment extends Fragment {

    private GroupModel groupModel;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public GroupDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment GroupDetailsFragment.
//     */
//    public static GroupDetailsFragment newInstance(String param1, String param2) {
//        GroupDetailsFragment fragment = new GroupDetailsFragment();
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
            groupModel = (GroupModel) args.get("group");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_group_details, container, false);
        //action bar setting
        TextView action_bar_title = (TextView) view.findViewById(R.id.title_action);
        action_bar_title.setText(groupModel.getGroup_name());

        ImageView back_btn = view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG1", "pressed on back button");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        TextView grp_title = view.findViewById(R.id.group_det_textview);
        grp_title.setText(groupModel.getGroup_name());
        TextView grp_date_textview = view.findViewById(R.id.date_det_view);
        grp_date_textview.setText("Added on " + Utility.setFormat(groupModel.getGroup_date().toDate()));
        TextView grp_price_textview = view.findViewById(R.id.price_det_view);
        grp_price_textview.setText("Group Price ₹"+groupModel.getGroup_price());
        expandableListView = view.findViewById(R.id.expandableListView);
        List<StockModel> arrayList = groupModel.getGroup_stocks()!=null ? groupModel.getGroup_stocks() : new ArrayList<>();
        expandableListDetail = ExpandableListDataPump.getData(arrayList);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        return view;
    }
}