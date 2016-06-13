package br.com.interaje.busmap.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rayquaza on 13/06/16.
 */
public class LocationBusMap implements LocationListener {

    private static final String TAG = "LocationBusMap";
    private String provider;
    private LocationManager locationManager;
    private Context mContext;
    private LatLng latLng;

    public LocationBusMap(Context mContext) throws SecurityException {
        this.mContext = mContext;
        locationManager = (LocationManager)
                this.mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        Log.d(TAG, "Localização: " + location.getLatitude() + "/" + location.getLongitude());
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
