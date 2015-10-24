package com.purefaithstudio.shopbiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by MY System on 10/20/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private final RecyclerListData[] items;
    private final DisplayImageOptions options;
    public ImageLoader imageLoader;
    public ClickListener clickListener;

    public RecyclerAdapter(Context context, RecyclerListData items[]) {
        Log.i("harjas", "names");
        this.items = items;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true).build();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        Log.i("harjas", "oncreate");
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        String uri = "http://cdn.shephertz.com/repository/files/50ca63a022074fdeb10d9e3ca5169e4052a631070d2cb62251dbf8a956b6d76c/13b220cdff8036161b403348821f4480b22385b5/58801e3391955d7ee7d51ba293351c3176f5b682.jpg";
        holder.textView.setText(items[position].getItemName());
        imageLoader.displayImage(uri, holder.imageView, options);
        //holder.imageView.setImageResource(items[position].getItemImage());
        //pass viewholder to async task-DownloadImage(viewholder,url)
        Log.i("harjas", "onbind");
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void itemClicked(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview_row);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);
            Log.i("harjas", "textview set");
        }

        @Override
        public void onClick(View v) {
            int position = getPosition();
            if (clickListener != null) {
                clickListener.itemClicked(v, position);
            }
        }
    }
}
