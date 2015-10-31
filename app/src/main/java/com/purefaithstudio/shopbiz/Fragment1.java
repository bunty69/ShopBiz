package com.purefaithstudio.shopbiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

interface authenticationListener {
    public void onAuthenticationSuccess();

    public void onAuthenticationFailure();
}

/**
 * Created by MY System on 10/18/2015.
 */

public class Fragment1 extends Fragment implements View.OnClickListener, authenticationListener {
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.apm.setAuthenticationListener(Fragment1.this);
            context.unregisterReceiver(broadcastReceiver);//oh sy
        }
    };
    private View rootView;
    private CatalogFragment fragment;
    private Button loginButton;
    private Button signupButton;
    private String userName;
    private String passWord;
    private EditText passWordEditText;
    private EditText userEditText;
    private FragmentManager fragmentManager;
    private WaitFragment waitFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter("com.purefaithstudio.shopbiz.CUSTOM"));
    }

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
                if (userName != null && passWord != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    waitFragment = new WaitFragment();
                    waitFragment.show(fragmentManager, "Tag1");
                    login(userName, passWord);
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Fields", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2://signUp
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, new SignUpFragment()).addToBackStack("signup").commit();
                break;
        }
    }

    private void login(String userName, String passWord) {
        if (!userName.equals("") && !passWord.equals(""))
            MainActivity.apm.authenticate(userName, passWord);
        else if (userName != "")
            Log.i("harsim", "username can't be empty");
        else
            Log.i("harsim", "password can't be empty");
    }

    @Override
    public void onAuthenticationSuccess() {
        Log.i("harsim", "authsuccess");
        waitFragment.dismiss();
        Intent intent = new Intent(rootView.getContext(), Catalog.class);
        startActivity(intent);
    }

    @Override
    public void onAuthenticationFailure() {
        waitFragment.dismiss();
        Snackbar.make(rootView, "Login Failed!!", Snackbar.LENGTH_LONG).show();
        Log.i("harsim", "authfail");
        Log.i("harsim", "login Failure");
    }
}
