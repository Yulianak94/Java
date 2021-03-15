package com.example.vaadapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vaadapp.Fragments.MainFragment;
import com.example.vaadapp.Fragments.Manager.AdminRegisterFragment;
import com.example.vaadapp.Fragments.LoginFragment;
import com.example.vaadapp.Models.Building;
import com.example.vaadapp.Models.Manager;
import com.example.vaadapp.Models.User;
import com.example.vaadapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
// authentication activity
public class AuthActivity extends AppCompatActivity implements LoginFragment.onLoginFragmentBtnSelected ,AdminRegisterFragment.AdminRegisterEvents {
    //Initializing variables
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    CollectionReference usersRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.auth_container, new LoginFragment(), "loginFrag");
        fragmentTransaction.commit();
    }

    // login press in fragment
    @Override
    public void onLoginPressed(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Authentication success.",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onRegisterPressed() {
        Intent userRegisterActivity = new Intent(this, UserRegisterActivity.class);
        startActivity(userRegisterActivity);
    }

    @Override
    public void onRegisterAdminPressed() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.auth_container, new AdminRegisterFragment()).addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onSingUpAdminPressed(String password, int seniority, String firstName, String lastName, String identity, String email) {
        // create a manager
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("successed", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            // Write a message to the database
                            Manager newManager = new Manager(seniority, firstName, lastName, uid, email, identity);
                            db.collection("managers").document(uid).set(newManager)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        // if succes move to login ( new Auth activity hold the login frament first by defualt)
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AuthActivity.this, "success", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AuthActivity.this, AuthActivity.class));
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        System.gc();
        System.exit(0);
    }
}