package com.purefaithstudio.shopbiz;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.support.v7.app.ActionBarActivity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends ActionBarActivity{

	private ImageSwitcher ims;
	private Bitmap image[];
	private int currImage=0;
	DisplayMetrics metrics;
	Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//display size
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//imageloader thread
		image=new Bitmap[3];
		thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		try{
			image[0]= BitmapFactory.decodeStream((InputStream)new URL("http://img.tradeindia.com/fp/1/772/320.jpg").getContent());
			image[1]=BitmapFactory.decodeStream((InputStream)new URL("http://teja1.kuikr.com/i5/20141001/AD-diamond-bangles-imitation-jewellery-in-low-price-garante-1976906379-1412140338.jpeg").getContent());
			image[2]=BitmapFactory.decodeStream((InputStream)new URL("http://baggout.tiles.large.s3-ap-southeast-1.amazonaws.com/Craftsvilla-Latest-Designer-Artificial-Jewellery-Green-AD-Locket-Set-Pendants-by-Jaipur-Mart-Jaipur-Mart-ce2aecea-92a3-46a2-ab7b-4d5bf75e50ae.jpg").getContent());
			resize();
			}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		    }
		});
		thread.start();
		ims=(ImageSwitcher) findViewById(R.id.imageSwitcher1);
	/*	ims.setFactory(new ViewFactory() {
			   public View makeView() {
				      ImageView myView = new ImageView(getApplicationContext());
				      return myView;
				   }
			}
		);*/
		Animation in = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
		Animation out = AnimationUtils.loadAnimation(this,R.anim.slide_out_left);
		ims.setInAnimation(in);
		ims.setOutAnimation(out);
		ims.postDelayed(new Runnable() {
            @SuppressWarnings("deprecation")
			public void run() {
                ims.setImageDrawable(
                		new BitmapDrawable(currImage==0?image[0]:currImage==1?image[1]:image[2]));
                ims.postDelayed(this, 2000);
                currImage++;
                if(currImage>2)
                	currImage=0;
            }
        }, 1000);
		
	}
	public void resize()
	{
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		image[0]=Bitmap.createScaledBitmap(image[0], metrics.widthPixels*2, metrics.heightPixels*2, true);
		image[1]=Bitmap.createScaledBitmap(image[1], metrics.widthPixels*2, metrics.heightPixels*2, true);
		image[2]=Bitmap.createScaledBitmap(image[2], metrics.widthPixels*2, metrics.heightPixels*2, true);
		Log.d("harsim","hg"+image[0].getHeight()+":wg"+image[0].getWidth()+"\n"+"hg"+image[1].getHeight()+":wg"+image[1].getWidth()+"\n"+"hg"+image[2].getHeight()+":wg"+image[2].getWidth());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
	}
		return super.onOptionsItemSelected(item);
	}
}
