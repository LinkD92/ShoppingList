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
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterBundleProducts extends RecyclerView.Adapter<AdapterBundleProducts.ViewHolder>{
    private final String TAG = "com.symbol.shoppinglistv2.Other.AdapterBundleProducts";
    private ArrayList<Product> productArrayList;
    MutableLiveData<HashMap<String, Product>> hashProducts;

    public AdapterBundleProducts(ArrayList<Product> productArrayList, MutableLiveData<HashMap<String, Product>> hashProducts) {
        this.productArrayList = productArrayList;
        this.hashProducts = hashProducts;
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
        btnRemoveListener(holder.btnRemoveProduct, position);

    }

    private void ibtnAmountChangeListener(Product product, ImageButton ibtnIncrease, ImageButton ibtnDecrease){
        ibtnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setAmount(product.getAmount() + 1);
                notifyDataSetChanged();
            }
        });

        ibtnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.getAmount() > 1){
                    product.setAmount(product.getAmount() -1);
                    notifyDataSetChanged();
                }
            }
        });

    }

    private void btnRemoveListener(ImageButton btnRemove, int position){
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = productArrayList.get(position);
                productArrayList.remove(position);
                hashProducts.getValue().remove(product.getName());
                notifyDataSetChanged();
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
        private ImageButton btnRemoveProduct;

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
