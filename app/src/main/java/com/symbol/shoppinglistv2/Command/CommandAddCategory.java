package com.symbol.shoppinglistv2.Command;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.symbol.shoppinglistv2.Activities.FragmentCreateCategory;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyColorPicker;

import androidx.constraintlayout.widget.ConstraintLayout;
//Logic to add category
public class CommandAddCategory implements Command{

    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandAddCategory";

    //Declaration of parameters
    private EditText etCategoryName;
    private ConstraintLayout clSquare;
    private Button btnConfirm;
    private String categoryName;
    private String oldName;
    private int categoryColor;
    private MyColorPicker myColorPicker;
    private Activity activity;
    private Button btnAddCategoryCancel;
    private FragmentCreateCategory fragmentCreateCategory;

    public CommandAddCategory(EditText etCategoryName, ConstraintLayout clSquare, Button btnConfirm,
                              Activity activity, Button btnAddCategoryCancel, FragmentCreateCategory fragmentCreateCategory) {
        this.etCategoryName = etCategoryName;
        this.clSquare = clSquare;
        this.btnConfirm = btnConfirm;
        this.activity = activity;
        this.btnAddCategoryCancel = btnAddCategoryCancel;
        this.fragmentCreateCategory = fragmentCreateCategory;
    }

    @Override
    public boolean execute() {
        FragmentMyOpener fragmentMyOpener = new FragmentMyOpener();
        btnCancelListener(fragmentMyOpener);
        //old name required to compare if name was changed
        oldName = etCategoryName.getText().toString();

        //color picker initliaization from custom class MyColorPicker
        myColorPicker = new MyColorPicker(activity, clSquare);
        clSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myColorPicker.start();
            }
        });

        //Confirm button action
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = etCategoryName.getText().toString();
                //assignig category (if it was provided)
                if(categoryName.length() >0){
                    ColorDrawable color = (ColorDrawable)clSquare.getBackground();
                    categoryColor = color.getColor();
                    Category category = new Category(categoryName, categoryColor);
                    FirebaseUtil.addCategory(category);
                }

                //to compare old and new name - if it is different - remove old record
                if(!oldName.equals(categoryName) && oldName.length()>0){
                    FirebaseUtil.reference.child("categories/" + oldName).removeValue();

                }
                //clear text view after button save action
                etCategoryName.setText("");
                //set square color picker to black
                clSquare.setBackgroundColor(-16777216);
                fragmentMyOpener.close("addCategory");
            }
        });
        return false;
    }

    private void btnCancelListener(FragmentMyOpener fragmentMyOpener){
        btnAddCategoryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMyOpener.close("addCategory");
            }
        });
    }
}
