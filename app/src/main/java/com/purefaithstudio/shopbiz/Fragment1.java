package com.purefaithstudio.shopbiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
    private LoginButton loginButtonFacebook;
    private CallbackManager callbackManager;
    private AccessToken access;
    private AccessTokenSource accesstokensource;
    private boolean loggedIn;

    public static void printHashKey(Context pContext) {
        try {
            PackageManager packageManager = pContext.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo("com.purefaithstudio.shopbiz", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Tag", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Tag", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Tag", "printHashKey()", e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printHashKey(getActivity().getApplicationContext());
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        getActivity().getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter("com.purefaithstudio.shopbiz.CUSTOM"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1, container, false);
        userEditText = (EditText) rootView.findViewById(R.id.username);
        passWordEditText = (EditText) rootView.findViewById(R.id.password);
        loginButton = (Button) rootView.findViewById(R.id.button1);
        signupButton = (Button) rootView.findViewById(R.id.button2);
        loginButtonFacebook = (LoginButton) rootView.findViewById(R.id.login_button_facebook);
        loginButtonFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButtonFacebook.setFragment(this);
        loggedIn=checkLoginFacebook();
        if(loggedIn) startUserHomeScreen();
        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                access = loginResult.getAccessToken();
                try {
                    Profile.fetchProfileForCurrentAccessToken();
                    //use this to fetch data of user
                    Profile profile = Profile.getCurrentProfile();
                    String username = profile.getFirstName();
                    startUserHomeScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return rootView;
    }

    private boolean checkLoginFacebook() {
        boolean loggedIn=false;
        if(AccessToken.getCurrentAccessToken()!=null)
            loggedIn=true;
    return loggedIn;
    }

    private void startUserHomeScreen() {
        Intent intent = new Intent(rootView.getContext(), Catalog.class);
        startActivity(intent);
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
        startUserHomeScreen();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAuthenticationFailure() {
        waitFragment.dismiss();
        Snackbar.make(rootView, "Login Failed!!", Snackbar.LENGTH_LONG).show();
        Log.i("harsim", "authfail");
        Log.i("harsim", "login Failure");
    }
}
