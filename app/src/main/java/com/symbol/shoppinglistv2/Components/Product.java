package com.symbol.shoppinglistv2.Components;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Product class that contains all necessary information
public class Product {

    private String name;
    private double price;
    private Category category;
    private Date expirationDate;
    private int amount = 0;
    private boolean checked;
    private int barCode = 0;
    private int avgExpirationDays;
    private int customID = 0;
    private int bundleAmount =0;
    private long lastCheckDate =0;



    public Product (){
    }
    public Product (String name){
        this.name = name;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }


    public Product (String name, double price, Category category, int barCode, int avgExpirationTime){
        this.name = name;
        this.price = price;
        this.category = category;
        this.barCode = barCode;
        this.avgExpirationDays = avgExpirationTime;
//        shared = false;
//        this.sharedWith = sharedWith;
    }


        public Product(Product copyProduct){
        this.name = copyProduct.name;
        this.price = copyProduct.price;
        this.category = copyProduct.category;


    }

    public Map<String, Object> addToMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("category", category);

        return result;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public int getAvgExpirationDays() {
        return avgExpirationDays;
    }

    public void setAvgExpirationDays(int avgExpirationDays) {
        this.avgExpirationDays = avgExpirationDays;
    }

    public int getCustomID() {
        return customID;
    }

    public void setCustomID(int customID) {
        this.customID = customID;
    }

    public int getBundleAmount() {
        return bundleAmount;
    }

    public void setBundleAmount(int bundleAmount) {
        this.bundleAmount = bundleAmount;
    }

    public long getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(long lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }
}
