package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageSwitcher;
import android.widget.ListView;

public class Catalog extends ActionBarActivity {
    private ImageSwitcher ims;
    Bitmap bmp;
    app42Manager apm;
    private String[] mNavigationDrawerItemTitles;
    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    private Bitmap image[] = new Bitmap[3];
    private int currImage = 0;
    int pos;
    private CatalogFragment fragment;
    private Toolbar toolbar;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavDrawer navDrawer = (NavDrawer) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_id);
        if (navDrawer != null) {
            navDrawer.setUp(R.id.nav_drawer_id, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new CatalogFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

        /*ims = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
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
        Log.d("harsim", "ims done init");*/
        try {
/*
            //apm = new app42Manager(this.getApplicationContext());
            apm = MainActivity.apm;
            Log.d("harsim", "apm loading");
            // first category from list of categories and items from categories
            apm.loadImage(apm.categories().get(0));
            Log.d("harsim", "apm loaded");
            //need blocking call else imageList is not set when called below
            Log.d("harsim", "image loaded");
            */
            apm = MainActivity.apm;
            //jam here use runOnUiThread and wait logic with broadcast reciever
            //apm.loadImage(apm.categories().get(0));
            mNavigationDrawerItemTitles = new String[apm.categories().size() - 1];
            ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[apm.categories().size() - 1];
            Log.d("harsim", "arrays size" + apm.categories().size());
            for (int i = 0; i < apm.categories().size() - 1; i++) {
                mNavigationDrawerItemTitles[i] = apm.categories().get(0).getItemList().get(i).getName();
                drawerItem[i] = new ObjectDrawerItem(R.drawable.ic_launcher, apm.categories().get(0).getItemList().get(i).getName());
                Log.d("harsim", "drawerItem:name" + drawerItem[i].name + ":icon res " + drawerItem[i].icon);
                drawerItem[i].setImage(apm.categories().get(i).getItemList().get(0).getUrl());
            }
            Log.d("harsim", "for looped");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);
            DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
            mDrawerList.setAdapter(adapter);
            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CatalogFragment.selectItem(position);

                }
            });


            Log.d("harsim", "navdrawer ready");
            //selectItem(-1);
            //Log.d("harsim", "item selected");
            /*ims.postDelayed(new Runnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                    ims.setImageDrawable(
                            new BitmapDrawable(currImage == 0 ? image[0] : currImage == 1 ? image[1] : image[2]));
                    ims.postDelayed(this, 2500);
                    currImage++;
                    if (currImage > 2)
                        currImage = 0;
                }
            }, 1000);*/
        } catch (Exception e) {
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


/*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectItem(int position) {
        Log.d("harsim", "item selected" + position);
        apm.loadImage(apm.categories().get(position + 1));
        image[0] = apm.getImage(0);
        try {
            image[1] = apm.getImage(1);
        } catch (ArrayIndexOutOfBoundsException AIOBE) {
            image[1] = image[0];
        }
        image[2] = image[0];

        if (position < 0)
            position = 0;
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);


    }*/
}
