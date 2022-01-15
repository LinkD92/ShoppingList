package com.symbol.shoppinglistv2.Command;

import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.MyLog;
import com.symbol.shoppinglistv2.Other.AdapterLogs;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandManageLogs implements Command{
    private ImageButton ibtnCloseFragment;
    private RecyclerView rvNotifications;
    private AdapterLogs adapterLogs;

    public CommandManageLogs(ImageButton ibtnCloseFragment, RecyclerView rvNotifications) {
        this.ibtnCloseFragment = ibtnCloseFragment;
        this.rvNotifications = rvNotifications;
    }

    @Override
    public boolean execute() {

        FireBaseUtil.readLog(new MyCallback() {
            @Override
            public void readLog(ArrayList<MyLog> myLogs) {
                super.readLog(myLogs);
                adapterLogs = new AdapterLogs(myLogs);
                rvNotifications.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                rvNotifications.setAdapter(adapterLogs);
            }
        });




        return false;
    }
}
