package com.symbol.shoppinglistv2.Command;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.symbol.shoppinglistv2.Activities.MainActivity;
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
    private int spinnerCurrentPos;
    private MutableLiveData<ArrayList<String>> listLoaded;


    public CommandMASpinnerAdapter(Spinner spinList){
        this.spinList = spinList;
    }
    @Override
    public boolean execute() {
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
                adapter = new ArrayAdapter(MainActivity.appContext, R.layout.support_simple_spinner_dropdown_item, strings);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinList.setSelection(FireBaseUtil.spinnerPositionERROR);
                spinList.setAdapter(adapter);
            }
        });







        return false;
    }
}
