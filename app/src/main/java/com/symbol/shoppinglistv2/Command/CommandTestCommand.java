package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.symbol.shoppinglistv2.Activities.FragmentMyLists;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Components.SharedList;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.Other.ProductAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CommandTestCommand implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandTestCommand";
    private FragmentMyLists fragmentMyLists;
    private MutableLiveData <ListOfProducts> currentList;
    private ProductAdapter productAdapter;
    private MutableLiveData<ArrayList<SharedList>> sharedListLoaded;

    public CommandTestCommand(FragmentMyLists fragmentMyLists, MutableLiveData<ListOfProducts> currentList, MutableLiveData<ArrayList<SharedList>> sharedListLoaded) {
        this.fragmentMyLists = fragmentMyLists;
        this.currentList = currentList;
        this.sharedListLoaded = sharedListLoaded;
    }

    @Override
    public boolean execute() {
        spinerListener();
        fillProductsRV();
        return false;
    }

    private void spinerListener(){
        fragmentMyLists.spinList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String listName = adapterView.getItemAtPosition(i).toString();
                FireBaseUtil.spinnerPositionERROR = i;
                FireBaseUtil.currentList = listName;
                String path = "lists/" + listName;
//                if(!listName.contains("(")){
//                    path = "lists/" + listName;
//                }else{
//                    for (SharedList sharedList:
//                            sharedListLoaded.getValue()) {
//
//
//                    }
//                }
                FireBaseUtil.readFullList(path, new MyCallback() {
                    @Override
                    public void readFullList(ListOfProducts listOfProducts) {
                        currentList.setValue(listOfProducts);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillProductsRV(){
        currentList.observeForever(new Observer<ListOfProducts>() {
            @Override
            public void onChanged(ListOfProducts listOfProducts) {
                HashMap<String, Product> products = listOfProducts.getProducts();
                ArrayList<Product> productArrayList = new ArrayList<>();
                for (Map.Entry<String, Product> entry:
                        products.entrySet()) {
                    Product product = entry.getValue();
                    Log.d(TAG, "fillProductsRV prod: " + product.getName());
                    productArrayList.add(product);
                }
                Log.d(TAG, "fillProductsRV: " + currentList.getValue().getProducts().size());
                Log.d(TAG, "fillProductsRV: arr " + productArrayList.size());
                productAdapter = new ProductAdapter(productArrayList, fragmentMyLists.fragmentContainer);
                fragmentMyLists.rvProducts.setLayoutManager(new LinearLayoutManager(MainActivity.appContext));
                fragmentMyLists.rvProducts.setAdapter(productAdapter);
            }
        });

    }


}
