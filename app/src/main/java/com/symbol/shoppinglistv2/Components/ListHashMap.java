package com.symbol.shoppinglistv2.Components;

import java.util.HashMap;

public class ListHashMap {

    private HashMap<String, ListOfProducts> listOfProductsHashMap = new HashMap<>();

    public ListHashMap() {
    }

    public ListHashMap(HashMap<String, ListOfProducts> listOfProductsHashMap) {
        this.listOfProductsHashMap = listOfProductsHashMap;
    }

    public HashMap<String, ListOfProducts> getListOfProductsHashMap() {
        return listOfProductsHashMap;
    }

    public void setListOfProductsHashMap(HashMap<String, ListOfProducts> listOfProductsHashMap) {
        this.listOfProductsHashMap = listOfProductsHashMap;
    }
}
