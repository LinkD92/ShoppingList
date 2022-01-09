package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandBundleList;
import com.symbol.shoppinglistv2.R;


public class FragmentMyManageBundles extends Fragment {

    private RecyclerView rvBundleList;
    private View container;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_bundles, container, false);
        View v2 = inflater.inflate(R.layout.fragment_manager, container, false);
        rvBundleList = v.findViewById(R.id.rvBundles);
        container = v2.findViewById(R.id.clFragmentManageContainerFAB);

        executeCommand(new CommandBundleList(rvBundleList, container));

        return v;
    }

    private void executeCommand(Command command){command.execute();}
}