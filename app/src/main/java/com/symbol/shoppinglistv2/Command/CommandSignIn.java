package com.symbol.shoppinglistv2.Command;

import android.content.Intent;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symbol.shoppinglistv2.Activities.ActivitySignIn;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;

//Force SignIn when user starts the app - App do not allow to interact without signing in
public class CommandSignIn implements Command{
    private FirebaseAuth mAuth;
    private MainActivity mainActivity;
    private ActivitySignIn activitySignIn;
    private static final String TAG = "trb";

    public CommandSignIn(MainActivity mainActivity){
        this.mainActivity = mainActivity;

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
            Intent intent = new Intent(mainActivity, activitySignIn.getClass());
            mainActivity.startActivity(intent);
        }else
        {
            //do nothing if user is already signIN
        }
        return false;
    }

}
