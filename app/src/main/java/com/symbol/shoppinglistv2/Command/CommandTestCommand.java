package com.symbol.shoppinglistv2.Command;

import android.content.ClipData;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.api.LogDescriptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.symbol.shoppinglistv2.Activities.FragmentMyLists;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Components.SharedList;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.Other.MyItemTouchHelper;
import com.symbol.shoppinglistv2.Other.ProductAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        FireBaseUtil.mutableList = currentList;
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
                String path;
                if(!listName.contains("(")){
                    path = "lists/" + listName;
                    FireBaseUtil.readFullList(path, new MyCallback() {
                        @Override
                        public void readFullList(ListOfProducts listOfProducts) {
                            FireBaseUtil.mutableList.setValue(listOfProducts);
                            currentList.setValue(listOfProducts);
                        }
                    });
                }else{
                    for (SharedList sharedList:
                            sharedListLoaded.getValue()) {
                        boolean check = listName.contains(sharedList.getName());
                        boolean check2 = listName.contains(sharedList.getEmail());
                        if(check && check2){
                            FireBaseUtil.readFullSharedList(sharedList, new MyCallback() {
                                @Override
                                public void readFullSharedList(ListOfProducts listOfProducts) {
                                    FireBaseUtil.mutableList.setValue(listOfProducts);
                                    currentList.setValue(listOfProducts);
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillProductsRV(){
        FireBaseUtil.mutableList.observeForever(new Observer<ListOfProducts>() {
            @Override
            public void onChanged(ListOfProducts listOfProducts) {
                HashMap<String, Product> products = listOfProducts.getProducts();
                ArrayList<Product> productArrayList = new ArrayList<>();
                for (Map.Entry<String, Product> entry:
                        products.entrySet()) {
                    Product product = entry.getValue();
                    productArrayList.add(product);
                }
                sortProducts(productArrayList);
                fragmentMyLists.rvProducts.setLayoutManager(new LinearLayoutManager(MainActivity.appContext));
                productAdapter = new ProductAdapter(productArrayList, fragmentMyLists.fragmentContainer, currentList);
                ItemTouchHelper.Callback callback = new MyItemTouchHelper(productAdapter, productArrayList);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(fragmentMyLists.rvProducts);
                productAdapter.setTouchHelper(itemTouchHelper);
                fragmentMyLists.rvProducts.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

            }
        });
    }

    private void sortProducts(ArrayList<Product> productArrayList){
        if(currentList.getValue().getSortType().equals("name")){
            sortName(productArrayList);
        }else if(currentList.getValue().getSortType().equals("category/name")){
            sortCategory(productArrayList);
        }else if(currentList.getValue().getSortType().equals("customID")){
            sortCustom(productArrayList);
        }
        sortWay(productArrayList);
    }

    private void sortWay(ArrayList<Product> productArrayList)
    {
        Collections.sort(productArrayList, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                if(product.isChecked()==true && t1.isChecked()==false){
                    return 1;
                }else if(product.isChecked()==false && t1.isChecked()==true){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }

    private void sortCustom(ArrayList<Product> productArrayList){
        Collections.sort(productArrayList, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                if(product.getCustomID() > t1.getCustomID()){
                    return 1;
                }else if(product.getCustomID() < t1.getCustomID()){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }

    private void sortName(ArrayList<Product> productArrayList){
        Collections.sort(productArrayList, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                if(product.getName().compareTo(t1.getName()) > 0){
                    return 1;
                }else if(product.getName().compareTo(t1.getName()) < 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }

    private void sortCategory(ArrayList<Product> productArrayList){
        Collections.sort(productArrayList, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                if(product.getCategory().getName().compareTo(t1.getCategory().getName()) > 0){
                    return 1;
                }else if(product.getCategory().getName().compareTo(t1.getCategory().getName()) < 0){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }
}
