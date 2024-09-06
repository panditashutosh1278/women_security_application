package com.example.womensecurity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Hospital extends AppCompatActivity {
    private ListView hospitalListView;
    private String[] options = {"Surat", "Baroda", "Bharuch"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_hospital);

        hospitalListView= findViewById(R.id.hospitalListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        hospitalListView.setAdapter(adapter);

        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = options[position];

                Intent intent = new Intent(Hospital.this, HospitalListActivity.class);
                intent.putExtra("selectedCity", selectedCity);
                startActivity(intent);
            }
        });
    }
}
