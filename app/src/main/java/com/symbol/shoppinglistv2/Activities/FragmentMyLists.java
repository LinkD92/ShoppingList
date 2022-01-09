package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandAddProductFAB;
import com.symbol.shoppinglistv2.Command.CommandMABundleDisplay;
import com.symbol.shoppinglistv2.Command.CommandMAProductDisplay;
import com.symbol.shoppinglistv2.Command.CommandMASpinnerAdapter;
import com.symbol.shoppinglistv2.Command.CommandManageLists;
import com.symbol.shoppinglistv2.Components.ListHashMap;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Other.BundleDetailsFiller;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentMyLists extends Fragment {

    private final String TAG = "com.symbol.shoppinglistv2.Activities.FragmentMyLists";
    public Spinner spinList;
    public RecyclerView rvProducts;
    public FloatingActionButton floatingActionButton;
    public View fragmentContainer;
    private ImageButton ibtnListDetails;
    private ImageButton ibtnListOptions;
    private RecyclerView rvBundles;


    public FragmentMyLists() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lists, container, false);
        View v2 = inflater.inflate(R.layout.fragment_manager, container, false);

        spinList = v.findViewById(R.id.spinLists);
        rvProducts = v.findViewById(R.id.rvProducts);
        rvBundles = v.findViewById(R.id.rvBundles);
        floatingActionButton = v.findViewById(R.id.fab);
        fragmentContainer = v.findViewById(R.id.clFragmentListsContainer);
        ibtnListOptions = v.findViewById(R.id.ibtnListOptions);
        ibtnListDetails = v.findViewById(R.id.ibtnListDetails);

        executeCommand(new CommandMASpinnerAdapter(spinList));
        executeCommand(new CommandMAProductDisplay(spinList, rvProducts, fragmentContainer, rvBundles));
        executeCommand(new CommandAddProductFAB(floatingActionButton, fragmentContainer));
        executeCommand(new CommandManageLists(ibtnListDetails, ibtnListOptions, fragmentContainer));
        return v;
    }



    private void executeCommand(Command command){
        command.execute();
    }
}