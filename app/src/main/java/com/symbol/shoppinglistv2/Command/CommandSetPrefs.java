package com.symbol.shoppinglistv2.Command;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
public class CommandSetPrefs implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandSetPrefs";
    private SharedPreferences preferences;

    public CommandSetPrefs(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean execute() {
        SharedPreferences.Editor editPrefs = preferences.edit();
        editPrefs.putBoolean("notifications", ActivityMain.notifications);
        editPrefs.putInt("daysBeforeExpire", ActivityMain.daysBeforeExpire);
        editPrefs.commit();
        return false;
    }
}
