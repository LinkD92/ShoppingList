package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandBundleToList;
import com.symbol.shoppinglistv2.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


public class FragmentBundleToList extends Fragment {

    private SearchableSpinner searchableSpinner;
    private ImageButton imageButton;

    public FragmentBundleToList() {
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
        View v =  inflater.inflate(R.layout.fragment_bundle_to_list, container, false);
        searchableSpinner = v.findViewById(R.id.ssBundleTolist);
        imageButton = v.findViewById(R.id.ibtnAddBundleToList);

        executeCommand(new CommandBundleToList(imageButton, searchableSpinner ));
        return v;
    }

    private void executeCommand(Command command){command.execute();}
}