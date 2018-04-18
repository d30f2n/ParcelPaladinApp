package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewRegister;
    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private TextView textViewSignIn;
    private ProgressBar progressBar;

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            //user already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);

        progressBar = (ProgressBar) findViewById(R.id.progressRegister);
        progressBar.setVisibility(View.INVISIBLE);

        textViewRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void fakeregister() {
        final String email = "testing@test.com";
        final String password = "testing";
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), UserQRActivity.class);
                            intent.putExtra("EMAIL", email);
                            intent.putExtra("PASSWORD", password);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void registerUser()
    {
        final String name = editTextFullName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String passwordConfirm = editTextConfirmPassword.getText().toString().trim();

        if(!password.equals(passwordConfirm))
        {
            Toast.makeText(this, "Your passwords don't match.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this, "Please create a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.getTrimmedLength(password)<6)
        {
            Toast.makeText(this, "Your password is too short", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference("users");
                            mDatabase.child(user.getUid());
                            UserInformation userInformation = new UserInformation(name);
                            mDatabase = FirebaseDatabase.getInstance().getReference("users/"+user.getUid());
                            mDatabase.child("Name").setValue(name);
                            mDatabase.child("Email").setValue(user.getEmail());

                            mDatabase = FirebaseDatabase.getInstance().getReference("users/"+user.getUid()+"/trackingNumbers");
                            String id = mDatabase.push().getKey();
                            mDatabase.child(id).setValue(user.getUid());

                            finish();

                            Intent intent = new Intent(getApplicationContext(), UserQRActivity.class);
                            intent.putExtra("EMAIL", email);
                            intent.putExtra("PASSWORD", password);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view==textViewRegister)
        {
            //SWITCH THESE FUNCTIONS FOR TESTING
            registerUser();
//            fakeregister();
        }
        if(view==textViewSignIn)
        {
            //Will open login activity here
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
