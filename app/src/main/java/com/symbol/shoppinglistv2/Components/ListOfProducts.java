package com.symbol.shoppinglistv2.Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListOfProducts {

    private String name;
    private boolean shared = false;
    private HashMap<String, SharedMember> sharedWith = new HashMap<>();
    private HashMap<String, Product> products = new HashMap<>();
    private HashMap<String, MyBundle> bundles = new HashMap<>();
    private String sortType = "name";

    public ListOfProducts(){};


    public ListOfProducts(String name, HashMap<String,Product> products){ //, boolean shared, String sharedWith, ArrayList<Product> products
        this.name = name;
        this.products = products;
//        this.shared = shared;
//        this.sharedWith = sharedWith;
//        this.products = products;
    }

    public ListOfProducts(String name) {
        this.name = name;
    }

    public ListOfProducts(String name, boolean shared) {
        this.name = name;
        this.shared = shared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }


    public HashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Product> productHashMap) {
        this.products = productHashMap;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public HashMap<String, MyBundle> getBundles() {
        return bundles;
    }

    public void setBundles(HashMap<String, MyBundle> bundles) {
        this.bundles = bundles;
    }

    public HashMap<String, SharedMember> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(HashMap<String, SharedMember> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
