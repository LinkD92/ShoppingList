package com.symbol.shoppinglistv2.Command;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyColorPicker;
import com.symbol.shoppinglistv2.R;

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

    public CommandAddCategory(EditText etCategoryName, ConstraintLayout clSquare, Button btnConfirm, Activity activity) {
        this.etCategoryName = etCategoryName;
        this.clSquare = clSquare;
        this.btnConfirm = btnConfirm;
        this.activity = activity;
    }

    @Override
    public boolean execute() {
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
                    FireBaseUtil.addCategory(category);
                }

                //to compare old and new name - if it is different - remove old record
                if(!oldName.equals(categoryName) && oldName.length()>0){
                    FireBaseUtil.reference.child("categories/" + oldName).removeValue();

                }
                //clear text view after button save action
                etCategoryName.setText("");
                //set square color picker to black
                clSquare.setBackgroundColor(-16777216);
            }
        });
        return false;
    }
}
