package br.com.interaje.busmap.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.utils.GPSTracker;

public class BusMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 666;
    private static final String TAG = "BusMapActivity";
    private GoogleMap mMap;
    private GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(BusMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BusMapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        setMapAndOptions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            Log.d(TAG, "Passou no OnResume");
            setMapAndOptions();
        }
    }

    private void setMapAndOptions() {
        gpsTracker = new GPSTracker(this);
        startService(new Intent(this, GPSTracker.class));
        LatLng sydney = new LatLng(-53, 119);
        if (gpsTracker.canGetLocation()) {
            Log.d(TAG, "Passou pelo CanGetLocation");
            sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            Log.d(TAG, "" + gpsTracker.getLatitude() + "/" + gpsTracker.getLongitude());
        } else {
            Log.d(TAG, "Passou pelo ShowAlert");
            gpsTracker.showSettingsAlert();
        }
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Theresina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gpsTracker = new GPSTracker(this);
                    startService(new Intent(this, GPSTracker.class));
                } else {
                    // Coloca um toast avisando que precisa da permissão
                }
                return;
            }
        }
    }
}
