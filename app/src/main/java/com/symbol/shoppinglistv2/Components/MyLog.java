package com.symbol.shoppinglistv2.Components;

import java.util.ArrayList;

public class MyLog {
    private String listName;
    private String productName;
    private String expirationDate;
    private ArrayList<MyLog> myLogArrayList;

    public MyLog() {
    }

    public MyLog(String listName, String productName, String expirationDate) {
        this.listName = listName;
        this.productName = productName;
        this.expirationDate = expirationDate;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExpirationDays() {
        return expirationDate;
    }

    public void setExpirationDays(String expirationDays) {
        this.expirationDate = expirationDays;
    }

    public ArrayList<MyLog> getMyLogArrayList() {
        return myLogArrayList;
    }

    public void setMyLogArrayList(ArrayList<MyLog> myLogArrayList) {
        this.myLogArrayList = myLogArrayList;
    }
}
