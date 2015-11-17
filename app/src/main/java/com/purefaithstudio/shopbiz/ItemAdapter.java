package com.purefaithstudio.shopbiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;

/**
 * Created by harsimran singh on 02-11-2015.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{


    private Catalogue.Category.Item item;
    private ItemExtra itemExtra;
    private ImageLoader imageLoader;
    private final DisplayImageOptions options;

    public ItemAdapter(Context context,Catalogue.Category.Item item,ItemExtra itemExtra) {
        super();
        this.item=item;
        this.itemExtra=itemExtra;
        imageLoader = ImageLoader.getInstance();//get instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));//loads that instance with init congig_default
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true).build();
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, null);
        Log.i("harjas", "oncreate");
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
       try {
           String uri = item.getUrl();
           holder.name.setText(item.getName());
           Log.d("harsim", "name:" + item.getName() + "price:" + item.getPrice().toString());
           holder.description.setText(item.getDescription());
           imageLoader.displayImage(uri, holder.imageView, options);
           holder.discount.setText("Discount:" + itemExtra.getDiscount() + "%");//nice
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }
    public int getItemCount() {
        // only 1 item for itemfullscreen
        return 1;
    }
    public class  ViewHolder extends RecyclerView.ViewHolder
    {

        TextView name, price,discount,description;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.item_image);
            name= (TextView) itemView.findViewById(R.id.ItemName);
            price= (TextView) itemView.findViewById(R.id.price);
            discount= (TextView) itemView.findViewById(R.id.discount);
            description= (TextView) itemView.findViewById(R.id.description);
        }
    }
}
