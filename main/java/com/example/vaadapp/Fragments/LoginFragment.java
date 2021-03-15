package com.example.vaadapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaadapp.Activities.AuthActivity;
import com.example.vaadapp.R;


public class LoginFragment extends Fragment {
    private onLoginFragmentBtnSelected listener;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnAdminRegister;
    private EditText loginEmailInput,loginPasswordInput;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onLoginFragmentBtnSelected) {
            listener = (onLoginFragmentBtnSelected) context;
        }else{
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginEmailInput = view.findViewById(R.id.loginEmailInput);
        loginPasswordInput = view.findViewById(R.id.loginPasswordInput);
        btnLogin = view.findViewById(R.id.loginBtn);
        btnRegister = view.findViewById(R.id.registerBtn);
        btnAdminRegister = view.findViewById(R.id.adminRegisterBtn);
        btnLogin.setBackgroundColor(Color.rgb(52,52,52));
        btnRegister.setBackgroundColor(Color.rgb(52,52,52));
        btnAdminRegister.setBackgroundColor(Color.rgb(52,52,52));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRegisterPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                listener.onLoginPressed(loginEmailInput.getText().toString(),loginPasswordInput.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getContext(),"The field can't be empty!",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRegisterAdminPressed();
            }
        });
        return view;
    }

    public interface onLoginFragmentBtnSelected{
        //Interface for transferring information from fragment to activity
        public void onLoginPressed(String email,String password);
        public void onRegisterPressed();
        public void onRegisterAdminPressed();
    }
}