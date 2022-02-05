package com.symbol.shoppinglistv2.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Spinner;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandAMbnvActions;
import com.symbol.shoppinglistv2.Command.CommandImportExportResult;
import com.symbol.shoppinglistv2.Command.CommandManageLogs;
import com.symbol.shoppinglistv2.Command.CommandPushNotification;
import com.symbol.shoppinglistv2.Command.CommandSetPrefs;
import com.symbol.shoppinglistv2.Command.CommandSignIn;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ActivityMain extends AppCompatActivity {

    public static final String TAG = "ActivityMain";
    public static Context appContext;
    public static FragmentManager fragmentManager;
    public static ActivityMain activityMain;
    public static AlarmManager service;
    public static NotificationManager notificationManager;
    public static boolean notifications;
    public static int daysBeforeExpire;
    public static Context test;
    private SharedPreferences prefs;

    //Views
    public ConstraintLayout clFragmentContainer;
    public ConstraintLayout clFragmentContainerBNV;
    public BottomNavigationView bnvBottomMenu;
    public Spinner spinList;
    public RecyclerView rvProducts;
    public FloatingActionButton floatingActionButton;
    public int PICKFILE_REQUEST_CODE = 0;






    //FragmentManager
    public FragmentManager manager = getSupportFragmentManager();
    public static LayoutInflater inflater;

    //Method required to manage import/export files
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        executeCommand(new CommandImportExportResult(requestCode, resultCode, data));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        appContext = this;
        activityMain = this;
        fragmentManager = getSupportFragmentManager();
        service = (AlarmManager) getSystemService(ALARM_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        inflater = getLayoutInflater();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Firebase startup
        FirebaseUtil.connect();


        //Views assignment
        clFragmentContainerBNV = findViewById(R.id.clFragmentContainerforBNV);
        bnvBottomMenu = findViewById(R.id.bnvBottomMenu);

        //Commands to execute
        executeCommands();




    }

    @Override
    protected void onStart() {
        super.onStart();
        executeCommand(new CommandSignIn(this));
        executeCommand(new CommandManageLogs());
        //executeCommand(new CommandPushNotification(this,service, notificationManager ));
    }

    private void executeCommand(Command command){
        command.execute();
    }

    private void executeCommands(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        notifications = prefs.getBoolean("notifications", false);
        daysBeforeExpire = prefs.getInt("daysBeforeExpire", 2);
        executeCommand(new CommandSignIn(this));
        executeCommand(new CommandAMbnvActions(bnvBottomMenu, clFragmentContainerBNV));


    }

    @Override
    protected void onPause() {
        super.onPause();
        executeCommand(new CommandSetPrefs(prefs));
        executeCommand(new CommandPushNotification(this,service, notificationManager ));
    }
}