package com.purefaithstudio.shopbiz;

/**
 * Created by MY System on 10/21/2015.
 */
public class RecyclerListData {
    String itemName;
    int itemImage;

    public RecyclerListData(String itemName, int itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }
    public String getItemName(){
        return itemName;
    }
    public int getItemImage(){
        return itemImage;
    }
}
