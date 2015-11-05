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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MY System on 10/20/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private final DisplayImageOptions options;
    private final HashMap<String, String> url_maps;
    public ClickListener clickListener;
    public ArrayList<TextSliderView> textSliderViews;
    int currImage = 0;
    private ArrayList<Catalogue.Category.Item> items;
    private ImageLoader imageLoader;

    public RecyclerAdapter(Context context, ArrayList<Catalogue.Category.Item> items) {
        Log.i("harjas", "names");
        this.items = items;
        imageLoader = ImageLoader.getInstance();//get instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));//loads that instance with init congig_default
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true).build();//this is options setting
        //hashmap example(create a category of offers and get url of images from there and add it to hashmap)
        url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        textSliderViews=new ArrayList<>();
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView.description(name).image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);
            if(textSliderView==null) Log.i("Null","Null");
            textSliderViews.add(textSliderView);
        }
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
            for (TextSliderView textSliderView : textSliderViews) {
                holder.sliderLayout.addSlider(textSliderView);
            }

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
        LinearLayout upperView;
        TextView textView, price;
        ImageView imageView;
        SliderLayout sliderLayout;

        public ViewHolder(View itemView, int viewType, Context context) {
            super(itemView);
            if (viewType == 0) {
                sliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
                sliderLayout.setDuration(1000);
                //replace im1 to dynamically passed images
               // dynamicColor(R.drawable.img1, context);

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
        public void dynamicColor(int imagId, Context context) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imagId);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                    upperView.setBackgroundColor(mutedColor);
                    upperView.getBackground().setAlpha(150);
                }
            });
        }

        private int convert(int n) {
            return Integer.valueOf(String.valueOf(n), 16);
        }
    }
}
