package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.FragmentBundleToList;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import androidx.annotation.NonNull;

public class CommandBNVAddToList implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandBNVAddToList";
    private FragmentMyOpener fragmentMyOpener;
    private FragmentAddProduct fragmentAddProduct = new FragmentAddProduct();
    private FragmentBundleToList fragmentBundleToList = new FragmentBundleToList();

    private View container;
    private BottomNavigationView bottomNavigationView;

    public CommandBNVAddToList(View container, BottomNavigationView bottomNavigationView) {
        this.container = container;
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public boolean execute() {
        fragmentMyOpener = new FragmentMyOpener(container);
        if(FirebaseUtil.currentSelection == R.id.bnvAddToListProduct || FirebaseUtil.currentSelection == 0){
            bottomNavigationView.setSelectedItemId(R.id.bnvAddToListProduct);
            fragmentMyOpener.open(fragmentAddProduct);
        }else if(FirebaseUtil.currentSelection != 0 && FirebaseUtil.currentSelection == R.id.bnvAddToListBundle){
            bottomNavigationView.setSelectedItemId(R.id.bnvAddToListBundle);
            fragmentMyOpener.open(fragmentBundleToList);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentMyOpener = new FragmentMyOpener(container);
                switch (item.getItemId()){
                    case R.id.bnvAddToListProduct:
                        fragmentMyOpener.open(fragmentAddProduct);
                        FirebaseUtil.currentSelection = R.id.bnvAddToListProduct;
                        item.setChecked(true);
                        break;
                    case R.id.bnvAddToListBundle:
                        fragmentMyOpener.open(fragmentBundleToList);
                        FirebaseUtil.currentSelection = R.id.bnvAddToListBundle;
                        item.setChecked(true);
                        break;
                }

                return false;
            }
        });

        return false;
    }
}
