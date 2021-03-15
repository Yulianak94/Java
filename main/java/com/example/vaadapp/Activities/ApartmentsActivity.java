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
import com.example.vaadapp.Models.Apartment;
import com.example.vaadapp.Models.Manager;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ApartmentsActivity extends AppCompatActivity implements CustomRecycleAdapter.OnItemClickListener {
    //Initializing variables
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    ArrayList<Apartment> apartments;
    CustomRecycleAdapter adapter;
    String bid;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);

        mAuth = FirebaseAuth.getInstance();
        apartments = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CustomRecycleAdapter(this, apartments, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        setUpRecyclerView();

        EditText editText = findViewById(R.id.searchApartmentInput);
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

        db.collection("managers").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists() && documentSnapshot.get("buildingId") != null) {

                        db.collection("building").document(documentSnapshot.get("buildingId").toString()).collection("apartments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                // after getting the data we are calling on success method
                                // and inside this method we are checking if the received
                                // query snapshot is empty or not.
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // if the snapshot is not empty we are hiding our
                                    // progress bar and adding our data in a list.
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    apartments.clear();
                                    for (DocumentSnapshot d : list) {
                                        // after getting this list we are passing that
                                        // list to our object class.
                                        Apartment dataModal = d.toObject(Apartment.class);

                                        // and we will pass this object class
                                        // inside our arraylist which we have
                                        // created for recycler view.
                                        apartments.add(dataModal);
                                    }
                                    // after adding the data to recycler view.
                                    // we are calling recycler view notifyDataSetChanged
                                    // method to notify that data has been changed in recycler view.
                                    adapter.notifyDataSetChanged();
                                } else {
                                    // if the snapshot is empty we are
                                    // displaying a toast message.
                                    Toast.makeText(ApartmentsActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // if we do not get any data or any error we are displaying
                                // a toast message that we do not get any data
                                Toast.makeText(ApartmentsActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(ApartmentsActivity.this, "Please create a building to see apartments", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ApartmentsActivity.this, "Please create a building to see apartments", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

        private void filter (String text){
            ArrayList<Apartment> filteredList = new ArrayList<>();
            for (Apartment item : apartments) {
                if (item.toString().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            adapter.filterList(filteredList);
        }

        @Override
        public void onItemClick (Object item){
            int apartmentIndex = apartments.indexOf(item);
            Apartment apartment = apartments.get(apartmentIndex);
            Intent intent = new Intent(this, PaymentsActivity.class);
            intent.putExtra("apartment", apartment.toString());
            intent.putExtra("buildingId", bid);
            intent.putExtra("apartmentId", apartment.get_id());
            startActivity(intent);
        }

        @Override
        public void onBackPressed () {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
