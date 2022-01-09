package com.symbol.shoppinglistv2.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.symbol.shoppinglistv2.Command.Command;
import com.symbol.shoppinglistv2.Command.CommandEditList;
import com.symbol.shoppinglistv2.Command.CommandListExtractData;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.R;

import java.util.List;

import javax.security.auth.callback.Callback;

public class FragmentMyManageLists extends Fragment {
    public final String TAG = "com.symbol.shoppinglistv2.Activities.FragmentMyManageLists";
    public ListOfProducts listOfProducts;
    public EditText etListSharedWith;
    public EditText etListName;
    public RadioGroup rgManageList;
    public Button btnEditListSaveChanges;
    public RadioButton rbPrivateList;
    public RadioButton rbSharedList;
    public RecyclerView rvSharedWithMembers;
    public Button btnAddMember;


    public FragmentMyManageLists() {
        // Required empty public constructor
    }
    public FragmentMyManageLists(ListOfProducts listOfProducts){
        this.listOfProducts = listOfProducts;
        Log.d(TAG, "testList: " + listOfProducts.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_manage_lists, container, false);
        etListName = v.findViewById(R.id.etListName);
        etListSharedWith = v.findViewById(R.id.etListSharedWith);
        btnEditListSaveChanges = v.findViewById(R.id.btnEditListSaveChanges);
        rgManageList = v.findViewById(R.id.rgManageList);
        rbPrivateList = v.findViewById(R.id.rbPrivateList);
        rbSharedList = v.findViewById(R.id.rbSharedList);
        btnAddMember = v.findViewById(R.id.btnAddSharedMember);
        rvSharedWithMembers = v.findViewById(R.id.rvSharedMembersContainer);

        executeCommand(new CommandEditList(this,listOfProducts ));

        return v;
    }

    private void executeCommand(Command command){
        command.execute();
    }
}