package com.application.granthavikri.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.model.BookModel;
import com.application.granthavikri.model.CDModel;
import com.application.granthavikri.model.FrameModel;
import com.application.granthavikri.viewmodel.BookViewModel;
import com.application.granthavikri.viewmodel.CDViewModel;
import com.application.granthavikri.viewmodel.FrameViewModel;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link InventoryFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class InventoryFragment extends Fragment{

    private RecyclerView recyclerView_book, recyclerView_cd, recyclerView_frame;

    private List<BookModel> arrayBooks;

    private List<CDModel> arrayCds;

    private List<FrameModel> arrayFrames;

    HomeActivity home;

    private BookViewModel bookViewModel;

    private FrameViewModel frameViewModel;

    private CDViewModel cdViewModel;

    private Spinner type_spinner;

    public InventoryFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment InventoryFragment.
//     */
//    public static InventoryFragment newInstance(String param1, String param2) {
//        InventoryFragment fragment = new InventoryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayBooks = new ArrayList<>();
        arrayCds = new ArrayList<>();
        arrayFrames = new ArrayList<>();
        if(getActivity()!=null){
            home = (HomeActivity)getActivity();
            bookViewModel = home.getBookViewModel();
            frameViewModel = home.getFrameViewModel();
            cdViewModel = home.getCdViewModel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        TextView actionbar_title = view.findViewById(R.id.subactionbar_card2_title);
        actionbar_title.setText(R.string.inventory);

        LinearLayout add_stock_view = view.findViewById(R.id.add_objects_btn);
        add_stock_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new AddStockFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        recyclerView_book = view.findViewById(R.id.book_list_recyclerview);
        recyclerView_book.setLayoutManager(new LinearLayoutManager(getContext()));
        Utility.OnItemClickListener bookClickListener = new Utility.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goToDetailsFragment(arrayBooks.get(position));
            }
        };
        RecyclerBookAdapter book_adapter = new RecyclerBookAdapter(getContext(), arrayBooks, bookClickListener);
        recyclerView_book.setAdapter(book_adapter);
        if(bookViewModel!=null){
            bookViewModel.getBookDataList().observe(getViewLifecycleOwner(), bookModels -> {
                arrayBooks = bookModels;
                book_adapter.setData(arrayBooks);
            });
        }

        recyclerView_cd = view.findViewById(R.id.cd_list_recyclerview);
        recyclerView_cd.setLayoutManager(new LinearLayoutManager(getContext()));
        Utility.OnItemClickListener cdClickListener = new Utility.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goToDetailsFragment(arrayCds.get(position));
            }
        };
        RecyclerCDAdapter cd_adapter = new RecyclerCDAdapter(getContext(), arrayCds, cdClickListener);
        recyclerView_cd.setAdapter(cd_adapter);
        if(cdViewModel!=null){
            cdViewModel.getCDDataList().observe(getViewLifecycleOwner(), cdModelList -> {
                arrayCds = cdModelList;
                cd_adapter.setData(arrayCds);
            });
        }

        recyclerView_frame = view.findViewById(R.id.frame_list_recyclerview);
        recyclerView_frame.setLayoutManager(new LinearLayoutManager(getContext()));
        Utility.OnItemClickListener frameClickListener = new Utility.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goToDetailsFragment(arrayFrames.get(position));
            }
        };
        RecyclerFrameAdapter frame_adapter = new RecyclerFrameAdapter(getContext(), arrayFrames, frameClickListener);
        recyclerView_frame.setAdapter(frame_adapter);
        if(frameViewModel!=null){
            frameViewModel.getFrameData().observe(getViewLifecycleOwner(), frameModelList -> {
                arrayFrames = frameModelList;
                frame_adapter.setData(arrayFrames);
            });
        }

        type_spinner = view.findViewById(R.id.type_inventory_spinner);
        Utility.setupSpinner(getContext(), type_spinner, Utility.types.subList(0, 4));
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    recyclerView_book.setVisibility(View.VISIBLE);
                    recyclerView_cd.setVisibility(View.GONE);
                    recyclerView_frame.setVisibility(View.GONE);
                }
                else if(position==2){
                    recyclerView_book.setVisibility(View.GONE);
                    recyclerView_cd.setVisibility(View.VISIBLE);
                    recyclerView_frame.setVisibility(View.GONE);
                }
                else if(position==3){
                    recyclerView_book.setVisibility(View.GONE);
                    recyclerView_cd.setVisibility(View.GONE);
                    recyclerView_frame.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView_book.setVisibility(View.GONE);
                    recyclerView_cd.setVisibility(View.GONE);
                    recyclerView_frame.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public <T> void goToDetailsFragment(T data) {
        // Handle click event here
        // You can get the clicked item position from the parameter
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        if(data instanceof BookModel) {
            BookModel bookData = (BookModel) data;
            bundle.putSerializable("item", bookData);
        }
        else if(data instanceof CDModel){
            CDModel cdData = (CDModel) data;
            bundle.putSerializable("item", cdData);
        }
        else if(data instanceof FrameModel){
            FrameModel frameData = (FrameModel) data;
            bundle.putSerializable("item", frameData);
        }
        StockDetailsFragment detailedViewFragment = new StockDetailsFragment();
        detailedViewFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detailedViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}