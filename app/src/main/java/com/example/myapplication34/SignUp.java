package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

   private TextView mTosignIn;
    private EditText mName,mEmail,mPassword,mID;
    Button mSignupbtn;
    private ProgressBar mProgressbar;
    private FirebaseAuth fAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = findViewById(R.id.name_userR);
        mEmail = findViewById(R.id.email_userR);
        mPassword = findViewById(R.id.password_userR);
        mSignupbtn = findViewById(R.id.signupbuttonU);
        mTosignIn = findViewById(R.id.switchToSignIn);
        mProgressbar = findViewById(R.id.progressBar2);
        mID= findViewById(R.id.id_userR);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mTosignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        mSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString().trim();
                String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                String Id = mID.getText().toString().trim();


                if(TextUtils.isEmpty(Name))
                {
                    mName.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(Email))
                {
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Email))
                {
                    mID.setError("ID Required");
                    return;
                }

                if(TextUtils.isEmpty(Name))
                {
                    mPassword.setError("Password is Required");
                    return;
                }

                if(mPassword.length() < 6)
                {
                    mPassword.setError("Password must be greater 5 digit");
                    return;
                }
                mProgressbar.setVisibility(View.VISIBLE);

                //Registration of user

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(SignUp.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            users_database user = new users_database(Name,Email,Password,Id);
                            String id = task.getResult().getUser().getUid();
                            DatabaseReference dt = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                            dt.setValue(true);
                            database.getReference().child("Users").child(id).setValue(user);

                            Toast.makeText(SignUp.this, "user added successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }

                        else
                        {
                            Toast.makeText(SignUp.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressbar.setVisibility(View.INVISIBLE);
                        }

                    }
                });



            }
        });


    }
}