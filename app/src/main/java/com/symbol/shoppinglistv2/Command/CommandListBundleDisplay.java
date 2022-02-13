package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterBundleItems;
import com.symbol.shoppinglistv2.Other.AdapterBundleOnList;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandListBundleDisplay implements Command{

    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandMABundleDisplay";
    private RecyclerView rvBundlesDisplay;
    private AdapterBundleItems adapterBundleItems;
    private AdapterBundleOnList adapterBundleOnList;
    private Spinner spinner;
    private MutableLiveData<ListOfProducts> currentList;

    public CommandListBundleDisplay(RecyclerView rvBundlesDisplay, Spinner spinner ) {//MutableLiveData<ListOfProducts> currentList
        this.spinner = spinner;
        this.rvBundlesDisplay = rvBundlesDisplay;
        //this.currentList = currentList;
    }

    @Override
    public boolean execute() {
        FirebaseUtil.mutableList.observeForever(new Observer<ListOfProducts>() {
            @Override
            public void onChanged(ListOfProducts listOfProducts) {
                ArrayList<MyBundle> bundleArrayList = new ArrayList<>();
                try{
                    for (Map.Entry<String, MyBundle> myBundle :
                            listOfProducts.getBundles().entrySet()) {
                        bundleArrayList.add(myBundle.getValue());
                    }
                    sortWay(bundleArrayList);
                    adapterBundleOnList = new AdapterBundleOnList(bundleArrayList);
                    rvBundlesDisplay.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                    //new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvBundlesDisplay);
                    rvBundlesDisplay.setAdapter(adapterBundleOnList);
                }catch(NullPointerException e){
                    Log.e(TAG, "onChanged: "+ e.toString());
                }

            }
        });

        return false;
    }

    private void sortWay(ArrayList<MyBundle> myBundleArrayList)
    {
        Collections.sort(myBundleArrayList, new Comparator<MyBundle>() {
            @Override
            public int compare(MyBundle bundle, MyBundle t1) {
                if(bundle.isChecked()==true && t1.isChecked()==false){
                    return 1;
                }else if(bundle.isChecked()==false && t1.isChecked()==true){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }
}
