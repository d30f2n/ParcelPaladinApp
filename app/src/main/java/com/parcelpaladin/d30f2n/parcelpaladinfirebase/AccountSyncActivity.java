package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountSyncActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textViewSyncing;
    private TextView textViewSyncSuccess;
    private ImageView imageViewCheckMark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sync);

        progressBar = (ProgressBar) findViewById(R.id.progressBarSynching);

        textViewSyncing = (TextView) findViewById(R.id.textViewSyncing);

        textViewSyncSuccess = (TextView) findViewById(R.id.textViewSyncSuccess);


        imageViewCheckMark = (ImageView) findViewById(R.id.imageViewCheckMark);


        view1();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                syncAccount();
                view2();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(i);
                    }
                }, 3000);
            }
        }, 3000);

    }

    private void view1() {
        textViewSyncSuccess.setVisibility(View.INVISIBLE);
        imageViewCheckMark.setVisibility(View.INVISIBLE);
    }

    private void view2() {
        textViewSyncing.setVisibility((View.INVISIBLE));
        progressBar.setVisibility(View.INVISIBLE);
        textViewSyncSuccess.setVisibility(View.VISIBLE);
        imageViewCheckMark.setVisibility(View.VISIBLE);
    }

    private void syncAccount() {

    }
}