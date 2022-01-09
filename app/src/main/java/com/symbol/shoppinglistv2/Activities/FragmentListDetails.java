package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandListDetails;
import com.symbol.shoppinglistv2.R;

public class FragmentListDetails extends Fragment {

    private RecyclerView rvListDetails;

    public FragmentListDetails() {
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
        View v = inflater.inflate(R.layout.fragment_list_details, container, false);
        rvListDetails =v.findViewById(R.id.rvListDetails);

        executeCommand(new CommandListDetails(rvListDetails));


        return v;
    }

    private void executeCommand(Command command){command.execute();}
}