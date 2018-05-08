package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class test extends AppCompatActivity {
    String [ ] months = {"January ", "Feb", "March"};
    private FirebaseAuth firebaseAuth;

    ListView lst;
    String testemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/trackingNumbers");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mine = ref.child("parcelpal-sjsu").child("users").child("-LAUrLPM7BZcG3FenoS2");
        lst=(ListView)findViewById(R.id.listView);

        mine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                //do what you want with the email
                testemail = email;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, testemail);
        lst.setAdapter(arrayAdapter);





    }
}
