package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;

    DatabaseReference databaseReference;

    private EditText editTextTracking;
    private Button buttonSave;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.user){
            Toast.makeText(this, "User Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting Activity", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.login){
            Toast.makeText(this, "Login Activity", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.logout){
            Toast.makeText(this, "Logout Activity", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        editTextTracking = (EditText) findViewById(R.id.editTextTracking);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        final TextView textViewUserEmail;
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        final String userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userID + "/Name");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = null;
                if(dataSnapshot.getValue() != null){
                    firstname = dataSnapshot.getValue().toString().split(" ")[0];
                }
                textViewUserEmail.setText("Welcome " + firstname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textViewUserEmail.setText("Welcome " +user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveTracking()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String tracking = editTextTracking.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/trackingNumbers");


        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(tracking);

        Toast.makeText(this, "Tracking number entered.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(v == buttonSave)
        {
            saveTracking();
        }
    }
}
