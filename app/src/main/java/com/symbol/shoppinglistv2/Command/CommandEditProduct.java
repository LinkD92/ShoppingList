package com.symbol.shoppinglistv2.Command;

import android.util.Log;

import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandEditProduct implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandEditProduct";
    private Product product;
    private FragmentAddProduct fragmentAddProduct;

    public CommandEditProduct(Product product, FragmentAddProduct fragmentAddProduct) {
        this.product = product;
        this.fragmentAddProduct = fragmentAddProduct;
    }

    @Override
    public boolean execute() {
        if(product != null){
            String avgDaysConvert = String.valueOf(product.getAmount());
            fragmentAddProduct.etAvgProductDays.setText(avgDaysConvert);

            fragmentAddProduct.etFABAddProductName.setText(product.getName());

            String priceConvert = String.valueOf(product.getPrice());
            fragmentAddProduct.etFABAddProductPrice.setText(priceConvert);

            String barcodeConvert = String.valueOf(product.getBarCode());
            fragmentAddProduct.etBarcodeValue.setText(barcodeConvert);

            Log.d(TAG, "execute: "  + fragmentAddProduct.spnProductCategory.getSelectedItem());
        }
        return false;
    }
}
