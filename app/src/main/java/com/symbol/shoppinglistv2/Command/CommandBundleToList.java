package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Activities.FragmentBundleToList;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.MyLog;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CommandBundleToList implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandBundleToList";
    private Button imageButton;
    private SearchableSpinner searchableSpinner;
    private ArrayAdapter adapter;
    private MyBundle myBundle;
    private Button buttonClose;
    private FragmentBundleToList fragmentBundleToList;

    public CommandBundleToList(Button imageButton, SearchableSpinner searchableSpinner, Button buttonClose, FragmentBundleToList fragmentBundleToList) {
        this.imageButton = imageButton;
        this.searchableSpinner = searchableSpinner;
        this.buttonClose = buttonClose;
        this.fragmentBundleToList = fragmentBundleToList;
    }

    @Override
    public boolean execute() {
        searchableSpinnerAdapter();
        ibtnListener();
        btnCloseListener();
        return false;
    }

    private void searchableSpinnerAdapter(){
        String fullPath = "/bundles";
        FirebaseUtil.getBundles(fullPath, new MyCallback() {
            @Override
            public void getBundles(ArrayList<MyBundle> myBundleArrayList) {
                ArrayList<String> stringBund = new ArrayList<>();
                for (MyBundle bundle :
                        myBundleArrayList) {
                    stringBund.add(bundle.getName());
                }
                adapter = new ArrayAdapter<>(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, stringBund);
                searchableSpinner.setAdapter(adapter);

                super.getBundles(myBundleArrayList);
            }
        });
    }

    private void ibtnListener(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bundleName = searchableSpinner.getSelectedItem().toString();
                //String fullPath = "lists/" + FirebaseUtil.currentList + "/bundles/";
                String pathFindBundle = "bundles/" + bundleName;
                FirebaseUtil.findBundle(pathFindBundle, new MyCallback() {
                    @Override
                    public MyBundle findBundle(MyBundle bundle) {
                        FirebaseUtil.addBundle(FirebaseUtil.mutableList.getValue(), bundle);
                        if(bundle.getProducts() != null){
                            for (Map.Entry<String, Product> prod:
                                    bundle.getProducts().entrySet()) {
                                int prodAmount = bundle.getAmount() * prod.getValue().getAmount();
                                Product product = new Product(prod.getValue());
                                product.setAmount(prodAmount);
                                product.setChecked(bundle.isChecked());
                                FirebaseUtil.addBundleProduct(FirebaseUtil.mutableList.getValue(), product);
                            }


                            myBundle = bundle;
                            Log.d(TAG, "findBundle: " + bundle.getName());

                        }
                        return bundle;
                    }
                });
            }
        });
    }

    private void btnCloseListener(){
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentMyOpener fragmentMyOpener = new FragmentMyOpener(fragmentBundleToList);
                fragmentMyOpener.close("test");

            }
        });
    }
}
