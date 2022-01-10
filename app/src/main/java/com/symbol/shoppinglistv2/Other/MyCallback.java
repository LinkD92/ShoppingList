package com.symbol.shoppinglistv2.Other;

import com.google.firebase.database.DatabaseReference;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.ListHashMap;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Components.SharedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Abstract callback class for syncing data from firebase
public abstract class MyCallback {
    public void  onProductCallback(ArrayList<Product> productArrayList){};
    public void  onListCallback(ArrayList<String> listsArrayList){};
    public void onCategoryCallback(ArrayList<Category> categoryArrayList){};
    public void onCategoryCallbackHM(HashMap<String, Category> categoryHashMap){};
    public boolean onProductExistsCallback(boolean existance){
        return existance;
    };
    public String onUserUIDCallback(String userUID){
        return userUID;
    }
    public void onReferenceConnectedCallback(DatabaseReference databaseReference) {}
    public Product getProduct(Product product){return product;}
    public void getList(ListOfProducts listOfProducts){}
    public void getBundles(ArrayList<MyBundle> myBundleArrayList){};
    public MyBundle findBundle(MyBundle bundle){return bundle;}
    public void readSharedLists(ArrayList<SharedList> sharedLists){}
    public void readListsInHash(HashMap<String, ListOfProducts> listHashMap) {}
    public void readFullList (ListOfProducts listOfProducts){}
    public void readAllList(ArrayList<String> listNames){}
    public void readUsers(HashMap<String,String> userEmails){}

}
