package com.symbol.shoppinglistv2.Command;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.ActivityBarcodeScanner;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.Other.mCodeScanner;

import androidx.fragment.app.Fragment;

//Functionality to add products to the firebase
public class CommandAddNewProduct implements Command {
    //variables
    private final String TAG = "CommandAddNewProduct";
    private FragmentAddProduct fragmentAddProduct;
    private Product productOld;
    private ImageButton ibtnScanner;
    public static final boolean SINGLE_SCAN = true;

    public CommandAddNewProduct(FragmentAddProduct fragmentAddProduct, Product productOld, ImageButton ibtnScanner){
        this.fragmentAddProduct = fragmentAddProduct;
        this.productOld = productOld;
        this.ibtnScanner = ibtnScanner;
    }

    @Override
    public boolean execute() {
        ibtnScannerListener();
        btnClose();
        fragmentAddProduct.btnFABAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //creation of Product object with extracted parameters
                String name = fragmentAddProduct.etFABAddProductName.getText().toString();
                
                if(name.length() > 0){
                    Product product = extractValues();
                    //Callback to check if product already exists - see more class: MyCallback.onProductExistsCallback
                    //String path = "lists/" +FireBaseUtil.currentList +"/products/"+ product.getName();
                    String path = FirebaseUtil.mutableList.getValue().getListPath() +"/products/"+ product.getName();
                    FirebaseUtil.ifPathExists(path, new MyCallback() {
                        @Override
                        public boolean onProductExistsCallback(boolean isTrue) {
                            //checking if product already exists
                            if(isTrue == true){
                                //returning dialog box for user to confirm action

                                FirebaseUtil.addProduct(FirebaseUtil.mutableList.getValue(), product);
                                //dialogInfo(product);
                            }else{
                                //add product do database
                                if(productOld != null){
                                    String oldName = productOld.getName();
                                    String newName = product.getName();
                                    FirebaseUtil.addProduct(FirebaseUtil.mutableList.getValue(), product);
                                    if(!newName.equals(oldName)){
                                        FirebaseUtil.removeProduct(FirebaseUtil.mutableList.getValue(), productOld);
                                    }
                                }else{
                                    FirebaseUtil.addProduct(FirebaseUtil.mutableList.getValue(), product);
                                }

                                Toast.makeText(ActivityMain.appContext, "Product has been added", Toast.LENGTH_LONG).show();
                            }
                            return super.onProductExistsCallback(isTrue);
                        }
                    });
                    FragmentMyOpener fragmentMyOpener = new FragmentMyOpener(fragmentAddProduct);
                    fragmentMyOpener.close("test");
                }

            }
        });

        return false;
    }

    //dialogBox method
    private void dialogInfo(Product product){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            //onClick action for buttons in dialog box
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        FirebaseUtil.addProduct(FirebaseUtil.mutableList.getValue(), product);
                        Log.d(ActivityMain.TAG, "Dialog Positive: ");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.d(ActivityMain.TAG, "Dialog Negative: ");
                        break;

                }
            }
        };
        //config of dialogbox
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.appContext);
        builder.setMessage("There already exists product: " + product.getName()+ ". Would you like to replace it?");
        builder.setPositiveButton("Yes", dialogClickListener);
        builder.setNegativeButton("Choose Another Name", dialogClickListener);
        builder.show();
    }

    private Product extractValues(){
        String name = fragmentAddProduct.etFABAddProductName.getText().toString();
        double price;
        long barcodeVal;
        int avgDays;
        if(name.length() > 0){
            if (fragmentAddProduct.etFABAddProductPrice.getText().toString().length() <= 0) {
                price =0;
            }else{
                price = Double.parseDouble(fragmentAddProduct.etFABAddProductPrice.getText().toString());
            }
            Category category;
            try{
                category = (Category) fragmentAddProduct.spnProductCategory.getSelectedItem();
            }catch (IndexOutOfBoundsException e){
                category = (Category) fragmentAddProduct.spnProductCategory.getItemAtPosition(0);
            }
            if(fragmentAddProduct.etBarcodeValue.getText().length() > 1){
                barcodeVal = Long.parseLong(fragmentAddProduct.etBarcodeValue.getText().toString());
            }else{
                barcodeVal = 0;
            }
            if(fragmentAddProduct.etAvgProductDays.getText().toString().length() >0){
                avgDays = Integer.parseInt(fragmentAddProduct.etAvgProductDays.getText().toString());
            }else{
                avgDays = 0;
            }
            Product product = new Product(name, price, category, barcodeVal, avgDays);
            return product;
        }
        return null;
    }


    private void ibtnScannerListener(){
        ibtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.appContext, ActivityBarcodeScanner.class);
                intent.putExtra("singleScan", SINGLE_SCAN);
                ActivityMain.appContext.startActivity(intent);
            }
        });

    }

    private void btnClose(){
        FragmentMyOpener fragmentMyOpener = new FragmentMyOpener(fragmentAddProduct);
        fragmentAddProduct.btnFabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMyOpener.close("test");
            }
        });
    }

}
