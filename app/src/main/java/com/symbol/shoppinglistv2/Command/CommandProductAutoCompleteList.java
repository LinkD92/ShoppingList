package com.symbol.shoppinglistv2.Command;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

/**
 * CLASS CURRENTLY NOT USED _ WILL REQUIRED FURTHER IMPLEMENTATION
 */
public class CommandProductAutoCompleteList implements Command{

    private final String TAG = "CommandProductAutoCompleteList";
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<String> acList;

    public CommandProductAutoCompleteList(AutoCompleteTextView autoCompleteTextView, ArrayList<String> acList) {
        this.autoCompleteTextView = autoCompleteTextView;
        this.acList = acList;
    }

    @Override
    public boolean execute() {
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.appContext, R.layout.support_simple_spinner_dropdown_item, allProducts());
        autoCompleteTextView.setAdapter(adapter);
        return false;
    }

    private ArrayList<String> allProducts(){
        FireBaseUtil.readProducts("/products/", new MyCallback() {
            @Override
            public void onProductCallback(ArrayList<Product> productArrayList) {
                for (Product product :
                        productArrayList) {
                    acList.add(product.getName());
                }
                super.onProductCallback(productArrayList);
            }
        });
        return acList;
    }
}
