package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.view.View;

import com.symbol.shoppinglistv2.Activities.FragmentCreateCategory;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.Category;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Fragment Opener class for simple manage fragments
public class FragmentMyOpener {
    private final String TAG = "com.symbol.shoppinglistv2.Other.FragmentMyOpener";
    private FragmentManager fragmentManager = ActivityMain.fragmentManager;
    private int container;
    private FragmentTransaction transaction;
    private Fragment fragment;
    private FragmentCreateCategory fragmentCreateCategory;

    public FragmentMyOpener(View container, Fragment fragment) {
        this.container = container.getId();
        this.fragment = fragment;
    }

    public FragmentMyOpener(Fragment fragment){
        this.fragment = fragment;
    }

    public FragmentMyOpener(View container) {
        this.container = container.getId();
    }

    public void open(Fragment fragment) {
        if(!fragment.isVisible()) {
            transaction = fragmentManager.beginTransaction();
            transaction.replace(container, fragment);
            transaction.attach(fragment).commit();
        }
    }

    public void close(Fragment fragment){
        if(fragment.isVisible()) {
            transaction = fragmentManager.beginTransaction();
            transaction.detach(fragment).commit();
        }
    }

    public void replace(Fragment fragment) {
        if(!fragment.isVisible()) {
            transaction = fragmentManager.beginTransaction();
            transaction.replace(container, fragment);
            transaction.attach(fragment).commit();
        }
    }

    public void createTag(Fragment fragment, String tag){
        fragmentManager.beginTransaction().add(container,fragment, tag).commit();
        Log.d(TAG, "createTag: " + tag);
    }

    public void open(String tag) {
         FragmentCreateCategory fragment = (FragmentCreateCategory) fragmentManager.findFragmentByTag(tag);
        if (!fragment.isVisible()) {
            Log.d(TAG, "open:  ::: " + fragment.isDetached() + " " + fragment.isAdded());
            transaction = fragmentManager.beginTransaction();
            //transaction.replace(container, fragment);
            transaction.attach(fragment).commit();
        }

    }

    public void close(String tag) {
        FragmentCreateCategory fragment = (FragmentCreateCategory) fragmentManager.findFragmentByTag(tag);
        if (fragment.isVisible()) {
            Log.d(TAG, "close ::: " + fragment.isVisible());
            transaction = fragmentManager.beginTransaction();
            transaction.detach(fragment).commit();
        }

    }

    public void setCategoryValues(Category category, String tag){
        FragmentCreateCategory fragmentCreateCategory = (FragmentCreateCategory) fragmentManager.findFragmentByTag(tag);
        if(category != null){
            Log.d(TAG, ": " + category.getName());
            fragmentCreateCategory.etCategoryName.setText(category.getName());
            fragmentCreateCategory.clSquare.setBackgroundColor(category.getColor());
            Log.d(TAG, "setCategoryValues: " + fragmentCreateCategory.etCategoryName.getText());
        }

    }

    public Fragment getFragmentByTag(String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        Log.d(TAG, "getFragmentAddCategory: " + fragment.getTag());
        return fragment;
    }


}
