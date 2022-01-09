package com.symbol.shoppinglistv2.Command;

import android.util.Log;

import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.MyDetail;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Other.DetailsAdapter;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceArray;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommandListDetails implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandListDetails";
    private RecyclerView rvListDetails;
    private HashMap<String, MyDetail> details;
    private DetailsAdapter detailsAdapter;

    public CommandListDetails(RecyclerView rvListDetails) {
        this.rvListDetails = rvListDetails;
    }

    @Override
    public boolean execute() {
        String buildPath = "/lists/" + FireBaseUtil.currentList  +"/products";
            FireBaseUtil.readProducts(buildPath, new MyCallback() {
                @Override
                public void onProductCallback(ArrayList<Product> productArrayList) {
                    details = new HashMap<>();
                    super.onProductCallback(productArrayList);
                    for (Product prod :
                            productArrayList) {
                        String catName =  prod.getCategory().getName();
                        double prodPrice = prod.getPrice();
                        MyDetail myDetail;
                        if(details.containsKey(catName)){
                            myDetail = details.get(catName);
                            myDetail.setAmount(myDetail.getAmount() + 1);
                            myDetail.setPrice(myDetail.getPrice() + prodPrice);
                            details.put(catName, myDetail);
                        }else {
                            myDetail = new MyDetail(catName, 1, prodPrice);
                            details.put(catName, myDetail);
                        }
                    }
                    detailsAdapter = new DetailsAdapter(details);
                    rvListDetails.setLayoutManager(new LinearLayoutManager(MainActivity.appContext));
                    rvListDetails.setAdapter(detailsAdapter);

                }
            });
        return false;
    }


}
