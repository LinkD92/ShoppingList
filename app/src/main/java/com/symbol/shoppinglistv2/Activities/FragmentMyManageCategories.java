package com.symbol.shoppinglistv2.Activities;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandCategoryDisplay;

import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentMyManageCategories extends FragmentMyManager {
    private final String TAG = "com.symbol.shoppinglistv2.Activities.FragmentMyManageCategories";
    private RecyclerView rvCategoryAdapter;
    private ConstraintLayout clContainer;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_manage_category, container, false);
        View v2 = inflater.inflate(R.layout.fragment_manager, container, false);

        clContainer = v2.findViewById(R.id.clFragmentManageContainerFAB);

        rvCategoryAdapter = v.findViewById(R.id.rvCategoryAdapter);
        executeCommand(new CommandCategoryDisplay(rvCategoryAdapter, clContainer));


        return v;

    }

    private void executeCommand(Command command){
        command.execute();
    }
}