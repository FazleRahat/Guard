package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication34.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private TextView name,id,enrollment,course;
    private String name1, id1, enroll1, course1;
    private ImageView img;

    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    FirebaseUser currentUser= firebaseAuth.getCurrentUser();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference reference= database.getReference("Users");

    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    name=findViewById(R.id.name);
    id= findViewById(R.id.id);
    enrollment= findViewById(R.id.enrollment);
    course= findViewById(R.id.course);
    img= findViewById(R.id.image);
    Context context=this;

        String uid=null;

     storageReference= FirebaseStorage.getInstance().getReference();

    if (currentUser!=null){
         uid= currentUser.getUid();
    }
    reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                name1= snapshot.child("name").getValue(String.class);
                id1= snapshot.child("id").getValue(String.class);
                enroll1= snapshot.child("enrollment").getValue(String.class);
                course1= snapshot.child("course1").getValue(String.class);

                String imgname= id1+".jpg";

                String imgurl= "gs://guard-d2545.appspot.com/"+imgname;

                Glide.with(context).load(imgurl).apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(img);

                // id1= snapshot.child("id").getValue(String.class);
               // enrollment1= snapshot.child("enrollment").getValue(String.class);


                name.setText(name1);
                id.setText(id1);
                enrollment.setText(enroll1);
                course.setText(course1);

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


    }
}