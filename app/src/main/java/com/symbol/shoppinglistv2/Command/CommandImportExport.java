package com.symbol.shoppinglistv2.Command;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Other.AdapterBundleItems;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CommandImportExport implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandImportExport";
    private Button btnImport, btnExport, btnImportGroups, btnExportGroups;
    private Gson gsonExport;
    private ActivityMain activityMain;
    private int CHOOSE_FILE_REQUESTCODE = 1;
    private int CREATE_FILE = 2;
    private int CREATE_FILE_BUNDLE = 4;
    private int CHOOSE_FILE_BUNDLE = 3;
    public CommandImportExport(Button btnImport, Button btnExport, Button btnImportGroups, Button btnExportGroups) {
        this.btnImport = btnImport;
        this.btnExport = btnExport;
        this.btnImportGroups = btnImportGroups;
        this.btnExportGroups = btnExportGroups;
        loadBundles();
    }

    @Override
    public boolean execute() {
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                Intent i = Intent.createChooser(intent, "File");
                ActivityMain.activityMain.startActivityForResult(i, CHOOSE_FILE_REQUESTCODE);
            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                Gson gson = new Gson();
                gson.toJson(FirebaseUtil.mutableList.getValue());
                String fileName = FirebaseUtil.mutableList.getValue().getName() +".json";
                intent.putExtra(Intent.EXTRA_TITLE, fileName);
                ActivityMain.activityMain.startActivityForResult(intent, CREATE_FILE);
            }
        });

        btnImportGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                Intent i = Intent.createChooser(intent, "File");
                ActivityMain.activityMain.startActivityForResult(i, CHOOSE_FILE_BUNDLE);
            }
        });

        btnExportGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                Gson gson = new Gson();
                gson.toJson(FirebaseUtil.arrayBundles.getValue());
                String fileName =  "bundles.json";
                intent.putExtra(Intent.EXTRA_TITLE, fileName);
                ActivityMain.activityMain.startActivityForResult(intent, CREATE_FILE_BUNDLE);
            }
        });

         return false;
    }

    private void loadBundles(){
        FirebaseUtil.getBundles("/bundles/", new MyCallback() {
            @Override
            public void getBundles(ArrayList<MyBundle> myBundleArrayList) {
                super.getBundles(myBundleArrayList);
                FirebaseUtil.arrayBundles = new MutableLiveData<>();
                FirebaseUtil.arrayBundles.setValue(myBundleArrayList);
            }
        });
    }
}
