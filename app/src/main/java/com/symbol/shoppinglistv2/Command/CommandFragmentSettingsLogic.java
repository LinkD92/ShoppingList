package com.symbol.shoppinglistv2.Command;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symbol.shoppinglistv2.Activities.ActivitySignIn;
import com.symbol.shoppinglistv2.Activities.FragmentNotificationLog;
import com.symbol.shoppinglistv2.Activities.FragmentSettings;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;

public class CommandFragmentSettingsLogic implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandFragmentSettingsLogic";
    private Button button, btnSettingsNotificationLog;
    private FirebaseAuth mAuth;
    private ActivitySignIn activitySignIn;
    private FragmentSettings fragmentSettings;
    private FragmentMyOpener fragmentMyOpener;
    private FragmentNotificationLog fragmentNotificationLog = new FragmentNotificationLog();

    public CommandFragmentSettingsLogic(Button button, Button btnSettingsNotificationLog, FragmentSettings fragmentSettings) {
        this.button = button;
        this.btnSettingsNotificationLog = btnSettingsNotificationLog;
        this.fragmentSettings = fragmentSettings;
    }

    @Override
    public boolean execute() {
        btnNotificationListener();
        activitySignIn = new ActivitySignIn();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " );
                Intent intent = new Intent(ActivityMain.activityMain, activitySignIn.getClass());
                ActivityMain.activityMain.startActivity(intent);
            }
        });
        return false;
    }

    private void btnNotificationListener(){
        fragmentMyOpener = new FragmentMyOpener(fragmentSettings.fragmentContainer);
        btnSettingsNotificationLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMyOpener.open(fragmentNotificationLog);
                fragmentMyOpener.close(fragmentNotificationLog);
            }
        });
    }
}
