package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Status extends AppCompatActivity {

    TextView res;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Intent intent = getIntent();
        result = intent.getExtras().getString("puzzle");
//
        final TextView textViewUserEmail;
                textViewUserEmail = (TextView) findViewById(R.id.emailtw);
                textViewUserEmail.setText("Tracking Number: " + result);

    }
}
