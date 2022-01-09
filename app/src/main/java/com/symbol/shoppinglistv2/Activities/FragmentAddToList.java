package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandBNVAddToList;
import com.symbol.shoppinglistv2.R;


public class FragmentAddToList extends Fragment {

    private BottomNavigationView bnvAddToList;
    private View fragmentContainer;

    public FragmentAddToList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_to_list, container, false);
        bnvAddToList = v.findViewById(R.id.bnvAddToList);
        fragmentContainer = v.findViewById(R.id.clAddToListContainer);

        executeCommand(new CommandBNVAddToList(fragmentContainer, bnvAddToList));


        return v;
    }

    private void executeCommand(Command command){command.execute();}

}