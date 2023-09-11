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

    //***************************************** declaration ***************************************
    private TextView name, id, enrollment, course, GPA, credit, week, classes, balance;
    private String name1;
    private String id1;
    private String enroll1;
    private String course1;
    private String result;
    private String ecredits;
    private String weekatt;
    private String Rclass;
    private String fee;
    private ImageView img;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //******************************** find id *********************************

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        enrollment = findViewById(R.id.enrollment);
        course = findViewById(R.id.course);
        GPA= findViewById(R.id.res);
        credit= findViewById(R.id.credits);
        week= findViewById(R.id.week);
        classes= findViewById(R.id.classes);
        balance= findViewById(R.id.bal);

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

                    //************************** data retrieval ***************************

                    name1 = snapshot.child("name").getValue(String.class);
                    id1 = snapshot.child("id").getValue(String.class);
                    enroll1 = snapshot.child("enrollment").getValue(String.class);
                    course1 = snapshot.child("course1").getValue(String.class);
                    result = snapshot.child("result").getValue(String.class);
                    ecredits = snapshot.child("credits").getValue(String.class);
                    weekatt= snapshot.child("weekly").getValue(String.class);
                    Rclass= snapshot.child("schedule").getValue(String.class);
                    fee= snapshot.child("fees").getValue(String.class);

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
                    GPA.setText(GPA.getText() + " " + result);
                    credit.setText(credit.getText() + " " + ecredits);
                    week.setText(week.getText()+ " " +weekatt);
                    classes.setText(classes.getText()+ " " +Rclass);
                    balance.setText(balance.getText()+ " " +fee);
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