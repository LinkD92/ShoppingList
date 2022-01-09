package com.symbol.shoppinglistv2.Command;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.internal.http.StreamAllocation;
import com.symbol.shoppinglistv2.Activities.ActivityManager;
import com.symbol.shoppinglistv2.Activities.ActivitySignIn;
import com.symbol.shoppinglistv2.Activities.FragmentMyLists;
import com.symbol.shoppinglistv2.Activities.FragmentMyManager;
import com.symbol.shoppinglistv2.Activities.FragmentSettings;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CommandMAbnvActions implements Command{
    private final String TAG = "CommandMAbnvActions";

    //Fragments and Fragment opener declaration;
    private FragmentMyOpener fragmentMyOpener;
    private FragmentMyManager fragmentMyManager = new FragmentMyManager();
    private FragmentMyLists fragmentMyLists = new FragmentMyLists();
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
         fragmentMyOpener.open(fragmentMyLists);
        //Listener on menu item of BottomNavigationViewClick
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Opening proper fragment according to button clicked
                switch (item.getItemId()){
                    case R.id.bnvMenuLists:
                        fragmentMyOpener.replace(fragmentMyLists);
                        item.setChecked(true);
                        break;
                    case R.id.bnvMenuManagement:
                        fragmentMyOpener.replace(fragmentMyManager);
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
