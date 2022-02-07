package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandFragmentSettingsLogic;
import com.symbol.shoppinglistv2.Command.CommandImportExport;
import com.symbol.shoppinglistv2.Command.CommandPushNotification;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.R;


public class FragmentSettings extends Fragment {

    private final String TAG = "com.symbol.shoppinglistv2.Activities.FragmentSettings";
    private Button button, btnSettingsNotificationLog, btnImport, btnExport, btnExportGroup, btnImportGroup;
    public View fragmentContainer;
    public SwitchCompat switchCompat;
    public Spinner spnDaysBeforeExpire;
    public TextView tvLoginCurrentUser;

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
        spnDaysBeforeExpire = v.findViewById(R.id.spnDaysToExpire);
        btnImport = v.findViewById(R.id.btnSettingImportList);
        btnExport = v.findViewById(R.id.btnSettingExportList);
        btnImportGroup = v.findViewById(R.id.btnSettingImportGroup);
        btnExportGroup = v.findViewById(R.id.btnSettingExportGroup);
        tvLoginCurrentUser = v.findViewById(R.id.tvLoginCurrentUser2);
        tvLoginCurrentUser.setText(FirebaseUtil.user.getEmail());


        executeCommand(new CommandFragmentSettingsLogic(button, btnSettingsNotificationLog, this));
        executeCommand(new CommandImportExport(btnImport, btnExport, btnImportGroup, btnExportGroup));

        return v;
    }

    private void executeCommand(Command command){command.execute();}
}