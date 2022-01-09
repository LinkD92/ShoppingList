package com.symbol.shoppinglistv2.Other;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//class for sectioned RecyclerView - not required yet
public class ProductAdapterSectioned extends SectionedRecyclerViewAdapter<ProductAdapterSectioned.ViewHolder> {

    Activity activity;
    ArrayList<String> sectionList;
    HashMap<String, ArrayList<String>> itemList = new HashMap<>();
    int selectedSection = -1;
    int selectedItem = -1;

    public ProductAdapterSectioned(Activity activity, ArrayList<String> sectionList,
                                   HashMap<String, ArrayList<String >> itemList){
        this.activity = activity;
        this.sectionList = sectionList;
        this.itemList = itemList;
    }


    @Override
    public int getSectionCount() {
        return sectionList.size();
    }

    @Override
    public int getItemCount(int section) {
        return itemList.get(sectionList.get(section)).size();
    }

    @Override
    public void onBindHeaderViewHolder(ProductAdapterSectioned.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(sectionList.get(i));
    }

    @Override
    public void onBindViewHolder(ProductAdapterSectioned.ViewHolder viewHolder, int i, int i1, int i2) {
        String sItem = itemList.get(sectionList.get(i)).get(i1);
        viewHolder.textView.setText(sItem);

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, sItem, Toast.LENGTH_LONG).show();
                selectedSection = i;
                selectedItem = i1;
                notifyDataSetChanged();
            }
        });

        if(selectedSection == i && selectedItem == i1){
            Log.d(MainActivity.TAG, "onBindViewHolder:  + tu sie zmienia tlo");
        }
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        if(section == 1){
            return 0;
        }
        return super.getItemViewType(section, relativePosition, absolutePosition);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layout = 0;

        if(viewType == VIEW_TYPE_HEADER){
           layout = R.layout.adapter_product_item;
        }else{
            
            
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        
        
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvItemProductName);
        }
    }
}
