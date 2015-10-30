package com.purefaithstudio.shopbiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MY System on 10/30/2015.
 */
public class SignUpFragment extends Fragment implements OnSignUpListener{
    private View rootView;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailIdEditText;
    private Button submit;
    private String userName;
    private String password;
    private String email;

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.apm.setOnSignUpListener(SignUpFragment.this);
            context.unregisterReceiver(broadcastReceiver);//oh sy
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getApplicationContext().registerReceiver(broadcastReceiver,new IntentFilter("com.purefaithstudio.shopbiz.CUSTOM"));
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
                Log.i("harjas123","Clicked");
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                email = emailIdEditText.getText().toString();
                System.out.println(email);
                if(!userName.equals("") && !password.equals("") && !email.equals(""))
                MainActivity.apm.register(userName, password, email);
            }
        });

        return rootView;
    }

    @Override
    public void signUpSuccess() {
        Intent intent = new Intent(rootView.getContext(), Catalog.class);
        startActivity(intent);
        Log.i("harjas123", "registered");
    }

    @Override
    public void signUpFailure() {

        Log.i("harjas123", "registration failed");
    }
}
interface OnSignUpListener
{
    public void signUpSuccess();
    public void signUpFailure();
}