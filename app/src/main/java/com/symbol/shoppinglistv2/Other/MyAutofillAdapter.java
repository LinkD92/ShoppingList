package com.symbol.shoppinglistv2.Other;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


//For fun or future implementation
public class MyAutofillAdapter extends ArrayAdapter<Product> {

    private final String TAG = "com.symbol.shoppinglistv2.Other.MyAutofillAdapter";
    private ArrayList<Product> productArrayList;
    private Context mContext;
    private View myView;
    private ArrayList<Product> suggestions;
    private ArrayList<Product> tempItems;

    public MyAutofillAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.mContext = context;

        this.productArrayList = objects;
        tempItems = new ArrayList<>(objects);
        suggestions = new ArrayList<Product>();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_autofill, parent, false);
        Product curProd = productArrayList.get(position);

        TextView tvAFN = convertView.findViewById(R.id.tvAutoFillName);
        tvAFN.setText(curProd.getName());

        return convertView;
    }

//    @Override
//    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Log.d(TAG, "cosrobie: ");
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_autofill, parent, false);
//
//
//        Product curProd = productArrayList.get(position);
//
//        TextView tvAFN = convertView.findViewById(R.id.tvAutoFillName);
//        tvAFN.setText("dupa");
//
//        return convertView;
//    }

    @Override
    public Filter getFilter()
    {
        return nameFilter;
    }

    Filter nameFilter = new Filter()
    {
        @Override
        public CharSequence convertResultToString(Object resultValue)
        {
            Product product = (Product) resultValue;
            String str = product.getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            if (constraint != null)
            {
                suggestions.clear();
                for (Product product : tempItems)
                {
                    if (product.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }
            else
            {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Product> filterList = (ArrayList<Product>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Product item : filterList) {
                    add(item);
                    notifyDataSetChanged();
                }
            }
        }
    };

}
