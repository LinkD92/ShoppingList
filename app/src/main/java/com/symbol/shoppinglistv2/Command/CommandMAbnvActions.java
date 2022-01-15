package com.symbol.shoppinglistv2.Command;

import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Activities.FragmentLists;
import com.symbol.shoppinglistv2.Activities.FragmentManagement;
import com.symbol.shoppinglistv2.Activities.FragmentSettings;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import androidx.annotation.NonNull;

public class CommandMAbnvActions implements Command{
    private final String TAG = "CommandMAbnvActions";

    //Fragments and Fragment opener declaration;
    private FragmentMyOpener fragmentMyOpener;
    private FragmentManagement fragmentManagement = new FragmentManagement();
    private FragmentLists fragmentLists = new FragmentLists();
    private FragmentSettings fragmentSettings = new FragmentSettings();

    //Views related to manipulate fragments
    private BottomNavigationView bottomNavigationView;
    private View container;


    public CommandMAbnvActions(BottomNavigationView bottomNavigationView, View container) {
        this.bottomNavigationView = bottomNavigationView;
        this.container = container;
    }

    @Override
    public boolean execute() {
        //declaration of fragment opener
         fragmentMyOpener = new FragmentMyOpener(container);
         //Opening the first fragment
         fragmentMyOpener.open(fragmentLists);
        //Listener on menu item of BottomNavigationViewClick
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Opening proper fragment according to button clicked
                switch (item.getItemId()){
                    case R.id.bnvMenuLists:
                        fragmentMyOpener.replace(fragmentLists);
                        item.setChecked(true);
                        break;
                    case R.id.bnvMenuManagement:
                        fragmentMyOpener.replace(fragmentManagement);
                        item.setChecked(true);
                        break;
                    case R.id.bnvMenuSettings:
                        fragmentMyOpener.replace(fragmentSettings);
                        item.setChecked(true);
                        break;
                }
                return false;
            }
        });
        return false;
    }
}
