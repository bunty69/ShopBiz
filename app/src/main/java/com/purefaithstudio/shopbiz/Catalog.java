package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

public class Catalog extends ActionBarActivity implements ListView.OnItemClickListener {
    private ImageSwitcher ims;
    Bitmap bmp;
    app42Manager apm;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    DownloadImageTask dit;
    Bitmap image[];
    int currImage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ims = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
        dit=new DownloadImageTask(ims);
        ims.setFactory(new ViewSwitcher.ViewFactory() {
                           public View makeView() {
                               ImageView myView = new ImageView(getApplicationContext());
                               return myView;
                           }
                       }
        );
        image = new Bitmap[3];
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        ims.setInAnimation(in);
        ims.setOutAnimation(out);
        Log.d("harsim", "ims done init");
       try {

           apm = new app42Manager(this.getApplicationContext());
           //apm=MainActivity.apm;
           Log.d("harsim", "apm loading");
           // first category a list of categories and items from categories
          // apm.loadImage(apm.categories().get(0));
           Log.d("harsim", "apm loaded");
           //need blocking call else imageList is not set when called below
           Log.d("harsim","image loaded");
           mNavigationDrawerItemTitles = new String[apm.categories().size()-1];
           ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[apm.categories().size()-1];
           Log.d("harsim","arrays size"+apm.categories().size());
           for (int i = 0; i < apm.categories().size()-1; i++) {
               mNavigationDrawerItemTitles[i] = apm.categories().get(0).getItemList().get(i).getName();
               drawerItem[i] = new ObjectDrawerItem(R.drawable.ic_launcher, apm.categories().get(0).getItemList().get(i).getName());
               Log.d("harsim","drawerItem:name"+drawerItem[i].name+":icon res "+drawerItem[i].icon);
               drawerItem[i].setImage(apm.categories().get(i).getItemList().get(0).getUrl());
           }
           Log.d("harsim","for looped");
           mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
           mDrawerList = (ListView) findViewById(R.id.left_drawer);
          DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
           mDrawerList.setAdapter(adapter);
           mDrawerList.setOnItemClickListener(this);
           Log.d("harsim", "navdrawer ready");
           selectItem(-1);
           Log.d("harsim", "item selected");
           ims.postDelayed(new Runnable() {
               @SuppressWarnings("deprecation")
               public void run() {
                   ims.setImageDrawable(
                           new BitmapDrawable(currImage==0?image[0]:currImage==1?image[1]:image[2]));
                 ims.postDelayed(this, 2500);
                   currImage++;
                   if (currImage > 2)
                       currImage = 0;
               }
           }, 1000);
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItem(int position) {
        final int pos=position;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    image[0]=BitmapFactory.decodeStream((InputStream) new URL(apm.categories().get(pos+1).getItemList().get(0).getUrl()).getContent());
                    image[1]=BitmapFactory.decodeStream((InputStream) new URL(apm.categories().get(pos+1).getItemList().get(1).getUrl()).getContent());
                    image[2]=image[0];
                }
                catch(ArrayIndexOutOfBoundsException e1)
                {
                 image[1]=image[0];
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
               // dit.execute(apm.categories().get(position+1).getItemList().get(0).getUrl());

        if(position<0)
          position=0;
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

        Log.d("harsim", "item selected" + position);
    }
}