package com.symbol.shoppinglistv2.Command;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Activities.ActivityManager;
import com.symbol.shoppinglistv2.Activities.FragmentMyManageBundles;
import com.symbol.shoppinglistv2.Activities.FragmentMyManageCategories;
import com.symbol.shoppinglistv2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * CLASS NO LONGER USED
 */
//Command actions for Activity - Manager (of Bundles and products)
public class CommandOpenFragmentAM implements Command{

    //Declaration of activity and frgaments that are going to be opened in the activity
    private ActivityManager activityManager;

    //Fragment manager need to be exported from Activity
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    //Declaration of fragments
    private FragmentMyManageCategories fragmentMyManageCategories = new FragmentMyManageCategories();
    private FragmentMyManageBundles fragmentMyManageBundles = new FragmentMyManageBundles();

    //declaration of container where fragment should be opened
    private int container;
    //declaration of BottomNavigationView
    private BottomNavigationView bottomNavigationView;

    public CommandOpenFragmentAM(ActivityManager activityManager){
        this.activityManager = activityManager;
        this.fragmentManager = activityManager.manager;
        this.bottomNavigationView = activityManager.bottomNavigationView;
        this.container = activityManager.llcAMFragmentContainer.getId();
    }

    @Override
    public boolean execute() {
        //Starting fragment together with Ativity
        transaction = fragmentManager.beginTransaction();
        transaction.replace(container, fragmentMyManageCategories);
        transaction.addToBackStack(null);
        transaction.commit();

        //On click listener for BottomNavigationView to change Fragments
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Instance of Fragment transaction needs to be created every time Fragment is changed
                transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bnvFragmentManageCategories:
                        if(!fragmentMyManageCategories.isVisible()){
                            transaction.replace(container, fragmentMyManageCategories);
                        }
                        break;
                    case R.id.bnvFragmentManageBundles:
                        if(!fragmentMyManageBundles.isVisible()){
                            transaction.replace(container, fragmentMyManageBundles);
                        }
                        break;
                }
                transaction.addToBackStack(null);
                transaction.commit();
                return false;
            }
        });
        return false;
    }
}
