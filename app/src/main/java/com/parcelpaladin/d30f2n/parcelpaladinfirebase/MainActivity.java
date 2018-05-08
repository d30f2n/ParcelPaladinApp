package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewWelcomeName;
    private ImageView imageViewUnlock;
    private ImageView imageViewTracking;
    private ImageView imageViewLogs;

    DatabaseReference databaseReference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.user){
//            Toast.makeText(this, "User Activity", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
//            startActivity(intent);
//        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting Activity", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        final String userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userID + "/Name");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = null;
                if(dataSnapshot.getValue() != null){
                    firstname = dataSnapshot.getValue().toString().split(" ")[0];
                }
                textViewWelcomeName.setText("Welcome " + firstname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imageViewUnlock = findViewById(R.id.imageViewUnlock);
        imageViewTracking = findViewById(R.id.imageViewTracking);
        imageViewLogs = findViewById(R.id.imageViewLogs);
        textViewWelcomeName = findViewById(R.id.textViewWelcomeName);
    }

    @Override
    public void onClick(View v) {
        if(v == imageViewUnlock) {
            Toast.makeText(this, "Box has been unlocked", Toast.LENGTH_SHORT).show();
        }
        if(v == imageViewTracking) {
            Toast.makeText(this, "Add or view your tracking numbers", Toast.LENGTH_SHORT).show();
        }
        if(v == imageViewLogs) {
            Toast.makeText(this, "See when your box was opened", Toast.LENGTH_SHORT).show();
        }
    }
}
