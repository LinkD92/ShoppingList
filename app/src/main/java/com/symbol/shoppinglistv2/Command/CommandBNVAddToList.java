package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.FragmentBundleToList;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
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
        Log.d(TAG, "execute select: " + FireBaseUtil.currentSelection);
        fragmentMyOpener = new FragmentMyOpener(container);
        if(FireBaseUtil.currentSelection == R.id.bnvAddToListProduct || FireBaseUtil.currentSelection == 0){
            fragmentMyOpener.open(fragmentAddProduct);
        }else if(FireBaseUtil.currentSelection != 0 && FireBaseUtil.currentSelection == R.id.bnvAddToListBundle){
            fragmentMyOpener.open(fragmentBundleToList);
        }
        bottomNavigationView.setSelectedItemId(R.id.bnvAddToListBundle);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentMyOpener = new FragmentMyOpener(container);

                switch (item.getItemId()){
                    case R.id.bnvAddToListProduct:
                        fragmentMyOpener.open(fragmentAddProduct);
                        FireBaseUtil.currentSelection = R.id.bnvAddToListProduct;
                        break;
                    case R.id.bnvAddToListBundle:
                        fragmentMyOpener.open(fragmentBundleToList);
                        FireBaseUtil.currentSelection = R.id.bnvAddToListBundle;
                        break;
                }

                return false;
            }
        });

        return false;
    }
}
