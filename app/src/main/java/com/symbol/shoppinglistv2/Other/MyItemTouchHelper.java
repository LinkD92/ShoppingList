package com.symbol.shoppinglistv2.Other;

import android.graphics.Color;
import android.util.Log;

import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
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

    }

    private void customIDSorter(RecyclerView.ViewHolder viewHolder){
        Product product = productArrayList.get(viewHolder.getAdapterPosition());
        Product prodBefore = null;
        Product prodAfter = null;
        Product tempProd = null;
        int position = viewHolder.getAdapterPosition();
        if(position >0 && position<productArrayList.size()-1){
            prodBefore = productArrayList.get(position-1);
            prodAfter = productArrayList.get(position+1);
            product.setCustomID(prodBefore.getCustomID()+1);
            if(prodAfter.getCustomID() <= product.getCustomID()){

                int counter = 0;
                for(int i = position+1; i<productArrayList.size(); i++){
                    counter++;
                    tempProd = productArrayList.get(i);

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
            if(prod.getGroup().length() >0){
                Log.d(TAG, "customIDSorter: trbls " + prod.getGroup());
                MyBundle bundle = FirebaseUtil.mutableList.getValue().getBundles().get(prod.getGroup());
                bundle.getProducts().get(prod.getName()).setCustomID(prod.getCustomID());
                FirebaseUtil.addBundle(FirebaseUtil.mutableList.getValue(), bundle);
                //FirebaseUtil.updateBundle(prod);
            }
            if(prod.getCustomID() > 99999){
                temps = arrayCleanUp(productArrayList);
                break;
            }
        }
        FirebaseUtil.mutableList.getValue().setProducts(temps);
        FirebaseUtil.addList(FirebaseUtil.mutableList.getValue());


    }

    private HashMap<String, Product> arrayCleanUp(ArrayList<Product> products){
        HashMap<String, Product> fixedHash = new HashMap<>();
        int counter = 1;
        for (Product product:
             products) {
            product.setCustomID(counter);
            fixedHash.put(product.getName()+product.getGroup(), product);
            counter++;
        }
        return fixedHash;
    };

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
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
