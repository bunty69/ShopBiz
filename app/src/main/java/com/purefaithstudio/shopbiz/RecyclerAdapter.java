package com.purefaithstudio.shopbiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MY System on 10/20/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private final RecyclerListData[] items;
   public ClickListener clickListener;
    public RecyclerAdapter(RecyclerListData items[]) {
        Log.i("harjas", "names");
        this.items = items;
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
         int position=getPosition();
            if(clickListener!=null){
                clickListener.itemClicked(v,position);
            }
        }
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        Log.i("harjas", "oncreate");
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.textView.setText(items[position].getItemName());
        holder.imageView.setImageResource(items[position].getItemImage());
        Log.i("harjas", "onbind");
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }
    public interface ClickListener{
        public void itemClicked(View v,int position);
    }
}
