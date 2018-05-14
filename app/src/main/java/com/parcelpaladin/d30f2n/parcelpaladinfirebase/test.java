package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {
    String [ ] months = {"January ", "Feb", "March"};
    List<String> usernames = new ArrayList<>();

    private FirebaseAuth firebaseAuth;

    ListView lst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/trackingNumbers");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference mine = ref.child("BbprFdYNYtVJG5xbjlXzxExfiU73").child("trackingNumbers");//.child("-LAUrLPM7BZcG3FenoS2");
        lst=(ListView)findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
        lst.setAdapter(arrayAdapter);



        mine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, Object> td = new HashMap<>();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                String email = dataSnapshot.getValue().toString();
//                    String email = dataSnapshot.getValue(String.class);
//                    td = (HashMap<String,Object>) dataSnapshot.getValue();


                //do what you want with the email
//                Map<String, String> map = dataSnapshot.getValue(Map.class);
//                String email = (String) map.get

                usernames.add(email);
                }

//                usernames = new ArrayList<Object>(td.values());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        mine.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
//                String value = childSnapshot.getValue(String.class);
//                usernames.add(value);
////                final TextView textViewUserEmail;
////                textViewUserEmail = (TextView) findViewById(R.id.emailtw);
////                textViewUserEmail.setText("Tracking Info: " + value);
//                }
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//




    }
}
