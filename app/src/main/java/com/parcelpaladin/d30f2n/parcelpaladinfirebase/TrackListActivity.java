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

import java.util.ArrayList;
import java.util.List;

public class TrackListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView textViewNoTracking;

    ListView lst;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main){
            Toast.makeText(this, "Main Activity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
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
        setContentView(R.layout.activity_track_list);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/trackingNumbers");

        final List<String> usernames = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference mine = ref.child(user.getUid()).child("trackingNumbers");
        lst=(ListView)findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
        lst.setAdapter(arrayAdapter);

        textViewNoTracking = findViewById(R.id.textViewNoTracking);



//        mine.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                Map<String, Object> td = new HashMap<>();
//                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
//                String email = dataSnapshot.getValue().toString();
////                    String email = dataSnapshot.getValue(String.class);
////                    td = (HashMap<String,Object>) dataSnapshot.getValue();
//
//
//                //do what you want with the email
////                Map<String, String> map = dataSnapshot.getValue(Map.class);
////                String email = (String) map.get
//
//                usernames.add(String.valueOf(dataSnapshot.getValue()));
//                }
//
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        mine.addChildEventListener(new ChildEventListener() {
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

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String details = lst.getAdapter().getItem(position).toString();
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
}
