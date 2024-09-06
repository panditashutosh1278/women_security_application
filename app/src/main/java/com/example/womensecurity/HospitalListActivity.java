package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;




public class HospitalListActivity extends AppCompatActivity {
    private ListView hospitalListView;
    private String[] hospitalNames;
    private String[] hospitalAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        hospitalListView = findViewById(R.id.hospitalListView);

        // Retrieve the selected city from the intent
        String selectedCity = getIntent().getStringExtra("selectedCity");

        // Set up the list of hospitals based on the selected city
        if (selectedCity.equals("Surat")) {
            hospitalNames = new String[]{"Hospital 1", "Hospital 2", "Hospital 3"};
            hospitalAddresses = new String[]{"Address 1", "Address 2", "Address 3"};
        } else if (selectedCity.equals("Baroda")) {
            hospitalNames = new String[]{"Hospital A", "Hospital B", "Hospital C"};
            hospitalAddresses = new String[]{"Address A", "Address B", "Address C"};
        } else if (selectedCity.equals("Bharuch")) {
            hospitalNames = new String[]{"Hospital X", "Hospital Y", "Hospital Z"};
            hospitalAddresses = new String[]{"Address X", "Address Y", "Address Z"};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hospitalNames);
        hospitalListView.setAdapter(adapter);

        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedHospitalName = hospitalNames[position];
                String selectedHospitalAddress = hospitalAddresses[position];

                // Handle the selected hospital (e.g., start a new activity, perform an action)
                Toast.makeText(HospitalListActivity.this, "Selected: " + selectedHospitalName + "\nAddress: " + selectedHospitalAddress, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
