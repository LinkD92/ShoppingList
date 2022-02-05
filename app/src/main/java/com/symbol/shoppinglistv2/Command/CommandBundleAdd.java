package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.FragmentAddBundle;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterBundleProducts;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandBundleAdd implements Command{
    private final String TAG = this.getClass().getSimpleName();
    private FragmentAddBundle fragmentAddBundle;
    private AdapterBundleProducts adapter;
    private ArrayList<Product> products;
    private MyBundle myBundle;
    private View container;
    private MutableLiveData<HashMap<String, Product>> hashProducts = new MutableLiveData<>();

    public CommandBundleAdd(FragmentAddBundle fragmentAddBundle, MyBundle myBundle, View container) {
        this.fragmentAddBundle = fragmentAddBundle;
        this.container = container;
        if(myBundle == null){
            this.myBundle = new MyBundle();
            hashProducts.setValue(new HashMap<String, Product>());
        }else{
            this.myBundle = myBundle;
            hashProducts.setValue(myBundle.getProducts());
        }
    }

    @Override
    public boolean execute() {
        extractData();
        ibtnAddProductListener();
        availableProducts();
        productToRecyclerView();
        btnSaveChangesListener();
        btnCancelListener();
        return false;
    }

    private void availableProducts(){
        HashMap<String, Product> productHashMap = FirebaseUtil.mutableList.getValue().getProducts();
        ArrayList<String> tempProducts = new ArrayList<>();
        for (Map.Entry<String, Product> prodEntry:
             productHashMap.entrySet()) {
            if(prodEntry.getValue().getGroup() != null && prodEntry.getValue().getGroup().length() == 0){
                tempProducts.add(prodEntry.getValue().getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, tempProducts);
        fragmentAddBundle.ssBundleAddProductName.setAdapter(adapter);

    }


    private void productToRecyclerView(){
    hashProducts.observeForever(new Observer<HashMap<String, Product>>() {
        @Override
        public void onChanged(HashMap<String, Product> productHashMap) {
            products = new ArrayList<>();
            for (Map.Entry<String, Product> prodEntry:
                    productHashMap.entrySet()) {
                products.add(prodEntry.getValue());
            adapter = new AdapterBundleProducts(products, hashProducts);
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Product product = products.get(viewHolder.getAdapterPosition());
                    products.remove(viewHolder.getAdapterPosition());
                    hashProducts.getValue().remove(product.getName());
                    adapter.notifyDataSetChanged();
                }
            };

                fragmentAddBundle.rvBundleProducts.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(fragmentAddBundle.rvBundleProducts);
                fragmentAddBundle.rvBundleProducts.setAdapter(adapter);


            }
        }
    });
    }

    private void ibtnAddProductListener(){
        fragmentAddBundle.ibtnAddBundleAddPorduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fragmentAddBundle.ssBundleAddProductName.getSelectedItem()!=null) {
                    String prodName = fragmentAddBundle.ssBundleAddProductName.getSelectedItem().toString();
                    ListOfProducts list = FirebaseUtil.mutableList.getValue();
                    Product product = list.getProducts().get(prodName);
                    hashProducts.getValue().put(product.getName(), product);
                    hashProducts.setValue(hashProducts.getValue());
                } else {
                    Toast.makeText(ActivityMain.appContext, "Product is not chosen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void btnSaveChangesListener(){
        FragmentMyOpener fragmentMyOpener = new FragmentMyOpener();
        fragmentAddBundle.btnAddBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bundleName = fragmentAddBundle.etAddBundleName.getText().toString();
                HashMap<String, Product> temp = new HashMap<>();
                if(bundleName.length() >0){
                    myBundle.setName(bundleName);
                    double price =0;
                    for (Product pr :
                            products) {
                        pr.setGroup(bundleName);
                        pr.setChecked(false);
                        temp.put(pr.getName(), pr);
                        price += pr.getPrice() * pr.getAmount();
                    }

                    myBundle.setPrice(price);
                    myBundle.setProducts(temp);
                    if(products.size() <= 0){


                        Toast.makeText(ActivityMain.appContext, "Products are missing", Toast.LENGTH_LONG).show();
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        Date test = calendar.getTime();
                        String test2 = test.getDate()+ "/"+test.getMonth() + "/" + test.getYear();
                        myBundle.setUpdateDate(test2);
                        FirebaseUtil.addBundle("bundles/", myBundle);
                        fragmentMyOpener.close("addBundle");
                    }


                }else{
                    Toast.makeText(ActivityMain.appContext, "Name is empty", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void extractData(){
        if(myBundle != null){
            fragmentAddBundle.etAddBundleName.setText(myBundle.getName());
        }
    }

    private void btnCancelListener(){
        FragmentMyOpener fragmentMyOpener = new FragmentMyOpener();
        fragmentAddBundle.btnBundleCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMyOpener.close("addBundle");
            }
        });
    }

}
