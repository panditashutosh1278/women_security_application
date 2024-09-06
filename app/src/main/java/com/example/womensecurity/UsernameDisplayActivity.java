package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UsernameDisplayActivity extends AppCompatActivity {
    TextView username4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_display);
        username4 = findViewById(R.id.username4);

        Intent intent = getIntent();
        String username1 = intent.getStringExtra("username");
        username4.setText("Hello "+username1);
    }
}