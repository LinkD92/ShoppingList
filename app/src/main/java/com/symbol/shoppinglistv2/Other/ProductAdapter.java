package com.symbol.shoppinglistv2.Other;

import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.FragmentMyLists;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

//class to create recycler view for products assigned to ArrayList
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private final String TAG = "com.symbol.shoppinglistv2.Other.ProductAdapter";
    private ArrayList<Product> productList;
    private ItemTouchHelper itemTouchHelper;
    private View container;


    public ProductAdapter(ArrayList<Product> productList, View fragmentContainer) {
        this.productList = productList;
        this.container = fragmentContainer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_product_item, viewGroup, false);

        return new ViewHolder(view);
    }

    //Method that binds data from Database to the view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Product product = productList.get(position);
        viewHolder.tvItemProductName.setText(product.getName());
        String price = Double.toString(product.getPrice());
        viewHolder.tvItemProductPrice.setText(price);
        String amount = Integer.toString(product.getAmount());
        viewHolder.tvCurrentAmountProduct.setText(amount);
        viewHolder.cbCheckedProduct.setChecked(product.isChecked());
        viewHolder.clContainerWholeProduct.setBackgroundColor(product.getCategory().getColor());
        viewHolder.clContainerWholeProduct.setAlpha(setAlpha(product.isChecked()));
        String bundleAmount = Integer.toString(product.getBundleAmount());
        viewHolder.tvCurrentBundleAmount.setText(bundleAmount);
        checkBoxListener(viewHolder.cbCheckedProduct, product);



    }
    private void checkBoxListener(CheckBox checkBox, Product product){
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    product.setChecked(checkBox.isChecked());
                    FireBaseUtil.addProduct(product);
                }else{
                    product.setChecked(checkBox.isChecked());
                    FireBaseUtil.addProduct(product);
                }

            }
        });
    }

    private float setAlpha(boolean isChecked){
        if(isChecked){
            return 0.35F;
        }else{
            return 1;
        }
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        Product product = productList.get(fromPos);
        productList.remove(product);
        productList.add(toPos, product);
        notifyItemMoved(fromPos, toPos);
    }


    @Override
    public void onItemSwiped(int pos, int direction) {
        //leftSwipe
        if(direction == 16){
            FragmentMyOpener fragmentMyOpener = new FragmentMyOpener(container);
            Product product = productList.get(pos);
            FragmentAddProduct fragmentAddProduct = new FragmentAddProduct(product);
            fragmentMyOpener.open(fragmentAddProduct);
            String buildPath = "lists/" + FireBaseUtil.currentList + "/products";
            FireBaseUtil.removeProduct(buildPath, product);
            FireBaseUtil.addProduct(product);

            //right Swipe
        }else if(direction == 32){
            String buildPath = "lists/" + FireBaseUtil.currentList + "/products";
            FireBaseUtil.removeProduct(buildPath, productList.get(pos));
            productList.remove(pos);
        }
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnTouchListener,
            GestureDetector.OnGestureListener

    {
        private TextView tvItemProductName;
        private TextView tvItemProductPrice;
        private TextView tvCurrentAmountProduct, tvCurrentBundleAmount;
        private CheckBox cbCheckedProduct;
        private ImageButton ibtnAddAmountProduct, ibtnReduceAmountProduct;
        private ConstraintLayout clContainerWholeProduct;
        private GestureDetector gestureDetector;

        public ViewHolder(View view) {
            super(view);
            tvItemProductName = view.findViewById(R.id.tvItemProductName);
            tvItemProductPrice = view.findViewById(R.id.tvItemProductPrice);
            tvCurrentAmountProduct = view.findViewById(R.id.tvCurrentAmountProduct);
            cbCheckedProduct = view.findViewById(R.id.cbCheckedProduct);
            clContainerWholeProduct = view.findViewById(R.id.clContainerWholeProduct);
            ibtnAddAmountProduct = view.findViewById(R.id.ibtnAddAmountProduct);
            ibtnReduceAmountProduct = view.findViewById(R.id.ibtnReduceAmountProduct);
            tvCurrentBundleAmount = view.findViewById(R.id.tvAmountFromBundle);
            this.gestureDetector = new GestureDetector(view.getContext(), this);
            itemView.setOnTouchListener(this);
            onClicklisteners();
        }


        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            itemTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        }

        private void onClicklisteners(){
            btnIncreaseListener();
            ibtnReduceAmountProductListener();

        }

        private void btnIncreaseListener(){
            ibtnAddAmountProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    product.setAmount(product.getAmount()+1);
                    FireBaseUtil.addProduct(product);
                }
            });

        }
        private void ibtnReduceAmountProductListener(){
            ibtnReduceAmountProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    if(product.getAmount() >0){
                        product.setAmount(product.getAmount()-1);
                        FireBaseUtil.addProduct(product);
                    }
                }
            });

        }



    }


}
