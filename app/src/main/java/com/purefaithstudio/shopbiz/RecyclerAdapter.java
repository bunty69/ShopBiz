package com.purefaithstudio.shopbiz;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MY System on 10/20/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final String[] names;

    public static class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.textview_row);
            Log.i("harjas","textview set");
        }
    }
    public RecyclerAdapter(String[] names){
        Log.i("harjas","names");
     this.names=names;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,null);
        Log.i("harjas","oncreate");
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
      holder.textView.setText(names[position]);
        Log.i("harjas","onbind");
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
}
