package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MY System on 10/18/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NavDrawer extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_drawer,container,false);
    }
}
