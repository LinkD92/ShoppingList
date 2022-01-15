package com.symbol.shoppinglistv2.Command;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symbol.shoppinglistv2.Activities.ActivitySignIn;
import com.symbol.shoppinglistv2.Activities.ActivityMain;

//Force SignIn when user starts the app - App do not allow to interact without signing in
public class CommandSignIn implements Command{
    private FirebaseAuth mAuth;
    private ActivityMain activityMain;
    private ActivitySignIn activitySignIn;
    private static final String TAG = "trb";

    public CommandSignIn(ActivityMain activityMain){
        this.activityMain = activityMain;

    }
    @Override
    public boolean execute() {
        //assigning Activity SignIn
        activitySignIn = new ActivitySignIn();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Checking if user is already Signedin
        if(currentUser == null){
            //If user is not signed in - Opens SignIn activity
            Intent intent = new Intent(activityMain, activitySignIn.getClass());
            activityMain.startActivity(intent);
        }else
        {
            //do nothing if user is already signIN
        }
        return false;
    }

}
