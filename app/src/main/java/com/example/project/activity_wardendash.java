package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class activity_wardendash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardendash);
    }
    public void updateNoticeBoard(View v){
        Toast.makeText(this, "Opening Update Notice Board", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(this,
                activity_updatenotice.class);
        startActivity(intent1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "You've successfully logged out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),activity_login.class));
        finish();
    }
}