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
    private TextView textView;
    private String descriptionText;
    private int imageId;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private CoordinatorLayout condinator;
    private int mutedColor;
    private Catalogue.Category.Item item;
    private ItemExtra itemExtra;
    public static ImageLoader imageLoader;
    private DisplayImageOptions options;

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
         Log.i("hars", "category" + bundle.getInt("category") + "item" + bundle.getInt("itemno"));
         item = MainActivity.apm.categories().get(bundle.getInt("category")).getItemList().get(bundle.getInt("itemno"));
         imageLoader = ImageLoader.getInstance();//get instance
         imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));//loads that instance with init congig_default
         options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true).build();//this is options setting
         imageId = bundle.getInt("itemImage");
         //show image of item in action bar
         //imageLoader.displayImage(item.getUrl(), imageView, options);
         collapsingToolbar.setTitle(item.getName());
         dynamicToolbarColor(imageId);
         // collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.appbar));
         //recycler view setup
         recyclerView = (RecyclerView) findViewById(R.id.list);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         mAdapter = new RecyclerAdapter(this, MainActivity.apm.categories().get(1).getItemList());
         recyclerView.setLayoutManager(linearLayoutManager);
         recyclerView.setAdapter(mAdapter);
     } catch(Exception e)
         {
             e.printStackTrace();
         }
     /*   Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String Url="http://www.allyoursjewels.com/wp-content/uploads/Dhara-Diamond-Bangles1.jpg";
                Log.d("harsim", "" + MainActivity.apm.categories().get(1).getItemList().get(0).getItemId());
                try {
                    //MainActivity.apm.putItemExtra(MainActivity.apm.categories().get(1).getItemList().get(0).getItemId(),Url,Url,Url,10,30);
                     MainActivity.apm.getItemExtra(item.getItemId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();*/

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
