package com.symbol.shoppinglistv2.Command;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.symbol.shoppinglistv2.Activities.FragmentListSummary;
import com.symbol.shoppinglistv2.Activities.FragmentManageLists;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.R;

import androidx.lifecycle.MutableLiveData;

//Button action for FragmentMyLists
public class CommandManageLists implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandManageLists";
    private ImageButton ibtnListDetails;
    private ImageButton ibtnListOptions;
    private View container;

    private FragmentMyOpener fragmentMyOpener;
    private FragmentListSummary fragmentListSummary = new FragmentListSummary();
    private FragmentManageLists fragmentManageLists;
    private MutableLiveData<ListOfProducts> currentList;


    public CommandManageLists(ImageButton ibtnListDetails, ImageButton ibtnListOptions, View container, MutableLiveData<ListOfProducts> currentList) {
        this.ibtnListDetails = ibtnListDetails;
        this.ibtnListOptions = ibtnListOptions;
        this.container = container;
        this.currentList = currentList;
    }

    @Override
    public boolean execute() {
        fragmentMyOpener = new FragmentMyOpener(container);
        ibtnListDetailsListener();
        //Method to open new fragment (FragmentAddList) - where list can be added
        // Method to remove currently chosen list - in spinner
        menuItemClickListeners();
        //Method to open new fragemnt (FragmentMymanageLists) - where list can be edited
        return false;
    }



    private void menuItemClickListeners(){
        ibtnListOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(ActivityMain.appContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.list_actions, popupMenu.getMenu());
                popupMenu.getMenu().getItem(3).setTitle("Sort By: " + currentList.getValue().getSortType());
                if(!currentList.getValue().getListPath().contains(FirebaseUtil.userPath)){
                    popupMenu.getMenu().getItem(1).setEnabled(false);
                    popupMenu.getMenu().getItem(2).setEnabled(false);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menuAddNewList:
                                menuItemClickAddList();
                                break;
                            case R.id.menuEditList:
                                menuItemClickEditList();
                                break;
                            case R.id.menuRemoveList:
                                menuItemClickRemoveList();
                            case R.id.menuItemSortName:
                                menuItemSortType("name");
                                break;
                            case R.id.menuItemSortCategory:
                                menuItemSortType("category/name");
                                break;
                            case R.id.menuItemSortCustom:
                                menuItemSortType("customID");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void menuItemClickAddList(){
        fragmentManageLists = new FragmentManageLists();
        fragmentMyOpener.open(fragmentManageLists);
        fragmentMyOpener.close(fragmentManageLists);
    }

    private void menuItemClickEditList(){
        fragmentManageLists = new FragmentManageLists(FirebaseUtil.mutableList.getValue());
        fragmentMyOpener.close(fragmentManageLists);
        fragmentMyOpener.open(fragmentManageLists);
    }

    private void menuItemClickRemoveList(){
        FirebaseUtil.removeShare(currentList.getValue());
        FirebaseUtil.removeList(currentList.getValue().getName());
    }

    private void menuItemSortType(String sortType){

        currentList.getValue().setSortType(sortType);
        FirebaseUtil.mutableList.setValue(FirebaseUtil.mutableList.getValue());
        FirebaseUtil.globalRef.child(currentList.getValue()
                .getListPath()).child("sortType").setValue(sortType);
    }

    private void ibtnListDetailsListener(){

        ibtnListDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMyOpener.open(fragmentListSummary);
                fragmentMyOpener.close(fragmentListSummary);
            }
        });

    }


}
