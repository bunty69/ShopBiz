package com.purefaithstudio.shopbiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

interface OnSignUpListener {
    public void signUpSuccess();

    public void signUpFailure();
}

/**
 * Created by MY System on 10/30/2015.
 */
public class SignUpFragment extends Fragment implements OnSignUpListener {
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("harjas123", "setOnsignUpListener");
            MainActivity.apm.setOnSignUpListener(SignUpFragment.this);
            context.unregisterReceiver(broadcastReceiver);//oh sy
        }
    };
    private View rootView;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailIdEditText;
    private Button submit;
    private String userName;
    private String password;
    private String email;
    private WaitFragment waitFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter("com.purefaithstudio.shopbiz.CUSTOM"));
        if (MainActivity.apm.flag) MainActivity.apm.setOnSignUpListener(SignUpFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_fragment, container, false);
        userNameEditText = (EditText) rootView.findViewById(R.id.username);
        passwordEditText = (EditText) rootView.findViewById(R.id.password);
        emailIdEditText = (EditText) rootView.findViewById(R.id.email);

        submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("harjas123", "Clicked");
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                email = emailIdEditText.getText().toString();
                System.out.println(email);
                if (!userName.equals("") && !password.equals("") && !email.equals("")) {
                    FragmentManager fragmentManager = getFragmentManager();
                    waitFragment = new WaitFragment();
                    waitFragment.show(fragmentManager, "Tag2");
                    MainActivity.apm.register(userName, password, email);
                }
            }
        });

        return rootView;
    }

    @Override
    public void signUpSuccess() {
        Log.i("harjas123", "registered");
        waitFragment.dismiss();
        Intent intent = new Intent(rootView.getContext(), Catalog.class);
        startActivity(intent);
    }

    @Override
    public void signUpFailure() {
        if (waitFragment.isVisible())
            waitFragment.dismiss();
        Snackbar.make(rootView, "SignUp Failed!!", Snackbar.LENGTH_LONG).show();
        Log.i("harjas123", "registration failed");
    }
}