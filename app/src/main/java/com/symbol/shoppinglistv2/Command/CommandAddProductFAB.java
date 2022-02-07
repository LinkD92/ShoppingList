package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.FragmentAddToList;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.mCodeScanner;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

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
                if(FirebaseUtil.mutableList.getValue() != null){
                    fragmentMyOpener.open(fragmentAddToList, "AddToList");
                    mCodeScanner.barcodeVal = 0;
                }else{
                    Toast.makeText(ActivityMain.appContext, "Create list first", Toast.LENGTH_LONG).show();
                }
            }
        });

        return false;
    }



}
