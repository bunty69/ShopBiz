package com.purefaithstudio.shopbiz;

import android.content.Intent;
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
public class SignUpFragment extends Fragment {
    private View rootView;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailIdEditText;
    private Button submit;
    private String userName;
    private String password;
    private String email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_fragment, container, false);
        userNameEditText = (EditText) rootView.findViewById(R.id.username);
        passwordEditText = (EditText) rootView.findViewById(R.id.username);
        emailIdEditText = (EditText) rootView.findViewById(R.id.username);
        submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("harjas123","Clicked");
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                email = emailIdEditText.getText().toString();
                Boolean registered = MainActivity.apm.register(userName, password, email);
                Log.i("harjas123",""+registered);
                if (registered) {
                    Intent intent = new Intent(rootView.getContext(), Catalog.class);
                    startActivity(intent);
                    Toast.makeText(getActivity().getApplicationContext(),"Success Registered!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
