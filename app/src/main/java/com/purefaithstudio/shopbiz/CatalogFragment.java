package com.purefaithstudio.shopbiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MY System on 10/19/2015.
 */


public class CatalogFragment extends Fragment implements RecyclerAdapter.ClickListener {
    static int pos = 0;
    private static RecyclerAdapter mAdapter;
    RecyclerListData data[] = {new RecyclerListData("RS.500", R.drawable.second),
            new RecyclerListData("Rs.600", R.drawable.third),
            new RecyclerListData("RS.700", R.drawable.fourth)};
    private View rootView;
    private Context context;
    private RecyclerView recyclerView;
    private int mutedColor;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void selectItem(final int position) {
        mAdapter.updateList(MainActivity.apm.categories().get(position + 1).getItemList());
        pos = position + 1;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 2;
                else
                    return 1;
            }
        });
        mAdapter = new RecyclerAdapter(getActivity().getApplicationContext(), MainActivity.apm.categories().get(pos).getItemList());
        mAdapter.setClickListener(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Log.i("harjas", "adapter set" + mAdapter.getItemCount());
        selectItem(-1);
        return rootView;
    }

    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ItemFullScreen.class);
        Bundle b = new Bundle();
        Log.i("harsim", "pos" + pos);
        b.putInt("category", pos);
        b.putInt("itemno", position - 1);
        b.putInt("itemImage", data[position].getItemImage());
        intent.putExtras(b);
        startActivity(intent);
    }

}
