package com.purefaithstudio.shopbiz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by harsimran singh on 16-10-2015.
 */
public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {
    Context mContext;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;
    public ObjectDrawerItem folder;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        folder = data[position];
        new DownloadImageTask(imageViewIcon).execute(folder.Url);
        imageViewIcon.setImageResource(folder.icon);
       textViewName.setText(folder.name);

        //if above fails for testing navdrawer setup is correct use this
        //imageViewIcon.setImageResource(R.drawable.ic_launcher);
       // textViewName.setText("categories");

        return listItem;
    }
}
