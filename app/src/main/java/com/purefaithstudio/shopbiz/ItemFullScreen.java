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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;

import org.json.JSONException;

public class ItemFullScreen extends AppCompatActivity {

    private ImageView imageView;
    private String descriptionText;
    private TextView textView;
    private int imageId;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private CoordinatorLayout condinator;
    private int mutedColor;
    private Catalogue.Category.Item item;
    private ItemExtra itemExtra;

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
     try {
         Bundle bundle = getIntent().getExtras();
         Log.i("harsim", "category" + bundle.getInt("category") + "item" + bundle.getInt("itemno"));
         item = MainActivity.apm.categories().get(bundle.getInt("category")).getItemList().get(bundle.getInt("itemno"));
         itemExtra= MainActivity.apm.getItemExtra(item.getItemId());
         Log.i("harsim","itemExtra"+itemExtra.getItemID()+"stock"+itemExtra.getStock());
         imageId = bundle.getInt("itemImage");
         collapsingToolbar.setTitle(item.getName());
         dynamicToolbarColor(imageId);
         // collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.appbar));
         //recycler view setup
         recyclerView = (RecyclerView) findViewById(R.id.list);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         mAdapter = new ItemAdapter(this, item,itemExtra);
         recyclerView.setLayoutManager(linearLayoutManager);
         recyclerView.setAdapter(mAdapter);
         MainActivity.apm.addToCart(item.getItemId(), 3, item.getPrice().intValueExact());
     } catch(Exception e)
         {
             e.printStackTrace();
         }

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
