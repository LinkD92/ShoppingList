package com.symbol.shoppinglistv2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandMAbnvActions;
import com.symbol.shoppinglistv2.Command.CommandPushNotification;
import com.symbol.shoppinglistv2.Command.CommandSignIn;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.R;

import java.util.Calendar;
import java.util.Date;

/*

#FINISHED

#PARTIALY FINISHED
- Log in / log out
- Spinner for lists
- Product display

#TO DO LIST
- fab to add products
    - retrieve data of product
- creation/edit of new products
- sorting
- categorizing
- category class
- product class update
- list class update

 */

public class ActivityMain extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static Context appContext;
    public static FragmentManager fragmentManager;
    public static ActivityMain activityMain;
    public static AlarmManager service;
    public static NotificationManager notificationManager;

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
        FireBaseUtil.connect();


        //Views assignment
        clFragmentContainerBNV = findViewById(R.id.clFragmentContainerforBNV);
        bnvBottomMenu = findViewById(R.id.bnvBottomMenu);

        //Commands to execute
        executeCommands();

        Date data = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 8);
        Date nowaData = calendar.getTime();

    }

    @Override
    protected void onStart() {
        super.onStart();
        executeCommand(new CommandSignIn(this));
        Log.d(ActivityMain.TAG, "MATAG: on Start");

    }

    private void executeCommand(Command command){
        command.execute();
    }

    private void executeCommands(){
        executeCommand(new CommandSignIn(this));
        executeCommand(new CommandMAbnvActions(bnvBottomMenu, clFragmentContainerBNV));
        executeCommand(new CommandPushNotification(this,service, notificationManager ));
    }

}