package com.dal.group7.tutorplus.ui.activities;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.LocationModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Gps_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private TextView txtOutputLatitude, txtOutputLongitude, mCurrentAddress;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private String lat, lon;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static String add = "";
    SearchView simpleSearchView;
    LocationModel ob = new LocationModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_layout);
        checkLocationPermission();
    }

    private void checkLocationPermission() {

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{mPermission}, REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            } else {
                buildGoogleApiClient();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == MockPackageManager.PERMISSION_GRANTED) {

         /*
          *  Continue action when user grants permission
          * */
                buildGoogleApiClient();
            } else {
         /*
          *  On Failure
          * */

            }
        }

    }


    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

         /*
          *  Setting the location of the user
          * */
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());

        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        getAddress(latitude, longitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size() > 0) {
                Address obj = addresses.get(0);
                add = add + "\n" + obj.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }
}
