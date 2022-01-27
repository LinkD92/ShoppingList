package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.symbol.shoppinglistv2.Activities.FragmentAddProduct;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.MyLog;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.R;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

//class to create recycler view for products assigned to ArrayList
public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> implements ItemTouchHelperAdapter {

    private final String TAG = this.getClass().getName();
    public ArrayList<Product> productList;
    private ItemTouchHelper itemTouchHelper;
    private View container;
    private MutableLiveData<ListOfProducts> mutableList;


    public AdapterProduct(){

    };
    public AdapterProduct(ArrayList<Product> productList, View fragmentContainer, MutableLiveData<ListOfProducts> mutableList) {

        this.productList = productList;
        this.container = fragmentContainer;
        this.mutableList = mutableList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_item_product, viewGroup, false);

        return new ViewHolder(view);
    }

    //Method that binds data from Database to the view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Product product = productList.get(position);

        viewHolder.tvItemProductName.setText(product.getName());
        String price = Double.toString(product.getCustomID());
        viewHolder.tvItemProductPrice.setText(price);
        String amount = Integer.toString(product.getAmount());
        viewHolder.tvCurrentAmountProduct.setText(amount);
        viewHolder.cbCheckedProduct.setChecked(product.isChecked());
        viewHolder.clContainerWholeProduct.setBackgroundColor(product.getCategory().getColor());
        viewHolder.clContainerWholeProduct.setAlpha(setAlpha(product.isChecked()));
        viewHolder.tvItemProductGroup.setText(product.getGroup());
        if(product.getGroup().length() > 0){
            viewHolder.ibtnAddAmountProduct.setVisibility(View.GONE);
            viewHolder.ibtnReduceAmountProduct.setVisibility(View.GONE);
            viewHolder.ibtnOptions.setVisibility(View.GONE);
            viewHolder.tvItemProductGroup.setVisibility(View.VISIBLE);
        }
        checkBoxClickListener(viewHolder.cbCheckedProduct, product);
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        Product product = productList.get(fromPos);
        productList.remove(product);
        productList.add(toPos, product);
        notifyItemMoved(fromPos, toPos);

    }


    private void checkBoxClickListener(CheckBox checkBox, Product product){
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListOfProducts list = FirebaseUtil.mutableList.getValue();
                product.setChecked(checkBox.isChecked());
                MyBundle bundle = list.getBundles().get(product.getGroup());
                if(product.isChecked()){
                    Calendar calendar = Calendar.getInstance();
                    Date date = new Date();
                    product.setLastCheckDate(date.getTime());

                    if(product.getGroup().length() >0){

                        bundle.getProducts().get(product.getName()).setChecked(checkBox.isChecked());
                        FirebaseUtil.addBundle(list, bundle);
                        if(allChecked(bundle)){
                            bundle.setChecked(true);
                            FirebaseUtil.addBundle(list, bundle);
                        }

                    }
                    if(product.getAvgExpirationDays() != 0){
                        String path = list.getListPath();
                        String test[] = path.split("/");
                        String logName = list.getName();
                        String logProduct = product.getName();
                        int days = product.getAvgExpirationDays();
                        calendar.add(Calendar.DAY_OF_YEAR, days);
                        String expirationDate = calendar.getTime().toString();
                        MyLog myLog = new MyLog(logName, logProduct, expirationDate);
                        FirebaseUtil.addLog(test[0], myLog);
                    }
                }else{
                    if(product.getGroup().length()>0){
                        Log.d(TAG, "MyTest: uncheck" + product.getGroup() );
                        bundle.getProducts().get(product.getName()).setChecked(checkBox.isChecked());
                        FirebaseUtil.addBundle(list, bundle);
                        if(!allChecked(bundle)){
                            Log.d(TAG, "MyTest: uncheck - all checked"  );
                            bundle.setChecked(false);
                            FirebaseUtil.addBundle(list, bundle);
                        }
                    }

                }
                FirebaseUtil.addProduct(FirebaseUtil.mutableList.getValue(), product);
            }
        });
    }

    private boolean allChecked(MyBundle bundle){
        for (Map.Entry<String, Product> prod :
                bundle.getProducts().entrySet()) {
            if(!prod.getValue().isChecked()){
                Log.d(TAG, "MyTest: prod check" + prod.getValue().isChecked());
                return false;
            }
        }
        return true;
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
        return productList.size();
    }



    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnTouchListener,
            GestureDetector.OnGestureListener

    {
        private TextView tvItemProductName, tvItemProductGroup;
        private TextView tvItemProductPrice;
        private TextView tvCurrentAmountProduct, tvCurrentBundleAmount;
        private CheckBox cbCheckedProduct;
        private ImageButton ibtnAddAmountProduct, ibtnReduceAmountProduct, ibtnOptions;
        private ConstraintLayout clContainerWholeProduct;
        private GestureDetector gestureDetector;
        private FragmentMyOpener fragmentMyOpener;
        private FragmentAddProduct fragmentAddProduct;

        public ViewHolder(View view) {
            super(view);
            ibtnOptions = view.findViewById(R.id.ibtnProductOptions);
            tvItemProductName = view.findViewById(R.id.tvItemProductName);
            tvItemProductPrice = view.findViewById(R.id.tvItemProductPrice);
            tvCurrentAmountProduct = view.findViewById(R.id.tvCurrentAmountProduct);
            cbCheckedProduct = view.findViewById(R.id.cbCheckedProduct);
            clContainerWholeProduct = view.findViewById(R.id.clContainerWholeProduct);
            ibtnAddAmountProduct = view.findViewById(R.id.ibtnAddAmountProduct);
            ibtnReduceAmountProduct = view.findViewById(R.id.ibtnReduceAmountProduct);
            tvItemProductGroup = view.findViewById(R.id.tvItemProductGroup);
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
            int pos = getAdapterPosition();
            Product tempProd = productList.get(pos);
            if(FirebaseUtil.mutableList.getValue().getSortType().equals("customID")
                    && tempProd.isChecked()==false){
                itemTouchHelper.startDrag(this);
            }
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
            ibtnOptionsListener();
        }

        private void btnIncreaseListener(){
            ibtnAddAmountProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    product.setAmount(product.getAmount()+1);
                    notifyDataSetChanged();
                    FirebaseUtil.addProduct(mutableList.getValue(), product);
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
                        notifyDataSetChanged();
                        FirebaseUtil.addProduct(mutableList.getValue(), product);
                    }
                }
            });

        }

        private void ibtnOptionsListener(){
            ibtnOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    PopupMenu popupMenu = new PopupMenu(ActivityMain.appContext, view);
                    popupMenu.getMenuInflater().inflate(R.menu.product_options, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){
                                case R.id.menuOptionsRemove:
                                    FirebaseUtil.removeProduct(FirebaseUtil.mutableList.getValue(), product);
                                    ListOfProducts list = FirebaseUtil.mutableList.getValue();
                                    list.getProducts().remove(product.getName());
                                    FirebaseUtil.mutableList.setValue(list);
                                    break;
                                case R.id.menuOptionsEdit:
                                    fragmentAddProduct = new FragmentAddProduct(product);
                                    fragmentMyOpener = new FragmentMyOpener(container);
                                    fragmentMyOpener.open(fragmentAddProduct);
                                    fragmentMyOpener.close(fragmentAddProduct);
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
