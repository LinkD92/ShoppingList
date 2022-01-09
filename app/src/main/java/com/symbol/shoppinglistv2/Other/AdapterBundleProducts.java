package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterBundleProducts extends RecyclerView.Adapter<AdapterBundleProducts.ViewHolder>{
    private final String TAG = "com.symbol.shoppinglistv2.Other.AdapterBundleProducts";
    private ArrayList<Product> productArrayList;

    public AdapterBundleProducts(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bundle_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.tvBundleProductName.setText(product.getName());
        String amountConvert = String.valueOf(product.getAmount());
        holder.tvAmount.setText(amountConvert);
        ibtnAmountChangeListener(product, holder.ibtnAmountIncrease, holder.ibtnAmoundDecrease);


    }

    private void ibtnAmountChangeListener(Product product, ImageButton ibtnIncrease, ImageButton ibtnDecrease){
        ibtnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setAmount(product.getAmount() + 1);
                FireBaseUtil.addBundleProduct(FireBaseUtil.currentBundle, product);
            }
        });

        ibtnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getAmount() > 0){
                    product.setAmount(product.getAmount() -1);
                    FireBaseUtil.addBundleProduct(FireBaseUtil.currentBundle, product);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvBundleProductName;
        private ImageButton ibtnAmountIncrease;
        private ImageButton ibtnAmoundDecrease;
        private TextView tvAmount;
        private Button btnRemoveProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBundleProductName = itemView.findViewById(R.id.tvItemProductName);
            ibtnAmoundDecrease = itemView.findViewById(R.id.ibtnReduceAmountProduct);
            ibtnAmountIncrease = itemView.findViewById(R.id.ibtnAddAmountProduct);
            tvAmount = itemView.findViewById(R.id.tvCurrentAmountProduct);
            btnRemoveProduct = itemView.findViewById(R.id.btnBundleRemoveProduct);
        }

    }

}
