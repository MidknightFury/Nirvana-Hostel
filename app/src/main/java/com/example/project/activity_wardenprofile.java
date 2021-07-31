package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_wardenprofile extends AppCompatActivity {

    TextView sFname, sLname, sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardenprofile);

        sFname = findViewById(R.id.firstName);
        sLname = findViewById(R.id.lastName);
        sEmail= findViewById(R.id.emailID);

        FirebaseUser sUser = FirebaseAuth.getInstance().getCurrentUser();
        String studentEmail = sUser.getEmail();
        sEmail.setText(studentEmail);

        String userID = sUser.getUid();

        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("userID").child(userID);
        mFirebaseDatabase.child("First name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                sFname.setText(String.valueOf(task.getResult().getValue()));
            }
        });
        mFirebaseDatabase.child("Last name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                sLname.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }
}