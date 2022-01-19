package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterBundleItems;
import com.symbol.shoppinglistv2.Other.AdapterBundleOnList;
import com.symbol.shoppinglistv2.Other.BundleDetailsFiller;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.Other.AdapterProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Display of products according to the select List in spinner
public class CommandMAProductDisplay implements Command {

    private final String TAG = "CommandProductDisplay";
    private RecyclerView rvProductDisplay;
    private Spinner spinList;
    private AdapterProduct adapter;
    private View container;
    private RecyclerView rvBundlesDisplay;
    private AdapterBundleItems adapterBundleItems;
    private AdapterBundleOnList adapterBundleOnList;

    public CommandMAProductDisplay(Spinner spinList, RecyclerView rvProductDisplay, View container, RecyclerView rvBundlesDisplay){
        this.rvProductDisplay = rvProductDisplay;
        this.spinList = spinList;
        this.container = container;
        this.rvBundlesDisplay = rvBundlesDisplay;
    }

    @Override
    public boolean execute() {
        spinList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //getting list choosen
                Object listClicked = adapterView.getItemAtPosition(i);
                //assignint the value to static field;
                FirebaseUtil.currentList = listClicked.toString();
                //Helper value to resolve issues - further investigation might be required -
                //After editing anything in "list" path of database the selection is going back to
                // first list
                FirebaseUtil.spinnerPositionERROR = i;
                String buildPath = "/lists/" + FirebaseUtil.currentList  +"/products";
                        FirebaseUtil.readProducts(buildPath, new MyCallback() {
                            @Override
                            public void onProductCallback(ArrayList<Product> productArrayList) {

                                FirebaseUtil.getList(FirebaseUtil.currentList, new MyCallback() {
                                    @Override
                                    public void getList(ListOfProducts listOfProducts) {
                                        //one of the sort methods - Pushes check products to the bottom of the list
                                        FirebaseUtil.sortMethod = listOfProducts.getSortType();
                                        Log.d(TAG, "SortLOL: " + FirebaseUtil.sortMethod);
                                        if(FirebaseUtil.sortMethod.equals("name")){
                                            sortName(productArrayList);
                                        }else if(FirebaseUtil.sortMethod.equals("category/name")){
                                            sortCategory(productArrayList);
                                        }else if(FirebaseUtil.sortMethod.equals("customID")){
                                            sortCustom(productArrayList);
                                        }
                                        sortWay(productArrayList);

                                        //adapter assignem for Recycler View
                                        FirebaseUtil.currentListProducts = productArrayList;
                                        BundleDetailsFiller test = new BundleDetailsFiller();
                                        //TU UWAGA - WYWALIC NULLA.
                                        adapter = new AdapterProduct(productArrayList, container, null);
                                        rvProductDisplay.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                                        //ItemTouchHelper.Callback callback = new MyItemTouchHelper(adapter, productArrayList);
                                        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                                        //adapter.setTouchHelper(itemTouchHelper);
                                        //itemTouchHelper.attachToRecyclerView(rvProductDisplay);
                                        rvProductDisplay.setAdapter(adapter);
                                    }

                                });



                                String fullPath = "/lists/" + FirebaseUtil.currentList + "/bundles";
                                FirebaseUtil.getBundles(fullPath, new MyCallback() {
                                    @Override
                                    public void getBundles(ArrayList<MyBundle> myBundleArrayList) {
                                        super.getBundles(myBundleArrayList);
                                        ArrayList<MyBundle> tempArrBundle = myBundleArrayList;

                                        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                                            @Override
                                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                                return false;
                                            }

                                            @Override
                                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                                MyBundle bundle = myBundleArrayList.get(viewHolder.getAdapterPosition());
                                                Log.d(TAG, "MyTest: " + bundle.isChecked());
                                                if(bundle.isChecked() == false){
                                                    tempArrBundle.remove(viewHolder.getAdapterPosition());
                                                    String path = "lists/" + FirebaseUtil.currentList + "/bundles/";
                                                    FirebaseUtil.removeBundle(path, bundle);
                                                }else{
                                                    Toast.makeText(ActivityMain.appContext, "Uncheck Bundle First", Toast.LENGTH_LONG);
                                                }

                                            }
                                        };

                                        adapterBundleOnList = new AdapterBundleOnList(tempArrBundle);
                                        rvBundlesDisplay.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                                        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvBundlesDisplay);
                                        rvBundlesDisplay.setAdapter(adapterBundleOnList);
                                    }
                                });

                            }
                        });

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return false;
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
