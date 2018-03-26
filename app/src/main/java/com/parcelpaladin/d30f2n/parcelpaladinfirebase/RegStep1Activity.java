package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegStep1Activity extends AppCompatActivity {

    private EditText editTextID;
    private TextView textViewStart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_step1);
    }
}
