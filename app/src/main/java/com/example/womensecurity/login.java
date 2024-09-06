package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private static EditText usernameEditText;
    private EditText phoneNumberEditText;
    private Button saveButton;

    private DatabaseHelper2 databaseHelper;
    public static String username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        saveButton = findViewById(R.id.saveButton);

        // Create DatabaseHelper instance
        databaseHelper = new DatabaseHelper2(this);

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  saveUser();
            }
        });
    }

    private void saveUser() {
        username1 = usernameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        // Check if username and phone number fields are empty
        if (username1.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a username and phone number", Toast.LENGTH_SHORT).show();
            return; // Exit the method without saving the user
        }

        // Insert data into the database
        long rowId = databaseHelper.insertUser(username1, phoneNumber);

        // Open DisplayActivity and pass the username
        Intent intent = new Intent(login.this, UsernameDisplayActivity.class);
        intent.putExtra("username", username1);
        startActivity(intent);
    }


    private boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

}
