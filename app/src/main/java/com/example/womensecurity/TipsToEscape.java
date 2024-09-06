package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TipsToEscape extends AppCompatActivity {
    ListView TipsToEscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_to_escape);

        TipsToEscape=findViewById(R.id.TipsToEscape);
        ArrayList<String> TipsToEscapeAr=new ArrayList<>();

        TipsToEscapeAr.add(getString(R.string.TTE1));
        TipsToEscapeAr.add(getString(R.string.TTE2));
        TipsToEscapeAr.add(getString(R.string.TTE3));
        TipsToEscapeAr.add(getString(R.string.TTE4));
        TipsToEscapeAr.add(getString(R.string.TTE5));


        CustomAdapter adapter5 = new CustomAdapter(this,TipsToEscapeAr);
        TipsToEscape.setAdapter(adapter5);
    }
}