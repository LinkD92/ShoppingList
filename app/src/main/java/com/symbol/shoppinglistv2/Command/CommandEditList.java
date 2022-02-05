package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.symbol.shoppinglistv2.Activities.FragmentManageLists;
import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Activities.FragmentManagement;
import com.symbol.shoppinglistv2.Components.ListOfProducts;
import com.symbol.shoppinglistv2.Components.SharedMember;
import com.symbol.shoppinglistv2.Other.AdapterSharedMembers;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.FragmentMyOpener;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

//Command with implementation of all edit List actions
public class CommandEditList implements Command {
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandEditList";

    private ListOfProducts listOfProducts;
    private FragmentManageLists fragmentManageLists;
    private MutableLiveData<HashMap<String, SharedMember>> sharedMembersListener = new MutableLiveData<>();
    private FragmentMyOpener fragmentMyOpener;
    private HashMap<String,SharedMember> oldMembers;

    public CommandEditList(){}

    public CommandEditList(FragmentManageLists fragmentManageLists, ListOfProducts listOfProducts) {
        this.fragmentManageLists = fragmentManageLists;
        if(listOfProducts == null){
            this.listOfProducts = new ListOfProducts();
            oldMembers = new HashMap<>();
        }else
        {
            this.listOfProducts = listOfProducts;
            oldMembers = new HashMap<>(listOfProducts.getSharedWith());

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
        //close fragment button
        btnCancelListener();
        return false;
    }

    private void btnSaveChangesListener(){
        fragmentManageLists.btnEditListSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentName = listOfProducts.getName();
                String newName = fragmentManageLists.etListName.getText().toString();



                listOfProducts.setName(fragmentManageLists.etListName.getText().toString());
                listOfProducts.setShared(fragmentManageLists.rbSharedList.isChecked());
                FirebaseUtil.mutableList.setValue(listOfProducts);
                if(listOfProducts.getListPath() == null){
                    listOfProducts.setListPath(FirebaseUtil.userPath + "/lists/" +listOfProducts.getName());
                }

                if(listOfProducts.getName().equals(null) || listOfProducts.getName().equals("")){
                    Toast.makeText(ActivityMain.appContext, "Nazwa nie moze byc pusta", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseUtil.addList(listOfProducts);
                    FirebaseUtil.updateShare(listOfProducts, oldMembers);

                }
                if(currentName != null && !currentName.equals(newName)){
                    String removePath = "lists/" + currentName;
                    FirebaseUtil.updateShare(listOfProducts, oldMembers);
                    listOfProducts.setListPath(FirebaseUtil.userPath + "/lists/"+listOfProducts.getName());
                    FirebaseUtil.addList("lists", listOfProducts);
                    listOfProducts.setName(currentName);
                    FirebaseUtil.mutableList.setValue(listOfProducts);
                    FirebaseUtil.removeValue(listOfProducts, oldMembers);
                    FirebaseUtil.removeValue(removePath);


                }
                fragmentMyOpener = new FragmentMyOpener(fragmentManageLists);
                fragmentMyOpener.close(fragmentManageLists);
            }
        });
    }

    private void btnRadioListener(){
        fragmentManageLists.rgManageList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbSharedList:
                        fragmentManageLists.etListSharedWith.setFocusable(true);
                        fragmentManageLists.etListSharedWith.setFocusableInTouchMode(true);
                        fragmentManageLists.btnAddMember.setVisibility(View.VISIBLE);
                        fragmentManageLists.etListSharedWith.setVisibility(View.VISIBLE);
                        fragmentManageLists.rvSharedWithMembers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbPrivateList:
                        fragmentManageLists.etListSharedWith.setFocusable(false);
                        fragmentManageLists.etListSharedWith.setVisibility(View.GONE);
                        fragmentManageLists.btnAddMember.setVisibility(View.GONE);
                        fragmentManageLists.rvSharedWithMembers.setVisibility(View.GONE);
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
                fragmentManageLists.rvSharedWithMembers.setLayoutManager(new LinearLayoutManager(ActivityMain.appContext));
                fragmentManageLists.rvSharedWithMembers.setAdapter(adapterSharedMembers);
            }
        });
    }

    private void addSharedMemberListener(){
        fragmentManageLists.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.readUsers(new MyCallback() {
                    @Override
                    public void readUsers(HashMap<String, String> userEmails) {
                        String findUser = fragmentManageLists.etListSharedWith.getText().toString();
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
        fragmentManageLists.etListName.setText(listOfProducts.getName());
        if(listOfProducts.isShared()){
            fragmentManageLists.rbSharedList.setChecked(true);
            fragmentManageLists.rbPrivateList.setChecked(false);
        }else{
            fragmentManageLists.rbSharedList.setChecked(false);
            fragmentManageLists.rbPrivateList.setChecked(true);
        }
    }

    private void btnCancelListener(){
        fragmentManageLists.btnEditListCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentMyOpener fragmentMyOpener = new FragmentMyOpener();
                fragmentMyOpener.close("addList");
            }
        });
    }

}
