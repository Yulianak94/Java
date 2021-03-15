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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vaadapp.Fragments.CreateBuilding;
import com.example.vaadapp.Fragments.MainFragment;
import com.example.vaadapp.Models.Apartment;
import com.example.vaadapp.Models.Building;
import com.example.vaadapp.Models.Payments;
import com.example.vaadapp.Models.User;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CreateBuilding.createBuildingListener{
    //Initializing variables
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

     FragmentManager fragmentManager;
     FragmentTransaction fragmentTransaction;
     FirebaseFirestore db = FirebaseFirestore.getInstance();
     CollectionReference userDb = db.collection("users");
     CollectionReference managerDb = db.collection("managers");
     CollectionReference building = db.collection("building");
     FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        DocumentReference docRef2 = managerDb.document(mAuth.getCurrentUser().getUid());
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Menu menu = navigationView.getMenu();
                        MenuItem target = menu.findItem(R.id.newBuilding);
                        MenuItem cashView = menu.findItem(R.id.casherMenu);
                        target.setVisible(true);
                        cashView.setVisible(true);
                    } else {
                        Menu menu = navigationView.getMenu();
                        MenuItem target = menu.findItem(R.id.newBuilding);
                        MenuItem cashView = menu.findItem(R.id.casherMenu);
                        target.setVisible(false);
                        cashView.setVisible(false);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MainFragment()).addToBackStack(null);;
        fragmentTransaction.commit();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.personal_details){
            //Select an item in the menu
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,new MainFragment()).addToBackStack(null);;
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.payments){
            ////Select an item in the menu
            managerDb.document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Intent intent = new Intent(MainActivity.this, ApartmentsActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
            userDb.document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    //Selecting payment information from the database and presenting it to the selected user
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                                User user = document.toObject(User.class);
                                Intent intent = new Intent(MainActivity.this,UserPayments.class);
                                intent.putExtra("apartmentId",user.getApartmentId().toString());
                                startActivity(intent);
                        }
                    }
                }
            });


        }
        if(item.getItemId() == R.id.newBuilding){
            //Select an item in the menu
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,new CreateBuilding()).addToBackStack(null);;
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.casherMenu){
            //Select an item in the menu
            startActivity(new Intent(MainActivity.this, BuilldingCasherActivity.class));
        }

        return true;
    }

    public void onCreateNewBuildingPressed(int numBuilding, int apartment, String street, String entrance, int myApartment) {
        //Creation of a new building by a user registered as an administrator
        try {
            Building buildingNew = new Building(numBuilding, mAuth.getCurrentUser().getUid(), apartment, entrance, street);

            building.add(buildingNew)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("succ", "DocumentSnapshot added with ID: " + documentReference.getId());
                            String _id = documentReference.getId();
                            building.document(documentReference.getId()).update("_id", _id);
                            db.collection("managers").document(mAuth.getCurrentUser().getUid()).update("buildingId", _id);
                            Apartment apartment1 = new Apartment(myApartment);
                            db.collection("building").document(_id).collection("apartments").add(apartment1).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    DocumentReference doc = task.getResult();
                                    String apartment2 = doc.getId();
                                    doc.update("_id",apartment2);
                                    db.collection("managers").document(mAuth.getCurrentUser().getUid()).update("apartmentId", apartment2);
                            Toast.makeText(MainActivity.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, MainActivity.class));
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new MainFragment())
                                    .addToBackStack(null).commit();

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("fail", "Error adding document", e);
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(this,"Something went wrong, please try agian",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Do You want to logout?")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       startActivity(new Intent(MainActivity.this,AuthActivity.class));
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}