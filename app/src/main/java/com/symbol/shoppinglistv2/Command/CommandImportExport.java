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
import com.symbol.shoppinglistv2.Other.FirebaseUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandImportExport implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandImportExport";
    private Button btnImport, btnExport;
    private Gson gsonExport;
    private ActivityMain activityMain;
    private int CHOOSE_FILE_REQUESTCODE = 1;
    private int CREATE_FILE = 2;

    public CommandImportExport(Button btnImport, Button btnExport) {
        this.btnImport = btnImport;
        this.btnExport = btnExport;
    }

    @Override
    public boolean execute() {
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
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
                Uri uri = intent.getData();



                // Optionally, specify a URI for the directory that should be opened in
                // the system file picker when your app creates the document.
                //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

                ActivityMain.activityMain.startActivityForResult(intent, CREATE_FILE);
            }
        });

         return false;
    }
}
