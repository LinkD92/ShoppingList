package com.symbol.shoppinglistv2.Other;

import android.graphics.Color;
import android.util.Log;

import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemTouchHelper extends ItemTouchHelper.Callback{

    private final String TAG = "com.symbol.shoppinglistv2.Other.MyItemTouchHelper";
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
        Product product = productArrayList.get(viewHolder.getAdapterPosition());
        viewHolder.itemView.setBackgroundColor(
                product.getCategory().getColor());
        String test  = FirebaseUtil.mutableList.getValue().getSortType();
        Log.d(TAG, "clearView: test " + test );
        HashMap<String, Product> tempHash = new HashMap<>();
        if(FirebaseUtil.mutableList.getValue().getSortType() == "customID"){
                int iter=0;
                for (Product pr :
                        productArrayList) {
                    pr.setCustomID(iter);
                    Log.d(TAG, "clearView: " + pr.getName() + " " + pr.getCustomID());
                    iter++;
                }

                for (Product pr :
                        productArrayList) {
                    tempHash.put(pr.getName(), pr);
                }
        }else{
            tempHash = FirebaseUtil.mutableList.getValue().getProducts();
        }
        //String buildPath = "lists/" + FireBaseUtil.currentList + "/products";
        ListOfProducts tempList = FirebaseUtil.mutableList.getValue();
        tempList.setProducts(tempHash);
        FirebaseUtil.mutableList.setValue(tempList);
        FirebaseUtil.addAddArrayProducts(FirebaseUtil.mutableList.getValue(), tempHash);



    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.d(TAG, "onSelectedChanged: " + actionState);


        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
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
