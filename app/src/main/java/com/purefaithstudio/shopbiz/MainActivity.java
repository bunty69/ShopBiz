package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button next;
    static app42Manager apm;
    TextView tv1;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Context context;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //next=(Button)findViewById(R.id.button1);
        //tv1=(TextView)findViewById(R.id.textview1);
        fragmentManager = getFragmentManager();
        fragment = new Fragment1();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        context=this.getApplicationContext();
        new Thread(new Runnable() {
            @Override
            public void run() {
                apm = new app42Manager(context);
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
