package com.symbol.shoppinglistv2.Command;

import android.bluetooth.le.ScanSettings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.MainActivity;
import com.symbol.shoppinglistv2.Components.SharedList;
import com.symbol.shoppinglistv2.Other.FireBaseUtil;
import com.symbol.shoppinglistv2.Other.MyCallback;
import com.symbol.shoppinglistv2.R;

import java.util.ArrayList;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

//adapter assignment and list gather from firebase
public class CommandMASpinnerAdapter implements Command{

    private final String TAG = "CommandMASpinnerItemClick";
    private Spinner spinList;
    private Spinner spinPrivShared;
    private ArrayAdapter adapter;
    private MutableLiveData<ArrayList<String>> listLoaded;
    private MutableLiveData<ArrayList<SharedList>> sharedListLoaded;
    private MutableLiveData<String> currentListType;



    public CommandMASpinnerAdapter(Spinner spinList, MutableLiveData<ArrayList<SharedList>> sharedListLoaded, Spinner spinPrivShared){
        this.spinList = spinList;
        this.sharedListLoaded = sharedListLoaded;
        this.spinPrivShared = spinPrivShared;
        currentListType = new MutableLiveData<>();
    }
    @Override
    public boolean execute() {
        spinPrivSharedValues();
        populateAdapter();
        return false;
    }

    private void getLocalLists(){
        listLoaded = new MutableLiveData<>();
        FireBaseUtil.readList(new MyCallback() {
            @Override
            public void onListCallback(ArrayList<String> listsArrayList) {
                listLoaded.setValue(listsArrayList);
            }
        });
        listLoaded.observeForever(new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                adapter = new ArrayAdapter(MainActivity.appContext, R.layout.support_simple_spinner_dropdown_item, listLoaded.getValue());
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinList.setAdapter(adapter);
                spinList.setSelection(FireBaseUtil.spinnerPositionERROR);
            }
        });
    }

    private void getSharedLists(){
        FireBaseUtil.readSharedList(new MyCallback() {
            @Override
            public void readSharedLists(ArrayList<SharedList> sharedLists) {
                sharedListLoaded.setValue(sharedLists);
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
                adapter = new ArrayAdapter(MainActivity.appContext, R.layout.support_simple_spinner_dropdown_item, tempArray);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinList.setAdapter(adapter);
                spinList.setSelection(FireBaseUtil.spinnerPositionERROR);
            }
        });

    }

    private void populateAdapter(){
        currentListType.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Private")){
                    FireBaseUtil.spinnerPositionERROR = 0;
                    getLocalLists();
                }else{
                    FireBaseUtil.spinnerPositionERROR = 0;
                    getSharedLists();
                }
            }
        });
    }

    private void spinPrivSharedValues(){
        ArrayList<String > privShared = new ArrayList<>();
        privShared.add("Private");
        privShared.add("Shared");
        adapter = new ArrayAdapter(MainActivity.appContext, R.layout.support_simple_spinner_dropdown_item, privShared);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinPrivShared.setAdapter(adapter);

        spinPrivShared.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getSelectedItemPosition()){
                    case 0:
                        currentListType.setValue(adapterView.getItemAtPosition(0).toString());
                        break;
                    case 1:
                        currentListType.setValue(adapterView.getItemAtPosition(1).toString());
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
