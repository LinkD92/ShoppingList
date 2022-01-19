package com.symbol.shoppinglistv2.Command;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symbol.shoppinglistv2.Activities.ActivitySignIn;
import com.symbol.shoppinglistv2.Activities.FragmentNotificationLog;
import com.symbol.shoppinglistv2.Activities.FragmentSettings;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

public class CommandFragmentSettingsLogic implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandFragmentSettingsLogic";
    private Button button, btnSettingsNotificationLog;
    private FirebaseAuth mAuth;
    private ActivitySignIn activitySignIn;
    private FragmentSettings fragmentSettings;
    private FragmentMyOpener fragmentMyOpener;
    private FragmentNotificationLog fragmentNotificationLog = new FragmentNotificationLog();
    private ArrayAdapter adapter;

    public CommandFragmentSettingsLogic(Button button, Button btnSettingsNotificationLog, FragmentSettings fragmentSettings) {
        this.button = button;
        this.btnSettingsNotificationLog = btnSettingsNotificationLog;
        this.fragmentSettings = fragmentSettings;
    }

    @Override
    public boolean execute() {
        btnNotificationListener();
        switchNotificationsListener();
        spinnerActions();
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

    private void switchNotificationsListener(){
        fragmentSettings.switchCompat.setChecked(ActivityMain.notifications);
        fragmentSettings.switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.notifications = fragmentSettings.switchCompat.isChecked();
            }
        });
    }

    private void spinnerActions(){
        ArrayList<Integer> expireDays = new ArrayList<>();
        expireDays.add(1);
        expireDays.add(2);
        expireDays.add(3);
        expireDays.add(4);
        expireDays.add(5);
        adapter = new ArrayAdapter(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, expireDays);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        fragmentSettings.spnDaysBeforeExpire.setAdapter(adapter);
        fragmentSettings.spnDaysBeforeExpire.setSelection(ActivityMain.daysBeforeExpire - 1);
        fragmentSettings.spnDaysBeforeExpire.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityMain.daysBeforeExpire = expireDays.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
