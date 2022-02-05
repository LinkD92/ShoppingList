package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    private FragmentManageBundles fragmentManageBundles;
    private FragmentManageCategories fragmentManageCategories;

    private BottomNavigationView bottomNavigationView;

    private MutableLiveData tracker;
    //tracke is a listener to check which fragment is currently open - required to assign proper action for FAB
    private View container;
    private TextView tvAMCurrentFragment;


    public CommandFMbnvActions(BottomNavigationView bottomNavigationView, View container,  MutableLiveData tracker, TextView tvAMCurrentFragment) {
        this.bottomNavigationView = bottomNavigationView;
        this.container = container;
        this.tracker = tracker;
        this.tvAMCurrentFragment = tvAMCurrentFragment;

        fragmentManageBundles = new FragmentManageBundles();
        fragmentManageCategories  = new FragmentManageCategories();
    }

    @Override
    public boolean execute() {
        //fragmentMyOpener is a custom class to simplify fragment management
        fragmentMyOpener = new FragmentMyOpener(container);
        if(tracker.getValue() == null){
            fragmentMyOpener.open(fragmentManageCategories);
            tracker.setValue("fragmentMyManageCategories");
            tvAMCurrentFragment.setText("Manage Categories");
        }else{
            if(tracker.getValue().equals("fragmentMyManageCategories")){
                fragmentMyOpener.open(fragmentManageCategories);
                tvAMCurrentFragment.setText("Manage Categories");

            }else{
                fragmentMyOpener.open(fragmentManageBundles);
                tvAMCurrentFragment.setText("Manage Bundles");

            }
        }
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bnvFragmentManageCategories:
                        tvAMCurrentFragment.setText("Manage Categories");
                        item.setChecked(true);
                        tracker.setValue("fragmentMyManageCategories");
                        fragmentMyOpener.replace(fragmentManageCategories);
                        fragmentMyOpener.close("addBundle");
                        break;

                    case R.id.bnvFragmentManageBundles:
                        tvAMCurrentFragment.setText("Manage Bundles");
                        item.setChecked(false);
                        tracker.setValue("fragmentMyManageBundles");
                        fragmentMyOpener.replace(fragmentManageBundles);
                        fragmentMyOpener.close("addCategory");
                        break;
                }
                return false;
            }
        });

        return false;
    }
}
