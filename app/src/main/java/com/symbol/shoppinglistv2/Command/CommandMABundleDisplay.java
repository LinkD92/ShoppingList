package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Other.AdapterBundleItems;
import com.symbol.shoppinglistv2.Other.AdapterBundleOnList;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandMABundleDisplay implements Command{

    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandMABundleDisplay";
    private RecyclerView rvBundlesDisplay;
    private AdapterBundleItems adapterBundleItems;
    private AdapterBundleOnList adapterBundleOnList;
    private Spinner spinner;

    public CommandMABundleDisplay(RecyclerView rvBundlesDisplay, Spinner spinner) {
        this.spinner = spinner;
        this.rvBundlesDisplay = rvBundlesDisplay;
    }

    @Override
    public boolean execute() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //FireBaseUtil.currentList = adapterView.getItemAtPosition(i).toString();
                String fullPath = "/lists/" + FireBaseUtil.currentList + "/bundles";
                Log.d(TAG, "Testoo " + FireBaseUtil.currentList);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        return false;
    }
}
