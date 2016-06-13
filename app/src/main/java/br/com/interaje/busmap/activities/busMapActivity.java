package br.com.interaje.busmap.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.utils.LocationBusMap;

public class busMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 666;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationBusMap myLocation = null;
        try {
            myLocation = new LocationBusMap(this);
        } catch (SecurityException se) {
            if (ContextCompat.checkSelfPermission(busMapActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(busMapActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }

        LatLng sydney = myLocation.getLatLng();
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Theresina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationBusMap myLocation = new LocationBusMap(this);
                } else {
                    // Coloca um toast avisando que precisa da permissão
                }
                return;
            }
        }
    }
}
