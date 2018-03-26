package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class UserQRActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageViewQRCode;
    private TextView textViewFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr);

        String email = getIntent().getStringExtra("EMAIL");
        String password = getIntent().getStringExtra("PASSWORD");

        String identifier = email + "," + password;

        imageViewQRCode = (ImageView) findViewById(R.id.imageViewQRCode);
        textViewFinish = (TextView) findViewById(R.id.textViewFinish);

        textViewFinish.setOnClickListener(this);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(identifier, BarcodeFormat.QR_CODE,400,400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        if(v == textViewFinish)
        {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
