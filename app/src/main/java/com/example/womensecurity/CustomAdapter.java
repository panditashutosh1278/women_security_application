package com.example.womensecurity;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public CustomAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String item = data.get(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextSize(20); // Set the text size to 20dp
        textView.setText(item);


       //TextView indexTextView = convertView.findViewById(android.R.id.text2);
       //indexTextView.setTextSize(12); // Set the index text size
       //indexTextView.setText(String.valueOf(position + 1)); // Index starts from 1

        return convertView;
    }
}

