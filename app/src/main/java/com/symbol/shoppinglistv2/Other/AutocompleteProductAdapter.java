package com.symbol.shoppinglistv2.Other;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * CURRENTLY NOT USED CLASS
 */
public class AutocompleteProductAdapter extends ArrayAdapter<Product> {

    private final String TAG = "AutocompleteProductAdapter";
    private ArrayList<Product> allProducts = new ArrayList<>();


    public AutocompleteProductAdapter(Context context, int resourceView, ArrayList<Product> allProducts){
        super(context, resourceView, allProducts);

    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        TextView test = convertView.findViewById(R.id.tvItemProductName);

        test.setText(product.getName());
        return convertView;
    }
}
