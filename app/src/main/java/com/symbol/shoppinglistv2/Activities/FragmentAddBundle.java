package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandBundleAdd;
import com.symbol.shoppinglistv2.Components.MyBundle;
import com.symbol.shoppinglistv2.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


//To be implemented
public class FragmentAddBundle extends Fragment {

    public EditText etAddBundleName;
    public Button btnAddBundleSave , btnBundleCancel;
    public AutoCompleteTextView actvBundleAddProductName;
    public SearchableSpinner ssBundleAddProductName;
    public ImageButton ibtnAddBundleAddPorduct;
    public RecyclerView rvBundleProducts;
    public MyBundle myBundle;
    public View container;
    public Button btnAddBundle;

    public FragmentAddBundle(){

    }

    public FragmentAddBundle(MyBundle myBundle){
        this.myBundle = myBundle;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_bundle, container, false);
        View v2 = inflater.inflate(R.layout.fragment_manager, container, false);

        etAddBundleName = v.findViewById(R.id.etAddBundleName);
        //actvBundleAddProductName = v.findViewById(R.id.actvBundleAddProductName);
        ssBundleAddProductName = v.findViewById(R.id.ssBundleAddProductName);
        ibtnAddBundleAddPorduct = v.findViewById(R.id.ibtnAddBundleAddProduct);
        rvBundleProducts = v.findViewById(R.id.rvBundleProducts);
        btnAddBundle = v.findViewById(R.id.btnAddBundle);
        btnBundleCancel = v.findViewById(R.id.btnBundleCancel);
        container = v2.findViewById(R.id.clFragmentManageContainerFAB);

        executeCommand(new CommandBundleAdd(this, myBundle, container));


        return v;
    }

    private void executeCommand(Command command){
        command.execute();
    }
}