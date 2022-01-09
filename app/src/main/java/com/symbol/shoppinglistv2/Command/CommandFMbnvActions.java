package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.FragmentMyManageBundles;
import com.symbol.shoppinglistv2.Activities.FragmentMyManageCategories;
import com.symbol.shoppinglistv2.Activities.FragmentMyManager;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
//Bottom navigation view in Management Fragment actions
public class CommandFMbnvActions implements Command{
    private final String TAG = "CommandFMbnvActions";

    private FragmentMyOpener fragmentMyOpener;
    private FragmentMyManageBundles fragmentMyManageBundles = new FragmentMyManageBundles();
    private FragmentMyManageCategories fragmentMyManageCategories = new FragmentMyManageCategories();

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
        fragmentMyOpener.open(fragmentMyManageCategories);
        tracker.setValue("fragmentMyManageCategories");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bnvFragmentManageCategories:
                        fragmentMyOpener.replace(fragmentMyManageCategories);
                        tracker.setValue("fragmentMyManageCategories");
                        break;

                    case R.id.bnvFragmentManageBundles:
                        fragmentMyOpener.replace(fragmentMyManageBundles);
                        tracker.setValue("fragmentMyManageBundles");
                        break;
                }
                return false;
            }
        });
        return false;
    }
}
