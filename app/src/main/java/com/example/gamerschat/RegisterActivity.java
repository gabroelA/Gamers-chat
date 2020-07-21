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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameText;
    private EditText emailText;
    private EditText passwordText;
    private Button createButton;
    private Button loginButton;

    private FirebaseAuth mAuth;

    // database
    FirebaseDatabase database;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        userNameText = findViewById(R.id.userNameText);
        userNameText.setOnClickListener(this);

        emailText = findViewById(R.id.emailText1);
        emailText.setOnClickListener(this);
        passwordText = findViewById(R.id.passwordText1);
        passwordText.setOnClickListener(this);

        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(this);
        loginButton = findViewById(R.id.loginButton1);
        loginButton.setOnClickListener(this);


        /*
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

            finish();
        }

        */
    }

    @Override
    public void onClick(View view) {

        if(view == loginButton)
            onBackPressed();

        if(view == createButton){
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();
            final String username = userNameText.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                emailText.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)) {
                passwordText.setError("Password is required");
                return;
            }
            if(TextUtils.isEmpty(username)) {
                userNameText.setError("Username is required");
                return;
            }

            if(password.length() < 6){
                passwordText.setError("Password need to be at least 6 characters");
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userId = firebaseUser.getUid();

                        myRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("userId", userId);
                        hashMap.put("userName", username);
                        hashMap.put("imageURL", "default");

                        myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Error, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });




                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Error, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}
