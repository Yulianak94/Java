package com.example.vaadapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaadapp.Fragments.CreateBuilding;
import com.example.vaadapp.Fragments.MainFragment;
import com.example.vaadapp.Helpers.CustomRecycleAdapter;
import com.example.vaadapp.Models.Payments;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserPayments extends AppCompatActivity implements CustomRecycleAdapter.OnItemClickListener {
    //Initializing variables
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private ArrayList<Payments> payments;
    private CustomRecycleAdapter adapter;
    String apartmentId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payments);
        mAuth = FirebaseAuth.getInstance();
        apartmentId = getIntent().getStringExtra("apartmentId");

        payments = new ArrayList<>();
        recyclerView = findViewById(R.id.userRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CustomRecycleAdapter(this, payments, this);
        recyclerView.setAdapter(adapter);
        setUpRecyclerView();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        EditText editText = findViewById(R.id.userSearchInput);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void setUpRecyclerView() {
        db.collection("payments").whereEqualTo("apartmentId", apartmentId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    payments.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Payments payment = document.toObject(Payments.class);
                        payments.add(payment);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserPayments.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,MainActivity.class));

    }


    private void filter(String text) {
        ArrayList<Payments> filteredList = new ArrayList<>();
        for (Payments item : payments) {
            if (item.toString().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public void onItemClick(Object item) {
        Log.d("item", "onItemClick: "+ item.toString());
    }

}