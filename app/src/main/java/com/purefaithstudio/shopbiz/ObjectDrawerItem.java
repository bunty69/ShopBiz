package com.purefaithstudio.shopbiz;

import android.graphics.Bitmap;

/**
 * Created by harsimran singh on 16-10-2015.
 */
public class ObjectDrawerItem {

        public Bitmap icon;
        public String name;
        public String Url;
        // Constructor.
        public ObjectDrawerItem(Bitmap icon, String name) {

            this.icon = icon;
            this.name = name;
        }

        public void setImage(String icon)
        {
            this.Url=icon;
        }
}

