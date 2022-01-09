package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandFMbnvActions;
import com.symbol.shoppinglistv2.Command.CommandManageFAB;
import com.symbol.shoppinglistv2.R;

public class FragmentMyManager extends Fragment {

    private final String TAG = "FragmentMyManager";
    public BottomNavigationView bottomNavigationView;
    public FloatingActionButton floatingActionButton;
    public ConstraintLayout fragmentContainer;
    public ConstraintLayout fragmentContainerFAB;
    public MutableLiveData tracker = new MutableLiveData();


    public FragmentMyManager() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manager, container, false);
        bottomNavigationView = v.findViewById(R.id.bnvFragmentMyManage);
        floatingActionButton = v.findViewById(R.id.fabFragmentMyManage);
        fragmentContainer = v.findViewById(R.id.clFragmentManageContainer);
        fragmentContainerFAB = v.findViewById(R.id.clFragmentManageContainerFAB);

        executeCommand(new CommandFMbnvActions(bottomNavigationView, fragmentContainer, tracker));
        executeCommand(new CommandManageFAB(floatingActionButton, fragmentContainerFAB, tracker));

        return v;
    }

    private void executeCommand(Command command){command.execute();}

    public View getFabContainer(){
        return fragmentContainerFAB;
    }
}