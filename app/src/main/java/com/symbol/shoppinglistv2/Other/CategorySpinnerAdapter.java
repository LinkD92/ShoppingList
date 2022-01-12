package com.symbol.shoppinglistv2.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CategorySpinnerAdapter extends ArrayAdapter{

    private ArrayList<Category> categoryArrayList;
    private Context appContext;
    private int resource;

    public CategorySpinnerAdapter(Context context, int textViewResourceId, ArrayList<Category> objects) {
        super(context, textViewResourceId, objects);
        this.categoryArrayList = objects;
        this.appContext = context;
        this.resource = textViewResourceId;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = MainActivity.inflater;
        View layout = inflater.inflate(R.layout.test_spinner_cat, parent, false);
        TextView tv = layout.findViewById(R.id.tvSpinnerCategoryName);
        ConstraintLayout cl = layout.findViewById(R.id.clSpinnerItem);

        try{
            tv.setText(categoryArrayList.get(position).getName());
            cl.setBackgroundColor(categoryArrayList.get(position).getColor());
        }catch (IndexOutOfBoundsException e){
            tv.setText(categoryArrayList.get(0).getName());
            cl.setBackgroundColor(categoryArrayList.get(0).getColor());
        }



        return layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
