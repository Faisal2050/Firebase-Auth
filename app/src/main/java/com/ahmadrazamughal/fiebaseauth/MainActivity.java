package com.ahmadrazamughal.fiebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    EditText editTextEmail,editTextPassword;
    Button buttonLogin;
    TextView textViewSignup;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

        buttonLogin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);

    }

    public void userLogin(){

            progressBar.setVisibility(View.VISIBLE);
        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();

        if(email.isEmpty()){
            editTextEmail.setError("Email is Emp");
            editTextEmail.requestFocus();
            return;

        }
        if(pass.isEmpty()){
            editTextPassword.setError("Password is Empty");
            editTextPassword.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            editTextEmail.setError("EMail Did Not Match");
            editTextEmail.requestFocus();
            return;
        }


        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Intent intent = new Intent(MainActivity.this,User_profile.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonLogin:
                userLogin();
                break;
            case R.id.textViewSignup:

                Intent intent  = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
                break;

        }

    }
}
