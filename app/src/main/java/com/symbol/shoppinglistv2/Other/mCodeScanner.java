package com.symbol.shoppinglistv2.Other;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.symbol.shoppinglistv2.Activities.ActivityBarcodeScanner;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.MyLog;
import com.symbol.shoppinglistv2.Components.Product;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

//class for using camera as barcode Scanner
public class mCodeScanner implements Command {
    private final String TAG = this.getClass().getSimpleName();
    private ActivityBarcodeScanner activityBarcodeScanner;
    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;
    private final int CAMERA_REQUEST_CODE = 101;
    private String scannedCode;
    public static long barcodeVal = 0;
    public static MutableLiveData<Long> barcodeMultiple = new MutableLiveData<>();

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

    private void codeScanner() {
        Intent intent = activityBarcodeScanner.getIntent();
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
                        if (!result.equals(null)) {
                            //Freezing camera when catching the barcode(Otherwise it is keep scanning it)
                            codeScanner.stopPreview();
                            codeScanner.releaseResources();
                            //result of scanner
                            scannedCode = result.getText();
                            //setting up barcode as text under the scanner
                            long parseLong = Long.parseLong(scannedCode);
                            if (intent.getExtras().getBoolean("singleScan") == true) {
                                activityBarcodeScanner.textView.setText(scannedCode);
                                barcodeVal = parseLong;
                                activityBarcodeScanner.finish();
                            } else {
                                barcodeMultiple.setValue(parseLong);
                                codeScanner.stopPreview();
                                scannerListener();
                            }
                        }
                    }
                });
            }
        });
        //onClick to wake up the scanner
        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityBarcodeScanner.tvProductDetails.setText("");
                activityBarcodeScanner.tvProductBundleDetails.setText("");
                codeScanner.startPreview();
            }
        });
    }

    //Permissions - User needs to allow app to use camera - checking permissions
    private void setupPermission() {
        int permission = ContextCompat.checkSelfPermission(activityBarcodeScanner, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    //Permissions - User needs to allow app to use camera - raising request
    private void makeRequest() {
        ActivityCompat.requestPermissions(activityBarcodeScanner, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    private void scannerListener() {
        try {
            for (Map.Entry<String, Product> eprod :
                    FirebaseUtil.mutableList.getValue().getProducts().entrySet()) {
                boolean findProduct = eprod.getValue().getBarCode() == mCodeScanner.barcodeMultiple.getValue();
                boolean checkedProduct = eprod.getValue().isChecked() == false;
                Log.d(TAG, "scannerListener: trbls find products" + findProduct);
                Log.d(TAG, "scannerListener: trbls check product " + checkedProduct);
                if (findProduct && checkedProduct) {
                    ListOfProducts list = FirebaseUtil.mutableList.getValue();
                    Product product = eprod.getValue();
                    MyBundle bundle = list.getBundles().get(product.getGroup());
                    eprod.getValue().setChecked(true);
                    if (product.isChecked()) {
                        Calendar calendar = Calendar.getInstance();
                        Date date = new Date();
                        product.setLastCheckDate(date.getTime());
                        if (product.getGroup().length() > 0) {
                            bundle.getProducts().get(product.getName()).setChecked(true);
                            FirebaseUtil.addBundle(list, bundle);
                            if (allChecked(bundle)) {
                                bundle.setChecked(true);
                                FirebaseUtil.addBundle(list, bundle);
                            }

                        }
                        if (product.getAvgExpirationDays() != 0) {
                            String path = list.getListPath();
                            String test[] = path.split("/");
                            String logName = list.getName();
                            String logProduct = product.getName();
                            int days = product.getAvgExpirationDays();
                            calendar.add(Calendar.DAY_OF_YEAR, days);
                            String expirationDate = calendar.getTime().toString();
                            MyLog myLog = new MyLog(logName, logProduct, expirationDate);
                            FirebaseUtil.addLog(test[0], myLog);
                        }
                    } else {
                        if (product.getGroup().length() > 0) {
                            Log.d(TAG, "MyTest: uncheck" + product.getGroup());
                            bundle.getProducts().get(product.getName()).setChecked(true);
                            FirebaseUtil.addBundle(list, bundle);
                            if (!allChecked(bundle)) {
                                Log.d(TAG, "MyTest: uncheck - all checked");
                                bundle.setChecked(false);
                                FirebaseUtil.addBundle(list, bundle);
                            }
                        }

                    }

                    if (product.getGroup().length() > 0) {
                        String outputBundle = "Bundle product name:" + product.getName() + "\nBundle Product amount: " + product.getAmount()
                                + "\nBundle Product Group : " + product.getGroup();
                        activityBarcodeScanner.tvProductBundleDetails.setText(outputBundle);
                    } else {
                        String textOutput = "Product name: " + product.getName() + "\nProduct amount: " + product.getAmount();
                        activityBarcodeScanner.tvProductDetails.setText(textOutput);
                    }
                    FirebaseUtil.addProduct(FirebaseUtil.mutableList.getValue(), product);
                } else if(findProduct == false){
                    String info = "Product not found";
                    activityBarcodeScanner.tvProductBundleDetails.setText(info);
                }else{
                    String info = "Product already scanned";
                    activityBarcodeScanner.tvProductBundleDetails.setText(info);
                }
            }
        } catch (Exception e) {
            String info = "This product does not exists";
            activityBarcodeScanner.tvProductBundleDetails.setText(info);
        }
    }

    private boolean allChecked(MyBundle bundle) {
        for (Map.Entry<String, Product> prod :
                bundle.getProducts().entrySet()) {
            if (!prod.getValue().isChecked()) {
                Log.d(TAG, "MyTest: prod check" + prod.getValue().isChecked());
                return false;
            }
        }
        return true;
    }


}
