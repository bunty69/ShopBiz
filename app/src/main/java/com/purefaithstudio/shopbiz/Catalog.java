package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Catalog extends ActionBarActivity {
    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    public static ImageLoader imageLoader;
    public Catalog contextglobal;
    Bitmap bmp;
    app42Manager apm;
    int pos;
    private ImageSwitcher ims;
    private String[] mNavigationDrawerItemTitles;
    private Bitmap image[] = new Bitmap[3];
    private int currImage = 0;
    private CatalogFragment fragment;
    private Toolbar toolbar;
    private boolean flag = false;
    private WaitFragment waitFragment;
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("harjas", "onRecieve");
            loadAfter(context);
            if (waitFragment != null)
                waitFragment.dismiss();//problem here wait
        }
    };
    private String userName = "default";
    private byte[] bytes;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        contextglobal = this;
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //receive username from fragment1 not from savedfile bcoz menu requires speed
        userName = getIntent().getStringExtra("username");
        Log.i("DebugHarjas", "Here");
        NavDrawer navDrawer = (NavDrawer) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_id);
        if (navDrawer != null) {
            navDrawer.setUp(R.id.nav_drawer_id, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        }
        registerReceiver(receiver, new IntentFilter("com.purefaithstudio.shopbiz.CUSTOM"));
        if (app42Manager.flag)
            loadAfter(contextglobal);
        Log.i("harjas", "registered");
        //waiting spinner
        if (!app42Manager.flag) {
            waitFragment = new WaitFragment();
            waitFragment.show(getSupportFragmentManager(), "Tag3");
        }

    }


    //called when apm completes it loading and content is ready
    private void loadAfter(Context conext) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new CatalogFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        try {

            apm = MainActivity.apm;

            mNavigationDrawerItemTitles = new String[apm.categories().size()];
            ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[apm.categories().size()];
            Log.d("harsim", "arrays size" + apm.categories().size());
            for (int i = 0; i < apm.categories().size(); i++) {
                mNavigationDrawerItemTitles[i] = apm.getNameList().getItemList().get(i).getName();
                drawerItem[i] = new ObjectDrawerItem(R.drawable.ic_launcher, apm.getNameList().getItemList().get(i).getName());
                Log.d("harsim", "drawerItem:name" + drawerItem[i].name + ":icon res " + drawerItem[i].icon);
                drawerItem[i].setImage(apm.getNameList().getItemList().get(i).getUrl());
            }
            Log.d("harsim", "for looped");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);
            DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(conext, R.layout.listview_item_row, drawerItem);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catalog, menu);
        MenuItem menuItem = menu.findItem(R.id.user_profile);
        Log.i("DebugHarjas", "Here2");
        if (userName == null) {
            Profile.fetchProfileForCurrentAccessToken();
            userName = Profile.getCurrentProfile().getName();
        }
        menuItem.setTitle(userName);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.user_profile) {
            Intent intent = new Intent(Catalog.this, UserProfile.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (waitFragment != null)
            waitFragment.dismiss();
        unregisterReceiver(receiver);
    }
}
