package com.symbol.shoppinglistv2.Other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.MyDetail;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

private HashMap<String , MyDetail> myDetails;
private ArrayList<MyDetail> myDetailArrayList;

    public DetailsAdapter(HashMap<String, MyDetail> myDetails) {
        this.myDetails = myDetails;
        myDetailArrayList = new ArrayList<>();
        for (String name :
                myDetails.keySet()) {
            myDetailArrayList.add(myDetails.get(name));
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_details, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MyDetail myDetail = myDetailArrayList.get(position);

        holder.tvDetailCategoryName.setText(myDetail.getName());
        String amountConvert = String.valueOf(myDetail.getAmount());
        holder.tvDetailCategoryAmount.setText(amountConvert);
        String priceConvert = String.valueOf(myDetail.getPrice());
        holder.tvDetailCategoryPrice.setText(priceConvert);
    }

    @Override
    public int getItemCount() {
        return myDetailArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDetailCategoryName;
        private TextView tvDetailCategoryAmount;
        private TextView tvDetailCategoryPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDetailCategoryName = itemView.findViewById(R.id.tvDetailCategoryName);
            tvDetailCategoryAmount = itemView.findViewById(R.id.tvDetailCategoryAmount);
            tvDetailCategoryPrice = itemView.findViewById(R.id.tvDetailCategoryPrice);

        }
    }
}
