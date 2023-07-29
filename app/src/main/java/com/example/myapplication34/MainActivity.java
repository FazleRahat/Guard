package com.example.myapplication34;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    private TextView name, id, enrollment, course;
    private String name1, id1, enroll1, course1;
    private ImageView img;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        enrollment = findViewById(R.id.enrollment);
        course = findViewById(R.id.course);
        img = findViewById(R.id.image);

        String uid = null;


        if (currentUser != null) {
            uid = currentUser.getUid();
        }
        assert uid != null;
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name1 = snapshot.child("name").getValue(String.class);
                    id1 = snapshot.child("id").getValue(String.class);
                    enroll1 = snapshot.child("enrollment").getValue(String.class);
                    course1 = snapshot.child("course1").getValue(String.class);

                    String imagePath = id1 + ".jpg";
                    final Uri[] imageUrl = {null};

                    FirebaseStorage
                            .getInstance().getReference().child(imagePath)
                            .getDownloadUrl()
                            .addOnCompleteListener(task -> {
                                        imageUrl[0] = task.getResult();
                                        try {
                                            Glide.with(MainActivity.this)
                                                    .load(imageUrl[0])
                                                    .apply(new RequestOptions()
                                                            .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                                                            .skipMemoryCache(true) // Skip memory caching
                                                    )
                                                    .into(img);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                            );

                    name.setText(name.getText() + " " + name1);
                    id.setText(id.getText() + " " + id1);
                    enrollment.setText(enrollment.getText() + " " + enroll1);
                    course.setText(course.getText() + " " + course1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });
    }

    //}

    //@Override
    //public void onCancelled(@NonNull DatabaseError error) {

}
//});


    /*}
    //private void retrieveImageFromStorage() {

    //}
}*/