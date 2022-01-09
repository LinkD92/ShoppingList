package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandAddNewProduct;
import com.symbol.shoppinglistv2.Command.CommandCategorySpnAdapter;
import com.symbol.shoppinglistv2.Command.CommandEditProduct;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.mCodeScanner;
import com.symbol.shoppinglistv2.R;

public class FragmentAddProduct extends Fragment {

    private final String TAG = "FragmentAddProduct";
    public EditText etFABAddProductName;
    public EditText etFABAddProductPrice;
    public Button btnFABAddProduct;
    public Spinner spnProductCategory;
    public EditText etBarcodeValue;
    public EditText etAvgProductDays;
    public ImageButton ibtnScanBarcode;
    private Product product;


    public FragmentAddProduct() {
        // Required empty public constructor
    }

    public FragmentAddProduct(Product product){
        this.product = product;
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);


        etFABAddProductName = v.findViewById(R.id.etFABAddProductName);
        etFABAddProductPrice = v.findViewById(R.id.etFABAddProductPrice);
        btnFABAddProduct = v.findViewById(R.id.btnFABAddProduct);
        spnProductCategory = v.findViewById(R.id.spnProductCategory);
        etBarcodeValue = v.findViewById(R.id.etBarcodeValue);
        etAvgProductDays = v.findViewById(R.id.etAvgProductDays);
        ibtnScanBarcode = v.findViewById(R.id.ibtnScanBarcode);

        //executeCommand(new CommandProductAutoCompleteList(etFABAddProductName, acList));
        executeCommand(new CommandAddNewProduct(this));
        executeCommand(new CommandCategorySpnAdapter(spnProductCategory, product));
        executeCommand(new CommandEditProduct(product,this));
        return v;
    }

    private void executeCommand(Command command){command.execute();}

    @Override
    public void onResume() {
        etBarcodeValue.setText(mCodeScanner.barcodeVal);
        super.onResume();
    }
}