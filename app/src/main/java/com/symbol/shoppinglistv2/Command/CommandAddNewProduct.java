package com.symbol.shoppinglistv2.Command;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

//Functionality to add products to the firebase
public class CommandAddNewProduct implements Command {
    //variables
    private final String TAG = "CommandAddNewProduct";
    private EditText productName;
    private EditText productPrice;
    private String sharedWith;
    private Button button;
    private FragmentAddProduct fragmentAddProduct;

    public CommandAddNewProduct(Button button, EditText productName, EditText productPrice){
        this.button = button;
        this.productName = productName;
        this.productPrice = productPrice;
    };

    public CommandAddNewProduct(FragmentAddProduct fragmentAddProduct){
        this.fragmentAddProduct = fragmentAddProduct;
    }

    @Override
    public boolean execute() {

        fragmentAddProduct.btnFABAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //creation of Product object with extracted parameters
                String name = fragmentAddProduct.etFABAddProductName.getText().toString();
                if(name.length() > 0){
                    Product product = extractValues();
                    //Callback to check if product already exists - see more class: MyCallback.onProductExistsCallback
                    //String path = "lists/" +FireBaseUtil.currentList +"/products/"+ product.getName();
                    String path = FireBaseUtil.mutableList.getValue().getListPath() +"/products/"+ product.getName();
                    FireBaseUtil.ifPathExists(path, new MyCallback() {
                        @Override
                        public boolean onProductExistsCallback(boolean isTrue) {
                            //checking if product already exists
                            if(isTrue == true){
                                //returning dialog box for user to confirm action
                                dialogInfo(product);
                            }else{
                                //add product do database
                                FireBaseUtil.addProduct(FireBaseUtil.mutableList.getValue(), product);
                                ListOfProducts list = FireBaseUtil.mutableList.getValue();
                                list.getProducts().put(product.getName(), product);
                                FireBaseUtil.mutableList.setValue(list);

                                Toast.makeText(ActivityMain.appContext, "Product has been added", Toast.LENGTH_LONG).show();
                            }
                            return super.onProductExistsCallback(isTrue);
                        }
                    });
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
                        FireBaseUtil.addProduct(FireBaseUtil.mutableList.getValue(), product);
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
        int barcodeVal;
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
            if(fragmentAddProduct.etBarcodeValue.getText().toString().length() > 0){
                barcodeVal = Integer.parseInt(fragmentAddProduct.etBarcodeValue.getText().toString());
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

}
