package com.symbol.shoppinglistv2.Command;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.CategorySpinnerAdapter;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommandCategorySpnAdapter implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandCategorySpnAdapter";
    private Spinner spnProductCategory;
    private ArrayAdapter<Category> adapter;
    private CategorySpinnerAdapter categoryAdapter;
    private Product product;

    public CommandCategorySpnAdapter(Spinner spnProductCategory, Product product) {
        this.spnProductCategory = spnProductCategory;
        this.product = product;
    }

    @Override
    public boolean execute() {
        FireBaseUtil.readCategory(new MyCallback() {
            @Override
            public void onCategoryCallback(ArrayList<Category> categoryArrayList) {
                categoryAdapter = new CategorySpinnerAdapter(MainActivity.appContext, R.layout.test_spinner_cat, categoryArrayList);
                categoryAdapter.setDropDownViewResource(R.layout.test_spinner_cat);
                spnProductCategory.setAdapter(categoryAdapter);

                spnProductCategory.setSelection(getProductCat(categoryArrayList));
                super.onCategoryCallback(categoryArrayList);
            }
        });
        return false;
    }

    private int getProductCat(ArrayList<Category> categoryArrayList){
        int incr =0;
        if(product != null){
            for (Category cat :
                    categoryArrayList) {
                if(product.getCategory().getName().equals(cat.getName())) {
                    return incr;
                }else{
                    incr++;
                }
            }
        }
        return incr;
    }

}
