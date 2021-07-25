package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openLoginPage(View v){
        Toast.makeText(this, "Opening Login Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,
                activity_login.class);
        startActivity(intent);
    }
    public void openKnowmorePage(View v){
        Toast.makeText(this, "Opening Know More Page", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(this,
                activity_knowmore.class);
        startActivity(intent1);
    }
}