package com.symbol.shoppinglistv2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScannerView;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Other.mCodeScanner;
import com.symbol.shoppinglistv2.R;

//Activity with view for barcodeScanner
public class ActivityBarcodeScanner extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    public CodeScannerView codeScannerView;
    public TextView textView, tvProductDetails, tvProductBundleDetails;
    public RecyclerView rvProductsSmall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_barcode_scanner);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        codeScannerView = findViewById(R.id.scanner_view);
        textView = findViewById(R.id.tv_text_View);
        tvProductDetails = findViewById(R.id.tvProductDetails);
        tvProductBundleDetails = findViewById(R.id.tvProductBundleDetails);

        executeCommand(new mCodeScanner(this));
    }


    private void executeCommand(Command command){
        command.execute();
    }
}