package com.symbol.shoppinglistv2.Other;

import android.graphics.Color;
import android.util.Log;

import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemTouchHelper extends ItemTouchHelper.Callback{

    private final String TAG = this.getClass().getSimpleName();
    private final ItemTouchHelperAdapter itemTouchHelperAdapter;
    private ArrayList<Product> productArrayList;

    public MyItemTouchHelper(ItemTouchHelperAdapter itemTouchHelperAdapter, ArrayList<Product> productArrayList) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        this.productArrayList = productArrayList;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }



    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        int position = viewHolder.getAdapterPosition();
        Product product = productArrayList.get(position);
        viewHolder.itemView.setBackgroundColor(
                product.getCategory().getColor());
        customIDSorter(viewHolder);

//        Product prodBefore = null;
//        Product prodAfter = null;
//        HashMap<String, Product> productHashMap = FirebaseUtil.mutableList.getValue().getProducts();
//        if(position >0 && position<productArrayList.size() ) {
//            Log.d(TAG, "trbls: Picking product: " + product.getName() + "from pos: " + position);
//            Log.d(TAG, "trbls array size: " + productArrayList.size());
//            prodBefore = productArrayList.get(position - 1);
//            int defaultID = 500;
//            product.setCustomID(prodBefore.getCustomID() + 1);
//            int currentID = prodBefore.getCustomID()-1;
//            productHashMap.get(product.getName()).setCustomID(currentID);
//
//            for (Map.Entry<String, Product> prod :
//                    productHashMap.entrySet()) {
//                int cuID = prod.getValue().getCustomID();
//                if(cuID < currentID){
//                    prod.getValue().setCustomID(prod.getValue().getCustomID() -1);
//                }if(cuID >= currentID){
//                    prod.getValue().setCustomID(prod.getValue().getCustomID() +1);
//                }
//
//            }
//        }else if(position == 0){
//            productHashMap.get(product.getName()).setCustomID(prodAfter.getCustomID()-1);
//        }
//        FirebaseUtil.addList(FirebaseUtil.mutableList.getValue());

//            for (int i = position - 1; i > 0; i--) {
//                Product tempProd = productArrayList.get(i);
//                if (!tempProd.isChecked()) {
//                    tempProd.setCustomID(--defaultID);
//                }
//            }
//            defaultID = 500;
//            for (int i = position + 1; i < productArrayList.size(); i++) {
//                Product tempProd = productArrayList.get(i);
//                if (!tempProd.isChecked()) {
//                    tempProd.setCustomID(++defaultID);
//                }


//            if(prodBefore.getCustomID() <9999){
//                product.setCustomID(prodBefore.getCustomID()+1);
//            }else{
//                product.setCustomID(prodBefore.getCustomID());
//            }
//        }else{
//            product.setCustomID(0);
//        }

//        HashMap<String, Product> temps = new HashMap<>();
//        for (Product prod :
//                productArrayList) {
//            temps.put(prod.getName() + prod.getGroup(), prod);
//        }
//        FirebaseUtil.mutableList.getValue().setProducts(temps);
//        FirebaseUtil.addList(FirebaseUtil.mutableList.getValue());
    }

    private void customIDSorter(RecyclerView.ViewHolder viewHolder){
        Product product = productArrayList.get(viewHolder.getAdapterPosition());
        Product prodBefore = null;
        Product prodAfter = null;
        Product tempProd = null;
        int position = viewHolder.getAdapterPosition();
        Log.d(TAG, "trbls POSITION: " + position);
        if(position >0 && position<productArrayList.size()-1){
            prodBefore = productArrayList.get(position-1);
            prodAfter = productArrayList.get(position+1);
            product.setCustomID(prodBefore.getCustomID()+1);
            Log.d(TAG, "trbls Prod : " + product.getCustomID());
            Log.d(TAG, "trbls Prod after: " + prodAfter.getCustomID());
            if(prodAfter.getCustomID() <= product.getCustomID()){
                Log.d(TAG, "trbls CONDITION:  TRUE " );
                int counter = 0;
                for(int i = position+1; i<productArrayList.size(); i++){
                    counter++;
                    tempProd = productArrayList.get(i);
                    int test = product.getCustomID()+counter;
                    Log.d(TAG, "trbls: " + test);
                    Log.d(TAG, "trbls: " + tempProd.getName());
                    if(!tempProd.isChecked()){
                        tempProd.setCustomID(product.getCustomID()+counter);
                    }else{
                        break;
                    }
                }
            }

        }else if(position == 0){
            prodAfter = productArrayList.get(position+1);
            product.setCustomID(prodAfter.getCustomID()-1);
        }else if(position == productArrayList.size()-1){
            prodBefore = productArrayList.get(position-1);
            product.setCustomID(prodBefore.getCustomID()+1);
        }



        HashMap<String, Product> temps = new HashMap<>();
        for (Product prod :
                productArrayList) {
            temps.put(prod.getName() + prod.getGroup(), prod);
        }
        FirebaseUtil.mutableList.getValue().setProducts(temps);
        FirebaseUtil.addList(FirebaseUtil.mutableList.getValue());

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //&& FirebaseUtil.mutableList.getValue().getSortType().equals("customID")
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG ){
            viewHolder.itemView.getContext().getResources();
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        itemTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    }

}
