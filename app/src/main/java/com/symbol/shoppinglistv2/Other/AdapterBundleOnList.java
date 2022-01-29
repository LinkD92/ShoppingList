package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        String converDouble = String.valueOf(bundle.getPrice()*bundle.getAmount());
        holder.tvBundlePrice.setText("$ "+ converDouble);
        String converInt = String.valueOf(bundle.getAmount());
        holder.tvCurrentBundleAmount.setText(converInt);
        holder.tvBundleItemName.setTextColor(bundle.getError());
        holder.cbCheckedBundle.setChecked(bundle.isChecked());
        holder.clContainerWholeBundle.setAlpha(setAlpha(bundle.isChecked()));
        holder.tvBundleDate.setText("Added: "+ bundle.getUpdateDate());

        //checkBoxListener(holder.cbCheckedBundle, bundle);
        cbListener(holder.cbCheckedBundle, bundle);

    }
    private void cbListener(CheckBox checkBox, MyBundle myBundle){
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBundle.setChecked(checkBox.isChecked());
                if(myBundle.isChecked()){
                    cbChecked(myBundle);
                }else{
                    cbUnchecked(myBundle);
                }
            }
        });

    }

    private void cbChecked(MyBundle myBundle){
        for (Map.Entry<String, Product> prod:
                myBundle.getProducts().entrySet()) {
            int prodAmount = myBundle.getAmount() * prod.getValue().getAmount();

            Product product = new Product(prod.getValue());
            product.setAmount(prodAmount);
            product.setChecked(true);
            FirebaseUtil.addBundleProduct(FirebaseUtil.mutableList.getValue(), product);
        }

        for (Map.Entry<String, Product> prod:
                myBundle.getProducts().entrySet()) {
            prod.getValue().setChecked(true);
        }

        FirebaseUtil.addBundle(FirebaseUtil.mutableList.getValue(), myBundle);
    }

    private void cbUnchecked(MyBundle myBundle){

        for (Map.Entry<String, Product> prod:
                myBundle.getProducts().entrySet()) {
            int prodAmount = myBundle.getAmount() * prod.getValue().getAmount();

            Product product = new Product(prod.getValue());
            product.setAmount(prodAmount);
            product.setChecked(false);
            FirebaseUtil.addBundleProduct(FirebaseUtil.mutableList.getValue(), product);
        }

        for (Map.Entry<String, Product> prod:
                myBundle.getProducts().entrySet()) {
            prod.getValue().setChecked(false);
        }

        FirebaseUtil.addBundle(FirebaseUtil.mutableList.getValue(), myBundle);
    }

    private float setAlpha(boolean isChecked){
        if(isChecked){
            return 0.35F;
        }else{
            return 1;
        }
    }


    @Override
    public int getItemCount() {
        return myBundleArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton ibtnIncreaseAmountBundle, ibtnDecreaseAmountBundle, ibtnBundleSetting;
        private TextView tvBundleItemName, tvBundlePrice, tvCurrentBundleAmount, tvBundleDate;
        private CheckBox cbCheckedBundle;
        private ConstraintLayout clContainerWholeBundle;


        public ViewHolder(@NonNull View v) {
            super(v);
            ibtnDecreaseAmountBundle = v.findViewById(R.id.ibtnReduceAmountBundle);
            ibtnIncreaseAmountBundle = v.findViewById(R.id.ibtnAddAmountBundle);
            tvBundleItemName = v.findViewById(R.id.tvBundleItemName);
            tvBundlePrice = v.findViewById(R.id.tvBundleItemTotalPrice);
            tvCurrentBundleAmount = v.findViewById(R.id.tvCurrentAmountBundle);
            cbCheckedBundle = v.findViewById(R.id.cbCheckedBundle);
            clContainerWholeBundle = v.findViewById(R.id.clContainerWholeBundle);
            tvBundleDate = v.findViewById(R.id.tvBundleUpdateDate);
            ibtnBundleSetting = v.findViewById(R.id.ibtnBundleOptions);
            //getBundlePrice();
            onClicklisteners();

        }

        private void onClicklisteners(){
            ibtnIncreaseAmountBundle();
            ibtnDecreaseAmountBundle();
            ibtnOptionsListener();
            //checkBoxListener();
        }

        private void ibtnIncreaseAmountBundle(){
            ibtnIncreaseAmountBundle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBundle bundle = myBundleArrayList.get(getAdapterPosition());
                    bundle.setAmount(bundle.getAmount()+1);
                    FirebaseUtil.addBundle(FirebaseUtil.mutableList.getValue(), bundle);
                    for (Map.Entry<String, Product> prod:
                         bundle.getProducts().entrySet()) {
                        int prodAmount = bundle.getAmount() * prod.getValue().getAmount();
                        Product product = new Product(prod.getValue());
                        product.setAmount(prodAmount);
                        FirebaseUtil.addBundleProduct(FirebaseUtil.mutableList.getValue(), product);
                    }
                }
            });

        }
        private void ibtnDecreaseAmountBundle(){
            ibtnDecreaseAmountBundle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBundle bundle = myBundleArrayList.get(getAdapterPosition());
                    if(bundle.getAmount() >1){
                        bundle.setAmount(bundle.getAmount()-1);
                        FirebaseUtil.addBundle(FirebaseUtil.mutableList.getValue(), bundle);
                        for (Map.Entry<String, Product> prod:
                                bundle.getProducts().entrySet()) {
                            int prodAmount = bundle.getAmount() * prod.getValue().getAmount();
                            Product product = new Product(prod.getValue());
                            product.setAmount(prodAmount);
                            FirebaseUtil.addBundleProduct(FirebaseUtil.mutableList.getValue(), product);
                        }
                    }
                }
            });

        }

        private void ibtnOptionsListener(){
            ibtnBundleSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBundle myBundle = myBundleArrayList.get(getAdapterPosition());
                    PopupMenu popupMenu = new PopupMenu(ActivityMain.appContext, view);
                    popupMenu.getMenuInflater().inflate(R.menu.bundle_options, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){
                                case R.id.menuOptionsRemove:
                                    ListOfProducts list = FirebaseUtil.mutableList.getValue();
                                    FirebaseUtil.removeBundle(list, myBundle);
                                    for (Map.Entry<String, Product> prod:
                                            myBundle.getProducts().entrySet()) {
                                        FirebaseUtil.removeProduct(list, prod.getValue());
                                    }
                                    list.getBundles().remove(myBundle.getName());
                                    break;
                                case R.id.menuOptionsEdit:
                                    String pathFindBundle = "bundles/" + myBundle.getName();
                                    FirebaseUtil.findBundle(pathFindBundle, new MyCallback() {
                                        @Override
                                        public MyBundle findBundle(MyBundle bundle) {
                                            String fullPath = "lists/" + FirebaseUtil.currentList + "/bundles/";
                                            FirebaseUtil.addBundle(fullPath, bundle);
                                            for (Map.Entry<String, Product> prod:
                                                    bundle.getProducts().entrySet()) {
                                                int prodAmount = bundle.getAmount() * prod.getValue().getAmount();
                                                Product product = new Product(prod.getValue());
                                                product.setAmount(prodAmount);
                                                product.setChecked(bundle.isChecked());
                                                FirebaseUtil.addBundleProduct(FirebaseUtil.mutableList.getValue(), product);
                                            }
                                            return super.findBundle(bundle);
                                        }
                                    });

                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }


    }

}
