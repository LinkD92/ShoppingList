package com.symbol.shoppinglistv2.Other;

import android.util.Log;

import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BundleDetailsFiller {
    private final String TAG = "com.symbol.shoppinglistv2.Other.BundleDetailsFiller";
    public BundleDetailsFiller() {
        test2();
    }

    private void test(){
        String productPath = "lists/" + FireBaseUtil.currentList + "/products/";
        FireBaseUtil.readProducts(productPath, new MyCallback() {
            @Override
            public void onProductCallback(ArrayList<Product> productArrayList) {
                super.onProductCallback(productArrayList);
                HashMap<String, Product> hashProd = new HashMap<>();
                for (Product prod :
                        productArrayList) {
                    hashProd.put(prod.getName(), prod);
                }
                String path = "bundles/";
                FireBaseUtil.getBundles(path, new MyCallback() {
                    @Override
                    public void getBundles(ArrayList<MyBundle> myBundleArrayList) {
                        for (MyBundle bund :
                                myBundleArrayList) {
                            Log.d(TAG, "MyTest: " + bund.getAmount());
                            double totalPrice =0;
                            for (Map.Entry<String, Product> pro:
                                 bund.getProducts().entrySet()) {
                                Product currentProd = pro.getValue();
                                String key = pro.getKey();
                                double testPrice = hashProd.get(key).getPrice() * currentProd.getAmount();
                                totalPrice = totalPrice + testPrice;
                                bund.setPrice(totalPrice);
                            }
                            String bundlePath = "lists/" + FireBaseUtil.currentList + "/bundles/";
                            FireBaseUtil.addBundle(bundlePath, bund);


                        }
                    }
                });




            }
        });

    }

    private void test2(){
        FireBaseUtil.productHashMap = new HashMap<>();
        for (Product pr :
                FireBaseUtil.currentListProducts) {
            FireBaseUtil.productHashMap.put(pr.getName(), pr);
        }
        String bundlePath = "lists/" + FireBaseUtil.currentList + "/bundles/";
        FireBaseUtil.getBundles(bundlePath, new MyCallback() {
            @Override
            public void getBundles(ArrayList<MyBundle> myBundleArrayList) {
                for (MyBundle bund : myBundleArrayList) {
                    double totalPrice =0;
                    bund.setError(-740056);
                    for (Map.Entry<String, Product> pro:
                            bund.getProducts().entrySet()) {
                        Product currentProd = pro.getValue();
                        String key = pro.getKey();

                        if(FireBaseUtil.productHashMap.containsKey(currentProd.getName())){
                            double testPrice =  FireBaseUtil.productHashMap.get(key).getPrice() * currentProd.getAmount();
                            totalPrice = totalPrice + testPrice;
                            bund.setPrice(totalPrice * bund.getAmount());

                        }else{
                            bund.setError(-504764);
                        }
                    }
                    String bundlePath = "lists/" + FireBaseUtil.currentList + "/bundles/";
                    FireBaseUtil.addBundle(bundlePath, bund);

                }

            }
        });
    }
}
