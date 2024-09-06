package com.example.womensecurity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewRegisteredGuardian extends AppCompatActivity {

    private ListView listViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_guardian);

        listViewData = findViewById(R.id.listViewData);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<String> dataList = databaseHelper.getAllData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewData.setAdapter(adapter);
        CustomAdapter2 adapter1 = new CustomAdapter2(this, dataList);
        listViewData.setAdapter(adapter1);






    }

}