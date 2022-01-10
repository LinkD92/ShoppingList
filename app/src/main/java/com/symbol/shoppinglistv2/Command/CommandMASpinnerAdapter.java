package com.symbol.shoppinglistv2.Command;

import android.util.Log;
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
    private ArrayAdapter adapter;
    private MutableLiveData<ArrayList<String>> listLoaded;
    private MutableLiveData<ArrayList<SharedList>> sharedListLoaded;


    public CommandMASpinnerAdapter(Spinner spinList, MutableLiveData<ArrayList<SharedList>> sharedListLoaded){
        this.spinList = spinList;
        this.sharedListLoaded = sharedListLoaded;

    }
    @Override
    public boolean execute() {
        getLocalLists();

        return false;
    }

    private void getLocalLists(){
        listLoaded = new MutableLiveData<>();
        FireBaseUtil.readList(new MyCallback() {
            @Override
            public void onListCallback(ArrayList<String> listsArrayList) {
                listLoaded.setValue(listsArrayList);
                //super.onListCallback(listsArrayList);
            }
        });
        listLoaded.observeForever(new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                getSharedLists();
                adapter = new ArrayAdapter(MainActivity.appContext, R.layout.support_simple_spinner_dropdown_item, strings);
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
                for (SharedList sh :
                        sharedLists) {
                    String sharedList = "(" +sh.getEmail() +")"+sh.getName();
                    listLoaded.getValue().add(sharedList);
                }

            }
        });

    }
}
