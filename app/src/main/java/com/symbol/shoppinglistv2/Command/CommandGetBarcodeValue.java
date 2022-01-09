package com.symbol.shoppinglistv2.Command;

import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.symbol.shoppinglistv2.Activities.ActivityBarcodeScanner;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Other.mCodeScanner;

//Will be implemeted fruther to extract barcode
public class CommandGetBarcodeValue implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandGetBarcodeValue";
    private ImageButton ibtnStartSCanner;
    private EditText etBarcodeValue;

    public CommandGetBarcodeValue(ImageButton ibtnStartSCanner, EditText etBarcodeValue) {
        this.etBarcodeValue = etBarcodeValue;
        this.ibtnStartSCanner = ibtnStartSCanner;

    }

    @Override
    public boolean execute() {
        if(!mCodeScanner.barcodeVal.equals("0")){
            int converBarcode = Integer.parseInt(mCodeScanner.barcodeVal);
            etBarcodeValue.setText(converBarcode);
        }
        ibtnStartSCanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntet = new Intent(MainActivity.appContext, ActivityBarcodeScanner.class);
                MainActivity.appContext.startActivity(myIntet);
            }
        });

        return false;
    }
}
