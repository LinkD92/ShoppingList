package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.MyLog;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterLogs;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandManageLogs implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandManageLogs";
    private ImageButton ibtnCloseFragment;
    private RecyclerView rvNotifications;
    private AdapterLogs adapterLogs;

    public CommandManageLogs(ImageButton ibtnCloseFragment, RecyclerView rvNotifications) {
        this.ibtnCloseFragment = ibtnCloseFragment;
        this.rvNotifications = rvNotifications;
    }

    @Override
    public boolean execute() {

        FirebaseUtil.readLog(new MyCallback() {
            @Override
            public void readLog(ArrayList<MyLog> myLogs) {
                super.readLog(myLogs);
                Collections.sort(myLogs, new Comparator<MyLog>() {
                    @Override
                    public int compare(MyLog mylog1, MyLog myLog) {
                        Date dateLog1 = new Date(Date.parse(mylog1.getExpirationDays()));
                        Date dateLog = new Date(Date.parse(myLog.getExpirationDays()));
                        if(dateLog1.getTime() > dateLog.getTime()){
                            return 1;
                        }else if(dateLog1.getTime() < dateLog.getTime()){
                            return -1;
                        }else{
                            return 0;
                        }
                    }
                });
                removeOldLogs(myLogs);

                adapterLogs = new AdapterLogs(myLogs);
                rvNotifications.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                rvNotifications.setAdapter(adapterLogs);
            }
        });
        return false;
    }

    private void removeOldLogs(ArrayList<MyLog> myLogs){
        Calendar calendar = Calendar.getInstance();
        for (MyLog myLog :
                myLogs) {
            Date temp = new Date(Date.parse(myLog.getExpirationDays()));
            if(!temp.after(calendar.getTime())){
                FirebaseUtil.removeLog(myLog);
            }
        }
    }
}
