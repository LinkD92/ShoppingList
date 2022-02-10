package com.symbol.shoppinglistv2.Command;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.ActivityMain;
import com.symbol.shoppinglistv2.Activities.FragmentLists;
import com.symbol.shoppinglistv2.Components.SharedList;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

//adapter assignment and list gather from firebase
public class CommandMASpinnerAdapter implements Command{

    private final String TAG = "CommandMASpinnerItemClick";
    private Spinner spinList;
    private Spinner spinPrivShared;
    private ArrayAdapter adapter;
    private MutableLiveData<ArrayList<String>> listLoaded;
    private MutableLiveData<ArrayList<SharedList>> sharedListLoaded;
    private MutableLiveData<String> currentListType;
    private ImageButton ibtnListDetails;
    private FragmentLists fragmentLists;


    public CommandMASpinnerAdapter(Spinner spinList, MutableLiveData<ArrayList<SharedList>> sharedListLoaded, Spinner spinPrivShared, ImageButton ibtnListDetails, FragmentLists fragmentLists){
        this.spinList = spinList;
        this.sharedListLoaded = sharedListLoaded;
        this.spinPrivShared = spinPrivShared;
        this.ibtnListDetails = ibtnListDetails;
        currentListType = new MutableLiveData<>();
        this.fragmentLists = fragmentLists;
    }
    @Override
    public boolean execute() {
        spinPrivSharedValues();
        populateAdapter();
        return false;
    }

    private void getLocalLists(){
        listLoaded = new MutableLiveData<>();
        FirebaseUtil.readList(new MyCallback() {
            @Override
            public void onListCallback(ArrayList<String> listsArrayList) {
                listLoaded.setValue(listsArrayList);
                if (listLoaded.getValue().size() == 0){
                    fragmentLists.rvProducts.setVisibility(View.GONE);
                    fragmentLists.rvBundles.setVisibility(View.GONE);
                }else{
                    fragmentLists.rvProducts.setVisibility(View.VISIBLE);
                    fragmentLists.rvBundles.setVisibility(View.VISIBLE);
                }
            }
        });
        listLoaded.observeForever(new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(listLoaded.getValue().size() >0){
                }
                adapter = new ArrayAdapter(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, listLoaded.getValue());
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinList.setAdapter(adapter);
                spinList.setSelection(FirebaseUtil.spinnerPositionERROR);
            }
        });
    }

    private void getSharedLists(){
        FirebaseUtil.readSharedList(new MyCallback() {
            @Override
            public void readSharedLists(ArrayList<SharedList> sharedLists) {
                sharedListLoaded.setValue(sharedLists);
                if (sharedListLoaded.getValue().size() == 0){
                    fragmentLists.rvProducts.setVisibility(View.GONE);
                    fragmentLists.rvBundles.setVisibility(View.GONE);
                }else{
                    fragmentLists.rvProducts.setVisibility(View.VISIBLE);
                    fragmentLists.rvBundles.setVisibility(View.VISIBLE);
                }
            }
        });

        sharedListLoaded.observeForever(new Observer<ArrayList<SharedList>>() {
            @Override
            public void onChanged(ArrayList<SharedList> sharedLists) {
                ArrayList<String> tempArray = new ArrayList<>();
                for (SharedList sh :
                        sharedLists) {
                    String sharedList = "(" +sh.getEmail() +")"+sh.getName();
                    tempArray.add(sharedList);
                }
                adapter = new ArrayAdapter(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, tempArray);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinList.setAdapter(adapter);
                spinList.setSelection(FirebaseUtil.spinnerPositionERROR);
            }
        });

    }

    private void populateAdapter(){
        currentListType.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Private")){
                    FirebaseUtil.spinnerPositionERROR = 0;
                    getLocalLists();
                }else{
                    FirebaseUtil.spinnerPositionERROR = 0;
                    getSharedLists();
                }
            }
        });
    }

    private void spinPrivSharedValues(){
        ArrayList<String > privShared = new ArrayList<>();
        privShared.add("Private");
        privShared.add("Shared");
        adapter = new ArrayAdapter(ActivityMain.appContext, R.layout.support_simple_spinner_dropdown_item, privShared);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinPrivShared.setAdapter(adapter);

        spinPrivShared.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getSelectedItemPosition()){
                    case 0:
                        currentListType.setValue(adapterView.getItemAtPosition(0).toString());
                        ibtnListDetails.setEnabled(true);
                        break;
                    case 1:
                        currentListType.setValue(adapterView.getItemAtPosition(1).toString());
                        ibtnListDetails.setEnabled(false);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
