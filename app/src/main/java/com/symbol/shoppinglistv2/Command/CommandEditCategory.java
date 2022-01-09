package com.symbol.shoppinglistv2.Command;

import android.widget.EditText;

import com.symbol.shoppinglistv2.Components.Category;

import androidx.constraintlayout.widget.ConstraintLayout;

//Edit category command
public class CommandEditCategory implements Command{

    private EditText etNewName;
    private ConstraintLayout clSquare;
    private Category category;

    public CommandEditCategory(EditText etNewName, ConstraintLayout clSquare, Category category) {
        this.etNewName = etNewName;
        this.clSquare = clSquare;
        this.category = category;
    }

    @Override
    public boolean execute() {
        //assigns values to the views if clicked on existing category edit button
        if(category!=null){
            etNewName.setText(category.getName());
            clSquare.setBackgroundColor(category.getColor());

        }


        return false;
    }
}
