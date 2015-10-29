package com.purefaithstudio.shopbiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;

import java.util.ArrayList;

/**
 * Created by MY System on 10/20/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private final DisplayImageOptions options;
    public ClickListener clickListener;
    int currImage = 0;
    private ArrayList<Catalogue.Category.Item> items;
    private ImageLoader imageLoader;

    public RecyclerAdapter(Context context, ArrayList<Catalogue.Category.Item> items) {
        Log.i("harjas", "names");
        this.items = items;
        imageLoader = ImageLoader.getInstance();//get instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));//loads that instance with init congig_default
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true).build();//this is options setting
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_switch, null);
            return new ViewHolder(itemView, 0, parent.getContext());
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        Log.i("harjas", "oncreate");
        return new ViewHolder(itemView, 1, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            final String uri = items.get(0).getUrl();
            imageLoader.displayImage(uri, holder.imageView2, options);
          /*  holder.imageView2.postDelayed(new Runnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                            //String URI=(currImage == 0 ? image[0] : currImage == 1 ? image[1] : image[2]);
                    imageLoader.displayImage(uri, holder.imageView2, options);
                    holder.imageView2.postDelayed(this, 2500);
                    currImage++;
                    if (currImage > 2)
                        currImage = 0;
                }
            }, 2000);*/

        } else {
            String uri = items.get(position - 1).getUrl();
            holder.textView.setText(items.get(position - 1).getName());
            holder.price.setText(items.get(position - 1).getPrice().toString());
            imageLoader.displayImage(uri, holder.imageView, options);
            Log.d("harsim", "name:" + items.get(position - 1).getName() + "price:" + items.get(position - 1).getPrice().toString());//nice
            //holder.imageView.setImageResource(items[position].getItemImage());
            //pass viewholder to async task-DownloadImage(viewholder,url)
        }
        Log.i("harjas", "onbind");
    }

    @Override
    public int getItemCount() {
        // Log.d("harsim","recycler items size:"+items.size());
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;//default
        if (position == 0)
            viewType = 0;
        return viewType;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateList(ArrayList<Catalogue.Category.Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public interface ClickListener {
        public void itemClicked(View v, int position);


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView imageText;
        TextView textView, price;
        ImageView imageView, imageView2;
        Animation out, in;


        public ViewHolder(View itemView, int viewType, Context context) {
            super(itemView);
            if (viewType == 0) {

                in = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
                out = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
                imageView2 = (ImageView) itemView.findViewById(R.id.imageSwitcher1);
                imageText=(TextView)itemView.findViewById(R.id.image_textlarge);
                //replace im1 to dynamically passed images
                dynamicColor(R.drawable.img1, context);
                imageView2.setAnimation(in);
                itemView.setOnClickListener(this);
            } else {
                textView = (TextView) itemView.findViewById(R.id.textview_name);
                price = (TextView) itemView.findViewById(R.id.textview_row);
                imageView = (ImageView) itemView.findViewById(R.id.item_image);
                itemView.setOnClickListener(this);
            }
            Log.i("harjas", "textview set");
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();//dec by 1 if header on plus add header logic
            if (clickListener != null) {
                clickListener.itemClicked(v, position);
            }
        }
        //added this to have dynamic color according to imageswitch images..problem is we need transparent colors this
        //methods generates solid colors...try this
        public void dynamicColor(int imagId,Context context) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imagId);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                    Log.i("harjas", String.valueOf(mutedColor));//we need 80% opacity eg: #80ccc where 80 represents opacity
                }
            });
        }
    }
}
