package com.symbol.shoppinglistv2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandAMbnvActions;
import com.symbol.shoppinglistv2.Command.CommandPushNotification;
import com.symbol.shoppinglistv2.Command.CommandSetPrefs;
import com.symbol.shoppinglistv2.Command.CommandSignIn;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.R;


public class ActivityMain extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static Context appContext;
    public static FragmentManager fragmentManager;
    public static ActivityMain activityMain;
    public static AlarmManager service;
    public static NotificationManager notificationManager;
    public static boolean notifications;
    public static int daysBeforeExpire;
    private SharedPreferences prefs;

    //Views
    public ConstraintLayout clFragmentContainer;
    public ConstraintLayout clFragmentContainerBNV;
    public BottomNavigationView bnvBottomMenu;
    public Spinner spinList;
    public RecyclerView rvProducts;
    public FloatingActionButton floatingActionButton;

    //FragmentManager
    public FragmentManager manager = getSupportFragmentManager();
    public static LayoutInflater inflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    private void executeCommand(Command command){
        command.execute();
    }

    private void executeCommands(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        notifications = prefs.getBoolean("notifications", false);
        daysBeforeExpire = prefs.getInt("daysBeforeExpire", 2);
        Log.d(TAG, "MyTest  execute : " + notifications);
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