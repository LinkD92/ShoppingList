package com.symbol.shoppinglistv2.Other;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import petrov.kristiyan.colorpicker.ColorPicker;

//custom Color picker class
public class MyColorPicker {
    private final String TAG = "MyColorPicker";
    private ArrayList<String> colors = new ArrayList<>();
    private Activity activity;
    private ColorPicker colorPicker;
    private int chosenColor;
    private ConstraintLayout clSquare;


    public MyColorPicker (Activity activity){
        this.activity = activity;
        //hexaColors();
    }

    public MyColorPicker(Activity activity, ConstraintLayout clSquare) {
        this.activity = activity;
        this.clSquare = clSquare;
        //hexaColors();
    }

    public void start(){
        colorPicker = new ColorPicker(activity);
        //colorPicker.setColors(colors);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                chosenColor = color;
                Log.d(TAG, "onChooseColor: " + chosenColor);
                clSquare.setBackgroundColor(chosenColor);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private ArrayList<String> hexaColors(){
        colors.add("#4287f5");
        colors.add("#d9ff00");
        colors.add("#00db1a");
        return colors;
    }

    public int getChosenColor(){
        Log.d(TAG, "getChosenColor: " + chosenColor);
        return chosenColor;
    }

}
