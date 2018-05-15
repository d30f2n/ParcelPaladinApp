package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewSignInButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            //Main activity here
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignInButton = (TextView) findViewById(R.id.textViewSignInButton);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        progressBar = (ProgressBar) findViewById(R.id.progressLogin);

        progressBar.setVisibility(View.INVISIBLE);



        textViewSignInButton.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.user){
//            Toast.makeText(this, "User Activity", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//            startActivity(intent);
//        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting Activity", Toast.LENGTH_SHORT).show();
        }
//        if(item.getItemId() == R.id.login){
//            Toast.makeText(this, "Login Activity", Toast.LENGTH_SHORT).show();
//        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting Activity", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting Activity", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.logout){
            Toast.makeText(this, "Logout Activity", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void userLogin()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //start main activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Your email and/or password is incorrect. Try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v==textViewSignInButton)
        {
            hideSoftKeyboard();
            userLogin();
        }
        if(v == textViewSignUp)
        {
            finish();
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }
}
