package com.symbol.shoppinglistv2.Command;

import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Activities.FragmentManageBundles;
import com.symbol.shoppinglistv2.Activities.FragmentManageCategories;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
//Bottom navigation view in Management Fragment actions
public class CommandFMbnvActions implements Command{
    private final String TAG = "CommandFMbnvActions";

    private FragmentMyOpener fragmentMyOpener;
    private FragmentManageBundles fragmentManageBundles = new FragmentManageBundles();
    private FragmentManageCategories fragmentManageCategories = new FragmentManageCategories();

    private BottomNavigationView bottomNavigationView;

    private MutableLiveData tracker;
    //tracke is a listener to check which fragment is currently open - required to assign proper action for FAB
    private View container;



    public CommandFMbnvActions(BottomNavigationView bottomNavigationView, View container,  MutableLiveData tracker){
        this.bottomNavigationView = bottomNavigationView;
        this.container = container;
        this.tracker = tracker;

    }

    @Override
    public boolean execute() {
        //fragmentMyOpener is a custom class to simplify fragment management
        fragmentMyOpener = new FragmentMyOpener(container);
        fragmentMyOpener.open(fragmentManageCategories);
        tracker.setValue("fragmentMyManageCategories");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bnvFragmentManageCategories:
                        fragmentMyOpener.replace(fragmentManageCategories);
                        tracker.setValue("fragmentMyManageCategories");
                        break;

                    case R.id.bnvFragmentManageBundles:
                        fragmentMyOpener.replace(fragmentManageBundles);
                        tracker.setValue("fragmentMyManageBundles");
                        break;
                }
                return false;
            }
        });
        return false;
    }
}
