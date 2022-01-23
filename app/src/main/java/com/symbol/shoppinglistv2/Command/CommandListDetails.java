package com.symbol.shoppinglistv2.Command;

import android.util.Log;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyDetail;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.AdapterDetails;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandListDetails implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandListDetails";
    private RecyclerView rvListDetails;
    private HashMap<String, MyDetail> details;
    private AdapterDetails adapterDetails;

    public CommandListDetails(RecyclerView rvListDetails) {
        this.rvListDetails = rvListDetails;
    }

    @Override
    public boolean execute() {
        ListOfProducts list = FirebaseUtil.mutableList.getValue();
        details = new HashMap<>();
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (Map.Entry<String, Product> productEntry:
             list.getProducts().entrySet()) {
            Product product = productEntry.getValue();
            MyDetail myDetail = new MyDetail();
            myDetail.setName(product.getCategory().getName());
            if(details.containsKey(myDetail.getName())){
                MyDetail currentDetail = details.get(myDetail.getName());
                if(!product.isChecked()){
                    int totalAmount = currentDetail.getAmount() + product.getAmount();
                    double price =currentDetail.getPrice() + (product.getPrice() * product.getAmount());
                    currentDetail.setAmount(totalAmount);
                    currentDetail.setPrice(price);
                    details.put(currentDetail.getName(), currentDetail);
                }
            }else{
                if(!product.isChecked()){
                    myDetail.setName(product.getCategory().getName());
                    myDetail.setAmount(product.getAmount());
                    double price = product.getAmount() * product.getPrice();
                    myDetail.setPrice(price);
                    details.put(myDetail.getName(), myDetail);
                }
            }
//            myDetail.setName(product.getCategory().getName());
//            myDetail.setAmount(myDetail.getAmount() + product.getAmount());
//            details.put(myDetail.getName(), myDetail);
        }
        adapterDetails = new AdapterDetails(details);
        rvListDetails.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
        rvListDetails.setAdapter(adapterDetails);

//        String buildPath = "/lists/" + FireBaseUtil.currentList  +"/products";
//            FireBaseUtil.readProducts(buildPath, new MyCallback() {
//                @Override
//                public void onProductCallback(ArrayList<Product> productArrayList) {
//                    details = new HashMap<>();
//                    super.onProductCallback(productArrayList);
//                    for (Product prod :
//                            productArrayList) {
//                        String catName =  prod.getCategory().getName();
//                        int prodAmount = prod.getAmount();
//                        double prodPrice = prod.getPrice();
//                        MyDetail myDetail;
//                        if(details.containsKey(catName)){
//                            myDetail = details.get(catName);
//                            myDetail.setAmount(myDetail.getAmount() + 1);
//                            myDetail.setPrice(myDetail.getPrice() + prodPrice * prodAmount);
//                            details.put(catName, myDetail);
//                        }else {
//                            myDetail = new MyDetail(catName, 1, prodPrice);
//                            details.put(catName, myDetail);
//                        }
//                    }
//                    detailsAdapter = new DetailsAdapter(details);
//                    rvListDetails.setLayoutManager(new LinearLayoutManager(MainActivity.appContext));
//                    rvListDetails.setAdapter(detailsAdapter);
//
//                }
//            });
        return false;
    }


}
