package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    private ListView listViewOpened;
    private ListView listViewFailed;
    private TextView textViewNoTracking;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main){
            startActivity(new Intent(this, MainActivity.class));
        }

        if(item.getItemId() == R.id.logout){
//            Toast.makeText(this, "Logout Activity", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

//        editTextTracking = findViewById(R.id.editTextTracking);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        final List<String> opened = new ArrayList<>();
        final List<String> failed = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference trackList = ref.child(user.getUid()).child("unlockLogs");
        DatabaseReference failedlist = ref.child(user.getUid()).child("failedAttempts");
        listViewOpened=findViewById(R.id.openlw);
        listViewFailed=findViewById(R.id.failedlw);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opened);
        listViewOpened.setAdapter(arrayAdapter);

        final ArrayAdapter<String> failedarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, failed);
        listViewFailed.setAdapter(failedarrayAdapter);

//        textViewNoTracking = findViewById(R.id.textViewNoTracking);



        trackList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                String value = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                String value = dataSnapshot.getValue(String.class).toString();

                opened.add(value);
                arrayAdapter.notifyDataSetChanged();
//                if(dataSnapshot.hasChildren())
//                    textViewNoTracking.setVisibility(View.VISIBLE);
//                else
//                    textViewNoTracking.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                String value = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                String value = dataSnapshot.getValue(String.class).toString();

                opened.add(value);
                arrayAdapter.notifyDataSetChanged();
//                if(dataSnapshot.hasChildren())
//                    textViewNoTracking.setVisibility(View.VISIBLE);
//                else
//                    textViewNoTracking.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        failedlist.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class).toString();

                failed.add(value);
                failedarrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class).toString();

                failed.add(value);
                failedarrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
