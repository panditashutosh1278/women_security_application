package com.example.womensecurity;

import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.womensecurity.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Places API
        Places.initialize(getApplicationContext(), "AIzaSyBc_uGDq7qJCqfehFZ5En3UgeOW8KQGh5A");
        placesClient = Places.createClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

        // Fetch and add nearby hospitals
        fetchNearbyHospitals();
    }

    private void fetchNearbyHospitals() {
        // Define place fields to return
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);

        // Use FindCurrentPlaceRequest for nearby hospitals
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        placesClient.findCurrentPlace(request).addOnSuccessListener((response) -> {
            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                Place place = placeLikelihood.getPlace();
                if (place.getTypes() != null && place.getTypes().contains(Place.Type.HOSPITAL)) {
                    // Add the hospital marker
                    LatLng hospitalLatLng = place.getLatLng();
                    if (hospitalLatLng != null) {
                        mMap.addMarker(new MarkerOptions().position(hospitalLatLng).title(place.getName()));
                    }
                }

            }
        }).addOnFailureListener((exception) -> {
            // Handle exception
        });
    }
}
