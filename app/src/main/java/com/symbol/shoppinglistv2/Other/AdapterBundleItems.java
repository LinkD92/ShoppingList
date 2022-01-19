package com.symbol.shoppinglistv2.Other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Activities.FragmentAddBundle;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBundleItems extends RecyclerView.Adapter<AdapterBundleItems.ViewHolder> {

    private ArrayList<MyBundle> bundleArrayList;
    private View container;
    private FragmentMyOpener fragmentMyOpener;
    private FragmentAddBundle fragmentAddBundle;

    public AdapterBundleItems(ArrayList<MyBundle> bundleArrayList, View container) {
        this.bundleArrayList = bundleArrayList;
        this.container = container;
    }

    public AdapterBundleItems(ArrayList<MyBundle> bundleArrayList) {
        this.bundleArrayList = bundleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bundle_editor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyBundle bundle = bundleArrayList.get(position);
        holder.bundleName.setText(bundle.getName());

        holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.currentBundle = bundle.getName();
                fragmentAddBundle = new FragmentAddBundle(bundle);
                fragmentMyOpener = new FragmentMyOpener(container);
                fragmentMyOpener.open(fragmentAddBundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bundleArrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        private TextView bundleName;
        private ImageButton ibtnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bundleName = itemView.findViewById(R.id.tvBundleItemName);
            ibtnEdit = itemView.findViewById(R.id.ibtnEditBundle);
        }
    }
}
