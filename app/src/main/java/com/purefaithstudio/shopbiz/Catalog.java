package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.content.Context;
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

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class Catalog extends ActionBarActivity {
    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    Bitmap bmp;
    app42Manager apm;
    int pos;
    private ImageSwitcher ims;
    private String[] mNavigationDrawerItemTitles;
    private Bitmap image[] = new Bitmap[3];
    private int currImage = 0;
    private CatalogFragment fragment;
    private Toolbar toolbar;
    public static ImageLoader imageLoader;

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


        try {

            apm = MainActivity.apm;

            mNavigationDrawerItemTitles = new String[apm.categories().size() - 1];
            ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[apm.categories().size() - 1];
            Log.d("harsim", "arrays size" + apm.categories().size());
            for (int i = 0; i < apm.categories().size() - 1; i++) {
                mNavigationDrawerItemTitles[i] = apm.categories().get(0).getItemList().get(i).getName();
                drawerItem[i] = new ObjectDrawerItem(R.drawable.ic_launcher, apm.categories().get(0).getItemList().get(i).getName());
                Log.d("harsim", "drawerItem:name" + drawerItem[i].name + ":icon res " + drawerItem[i].icon);
                drawerItem[i].setImage(apm.categories().get(0).getItemList().get(i).getUrl());
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
                    if (position < 0)
                        position = 0;
                    getSupportActionBar().setTitle(mNavigationDrawerItemTitles[position]);
                }
            });

            Log.d("harsim", "navdrawer ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onBackPressed()
    {
        if(getSupportActionBar().getTitle().equals("Catalog")) {
            super.onBackPressed();
        }
        else {
            CatalogFragment.selectItem(-1);
            getSupportActionBar().setTitle("Catalog");
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

}
