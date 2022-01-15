package com.symbol.shoppinglistv2.Command;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.symbol.shoppinglistv2.Activities.FragmentAddBundle;
import com.symbol.shoppinglistv2.Activities.FragmentCreateCategory;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;

import androidx.lifecycle.MutableLiveData;

//Command to manage FAB actions according to the currently opened fragment
public class CommandManageFAB implements Command{

    private final String TAG = "CommandManageFAB";
    private FloatingActionButton fab;
    private View container;

    private FragmentAddBundle fragmentAddBundle = new FragmentAddBundle();
    private FragmentCreateCategory fragmentCreateCategory = new FragmentCreateCategory();
    private MutableLiveData tracker;

    public CommandManageFAB(FloatingActionButton fab, View container, MutableLiveData tracker){
        this.container = container;
        this.fab = fab;
        this.tracker = tracker;
    }

    @Override
    public boolean execute() {
        //actions of FAB accroding to the opened fragemnt - checked by tracker
        FragmentMyOpener fragmentMyOpener = new FragmentMyOpener(container);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tracker.getValue().equals("fragmentMyManageCategories")){
                    fragmentMyOpener.close(fragmentCreateCategory);
                    fragmentMyOpener.replace(fragmentCreateCategory);

                }else if (tracker.getValue().equals("fragmentMyManageBundles")){
                    fragmentMyOpener.close(fragmentAddBundle);
                    fragmentMyOpener.replace(fragmentAddBundle);
                }

            }
        });
        return false;
    }
}
