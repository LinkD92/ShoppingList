package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandAddCategory;
import com.symbol.shoppinglistv2.Command.CommandEditCategory;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.R;

//Add category Fragment views
public class FragmentCreateCategory extends FragmentManageCategories {


    private final String TAG = "FragmentAddCategory";
    public  EditText etCategoryName;
    public  ConstraintLayout clSquare;
    private Button btnConfirm;
    private Category category;


    public FragmentCreateCategory(Category category){
        this.category = category;
    }

    public FragmentCreateCategory(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_category, container, false);



        etCategoryName = v.findViewById(R.id.etCategoryName);
        clSquare = v.findViewById(R.id.clAddCategoryColorSqure);
        btnConfirm = v.findViewById(R.id.btnAddCategoryConfirm);

        executeCommand(new CommandEditCategory(etCategoryName, clSquare, category));
        executeCommand(new CommandAddCategory(etCategoryName, clSquare, btnConfirm, getActivity()));


        return v;
    }

    private void executeCommand(Command command){command.execute();}
}