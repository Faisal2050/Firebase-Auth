package com.ahmadrazamughal.fiebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class Signup extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    EditText editTextEmail,editTextPassword;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);

        buttonSignUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
    }

    public void UserSignUp(){

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

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Intent intent = new Intent(Signup.this,User_profile.class);
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

            case R.id.buttonSignUp:
                UserSignUp();
                break;
            case R.id.textViewLogin:

                Intent intent  = new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}
