package com.example.gamerschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailText;
    private EditText passwordText;
    private Button registerButton;
    private Button loginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        emailText.setOnClickListener(this);
        passwordText = findViewById(R.id.passwordText);
        passwordText.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);


        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

            finish();
        }


    }


    @Override
    public void onClick(View view) {

        if(registerButton == view)
            openRegisterActivity();

        if(view == loginButton){
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                emailText.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)) {
                passwordText.setError("Password is required");
                return;
            }
            if(password.length() < 6){
                passwordText.setError("Password need to be at least 6 characters");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Error, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
