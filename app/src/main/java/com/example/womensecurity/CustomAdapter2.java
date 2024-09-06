package com.example.womensecurity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter2 extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> dataList;

    DatabaseHelper databaseHelper;



    public CustomAdapter2(Context context, ArrayList<String> dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView textViewData = convertView.findViewById(R.id.textViewName);
        ImageView imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
        ImageView imageViewCall = convertView.findViewById(R.id.imageViewCall);

        textViewData.setText(data);

        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform delete operation for this item
                String data = dataList.get(position); // Get the data at the clicked position
                String name = data.substring(data.indexOf("Name: ") + 6, data.indexOf("\nPhone: "));
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                int rowsAffected = dbHelper.deleteUser(name);
                if (rowsAffected > 0) {
                    // User deleted successfully
                    dataList.remove(position); // Remove the item from the dataList
                    Toast.makeText(context, "Guardian Deleted !!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged(); // Update the ListView
                } else {
                    // Failed to delete user
                }
            }
        });





        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform call operation for this item
                String phone = data.substring(data.indexOf("\nPhone: ") + 8);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}