package com.symbol.shoppinglistv2.Command;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

//Class to add existing product from list to bundle - implementation in progress
public class CommandAddProduct implements Command{

    private final String TAG = "CommandAddProduct";
    private Product product;
    private DatabaseReference refProductLocation;
    private DatabaseReference refProductToList;
    private Button button;
    private EditText editText;
    private Product productCopied;

    public CommandAddProduct(Button button, EditText editText){
        this.button = button;
        this.editText = editText;
    }



    @Override
    public boolean execute() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productLocation = editText.getText().toString();
                FireBaseUtil.getProduct(productLocation, new MyCallback() {
                    @Override
                    public Product getProduct(Product product) {
                        FireBaseUtil.addProduct(product);
                        return super.getProduct(product);
                    }
                });
            }

        });


        return false;
    }
}
