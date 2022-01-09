package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.symbol.shoppinglistv2.Activities.FragmentAddCategory;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Other.CategoryAdapter;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Command to display existing categories
public class CommandCategoryDisplay implements Command{

    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandCategoryDisplay";
    private RecyclerView rvCategoryDisplay;
    private CategoryAdapter aaCategoryAdapter;
    private View clContainer;

    public CommandCategoryDisplay(RecyclerView rvCategoryDisplay, View clContainer) {
        this.rvCategoryDisplay = rvCategoryDisplay;
        this.clContainer = clContainer;
    }

    @Override
    public boolean execute() {
        //Callback to get categories from database
        FireBaseUtil.readCategory(new MyCallback() {
            @Override
            public void onCategoryCallback(ArrayList<Category> categoryArrayList) {
                //after list is gathered RecyclerView adapter is diplaying categories
                rvCategoryDisplay.setLayoutManager(new LinearLayoutManager(MainActivity.appContext));
                aaCategoryAdapter = new CategoryAdapter(categoryArrayList, clContainer);
                rvCategoryDisplay.setAdapter(aaCategoryAdapter);
            }
        });
        return false;
    }
}
