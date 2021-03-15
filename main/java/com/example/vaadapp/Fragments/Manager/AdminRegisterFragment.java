package com.example.vaadapp.Fragments.Manager;

import android.content.Context;
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

import com.example.vaadapp.R;



public class AdminRegisterFragment extends Fragment {
    //Initializing variables
    private EditText email,identityNum,firstName,lastName,password, seniorityAdmin;
    private AdminRegisterEvents listener;

    Button singUpAdminBtn;

    public AdminRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AdminRegisterFragment.AdminRegisterEvents) {
            listener = (AdminRegisterFragment.AdminRegisterEvents) context;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_register, container, false);
        email = view.findViewById(R.id.registerEmailAdminInput);
        password = view.findViewById(R.id.registerAdminPassInput);
        firstName = view.findViewById(R.id.firstNameAdminInput);
        lastName = view.findViewById(R.id.lastNameAdminInput);
        identityNum = view.findViewById(R.id.identityNumberAdminInput);
        seniorityAdmin = view.findViewById(R.id.seniorityInput);
        singUpAdminBtn = view.findViewById(R.id.singUpAdminBtn);
        singUpAdminBtn.setBackgroundColor(Color.rgb(52, 52, 52));
        singUpAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                listener.onSingUpAdminPressed(password.getText().toString(), Integer.parseInt(seniorityAdmin.getText().toString()), firstName.getText().toString(), lastName.getText().toString(), identityNum.getText().toString(), email.getText().toString());

                }catch (Exception e){
                    Toast.makeText(getContext(),"the fields cannot be empty",Toast.LENGTH_LONG).show();
                }
            }});
        return view;
    }

    public interface AdminRegisterEvents{
        //Interface for transferring information from fragment to activity
        public void onSingUpAdminPressed(String password, int seniorityAdmin, String firstName, String lastName,String identity, String email);
    }
}

