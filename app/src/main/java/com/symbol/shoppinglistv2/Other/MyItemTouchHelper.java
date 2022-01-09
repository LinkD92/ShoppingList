package com.symbol.shoppinglistv2.Other;

import android.graphics.Color;
import android.util.Log;

import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
        return true;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(
                productArrayList.get(viewHolder.getAdapterPosition()).getCategory().getColor());
        int iter=0;
        for (Product pr :
                productArrayList) {
            pr.setCustomID(iter);
            iter++;
        }
        HashMap<String, Product> tempHash = new HashMap<>();
        for (Product pr :
                productArrayList) {
            tempHash.put(pr.getName(), pr);
        }

        String buildPath = "lists/" + FireBaseUtil.currentList + "/products";
        FireBaseUtil.addAddArrayProducts(buildPath, tempHash);

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            Log.d(TAG, "onSelectedChanged: " + viewHolder.itemView.getDrawingCacheBackgroundColor());
            viewHolder.itemView.getContext().getResources();
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);

            int iter = 0;
            for (Product pr :
                    productArrayList) {
                pr.setCustomID(iter);
                iter++;
            }

            for (Product pr :
                    productArrayList) {
                Log.d(TAG, "ProductControl custom ID - Reset  : Name " + pr.getName() +  ":: " + pr.getCustomID());
            }
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
        itemTouchHelperAdapter.onItemSwiped(viewHolder.getAdapterPosition(), direction);

    }

}
