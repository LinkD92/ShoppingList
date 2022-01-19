package com.symbol.shoppinglistv2.Other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Activities.FragmentCreateCategory;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder>{

    private final String TAG = "com.symbol.shoppinglistv2.Other.CategoryAdapter";
    private ArrayList<Category> categoryArrayList;
    private View container;
    private FragmentCreateCategory fragmentCreateCategory;



    public AdapterCategory(ArrayList<Category> categoryList, View container) {
        this.categoryArrayList = categoryList;
        this.container = container;
    }

    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_category_item, viewGroup, false);

        return new AdapterCategory.ViewHolder(view);
    }

    //Method that binds data from Database to the view
    @Override
    public void onBindViewHolder(AdapterCategory.ViewHolder viewHolder, final int position) {
        //proper Values assigned to the Views
        Category category = categoryArrayList.get(position);
        viewHolder.categoryName.setText(category.getName());
        viewHolder.categoryName.setTextColor(category.getColor());
        FragmentMyOpener fragmentMyOpener = new FragmentMyOpener(container);

        //button click action to open edit fragment
        viewHolder.ibtnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens FragmentAddCategory fragment with category parameter to assign values of
                // currently selected category to the Views.
                fragmentCreateCategory = new FragmentCreateCategory(category);
                fragmentMyOpener.close(fragmentCreateCategory);
                fragmentMyOpener.replace(fragmentCreateCategory);
            }
        });
        viewHolder.ibtnRemoveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.removeCategory(category);
            }
        });

    }



    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageButton ibtnEditCategory;
        private ImageButton ibtnRemoveCategory;

        public ViewHolder(View view) {
            super(view);
            categoryName = view.findViewById(R.id.tvCategoryName);
            ibtnEditCategory = view.findViewById(R.id.ibtnEditCategory);
            ibtnRemoveCategory = view.findViewById(R.id.ibtnRemoveCategory);
        }


    }


}
