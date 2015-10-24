package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by MY System on 10/19/2015.
 */


public class CatalogFragment extends Fragment implements RecyclerAdapter.ClickListener {
    private static DrawerLayout mDrawerLayout;
    private static boolean loaded = false;
    private static Thread thread;
    private static ImageSwitcher imageSwicher;
    private static String[] mNavigationDrawerItemTitles;
    private static ListView mDrawerList;
    private static Bitmap[] image = new Bitmap[3];
    private static int currImage = 0;
    Bitmap bmp;
    app42Manager apm;
    int pos;
    RecyclerListData data[] = {new RecyclerListData("RS.500", R.drawable.second),
            new RecyclerListData("Rs.600", R.drawable.third),
            new RecyclerListData("RS.700", R.drawable.fourth)};
    private View rootView;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void selectItem(final int position) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int pos = position;
                int size = MainActivity.apm.categories().get(pos + 1).getItemList().size();
                for (int i = 0; i < size; ++i) {
                    try {
                        image[size - 1 - i] = BitmapFactory.decodeStream((InputStream) new URL(MainActivity.apm.categories().get(pos + 1).getItemList().get(size - 1 - i).getUrl()).getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        // dit.execute(apm.categories().get(position+1).getItemList().get(0).getUrl());

       /* if(position<0)
            position=0;
*/
        //getActivity().getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
        Catalog.mDrawerLayout.closeDrawers();

        Log.d("harsim", "item selected" + position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.catalog_fragment, container, false);
        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        mAdapter = new RecyclerAdapter(getActivity().getApplicationContext(),data);
        mAdapter.setClickListener(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Log.i("harjas", "adapter set" + mAdapter.getItemCount());
        imageSwicher = (ImageSwitcher) rootView.findViewById(R.id.imageSwitcher1);
        imageSwicher.setFactory(new ViewSwitcher.ViewFactory() {
                                    public View makeView() {
                                        ImageView myView = new ImageView(context);
                                        myView.setScaleType(ImageView.ScaleType.FIT_XY);
                                        myView.setMaxHeight(50);
                                        myView.setMaxWidth(100);
                                        myView.setAdjustViewBounds(true);
                                        return myView;
                                    }
                                }
        );

        Animation in = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        imageSwicher.setInAnimation(in);
        imageSwicher.setOutAnimation(out);
        imageSwicher.postDelayed(new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                imageSwicher.setImageDrawable(
                        new BitmapDrawable(currImage == 0 ? image[0] : currImage == 1 ? image[1] : image[2]));
                imageSwicher.postDelayed(this, 2500);
                currImage++;
                if (currImage > 2)
                    currImage = 0;
            }
        }, 1000);


        return rootView;
    }

    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ItemFullScreen.class);
        Bundle b = new Bundle();
        switch (position) {
            case 0:
                b.putString("itemName", data[position].getItemName());
                b.putInt("itemImage", data[position].getItemImage());
                break;
            case 1:
                b.putString("itemName", data[position].getItemName());
                b.putInt("itemImage", data[position].getItemImage());
                break;
            case 2:
                b.putString("itemName", data[position].getItemName());
                b.putInt("itemImage", data[position].getItemImage());
                break;
        }
        intent.putExtras(b);
        startActivity(intent);
    }
}
