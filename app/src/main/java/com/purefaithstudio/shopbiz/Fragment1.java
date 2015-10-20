package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by MY System on 10/18/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment1 extends Fragment {
    private View rootView;
    private Button next;
    private CatalogFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment1,container,false);
        next=(Button)rootView.findViewById(R.id.button1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(),Catalog.class);
                startActivity(intent);

            }
        });
        return rootView;
    }
}
