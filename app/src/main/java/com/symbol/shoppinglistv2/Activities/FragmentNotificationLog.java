package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandManageLogs;
import com.symbol.shoppinglistv2.R;


public class FragmentNotificationLog extends Fragment {

    private ImageButton ibtnCloseFragment;
    private RecyclerView rvNotifications;


    public FragmentNotificationLog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_notification_log, container, false);

        rvNotifications = v.findViewById(R.id.rvNotifications);

        executeCommand(new CommandManageLogs(ibtnCloseFragment, rvNotifications));

        return v;
    }

    private void executeCommand(Command command){command.execute();}
}