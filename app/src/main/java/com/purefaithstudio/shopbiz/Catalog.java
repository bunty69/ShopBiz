package com.purefaithstudio.shopbiz;

import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.graphics.Bitmap;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;
import com.shephertz.app42.paas.sdk.android.shopping.CatalogueService;
import com.shephertz.app42.paas.sdk.android.App42API;

import java.io.InputStream;
import java.net.URL;

import java.util.ArrayList;

public class Catalog extends ActionBarActivity {
	ImageView imgv;
	Bitmap bmp;
	app42Manager apm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog);
		imgv=(ImageView)findViewById(R.id.imageView1);
		apm= new app42Manager(this.getApplicationContext());

		imgv.setImageBitmap(bmp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catalog, menu);
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
