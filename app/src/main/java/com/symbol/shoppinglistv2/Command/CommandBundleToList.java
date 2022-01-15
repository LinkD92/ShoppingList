package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class CommandBundleToList implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandBundleToList";
    private ImageButton imageButton;
    private SearchableSpinner searchableSpinner;
    private ArrayAdapter adapter;
    private MyBundle myBundle;

    public CommandBundleToList(ImageButton imageButton, SearchableSpinner searchableSpinner) {
        this.imageButton = imageButton;
        this.searchableSpinner = searchableSpinner;
    }

    @Override
    public boolean execute() {
        searchableSpinnerAdapter();
        ibtnListener();
        return false;
    }

    private void searchableSpinnerAdapter(){
        String fullPath = "/bundles";
        FireBaseUtil.getBundles(fullPath, new MyCallback() {
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
                String fullPath = "lists/" + FireBaseUtil.currentList + "/bundles/";
                String pathFindBundle = "bundles/" + bundleName;
                FireBaseUtil.findBundle(pathFindBundle, new MyCallback() {
                    @Override
                    public MyBundle findBundle(MyBundle bundle) {
                        FireBaseUtil.addBundle(fullPath, bundle);
                        myBundle = bundle;
                        Log.d(TAG, "findBundle: " + bundle.getName());
                        return bundle;
                    }
                });
            }
        });
    }
}
