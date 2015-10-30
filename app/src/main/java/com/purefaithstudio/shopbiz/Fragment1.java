package com.purefaithstudio.shopbiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MY System on 10/18/2015.
 */

public class Fragment1 extends Fragment implements View.OnClickListener {
    private View rootView;
    private CatalogFragment fragment;
    private Button loginButton;
    private Button signupButton;
    private String userName;
    private String passWord;
    private EditText passWordEditText;
    private EditText userEditText;
    private FragmentManager fragmentManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1, container, false);
        userEditText = (EditText) rootView.findViewById(R.id.username);
        passWordEditText = (EditText) rootView.findViewById(R.id.password);
        loginButton = (Button) rootView.findViewById(R.id.button1);
        signupButton = (Button) rootView.findViewById(R.id.button2);
        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button1://login
                userName = userEditText.getText().toString();
                passWord = passWordEditText.getText().toString();
                if(userName!=null && passWord!=null)
                login(userName, passWord);
                else Toast.makeText(getActivity().getApplicationContext(),"Please Enter Fields",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2://signUp
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, new SignUpFragment()).commit();
                break;
        }
    }

    private void login(String userName, String passWord) {
        Boolean isUserTrue = MainActivity.apm.authenticate(userName, passWord);
        Log.i("harjas123", ""+isUserTrue);
        if (isUserTrue) {
            Intent intent = new Intent(rootView.getContext(), Catalog.class);
            startActivity(intent);
        }
    }
}
