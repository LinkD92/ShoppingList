package com.symbol.shoppinglistv2.Command;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symbol.shoppinglistv2.Activities.ActivitySignIn;
import com.symbol.shoppinglistv2.Activities.MainActivity;

public class CommandFragmentSettingsLogic implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandFragmentSettingsLogic";
    private Button button;
    private FirebaseAuth mAuth;
    private ActivitySignIn activitySignIn;

    public CommandFragmentSettingsLogic(Button button) {
        this.button = button;
    }

    @Override
    public boolean execute() {
        activitySignIn = new ActivitySignIn();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " );
                Intent intent = new Intent(MainActivity.mainActivity, activitySignIn.getClass());
                MainActivity.mainActivity.startActivity(intent);
            }
        });
        return false;
    }

    private void executeCommand(Command command){command.execute();}
}
