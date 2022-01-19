package com.symbol.shoppinglistv2.Command;

import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterCategorySpinner;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

public class CommandCategorySpnAdapter implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandCategorySpnAdapter";
    private Spinner spnProductCategory;
    private AdapterCategorySpinner categoryAdapter;
    private Product product;

    public CommandCategorySpnAdapter(Spinner spnProductCategory, Product product) {
        this.spnProductCategory = spnProductCategory;
        this.product = product;
    }

    @Override
    public boolean execute() {
        FirebaseUtil.readCategory(new MyCallback() {
            @Override
            public void onCategoryCallback(ArrayList<Category> categoryArrayList) {
                if(categoryArrayList.size() == 0){
                    String categoryName = "Default";
                    int categoryColor = -11184811;
                    Category defaultCategory = new Category(categoryName, categoryColor);
                    categoryArrayList.add(defaultCategory);

                }
                categoryAdapter = new AdapterCategorySpinner(ActivityMain.appContext, R.layout.test_spinner_cat, categoryArrayList);
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
