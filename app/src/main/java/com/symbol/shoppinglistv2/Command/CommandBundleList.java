package com.symbol.shoppinglistv2.Command;

import android.view.View;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Other.AdapterBundleItems;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandBundleList implements Command{

    private RecyclerView rvBundleList;
    private AdapterBundleItems adapterBundleItems;
    private View container;
    private ArrayList<MyBundle> bundleArrayList;

    public CommandBundleList(RecyclerView rvBundleList, View container) {
        this.rvBundleList = rvBundleList;
        this.container = container;
    }

    @Override
    public boolean execute() {
        FireBaseUtil.getBundles("/bundles/", new MyCallback() {
            @Override
            public void getBundles(ArrayList<MyBundle> myBundleArrayList) {
                super.getBundles(myBundleArrayList);
                bundleArrayList = myBundleArrayList;
                adapterBundleItems = new AdapterBundleItems(myBundleArrayList,container );
                rvBundleList.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                new ItemTouchHelper(test()).attachToRecyclerView(rvBundleList);
                rvBundleList.setAdapter(adapterBundleItems);

            }
        });



        return false;
    }

    private ItemTouchHelper.SimpleCallback test(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MyBundle bundle = bundleArrayList.get(viewHolder.getAdapterPosition());
                bundleArrayList.remove(viewHolder.getAdapterPosition());
                String path = "bundles/";
                FireBaseUtil.removeBundle(path, bundle);
            }
        };
        return simpleCallback;
    }
}
