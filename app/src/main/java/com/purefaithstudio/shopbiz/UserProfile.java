package com.purefaithstudio.shopbiz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.login.LoginManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class UserProfile extends AppCompatActivity {

    private UserSaveData userSaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button logout=(Button)findViewById(R.id.logout);
        setSupportActionBar(toolbar);
        userSaveData = getUserSavedData();
        byte bytes[] = userSaveData.getBytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ImageView imageView = (ImageView) findViewById(R.id.profile_pic);
        imageView.setImageBitmap(bitmap);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                NavUtils.navigateUpFromSameTask(UserProfile.this);
            }
        });
    }

    private UserSaveData getUserSavedData() {
        String FILE_NAME = "USER_DATA.ser";
        UserSaveData userSaveData = null;
        try {
            FileInputStream inputStream = openFileInput(FILE_NAME);
            Log.i("DebugHarjas", "File not found");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            userSaveData = (UserSaveData) objectInputStream.readObject();
            Log.i("DebugHarjas", "getuserComplete");
            if (userSaveData == null) Log.i("DebugHarjas", "Null");
        } catch (IOException e) {
            Log.i("LoguserProfile", e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userSaveData;
    }

}
