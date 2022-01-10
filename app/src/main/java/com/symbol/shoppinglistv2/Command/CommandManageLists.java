package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.symbol.shoppinglistv2.Activities.FragmentListDetails;
import com.symbol.shoppinglistv2.Activities.FragmentMyManageLists;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import androidx.lifecycle.MutableLiveData;

//Button action for FragmentMyLists
public class CommandManageLists implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandManageLists";
    private ImageButton ibtnListDetails;
    private ImageButton ibtnListOptions;
    private View container;

    private FragmentMyOpener fragmentMyOpener;
    private FragmentListDetails fragmentListDetails = new FragmentListDetails();
    private FragmentMyManageLists fragmentMyManageLists;
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

                PopupMenu popupMenu = new PopupMenu(MainActivity.appContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.list_actions, popupMenu.getMenu());
                popupMenu.getMenu().getItem(3).setTitle("Sort By: " + currentList.getValue().getSortType());
                Log.d(TAG, "onClick: " +popupMenu.toString());
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
        fragmentMyManageLists = new FragmentMyManageLists();
        fragmentMyOpener.open(fragmentMyManageLists);
        fragmentMyOpener.close(fragmentMyManageLists);
    }

    private void menuItemClickEditList(){
        fragmentMyManageLists = new FragmentMyManageLists(currentList.getValue());
        fragmentMyOpener.close(fragmentMyManageLists);
        fragmentMyOpener.open(fragmentMyManageLists);
    }

    private void menuItemClickRemoveList(){
        FireBaseUtil.removeShare(currentList.getValue());
        FireBaseUtil.removeList(currentList.getValue().getName());
    }

    private void menuItemSortType(String sortType){
        FireBaseUtil.getList(FireBaseUtil.currentList, new MyCallback() {
            @Override
            public void getList(ListOfProducts listOfProducts) {
                //return super.getList(listOfProducts);
                ListOfProducts list = listOfProducts;
                list.setSortType(sortType);
                FireBaseUtil.sortMethod = sortType;
                Log.d(TAG, "getList: " + FireBaseUtil.reference.child("lists/" + FireBaseUtil.currentList).child("sortType").setValue(sortType));
            }
        });
    }

    private void ibtnListDetailsListener(){
        ibtnListDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMyOpener.open(fragmentListDetails);
                fragmentMyOpener.close(fragmentListDetails);
            }
        });

    }


}
