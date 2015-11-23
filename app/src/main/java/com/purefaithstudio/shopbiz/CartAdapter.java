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
import com.shephertz.app42.paas.sdk.android.shopping.Cart;

import java.util.ArrayList;

/**
 * Created by harsimran singh on 14-11-2015.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final DisplayImageOptions options;
    ArrayList<Cart.Item> cartItems;
    private ArrayList<String> cartImages;
    private ImageLoader imageLoader;

    public CartAdapter(Context applicationContext, ArrayList<Cart.Item> items) {
        super();
        cartItems = items;
        cartImages = MainActivity.apm.cartExtras;
        imageLoader = ImageLoader.getInstance();//get instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(applicationContext));//loads that instance with init congig_default
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true).build();

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void changeItems(ArrayList<Cart.Item> items) {
        cartItems = items;
        notifyDataSetChanged();
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Log.i("listD",""+MainActivity.apm.cartExtras.get(position));
        String uri="";
        if(MainActivity.apm.cartExtras.get(position)!=null) {
            String cartExtrasString[] = MainActivity.apm.cartExtras.get(position).split(",");//bug here second one is null
            uri = String.valueOf(cartExtrasString[0]);
            holder.name.setText(cartExtrasString[1]);
        }
            Log.d("harsim", cartItems.get(position).toString() + "" + cartItems.get(position).getTotalAmount());
            Log.d("harsim", String.valueOf(cartItems.get(position).getPrice().intValueExact()));
            holder.price.setText(String.valueOf(cartItems.get(position).getPrice().intValueExact()));
            holder.Quantity.setText(String.valueOf(cartItems.get(position).getQuantity()));
            holder.total_on_item.setText(String.valueOf(cartItems.get(position).getTotalAmount()));
            imageLoader.displayImage(uri, holder.imageView, options);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, price, Quantity, total_on_item;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_cart_name);
            Quantity = (TextView) itemView.findViewById(R.id.item_cart_quantity);
            price = (TextView) itemView.findViewById(R.id.item_cart_price);
            imageView = (ImageView) itemView.findViewById(R.id.item_cart_image);
            total_on_item = (TextView) itemView.findViewById(R.id.item_cart_Total);
            itemView.setOnClickListener(this);
            Log.i("harjas", "textview set");
        }

        @Override
        public void onClick(View v) {
            //select item for add or change quantity
        }

        private int convert(int n) {
            return Integer.valueOf(String.valueOf(n), 16);
        }
    }
}
