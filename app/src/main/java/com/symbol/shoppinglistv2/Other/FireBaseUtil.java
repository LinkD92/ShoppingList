package com.symbol.shoppinglistv2.Other;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.Category;
import com.symbol.shoppinglistv2.Components.ListHashMap;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.Components.Product;
import com.symbol.shoppinglistv2.Components.SharedList;
import com.symbol.shoppinglistv2.Components.SharedMember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

//Class created to manage Firebase access
public class FireBaseUtil {

    //Values for weired errors
    public static int spinnerPositionERROR; //spinner position resets to 0 every time product is added - this variable remembers last position of spinner


    public static final String TAG = "FirebaseUtil";
    public static DatabaseReference reference;
    //This will be managed in app according to the current location
    public static String databaseLocation;
    //Current list selected
    public static String currentList;
    //Current Bundle selection
    public static String currentBundle;
    //Current fragment opened;
    public static int currentSelection;
    //Sort Method
    public static String sortMethod;
    //path in Firebase - UID
    public static String userPath;
    public static FirebaseUser user;
    //Address of my database
    static final String source = "https://shopping-c267a-default-rtdb.europe-west1.firebasedatabase.app/";


    private static Product product;
    private static MyBundle bundle;
    private static ListOfProducts listOfProducts;

    public static ArrayList<Product> currentListProducts;
    public static HashMap<String, Product> productHashMap;

    private FireBaseUtil(){

    }

    //begins interaction with Firebase
    public static void connect(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        //"products" is a temp path
        if(user != null){
            userPath = user.getUid();
            reference = FirebaseDatabase.getInstance(source).getReference().child("users").child(userPath);
            reference.child("email").setValue(user.getEmail());
        }else{
            reference = FirebaseDatabase.getInstance(source).getReference().child("users");
        }
    }

    //method to read data from firebase supported by Abstractclass MyCallback - see more class: MyCallback.readData
    public static Product getProduct(String path, final MyCallback myCallback){
        DatabaseReference currentRef = FireBaseUtil.reference.child("lists/" + currentList).child("/products/" + path);
        currentRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Object value = task.getResult().getValue();
                    if (value == null) {
                        Log.d(TAG, "ToBeImplemented: readExistingProduct" );
                    } else {
                        product = task.getResult().getValue(Product.class);
                        myCallback.getProduct(product);
                    }
                } else {
                    Toast.makeText(MainActivity.appContext, "Sth went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        return product;
    }

    public static void addProduct(Product product){
        String buildPath = "lists/" + currentList + "/products";
        reference.child(buildPath).child(product.getName()).setValue(product);
    }

    public static void addBundleProduct(String path, Product product){
        String buildPath = "bundles/" + path  + "/products";
        reference.child(buildPath).child(product.getName()).setValue(product);
    }

    public static void removeBundleProduct(String path, Product product){
        String buildPath = "bundles/" + path  + "/products";
        reference.child(buildPath).child(product.getName()).removeValue();
    }

    public static void addAddArrayProducts(String path, HashMap<String, Product> productHashMap){
        reference.child(path).setValue(productHashMap);
    }

    public static void removeProduct(String path, Product product){
        reference.child(path).child(product.getName()).removeValue();
    }

    public static void addList(String path, ListOfProducts list){
        reference.child(path).setValue(list);
    }

    public static void removeList(String listName){
        reference.child("lists").child(listName).removeValue();
    }

    public static void removeValue(String path){
        reference.child(path).removeValue();
    }

    public static void addSharedMemeber(String path, SharedMember sharedMember){
        reference.child(path).child(sharedMember.getUid()).setValue(sharedMember);
    }

    public static void addCategory(Category category){
        reference.child("categories").child(category.getName()).setValue(category);
    }

    public static void removeCategory(Category category){
        FireBaseUtil.reference.child("categories/" + category.getName()).removeValue();
    }

    public static void sendShare(ListOfProducts listOfProducts){
        HashMap<String, SharedMember> hashShared = listOfProducts.getSharedWith();
            for (Map.Entry<String, SharedMember> entry:
                    hashShared.entrySet()) {
                SharedMember sharedMember = entry.getValue();
                if(listOfProducts.isShared()){
                    String email = FireBaseUtil.user.getEmail();
                    String uid = FireBaseUtil.user.getUid();
                    String name = listOfProducts.getName();
                    SharedList sharedList = new SharedList(email, uid, name);
                    FirebaseDatabase.getInstance(source).getReference().child("users")
                        .child(sharedMember.getUid()).child("sharedLists")
                        .child(FireBaseUtil.userPath).child(listOfProducts.getName())
                        .setValue(sharedList);
                }else {
                    FirebaseDatabase.getInstance(source).getReference().child("users")
                        .child(sharedMember.getUid()).child("sharedLists")
                        .child(FireBaseUtil.userPath).child(listOfProducts.getName())
                        .removeValue();
                }
        }
    }
    
    public static void removeShare(ListOfProducts listOfProducts){
        HashMap<String, SharedMember> hashShared = listOfProducts.getSharedWith();
        for (Map.Entry<String, SharedMember> entry:
                hashShared.entrySet()) {
            SharedMember sharedMember = entry.getValue();
            FirebaseDatabase.getInstance(source).getReference().child("users")
                    .child(sharedMember.getUid()).child("sharedLists")
                    .child(FireBaseUtil.userPath).child(listOfProducts.getName())
                    .removeValue();
            }
    }

    public static void addBundle(String path, MyBundle bundle){
        FireBaseUtil.reference.child(path + bundle.getName()).setValue(bundle);
    }

    public static void removeBundle(String path, MyBundle bundle){
        FireBaseUtil.reference.child(path + bundle.getName()).removeValue();
    }

    public static MyBundle findBundle(String path, final MyCallback myCallback){
        DatabaseReference currentRef = FireBaseUtil.reference.child(path);
        Log.d(TAG, "findBundle: " + currentRef);
        currentRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Object value = task.getResult().getValue();
                    if (value == null) {
                        Log.d(TAG, "ToBeImplemented: readExistingProduct" );
                    } else {
                        bundle = task.getResult().getValue(MyBundle.class);
                        myCallback.findBundle(bundle);
                        Log.d(TAG, "onComplete: " + bundle.getName());
                    }
                } else {
                    Toast.makeText(MainActivity.appContext, "Sth went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        return bundle;
    }

    public static void getBundles(String path, final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(reference != null) {
                    ArrayList<MyBundle> bundles = new ArrayList<>();
                    for (DataSnapshot ds :
                            snapshot.getChildren()) {
                        MyBundle myBundle = ds.getValue(MyBundle.class);
                        HashMap<String, Product> productHashMap = new HashMap<>();
                        ArrayList<Product> productArrayList = new ArrayList<>();
                        for (DataSnapshot ds2 :
                                ds.child("products").getChildren()) {
                                Product product = ds2.getValue(Product.class);
                                //productArrayList.add(product);
                                productHashMap.put(product.getName(), product);
                        }
                        //myBundle.setBundleProducts(productArrayList);
                        myBundle.setProducts(productHashMap);
                        bundles.add(myBundle);
                    }
                    myCallback.getBundles(bundles);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        Log.d(TAG, "BundleFB: " + reference.child(path).toString());
        reference.child(path).addValueEventListener(listener);
    }


    //method to get list parameters
    public static ListOfProducts getList(String listName, final MyCallback myCallback){
        DatabaseReference currentRef = FireBaseUtil.reference.child("lists/" + listName);
        currentRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Object value = task.getResult().getValue();
                    if (value == null) {
                        Log.d(TAG, "ToBeImplemented: readExistingProduct" );
                    } else {
                        //list have got "Product" list in child path, so list parameters and Product parameters needs to be separated
                        //assigment of parameters of List
                        String name = task.getResult().child("name").getValue(String.class);
                        boolean shared = task.getResult().child("shared").getValue(boolean.class);
                        String sortType = task.getResult().child("sortType").getValue(String.class);

                        //HashMap declaration for products
                        HashMap<String, Product> products = new HashMap<>();
                        //getting all products for current list path
                        for (DataSnapshot ds :
                                task.getResult().child("products").getChildren()) {
                            Product product = ds.getValue(Product.class);
                            //assigned extracted product to the HashMap
                            products.put(product.getName(), product);

                        }
                        //new object of List created + values setters
                        listOfProducts = new ListOfProducts(name);
                        listOfProducts.setShared(shared);
                        //listOfProducts.setSharedWith(sharedWith);
                        listOfProducts.setProducts(products);
                        listOfProducts.setSortType(sortType);
                        myCallback.getList(listOfProducts);
                        }

                } else {
                    Toast.makeText(MainActivity.appContext, "Sth went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        return listOfProducts;
    }

    //Method to read products from extracted path
    public static void readProducts(String path, final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(reference != null) {
                    ArrayList<Product> productList = new ArrayList<>();
                    for (DataSnapshot ds :
                            snapshot.getChildren()) {
                        Product product = ds.getValue(Product.class);
                        productList.add(product);
                    }
                    myCallback.onProductCallback(productList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        Log.d(TAG, "onProductCallback: " + reference.child(path).toString());
        reference.child(path).addValueEventListener(listener);
    }



    //Method to read list names only - for spinner
    public static void readList(final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(reference != null) {
                    ArrayList<String> lists = new ArrayList<>();
                    for (DataSnapshot ds :
                            snapshot.getChildren()) {
                        String list = ds.child("name").getValue(String.class);
                        lists.add(list);
                    }
                    myCallback.onListCallback(lists);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error);
            }
        };
        reference.child("lists").addValueEventListener(listener);
    }

    public static void readSharedList(final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(reference != null) {
                    ArrayList<SharedList> lists = new ArrayList<>();
                    for (DataSnapshot ds :
                            snapshot.getChildren()) {
                        for (DataSnapshot snap :
                                ds.getChildren()) {
                            SharedList sharedList = snap.getValue(SharedList.class);
                            lists.add(sharedList);
                        }
                    }
                    myCallback.readSharedLists(lists);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.child("sharedLists").addValueEventListener(listener);
    }

    public static void readFullList(String listName, final MyCallback myCallback){
       // if(!listName.contains("("))
        DatabaseReference currentRef = FireBaseUtil.reference.child(listName);
        Log.d(TAG, "PrintPath: " + currentRef);
        currentRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ListOfProducts listOfProducts = task.getResult().getValue(ListOfProducts.class);
                myCallback.readFullList(listOfProducts);
            }
        });
    }

    public static void readAllLists(String path, final MyCallback myCallback){
        DatabaseReference ref = FireBaseUtil.reference.child(path);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<String> test = new ArrayList<>();
                for (DataSnapshot ds:
                     task.getResult().getChildren()) {
                    test.add(ds.getKey());
                }
                myCallback.readAllList(test);
            }
        });
    }

    //Method to read categories
    public static void readCategory(final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(reference != null) {
                    ArrayList<Category> categories = new ArrayList<>();
                    for (DataSnapshot ds :
                            snapshot.getChildren()) {
                        Category category = ds.getValue(Category.class);
                        categories.add(category);
                    }
                    myCallback.onCategoryCallback(categories);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.child("categories").addValueEventListener(listener);
    }

    public static void readCategories(final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(reference != null) {
                    HashMap<String, Category> categories = new HashMap<>();
                    for (DataSnapshot ds :
                            snapshot.getChildren()) {
                        Category category = ds.getValue(Category.class);
                        categories.put(category.getName(), category);
                    }
                    myCallback.onCategoryCallbackHM(categories);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error);
            }
        };
        reference.child("categories").addValueEventListener(listener);
    }


    public static void connectedToPath(DatabaseReference databaseReference, final MyCallback myCallback){
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            DatabaseReference returnRef;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    myCallback.onReferenceConnectedCallback(databaseReference);
                }else{
                    Toast.makeText(MainActivity.appContext, "Sth went wrong", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    //Method to check if path is already taken - This will prevent users from doubling the data. Supported by MyCallback.ifPathExists
    public static void ifPathExists(String path, final MyCallback myCallback){
        reference.child(path).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            boolean pathExists;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Object value = task.getResult().getValue();
                    if (value == null) {
                        pathExists = false;
                        myCallback.onProductExistsCallback(pathExists);
                    } else {
                        pathExists = true;
                        myCallback.onProductExistsCallback(pathExists);
                    }
                } else {
                    Toast.makeText(MainActivity.appContext, "Sth went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void readUsers(final MyCallback myCallback){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String > hashMapUsers = new HashMap<>();
                for (DataSnapshot ds :
                        snapshot.getChildren()) {
                    String uid = ds.getKey();
                    String email = ds.child("email").getValue(String.class);
                    hashMapUsers.put(uid,email);
                    myCallback.readUsers(hashMapUsers);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        FirebaseDatabase.getInstance(source).getReference().child("users").addValueEventListener(listener);
    }

}
