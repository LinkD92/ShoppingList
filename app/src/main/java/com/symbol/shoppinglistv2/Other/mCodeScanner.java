package com.symbol.shoppinglistv2.Other;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.symbol.shoppinglistv2.Activities.ActivityBarcodeScanner;
import com.symbol.shoppinglistv2.Command.Command;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//class for using camera as barcode Scanner
public class mCodeScanner implements Command {
    private ActivityBarcodeScanner activityBarcodeScanner;
    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;
    private final int CAMERA_REQUEST_CODE =101;
    private String scannedCode;
    public static String barcodeVal = "0";

    public mCodeScanner(ActivityBarcodeScanner activityBarcodeScanner) {
        this.activityBarcodeScanner = activityBarcodeScanner;
    }

    @Override
    public boolean execute() {
        codeScannerView = activityBarcodeScanner.codeScannerView;
        codeScanner = new CodeScanner(activityBarcodeScanner, codeScannerView);
        setupPermission();
        codeScanner();

        return false;
    }

    private void codeScanner(){
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ONE_DIMENSIONAL_FORMATS);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);

        //Camera is frozen - tap to wake it up
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                activityBarcodeScanner.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!result.equals(null))
                        {
                            //Freezing camera when catching the barcode(Otherwise it is keep scanning it)
                            codeScanner.stopPreview();
                            codeScanner.releaseResources();
                            //result of scanner
                            scannedCode = result.getText();
                            //setting up barcode as text under the scanner
                            activityBarcodeScanner.textView.setText(scannedCode);
                            Intent intent = activityBarcodeScanner.getIntent();
                            barcodeVal = scannedCode;
                            activityBarcodeScanner.finish();
                        }
                    }
                });
            }
        });
        //onClick to wake up the scanner
        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    //Permissions - User needs to allow app to use camera - checking permissions
    private void setupPermission(){
        int permission = ContextCompat.checkSelfPermission(activityBarcodeScanner, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }
    //Permissions - User needs to allow app to use camera - raising request
    private void makeRequest() {
        ActivityCompat.requestPermissions(activityBarcodeScanner, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }


}
