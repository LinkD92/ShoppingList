package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandFragmentSettingsLogic;
import com.symbol.shoppinglistv2.R;


public class FragmentSettings extends Fragment {

    private Button button;

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

        executeCommand(new CommandFragmentSettingsLogic(button));
        return v;
    }

    private void executeCommand(Command command){command.execute();}
}