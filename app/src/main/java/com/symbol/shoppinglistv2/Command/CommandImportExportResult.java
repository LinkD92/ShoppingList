package com.symbol.shoppinglistv2.Command;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.MyBundles;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.Nullable;

public class CommandImportExportResult implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandImportExportResult";
    private int requestCode;
    private int resultCode;
    private Intent data;

    public CommandImportExportResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    @Override
    public boolean execute() {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if(data != null){
                uri = data.getData();
                Log.d(TAG, "onActivityResult: " + uri.getPath());
                try {
                    String test = readTextFromUri(uri);
                    Gson gson = new Gson();
                    gson.toJson(test);
                    ListOfProducts list = gson.fromJson(test, ListOfProducts.class);
                    list.setListPath(FirebaseUtil.userPath + "/lists/" +list.getName());
                    FirebaseUtil.addList("lists", list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode == 2 && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: " + data);
            Uri uri = null;
            if(data != null){
                uri = data.getData();
                alterDocument(uri);
            }
        }else if(requestCode == 3 && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if(data != null){
                uri = data.getData();
                Log.d(TAG, "onActivityResult: " + uri.getPath());
                try {
                    String test = readTextFromUri(uri);
                    Gson gson = new Gson();
                    gson.toJson(test);
                    MyBundles myBundleArray = gson.fromJson(test, MyBundles.class);
                    for (MyBundle bund:
                            myBundleArray.getArrayList()) {
                            FirebaseUtil.addBundle("bundles/", bund);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode == 4 && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: " + data);
            Uri uri = null;
            if(data != null){
                uri = data.getData();
                alterBundlesDocument(uri);
            }
        }
        
        
        
        
        return false;
    }

    private void alterDocument(Uri uri) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        String test = gson2.toJson(FirebaseUtil.mutableList.getValue());
        Log.d(TAG, "alterDocument: "+ test);
        try {
            ParcelFileDescriptor pfd = ActivityMain.activityMain.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write((test).getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alterBundlesDocument(Uri uri){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        Log.d(TAG, "alterDocument: trbls " + FirebaseUtil.arrayBundles.getValue().size());
        MyBundles myBundles = new MyBundles(FirebaseUtil.arrayBundles.getValue());
        String bundle = gson2.toJson(myBundles);
        Log.d(TAG, "alterDocument BUNDLE : "+ bundle);
        try {
            ParcelFileDescriptor pfd = ActivityMain.activityMain.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write((bundle).getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     ActivityMain.activityMain.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}
