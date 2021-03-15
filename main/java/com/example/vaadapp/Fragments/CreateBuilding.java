package com.example.vaadapp.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CreateBuilding extends Fragment {
    //Initializing variables
    Button btnSaveBuilding;
    EditText numBuilding, numMaxApartment, street, entrance,myApartment;
    private createBuildingListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference building = db.collection("building");



    public CreateBuilding() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreateBuilding.createBuildingListener) {
            listener = (CreateBuilding.createBuildingListener) context;
        }else{
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_building, container, false);

        btnSaveBuilding = view.findViewById(R.id.saveBuilding);
        numBuilding = view.findViewById(R.id.buildingNum);
        numMaxApartment = view.findViewById(R.id.MAXapartmentNumber);
        street = view.findViewById(R.id.address);
        entrance = view.findViewById(R.id.enetery);
        myApartment = view.findViewById(R.id.myApartmentInput);
        btnSaveBuilding.setBackgroundColor(Color.rgb(50,50,50));
        btnSaveBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                listener.onCreateNewBuildingPressed(Integer.parseInt(numBuilding.getText().toString()), Integer.parseInt(numMaxApartment.getText().toString()), street.getText().toString(), entrance.getText().toString(),Integer.parseInt(myApartment.getText().toString()));
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"Something went wrong please try again",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public interface createBuildingListener{
        //Interface for transferring information from fragment to activity
        public void onCreateNewBuildingPressed(int numBuilding, int apartment, String street, String entrance,int myApartment);
    }


}


