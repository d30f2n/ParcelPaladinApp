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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    private EditText editTextTracking;
    private Button buttonSave;
    private ListView listViewTracking;
    private TextView textViewNoTracking;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        setContentView(R.layout.activity_tracking);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        editTextTracking = findViewById(R.id.editTextTracking);
        buttonSave = findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonSave.setOnClickListener(this);

        final List<String> usernames = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference trackList = ref.child(user.getUid()).child("trackingNumbers");
        listViewTracking=findViewById(R.id.listViewTracking);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
        listViewTracking.setAdapter(arrayAdapter);

        textViewNoTracking = findViewById(R.id.textViewNoTracking);



        trackList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                usernames.add(value);
                arrayAdapter.notifyDataSetChanged();
                if(dataSnapshot.hasChildren())
                    textViewNoTracking.setVisibility(View.VISIBLE);
                else
                    textViewNoTracking.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                usernames.add(value);
                arrayAdapter.notifyDataSetChanged();
                if(dataSnapshot.hasChildren())
                    textViewNoTracking.setVisibility(View.VISIBLE);
                else
                    textViewNoTracking.setVisibility(View.INVISIBLE);
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

        listViewTracking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String details = listViewTracking.getAdapter().getItem(position).toString();
                String usps = new String("9400");
                if(details.toLowerCase().contains(usps.toLowerCase()))
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://tools.usps.com/go/TrackConfirmAction?tLabels=" + details));
                    startActivity(browserIntent);
                }
                else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://google.com/search?q=" + details));
                    startActivity(browserIntent);
                }

            }
        });

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
        if(v == buttonSave)
        {
            saveTracking();
        }
    }
}
