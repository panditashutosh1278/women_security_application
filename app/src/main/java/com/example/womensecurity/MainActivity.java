package com.example.womensecurity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView RegisterUser, viewRegistered, RegisterGuardian;
    ImageView SOS, callHelpline, callPolice, CallAmbulance, NearByHospital, TipsWomenSafety, TipsToEscape;
    TextView locationTextView;
    LocationManager locationManager;
    LocationListener locationListener;

    String address;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;

    // MediaPlayer for alarm sound
    private MediaPlayer mediaPlayer;

    // Vibrator for vibration
    private Vibrator vibrator;

    // FOR SOS Implementation
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        // Initialize MediaPlayer for the alarm sound
        mediaPlayer = MediaPlayer.create(this, R.raw.sos_signal_137144);  // Add your MP3 file in res/raw

        // Initialize Vibrator for vibration
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        // Initialization of views
        RegisterUser = findViewById(R.id.RegisterUser);
        viewRegistered = findViewById(R.id.viewRegistered);
        RegisterGuardian = findViewById(R.id.RegisterGuardian);
        callHelpline = findViewById(R.id.callHelpline);
        callPolice = findViewById(R.id.callPolice);
        CallAmbulance = findViewById(R.id.CallAmbulance);
        NearByHospital = findViewById(R.id.NearByHospital);
        TipsWomenSafety = findViewById(R.id.TipsWomenSafety);
        TipsToEscape = findViewById(R.id.TipsToEscape);
        SOS = findViewById(R.id.SOS);
        locationTextView = findViewById(R.id.locationTextView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Initialize LocationListener for getting location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Get the exact address from coordinates
                address = getAddressFromCoordinates(MainActivity.this, location.getLatitude(), location.getLongitude());
                // Update the location TextView with the address
                locationTextView.setText(address);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            startLocationUpdates();
        }

        // SOS button click listener
        SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    // Play the alarm sound
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);  // Loop the alarm sound

                    // Start vibration
                    if (vibrator != null) {
                        vibrator.vibrate(VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE)); // 1 second vibration
                    }

                    // Send SOS message
                    sendSOSMessage();
                } else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                }
            }
        });

        // Other button click listeners
        RegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(MainActivity.this, login.class);
                startActivity(logInIntent);
            }
        });

        RegisterGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegGuardIntent = new Intent(MainActivity.this, RegisterGuardian.class);
                startActivity(RegGuardIntent);
            }
        });

        viewRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ViewRegisterIntent = new Intent(MainActivity.this, ViewRegisteredGuardian.class);
                startActivity(ViewRegisterIntent);
            }
        });

        callHelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:181"));
                startActivity(callIntent);
            }
        });

        CallAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callAmIntent = new Intent(Intent.ACTION_DIAL);
                callAmIntent.setData(Uri.parse("tel:108"));
                startActivity(callAmIntent);
            }
        });

        TipsWomenSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent WmnTips = new Intent(MainActivity.this, SafetyTips.class);
                startActivity(WmnTips);
            }
        });

        TipsToEscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent WmnTipsToEscape = new Intent(MainActivity.this, TipsToEscape.class);
                startActivity(WmnTipsToEscape);
            }
        });

        NearByHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Hospital.class);
                startActivity(intent);
            }
        });

        callPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callPoIntent = new Intent(Intent.ACTION_DIAL);
                callPoIntent.setData(Uri.parse("tel:100"));
                startActivity(callPoIntent);
            }
        });
    }

    private void startLocationUpdates() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
        }
    }

    private String getAddressFromCoordinates(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressText = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }

                addressText = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressText;
    }

    private void sendSOSMessage() {
        String username = login.username1;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("mytable", null, null, null, null, null, null);

        ArrayList<String> guardianNumbers = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String guardianNumber = cursor.getString(cursor.getColumnIndex("phone"));
            guardianNumbers.add(guardianNumber);
        }

        if (guardianNumbers.isEmpty()) {
            Toast.makeText(this, "No guardian numbers found", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        if (address == null || address.isEmpty()) {
            Toast.makeText(this, "Location not available. Please try again.", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            String message = "Please Help Me. My name is " + username + ". Emergency! Need Help Urgent. My Location is: " + address;

            for (String guardianNumber : guardianNumbers) {
                smsManager.sendTextMessage(guardianNumber, null, message, null, null);
            }

            Toast.makeText(this, "SOS message sent to all guardians", Toast.LENGTH_SHORT).show();

            // Stop the alarm after sending the message
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            // Stop vibration after sending the message
            if (vibrator != null) {
                vibrator.cancel();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SOS message", Toast.LENGTH_SHORT).show();
        } finally {
            cursor.close(); // Ensure cursor is closed
        }
    }
}


