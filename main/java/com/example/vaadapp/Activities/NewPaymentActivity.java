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

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vaadapp.Fragments.CreateBuilding;
import com.example.vaadapp.Fragments.MainFragment;
import com.example.vaadapp.Helpers.CustomSpinnerAdapter;
import com.example.vaadapp.Models.Apartment;
import com.example.vaadapp.Models.Payments;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.common.base.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

public class NewPaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Initializing variables
    ArrayList<Apartment> apartments;
    String buildingId;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText month, year, amount;
    String apartmentId;
    ArrayList<String> months;
    Spinner monthSpinner;
    CustomSpinnerAdapter adapter;
    String chosenMonth;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_payment);
        //Defining a monthly array for payment
        mAuth = FirebaseAuth.getInstance();
        months = new ArrayList<>();
        String[] monthsArr = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 0; i < monthsArr.length; i++) {
            String month = monthsArr[i];
            System.out.println("month = " + month);
            months .add(monthsArr[i]);
        }
        buildingId = getIntent().getStringExtra("buildingId");
        apartmentId = getIntent().getStringExtra("apartmentId");
        monthSpinner = findViewById(R.id.monthSpinner);
        monthSpinner.setOnItemSelectedListener(this);
        adapter = new CustomSpinnerAdapter<>(this, R.layout.custom_spinner_view, months);
        monthSpinner.setAdapter(adapter);
        amount = findViewById(R.id.amountInput);
        year = findViewById(R.id.yearInput);
        Button btn = findViewById(R.id.createPaymentBtn);
        btn.setBackgroundColor(Color.rgb(52, 52, 52));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adding payment to tenant
                int amountToInt = Integer.parseInt(amount.getText().toString());
                Payments payment = new Payments(chosenMonth, amountToInt, apartmentId, year.getText().toString());
                db.collection("payments").add(payment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(NewPaymentActivity.this, PaymentsActivity.class);
                            intent.putExtra("apartmentId", apartmentId);
                            startActivity(intent);
                            Toast.makeText(NewPaymentActivity.this, "New Payment has been created", Toast.LENGTH_LONG);
                        } else {
                            Toast.makeText(NewPaymentActivity.this, "Something went wrong please try agian", Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        //View the array of active apartments in the building they paid for
        Intent intent = new Intent(this, PaymentsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ////View the monthly payment schedule of the tenant
        chosenMonth = months.get(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        chosenMonth = months.get(0);
        adapter.notifyDataSetChanged();
    }
}