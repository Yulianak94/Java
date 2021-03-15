package com.example.vaadapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vaadapp.Models.Apartment;
import com.example.vaadapp.Models.Manager;
import com.example.vaadapp.Models.User;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class MainFragment extends Fragment {
    //Displays user information
    FirebaseAuth mAuth;
    TextView userText,apartmentText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userDb = db.collection("users");
    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        userText = view.findViewById(R.id.userText);
        userText.setText("Wellcome to Vaad App");
        return view;
    }
}