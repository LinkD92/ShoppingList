package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.symbol.shoppinglistv2.Activities.FragmentAddBundle;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterBundleProducts;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandBundleAdd implements Command{

    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandBundleAdd";
    private FragmentAddBundle fragmentAddBundle;
    private AdapterBundleProducts adapter;
    private ArrayList<Product> products;
    private MyBundle myBundle;
    private View container;

    public CommandBundleAdd(FragmentAddBundle fragmentAddBundle, MyBundle myBundle, View container) {
        this.fragmentAddBundle = fragmentAddBundle;
        products = new ArrayList<>();
        this.myBundle = myBundle;
        this.container = container;
        if(myBundle == null){
            fragmentAddBundle.ibtnAddBundleAddPorduct.setVisibility(View.GONE);
            fragmentAddBundle.rvBundleProducts.setVisibility(View.GONE);
            fragmentAddBundle.ssBundleAddProductName.setVisibility(View.GONE);
        }else{
            fragmentAddBundle.etAddBundleName.setText(myBundle.getName());
        }
    }

    @Override
    public boolean execute() {
        ibtnListener();
        actvProduct();
        //addProductToBundle();
        productToRecyclerView();
        btnListener();
        return false;
    }

    private void actvProduct(){
        String fullPath = "/lists/" + FirebaseUtil.currentList + "/products/";
        FirebaseUtil.readProducts(fullPath, new MyCallback() {
            @Override
            public void onProductCallback(ArrayList<Product> productArrayList) {
                Log.d(TAG, "onProductCallback arr: " + productArrayList.size());
                ArrayList<String> stringBund = new ArrayList<>();
                for (Product pr :
                        productArrayList) {
                    stringBund.add(pr.getName());
                    Log.d(TAG, "onProductCallback string: " + stringBund.size());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, stringBund);
                fragmentAddBundle.ssBundleAddProductName.setAdapter(adapter);
            }
        });

    }

//    private void addProductToBundle(){
//        fragmentAddBundle.btnAddBundleSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyBundle myBundle;
//                String bundleName = fragmentAddBundle.etAddBundleName.getText().toString();
//                String productName = fragmentAddBundle.actvBundleAddProductName.getText().toString();
//                Product product = new Product();
//                product.setName(productName);
//                myBundle = new MyBundle(bundleName);
//                FireBaseUtil.addBundle(myBundle);
//            }
//        });
//    }

    private void productToRecyclerView(){
        if(myBundle != null) {
            String path = "bundles/" + myBundle.getName() + "/products";
            FirebaseUtil.readProducts(path, new MyCallback() {
                @Override
                public void onProductCallback(ArrayList<Product> productArrayList) {
                    super.onProductCallback(productArrayList);
                    Log.d(TAG, "onProductCallback: " + productArrayList.size());
                    products = productArrayList;
                    adapter = new AdapterBundleProducts(productArrayList);
                    fragmentAddBundle.rvBundleProducts.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(fragmentAddBundle.rvBundleProducts);
                    fragmentAddBundle.rvBundleProducts.setAdapter(adapter);
                }

                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Product product = products.get(viewHolder.getAdapterPosition());
                        products.remove(viewHolder.getAdapterPosition());
                        Log.d(TAG, "onSwiped: " + product.getName());
                        FirebaseUtil.removeBundleProduct(FirebaseUtil.currentBundle, product);
                    }
                };

            });
        }


    }

    private void ibtnListener(){
        fragmentAddBundle.ibtnAddBundleAddPorduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prodName = fragmentAddBundle.ssBundleAddProductName.getSelectedItem().toString();
                Log.d(TAG, "prodName: " + prodName);
                Product product = new Product(prodName);
                FirebaseUtil.addBundleProduct(myBundle.getName(), product);
            }
        });
    }

    private void btnListener(){
        fragmentAddBundle.btnAddBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bundleName = fragmentAddBundle.etAddBundleName.getText().toString();
                MyBundle bundle = new MyBundle(bundleName);
                FirebaseUtil.addBundle("bundles/", bundle);

            }
        });
    }

}
