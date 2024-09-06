package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ListView;

import java.util.ArrayList;

public class SafetyTips extends AppCompatActivity {
    ListView safetyTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);

        safetyTips=findViewById(R.id.safetyTips);


        ArrayList<String> Tips=new ArrayList<>();

        Tips.add(getString(R.string.tips1));
        Tips.add(getString(R.string.tips2));
        Tips.add(getString(R.string.tip3));
        Tips.add(getString(R.string.tip4));
        Tips.add(getString(R.string.tip5));
        Tips.add(getString(R.string.tip6));
        Tips.add(getString(R.string.tip7));

        CustomAdapter adapter = new CustomAdapter(this,Tips);
        safetyTips.setAdapter(adapter);

    }
}