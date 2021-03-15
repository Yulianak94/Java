package com.example.vaadapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.vaadapp.Helpers.CustomRecycleAdapter;
import com.example.vaadapp.Models.Casher;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BuilldingCasherActivity extends AppCompatActivity implements CustomRecycleAdapter.OnItemClickListener {
    //Initializing variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> cashers;
    RecyclerView recyclerView;
    CustomRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buillding_casher);
        cashers = new ArrayList<>();
        recyclerView = findViewById(R.id.cahserRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CustomRecycleAdapter(this, cashers, this);
        recyclerView.setAdapter(adapter);
        setUpRecyclerView();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void setUpRecyclerView() {
        //Presentation of monthly income from all apartments
        String[] monthsArr = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        CollectionReference payments  = db.collection("payments");
        for (String month : monthsArr ){
            Query query = payments.whereEqualTo("month", month);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete( Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        long count = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            long amountTotal = document.getLong("amount");
                            count = count + amountTotal;
                        }
                        Casher casher = new Casher(month,count);
                        cashers.add(casher.toString());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(Object item) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}