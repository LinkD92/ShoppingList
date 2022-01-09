package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.FragmentAddToList;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Command to open/close fragment to add product
public class CommandAddProductFAB implements Command{
    private final String TAG = "CommandMAFABActions";
    private FloatingActionButton floatingActionButton;
    private FragmentMyOpener fragmentMyOpener;
    private View container;
    private FragmentAddProduct fragmentAddProduct = new FragmentAddProduct();
    private FragmentAddToList fragmentAddToList = new FragmentAddToList();

    public CommandAddProductFAB(FloatingActionButton floatingActionButton, View container){
        this.floatingActionButton = floatingActionButton;
        this.container = container;
    }

    @Override
    public boolean execute() {
        fragmentMyOpener = new FragmentMyOpener(container);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragmentMyOpener.open(fragmentAddProduct);
//                fragmentMyOpener.close(fragmentAddProduct);

                fragmentMyOpener.open(fragmentAddToList);
                fragmentMyOpener.close(fragmentAddToList);
            }
        });

        return false;
    }



}
