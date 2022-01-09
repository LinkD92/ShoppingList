package com.symbol.shoppinglistv2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandMAbnvActions;
import com.symbol.shoppinglistv2.Command.CommandSignIn;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.R;

/*

#FINISHED

#PARTIALY FINISHED
- Log in / log out
- Spinner for lists
- Product display

#TO DO LIST
- fab to add products
    - retrieve data of product
- creation/edit of new products
- sorting
- categorizing
- category class
- product class update
- list class update

 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static Context appContext;
    public static FragmentManager fragmentManager;
    public static MainActivity mainActivity;

    //Views
    public ConstraintLayout clFragmentContainer;
    public ConstraintLayout clFragmentContainerBNV;
    public BottomNavigationView bnvBottomMenu;
    public Spinner spinList;
    public RecyclerView rvProducts;
    public FloatingActionButton floatingActionButton;

    //FragmentManager
    public FragmentManager manager = getSupportFragmentManager();
    public static LayoutInflater inflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appContext = this;
        mainActivity = this;
        fragmentManager = getSupportFragmentManager();
        inflater = getLayoutInflater();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Firebase startup
        FireBaseUtil.connect();


        //Views assignment
        clFragmentContainerBNV = findViewById(R.id.clFragmentContainerforBNV);
        bnvBottomMenu = findViewById(R.id.bnvBottomMenu);

        //Commands to execute
        executeCommands();
    }

    @Override
    protected void onStart() {
        super.onStart();
        executeCommand(new CommandSignIn(this));
        Log.d(MainActivity.TAG, "MATAG: on Start");

    }

    private void executeCommand(Command command){
        command.execute();
    }

    private void executeCommands(){
        executeCommand(new CommandSignIn(this));
        executeCommand(new CommandMAbnvActions(bnvBottomMenu, clFragmentContainerBNV));
    }

}