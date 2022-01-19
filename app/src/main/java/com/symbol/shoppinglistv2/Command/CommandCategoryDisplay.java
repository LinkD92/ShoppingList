package com.symbol.shoppinglistv2.Command;

import android.view.View;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Other.AdapterCategory;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Command to display existing categories
public class CommandCategoryDisplay implements Command{

    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandCategoryDisplay";
    private RecyclerView rvCategoryDisplay;
    private AdapterCategory aaAdapterCategory;
    private View clContainer;

    public CommandCategoryDisplay(RecyclerView rvCategoryDisplay, View clContainer) {
        this.rvCategoryDisplay = rvCategoryDisplay;
        this.clContainer = clContainer;
    }

    @Override
    public boolean execute() {
        //Callback to get categories from database
        FirebaseUtil.readCategory(new MyCallback() {
            @Override
            public void onCategoryCallback(ArrayList<Category> categoryArrayList) {
                //after list is gathered RecyclerView adapter is diplaying categories
                rvCategoryDisplay.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                aaAdapterCategory = new AdapterCategory(categoryArrayList, clContainer);
                rvCategoryDisplay.setAdapter(aaAdapterCategory);
            }
        });
        return false;
    }
}
