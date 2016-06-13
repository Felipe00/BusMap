package br.com.interaje.busmap.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rayquaza on 13/06/16.
 */
public class LocationBusMap implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationBusMap";
    private Context mContext;
    private LatLng latLng;
    private GoogleApiClient mGoogleApiClient;

    public LocationBusMap(Context mContext) throws SecurityException {
        this.mContext = mContext;
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mGoogleApiClient.connect();
            }
        });
        t.start();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            try {
                ProgressDialog dialog = new ProgressDialog(mContext);
                dialog.setTitle("Aguarde");
                dialog.show();
                Thread.sleep(3000L);
                dialog.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        } catch (SecurityException se) {
            // Avise aqui q o GPS não consegue pegar a Posição
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

}
