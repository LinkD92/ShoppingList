package com.symbol.shoppinglistv2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScannerView;
import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Other.mCodeScanner;
import com.symbol.shoppinglistv2.R;

//Activity with view for barcodeScanner
public class ActivityBarcodeScanner extends AppCompatActivity {
    public CodeScannerView codeScannerView;
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_barcode_scanner);
        super.onCreate(savedInstanceState);
        codeScannerView = findViewById(R.id.scanner_view);
        textView = findViewById(R.id.tv_text_View);
        executeCommand(new mCodeScanner(this));
    }

//    public void getInfo(String url){
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        wtfValue = response.toString();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("trb - Success", error.toString());
//                        wtfValue = "Loading";
//                    }
//                }
//        );
//
//        requestQueue.add(objectRequest);
//    }

    private void executeCommand(Command command){
        command.execute();
    }
}