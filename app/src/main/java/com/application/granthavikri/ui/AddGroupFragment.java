package com.application.granthavikri.ui;

import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.FrameModel;
import com.application.granthavikri.model.GroupModel;
import com.application.granthavikri.model.StockModel;
import com.application.granthavikri.viewmodel.BookViewModel;
import com.application.granthavikri.viewmodel.CDViewModel;
import com.application.granthavikri.viewmodel.FrameViewModel;
import com.application.granthavikri.viewmodel.GroupViewModel;
import com.google.firebase.Timestamp;

import java.util.List;

import java.util.ArrayList;
import java.util.HashSet;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddGroupFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddGroupFragment extends Fragment {

    private Spinner content_spinner, type_spinner;

    private String member_id;

    private Button add_member_btn, add_grp_btn;

    private List<String> type;

    private List<String> names;

    private ArrayList<StockModel> stockModelArrayList;

    private EditText edit_group_name, edit_group_price;

    private RecyclerView recyclerView;

    private String chosen;

    private RecyclerGroupSelectionAdapter recyclerGroupSelectionAdapter;

    private HashSet<String> hs;
    private BookViewModel bookViewModel;

    private CDViewModel cdViewModel;

    private FrameViewModel frameViewModel;

    private HomeActivity home;

    ArrayList<StockModel> content;

    private GroupViewModel groupViewModel;

    private int pos=-1;

    private GroupFragment groupFragment;

    public AddGroupFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddGroupFragment.
//     */
//    public static AddGroupFragment newInstance(String param1, String param2) {
//        AddGroupFragment fragment = new AddGroupFragment();
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
        hs = new HashSet<>();
        stockModelArrayList = new ArrayList<>();
        names = new ArrayList<>();
        content = new ArrayList<>();
        bookViewModel = home.getBookViewModel();
        frameViewModel = home.getFrameViewModel();
        cdViewModel = home.getCdViewModel();
        type = Utility.types.subList(0, 4);
        member_id = null;
//        groupFragment = (GroupFragment) home.getSupportFragmentManager().findFragmentByTag("groupFragment");
        groupViewModel = home.getGroupViewModel();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        edit_group_name = view.findViewById(R.id.group_name_edit);
        edit_group_price = view.findViewById(R.id.group_price_edit);

        recyclerView = view.findViewById(R.id.recyclerview_grp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerGroupSelectionAdapter = new RecyclerGroupSelectionAdapter(getContext(), stockModelArrayList);
        recyclerView.setAdapter(recyclerGroupSelectionAdapter);

        TextView title = view.findViewById(R.id.title_action);
        title.setText("Add Group");

        content_spinner = view.findViewById(R.id.stock_spinner);
        Utility.setupSpinner(getContext(), content_spinner, names);
        content_spinner.setEnabled(false);

        type_spinner = view.findViewById(R.id.type_of_stock_spinner);
        Utility.setupSpinner(getContext(), type_spinner, type);
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosen = type.get(position);
                setSpinner(type.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add_member_btn = view.findViewById(R.id.add_member_btn);
        add_member_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member_id!=null){
                    if(hs.contains(member_id)){
                        Toast.makeText(getContext(), "Item alredy added!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addItem();
                    }
                    member_id = null;
                    pos= -1;
                }
                else{
                    Toast.makeText(getContext(), "No item selected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_grp_btn = view.findViewById(R.id.add_group_buttonn);
        add_grp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = addGroup();
                if(check==-1){
                    Toast.makeText(getContext(), "Group Name is mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(check==-2){
                    Toast.makeText(getContext(), "Group Price is mandatory!", Toast.LENGTH_SHORT).show();
                }

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


        return view;
    }

    private void setSpinner(String chosen){
        content.clear();
        boolean flag = false;
        resetSpinner();
        if(chosen.compareTo(type.get(0))==0){
            Utility.setupSpinner(getContext(), content_spinner, names);
            content_spinner.setEnabled(false);
            return;
        }
        // book
        else if(chosen.compareTo(type.get(1))==0){
            List<BookModel> bookModelList = bookViewModel.getBookDataList().getValue();
            if(bookModelList.size()==0){
                flag = true;
            }
            else{
                for(BookModel bookModel: bookModelList){
                    StockModel stockModel = new StockModel();
                    stockModel.setStock_id(bookModel.getBook_id());
                    stockModel.setStock_name(bookModel.getName());
                    stockModel.setType(type.get(1));
                    names.add(bookModel.getName());
                    content.add(stockModel);
                }
            }

        }
        //cd
        else if(chosen.compareTo(type.get(2))==0){
            List<CDModel> cdModelList = cdViewModel.getCDDataList().getValue();
            if(cdModelList.size()==0){
                flag = true;
            }
            else {
                for (CDModel cdModel : cdModelList) {
                    StockModel stockModel = new StockModel();
                    stockModel.setStock_id(cdModel.getCd_id());
                    stockModel.setStock_name(cdModel.getName());
                    stockModel.setType(type.get(2));
                    names.add(cdModel.getName());
                    content.add(stockModel);
                }
            }
        }
        //frame
        else if(chosen.compareTo(type.get(3))==0){
            List<FrameModel> frameModelList = frameViewModel.getFrameData().getValue();
            if(frameModelList.size()==0){
                flag = true;
            }
            else {
                for (FrameModel frameModel : frameModelList) {
                    StockModel stockModel = new StockModel();
                    stockModel.setStock_id(frameModel.getFrame_id());
                    stockModel.setStock_name(frameModel.getName());
                    stockModel.setType(type.get(3));
                    names.add(frameModel.getName());
                    content.add(stockModel);
                }
            }
        }

        if(flag){
            names.clear();
            names.add("No "+chosen+"s");
        }

        content_spinner.setEnabled(true);
        Utility.setupSpinner(getContext(), content_spinner, names);

        content_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
                if(position==0){
                    member_id = null;
                    pos = -1;
                }
                else{
                    member_id = content.get(position-1).getStock_id().toString();
                    pos = position-1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void addItem(){
        hs.add(member_id);

        stockModelArrayList.add(content.get(pos));
        recyclerGroupSelectionAdapter.notifyItemInserted(stockModelArrayList.size()-1);

        if(stockModelArrayList.size()>1) add_grp_btn.setEnabled(true);
    }

    private int addGroup(){
        String group_name = edit_group_name.getText().toString().trim();
        if(group_name.isEmpty()){
            return -1;
        }

        String group_price = edit_group_price.getText().toString().trim();
        if(group_price.isEmpty()){
            return -2;
        }
        float price = 0;
        price = Float.valueOf(group_price);

        GroupModel group = new GroupModel();
        group.setGroup_name(group_name);
        group.setGroup_price(price);
        group.setGroup_date(Timestamp.now());
        group.setGroup_stocks(stockModelArrayList);

        groupViewModel.addGroupData(group, new Utility.FirestoreCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Group added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
                stockModelArrayList.clear();
                recyclerGroupSelectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Error occurred! Try again!", Toast.LENGTH_SHORT).show();
            }
        });
        return 1;
    }

    private void resetSpinner(){
        names.clear();
        names.add("Select Item");
    }

    private void clearFields(){
        Utility.clear_field(edit_group_name, "Group Name(Mandatory)");
        Utility.clear_field(edit_group_price, "Group Price(Mandatory)");
        pos = -1;
        member_id = null;
    }
}