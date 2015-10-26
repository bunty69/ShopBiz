package com.purefaithstudio.shopbiz;

/**
 * Created by harsimran singh on 24-10-2015.
 */
public class ItemExtra {
    private String URL[];
    private int stock=0;
    private String itemID;
    private int discount=0;
    public ItemExtra()
    {

    }

    public ItemExtra(String itemID)
    {
        this.itemID=itemID;
        URL=new String[3];
    }
    public void setStock(int stock)
    {
        this.stock=stock;
    }
    public void setURL(int index,String Image)
    {
        this.URL[index]=Image;
    }
    public int getStock()
    {
        return stock;
    }
    public String getURL(int index)
    {
        return URL[index];
    }
    public void setDiscount(int dis){this.discount=dis;}
    public int getDiscount(){return discount;}
}
