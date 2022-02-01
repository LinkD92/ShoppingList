package com.symbol.shoppinglistv2.Command;

import android.widget.ArrayAdapter;

import com.symbol.shoppinglistv2.Activities.ActivityBarcodeScanner;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Other.AdapterProduct;

import androidx.recyclerview.widget.LinearLayoutManager;

public class CommandProductsOnScanner implements Command{
    private ActivityBarcodeScanner activityBarcodeScanner;

    public CommandProductsOnScanner(ActivityBarcodeScanner activityBarcodeScanner){
        this.activityBarcodeScanner = activityBarcodeScanner;
    }

    @Override
    public boolean execute() {
        activityBarcodeScanner.rvProductsSmall.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));

        return false;
    }
}
