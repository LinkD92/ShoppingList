package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBundleOnList extends RecyclerView.Adapter<AdapterBundleOnList.ViewHolder> {
    private final String TAG = "com.symbol.shoppinglistv2.Other.AdapterBundleOnList";
    private double totalPrice =0;
    private int incr = 0;

    private ArrayList<MyBundle> myBundleArrayList;

    public AdapterBundleOnList(ArrayList<MyBundle> myBundleArrayList) {
        this.myBundleArrayList = myBundleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bundle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyBundle bundle = myBundleArrayList.get(position);
        holder.tvBundleItemName.setText(bundle.getName());
        Log.d(TAG, "onBindViewHolder: " + bundle.getBundleProducts());
        String converDouble = String.valueOf(bundle.getPrice());
        holder.tvBundlePrice.setText(converDouble);
        String converInt = String.valueOf(bundle.getAmount());
        holder.tvCurrentBundleAmount.setText(converInt);
        holder.tvBundleItemName.setTextColor(bundle.getError());
        holder.cbCheckedBundle.setChecked(bundle.isChecked());
        checkBoxListener(holder.cbCheckedBundle, bundle);


    }

    private void checkBoxListener(CheckBox cbCheckedBundle, MyBundle bundle ){
        cbCheckedBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = "lists/" + FirebaseUtil.currentList + "/bundles/";
               // MyBundle bundle = myBundleArrayList.get(getAdapterPosition());
                if(cbCheckedBundle.isChecked()){
                    bundle.setChecked((cbCheckedBundle.isChecked()));
                    for (Map.Entry<String, Product> productEntry :
                            bundle.getProducts().entrySet()) {
                        Product product = productEntry.getValue();
                        String prodPath = "lists/" + FirebaseUtil.currentList + "/products/" + productEntry.getKey() + "/bundleAmount";
                        int currentAmount = FirebaseUtil.productHashMap.get(product.getName()).getBundleAmount() + product.getAmount();
                        FirebaseUtil.reference.child(prodPath).setValue(currentAmount);
                        Log.d(TAG, "onClick: " + FirebaseUtil.productHashMap.size());
                    }
                    FirebaseUtil.addBundle(path, bundle);
                }else{
                    bundle.setChecked(cbCheckedBundle.isChecked());
                    for (Map.Entry<String, Product> productEntry :
                            bundle.getProducts().entrySet()) {
                        Product product = productEntry.getValue();
                        String prodPath = "lists/" + FirebaseUtil.currentList + "/products/" + productEntry.getKey() + "/bundleAmount";
                        int currentAmount = FirebaseUtil.productHashMap.get(product.getName()).getBundleAmount() - product.getAmount();
                        FirebaseUtil.reference.child(prodPath).setValue(currentAmount);
                    }
                    FirebaseUtil.addBundle(path, bundle);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return myBundleArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton ibtnIncreaseAmountBundle, ibtnDecreaseAmountBundle;
        private TextView tvBundleItemName, tvBundlePrice, tvCurrentBundleAmount;
        private CheckBox cbCheckedBundle;


        public ViewHolder(@NonNull View v) {
            super(v);
            ibtnDecreaseAmountBundle = v.findViewById(R.id.ibtnReduceAmountBundle);
            ibtnIncreaseAmountBundle = v.findViewById(R.id.ibtnAddAmountBundle);
            tvBundleItemName = v.findViewById(R.id.tvBundleItemName);
            tvBundlePrice = v.findViewById(R.id.tvBundleItemTotalPrice);
            tvCurrentBundleAmount = v.findViewById(R.id.tvCurrentAmountBundle);
            cbCheckedBundle = v.findViewById(R.id.cbCheckedBundle);
            //getBundlePrice();
            onClicklisteners();

        }

        private void onClicklisteners(){
            ibtnIncreaseAmountBundle();
            ibtnDecreaseAmountBundle();
            //checkBoxListener();
        }

        private void ibtnIncreaseAmountBundle(){
            ibtnIncreaseAmountBundle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBundle bundle = myBundleArrayList.get(getAdapterPosition());
                    bundle.setAmount(bundle.getAmount()+1);
                    String path = "lists/" + FirebaseUtil.currentList + "/bundles/";
                    FirebaseUtil.addBundle(path, bundle);
                }
            });

        }
        private void ibtnDecreaseAmountBundle(){
            ibtnDecreaseAmountBundle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBundle bundle = myBundleArrayList.get(getAdapterPosition());
                    if(bundle.getAmount() >0){
                        bundle.setAmount(bundle.getAmount()-1);
                        String path = "lists/" + FirebaseUtil.currentList + "/bundles/";
                        FirebaseUtil.addBundle(path, bundle);
                    }
                }
            });

        }


    }

}
