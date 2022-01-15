package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Switch;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandFragmentSettingsLogic;
import com.symbol.shoppinglistv2.Command.CommandPushNotification;
import com.symbol.shoppinglistv2.R;


public class FragmentSettings extends Fragment {

    private Button button, btnSettingsNotificationLog;
    public View fragmentContainer;
    public SwitchCompat switchCompat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        button = v.findViewById(R.id.btnOpenLoginScreen);
        btnSettingsNotificationLog = v.findViewById(R.id.btnSettingsNotificationLog);
        fragmentContainer = v.findViewById(R.id.clSettingFragmentContainer);
        switchCompat = v.findViewById(R.id.swSettingsNotifications);

        executeCommand(new CommandFragmentSettingsLogic(button, btnSettingsNotificationLog, this));
        //executeCommand(new CommandPushNotification(this));

        return v;
    }

    private void executeCommand(Command command){command.execute();}
}