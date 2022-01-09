package com.symbol.shoppinglistv2.Other;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPos, int toPos);

    void onItemSwiped(int pos, int direction);
}
