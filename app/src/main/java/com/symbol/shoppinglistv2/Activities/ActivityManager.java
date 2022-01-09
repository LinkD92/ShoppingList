package com.symbol.shoppinglistv2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandOpenFragmentAM;
import com.symbol.shoppinglistv2.R;

public class ActivityManager extends AppCompatActivity {

    public FragmentManager manager = getSupportFragmentManager();
    public BottomNavigationView bottomNavigationView;
    public FragmentMyManageBundles fragmentMyManageBundles;
    public LinearLayoutCompat llcAMFragmentContainer;
    public static Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fragmentMyManageBundles = new FragmentMyManageBundles();
        bottomNavigationView = findViewById(R.id.bnvFragmentMyManage);
        llcAMFragmentContainer = findViewById(R.id.llcAMFragmentContainer);

        context = this;



        executeCommands();
    }

    private void executeCommands(){
        executeCommand(new CommandOpenFragmentAM(this));
    }

    private void executeCommand(Command command){command.execute();
    }

}