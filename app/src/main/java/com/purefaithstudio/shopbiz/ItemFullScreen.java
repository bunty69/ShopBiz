package com.purefaithstudio.shopbiz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemFullScreen extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private String descriptionText;
    private int imageId;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerListData recyclerListData[] = {new RecyclerListData("Harjas", R.drawable.second),
            new RecyclerListData("Harsimran", R.drawable.third),
            new RecyclerListData("Jassi", R.drawable.fourth), new RecyclerListData("Harjas", R.drawable.second),
            new RecyclerListData("Harsimran", R.drawable.third),
            new RecyclerListData("Jassi", R.drawable.fourth), new RecyclerListData("Harjas", R.drawable.second),
            new RecyclerListData("Harsimran", R.drawable.third),
            new RecyclerListData("Jassi", R.drawable.fourth)};
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private CoordinatorLayout condinator;
    private int mutedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_full_screen);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        condinator = (CoordinatorLayout) findViewById(R.id.cordinator);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //textView=(TextView)findViewById(R.id.full_description);
        imageView = (ImageView) findViewById(R.id.header);
        Bundle bundle = getIntent().getExtras();
        descriptionText = bundle.getString("itemName");
        imageId = bundle.getInt("itemImage");
        imageView.setImageResource(imageId);
        collapsingToolbar.setTitle(descriptionText);
        dynamicToolbarColor(imageId);
        // collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.appbar));
        //recycler view setup
        recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(this, MainActivity.apm.categories().get(1).getItemList());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }

    public void dynamicToolbarColor(int imagId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imagId);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
