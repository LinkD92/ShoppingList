package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.FragmentMyManageLists;
import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.SharedMember;
import com.symbol.shoppinglistv2.Other.AdapterSharedMembers;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

//Command with implementation of all edit List actions
public class CommandEditList implements Command {
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandEditList";

    private ListOfProducts listOfProducts;
    private FragmentMyManageLists fragmentMyManageLists;
    private MutableLiveData<HashMap<String, SharedMember>> sharedMembersListener = new MutableLiveData<>();


    public CommandEditList(){}

    public CommandEditList(FragmentMyManageLists fragmentMyManageLists, ListOfProducts listOfProducts) {
        this.fragmentMyManageLists = fragmentMyManageLists;
        if(listOfProducts == null){
            this.listOfProducts = new ListOfProducts();
        }else
        {
            this.listOfProducts = listOfProducts;
        }
    }

    @Override
    public boolean execute() {
        sharedMembersListener.setValue(listOfProducts.getSharedWith());
        //method to hide/show edit text with email address
        btnSaveChangesListener();
        //method with radio button actions
        btnRadioListener();
        //adapter filler
        rvFiller();
        //Extraction user data to share with
        addSharedMemberListener();
        //fill data if current list edited
        fillListData();
        return false;
    }

    private void btnSaveChangesListener(){
        fragmentMyManageLists.btnEditListSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentName = listOfProducts.getName();
                String newName = fragmentMyManageLists.etListName.getText().toString();
                listOfProducts.setName(fragmentMyManageLists.etListName.getText().toString());
                listOfProducts.setShared(fragmentMyManageLists.rbSharedList.isChecked());
                listOfProducts.setListPath(FireBaseUtil.userPath + "/" +listOfProducts.getName());

                String path = "lists/" + listOfProducts.getName();
                if(listOfProducts.getName().equals(null) || listOfProducts.getName().equals("")){
                    Toast.makeText(MainActivity.appContext, "Nazwa nie moze byc pusta", Toast.LENGTH_LONG).show();
                }else{
                    FireBaseUtil.addList(path, listOfProducts);
                    FireBaseUtil.sendShare(listOfProducts);
                }
                if(currentName != null && !currentName.equals(newName)){
                    String removePath = "lists/" + currentName;
                    listOfProducts.setName(currentName);
                    FireBaseUtil.removeShare(listOfProducts);
                    FireBaseUtil.removeValue(removePath);
                }
            }
        });
    }

    private void btnRadioListener(){
        fragmentMyManageLists.rgManageList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbSharedList:
                        fragmentMyManageLists.etListSharedWith.setFocusable(true);
                        fragmentMyManageLists.etListSharedWith.setFocusableInTouchMode(true);
                        fragmentMyManageLists.btnAddMember.setVisibility(View.VISIBLE);
                        fragmentMyManageLists.etListSharedWith.setVisibility(View.VISIBLE);
                        fragmentMyManageLists.rvSharedWithMembers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbPrivateList:
                        fragmentMyManageLists.etListSharedWith.setFocusable(false);
                        fragmentMyManageLists.etListSharedWith.setVisibility(View.GONE);
                        fragmentMyManageLists.btnAddMember.setVisibility(View.GONE);
                        fragmentMyManageLists.rvSharedWithMembers.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void rvFiller(){
        sharedMembersListener.observeForever(new Observer<HashMap<String, SharedMember>>() {
            @Override
            public void onChanged(HashMap<String, SharedMember> stringSharedMemberHashMap) {
                HashMap<String, SharedMember> sharedMemberHashMap = sharedMembersListener.getValue();
                ArrayList<SharedMember> sharedMembers = new ArrayList<>();
                for (Map.Entry<String, SharedMember> sharedMemberEntry :
                        sharedMemberHashMap.entrySet()) {
                    SharedMember test = sharedMemberEntry.getValue();
                    sharedMembers.add(test);
                }
                AdapterSharedMembers adapterSharedMembers = new AdapterSharedMembers(sharedMembers, sharedMembersListener);
                fragmentMyManageLists.rvSharedWithMembers.setLayoutManager(new LinearLayoutManager(MainActivity.appContext));
                fragmentMyManageLists.rvSharedWithMembers.setAdapter(adapterSharedMembers);
            }
        });
    }

    private void addSharedMemberListener(){
        fragmentMyManageLists.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireBaseUtil.readUsers(new MyCallback() {
                    @Override
                    public void readUsers(HashMap<String, String> userEmails) {
                        String findUser = fragmentMyManageLists.etListSharedWith.getText().toString();
                        boolean test = userEmails.containsValue(findUser);
                        if(test){
                            for (Map.Entry<String, String > entry :
                                    userEmails.entrySet()) {
                                    if(entry.getValue().equals(findUser)){
                                        String uid = entry.getKey();
                                        String email = entry.getValue();
                                        SharedMember sharedMember = new SharedMember(uid, email);
                                        listOfProducts.getSharedWith().put(sharedMember.getUid(), sharedMember);
                                        sharedMembersListener.setValue(listOfProducts.getSharedWith());
                                    }
                            }
                        }
                    }
                });
            }
        });
    }

    private void fillListData(){
        fragmentMyManageLists.etListName.setText(listOfProducts.getName());
        if(listOfProducts.isShared()){
            fragmentMyManageLists.rbSharedList.setChecked(true);
            fragmentMyManageLists.rbPrivateList.setChecked(false);
        }else{
            fragmentMyManageLists.rbSharedList.setChecked(false);
            fragmentMyManageLists.rbPrivateList.setChecked(true);
        }
    }
}
