package com.symbol.shoppinglistv2.Components;

import java.util.ArrayList;
import java.util.HashMap;

public class MyBundle{

    private String name;
    private int amount = 0;
    private double price =0;
    private ArrayList<Product> bundleProducts;
    private HashMap<String, Product> products;
    private int error = -740056;
    private boolean isChecked;

    public MyBundle(String name){
        this.name = name;
    }
    public MyBundle() {
    }

    public MyBundle(String bundleName, int bundleAmount, ArrayList<Product> bundleProducts) {
        this.name = bundleName;
        this.amount = bundleAmount;
        this.bundleProducts = bundleProducts;
    }

    public MyBundle(String name, ArrayList<Product> bundleProducts) {
        this.name = name;
        this.bundleProducts = bundleProducts;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<Product> getBundleProducts() {
        return bundleProducts;
    }

    public void setBundleProducts(ArrayList<Product> bundleProducts) {
        this.bundleProducts = bundleProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
