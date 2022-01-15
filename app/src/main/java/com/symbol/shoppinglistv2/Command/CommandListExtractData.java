package com.symbol.shoppinglistv2.Command;

import com.symbol.shoppinglistv2.Activities.FragmentManageLists;
import com.symbol.shoppinglistv2.Components.ListOfProducts;

//Command to assign data of the choosen list to the view where list is edditd
public class CommandListExtractData implements Command{
    private final String TAG = "com.symbol.shoppinglistv2.Command.CommandListExtractData";
    private ListOfProducts listOfProducts;
    private FragmentManageLists fragmentManageLists;

    public CommandListExtractData(FragmentManageLists fragmentManageLists, ListOfProducts listOfProducts){
        this.fragmentManageLists = fragmentManageLists;
        if (listOfProducts != null){
            this.listOfProducts = listOfProducts;
        }else {
            this.listOfProducts = new ListOfProducts();
        }
    }

    @Override
    public boolean execute() {

        return false;
    }
}
