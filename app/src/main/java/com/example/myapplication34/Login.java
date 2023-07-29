package com.example.myapplication34;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextView mTosignUp;
    private EditText mEmail, mPassword;
    Button mloginbtn;
    private FirebaseAuth fAuth;
    private ProgressBar mprogressbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.email_userL);
        mPassword = findViewById(R.id.password_userL);
        mloginbtn = findViewById(R.id.loginbuttonU);
        mprogressbar1 = findViewById(R.id.progressbar1);
        fAuth = FirebaseAuth.getInstance();


        mTosignUp = findViewById(R.id.switchToSignUp);

        mTosignUp.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SignUp.class)));

        mloginbtn.setOnClickListener(v -> {
            String Email = mEmail.getText().toString().trim();
            String Password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(Email)) {
                mEmail.setError("Email is Required");
                return;
            }
            if (TextUtils.isEmpty(Password)) {
                mPassword.setError("Password is Required");
                return;
            }

            if (mPassword.length() < 6) {
                mPassword.setError("Password must be greater than 5 digit");
                return;
            }
            mprogressbar1.setVisibility(View.VISIBLE);

            //authenticate user
            fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    mprogressbar1.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(Login.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    mprogressbar1.setVisibility(View.INVISIBLE);
                }
            });
        });
    }
}