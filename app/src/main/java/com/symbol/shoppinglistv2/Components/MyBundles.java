package com.symbol.shoppinglistv2.Components;

import java.util.ArrayList;

public class MyBundles {
    private ArrayList<MyBundle> arrayList;

    public MyBundles(ArrayList<MyBundle> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<MyBundle> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MyBundle> arrayList) {
        this.arrayList = arrayList;
    }
}

