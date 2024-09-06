package com.example.womensecurity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegisterGuardian extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhone;
    private Button buttonDisplay;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_guardian);

        editTextName = findViewById(R.id.editTextNamusernameEditText);
        editTextPhone = findViewById(R.id.phoneNumberEditText);
        buttonDisplay = findViewById(R.id.saveButton);

        databaseHelper = new DatabaseHelper(this);

        buttonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();

                // Check if name and phone fields are empty
                if (isEmpty(name) || isEmpty(phone)) {
                    Toast.makeText(RegisterGuardian.this, "Please enter a name and phone number", Toast.LENGTH_SHORT).show();
                    return; // Exit the method without inserting the data
                }

                long rowId = databaseHelper.insertData(name, phone);
                if (rowId != -1) {
                    Toast.makeText(RegisterGuardian.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();

                    ArrayList<String> dataList = databaseHelper.getAllData(); // Retrieve all data from the database

                    Intent intent = new Intent(RegisterGuardian.this, ViewRegisteredGuardian.class);
                    intent.putStringArrayListExtra("dataList", dataList);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterGuardian.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

}