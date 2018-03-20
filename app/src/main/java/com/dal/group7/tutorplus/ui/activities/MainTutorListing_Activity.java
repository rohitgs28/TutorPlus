
package com.dal.group7.tutorplus.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.LocationModel;
import com.dal.group7.tutorplus.model.TutorModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainTutorListing_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    //public Button b1;
    private TextView txtOutputLatitude, txtOutputLongitude, mCurrentAddress;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private String lat, lon;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static String add = "";
    SearchView simpleSearchView;
    LocationModel ob=new LocationModel();
    EditText studentlocshow;
    LocationManager locationManager;
    ImageView navlogo;
    public String message1;
    DBhelper dbHelper = new DBhelper(MainTutorListing_Activity.this);
    ArrayList<TutorModel> listofTutors = new ArrayList<>();
    GoogleLogin_Activity logindetails = new GoogleLogin_Activity();
    String email = logindetails.personEmail;
    String user_name = logindetails.personName;
    String role_choose = logindetails.role;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_listing);
        Toast.makeText(this,email+" "+user_name+" "+role_choose,Toast.LENGTH_SHORT).show();

        ListView list= (ListView) findViewById(R.id.listView1);
        ArrayList<String> category=new ArrayList<>();
        category.add("Science");
        category.add("Maths");
        category.add("Geography");
        category.add("Chemistry");
        category.add("Physics");

        studentlocshow = (EditText) findViewById(R.id.studentlocshow);
        navlogo = (ImageView) findViewById(R.id.navlogo);
        navlogo.setImageResource(R.drawable.nav);
        //simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView);

        checkLocationPermission();

     if(!isLocationEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainTutorListing_Activity.this);
            builder.setTitle("GPS is Not Enabled")
                    .setMessage("Turn on?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }


       listofTutors = dbHelper.fetchListTutorDetails(dbHelper.getWritableDatabase());


        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,category);
        list.setAdapter(adapter);
        //ListView lv = getListView();
        //setContentView(lv);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {


                if (position == 0) {
                    message1="Science";
                    Intent intent = new Intent(MainTutorListing_Activity.this, DisplayTutors.class);
                    intent.putExtra("message", message1);
                    startActivity(intent);
                }
                if (position == 1) {
                    String message2="Maths";
                    Intent intent = new Intent(MainTutorListing_Activity.this, DisplayTutors.class);
                    intent.putExtra("message", message2);
                    startActivity(intent);
                }
                if (position == 2) {
                    String message3="Geography";
                    Intent intent = new Intent(MainTutorListing_Activity.this, DisplayTutors.class);
                    intent.putExtra("message", message3);
                    startActivity(intent);
                }
                if (position == 3) {
                    String message4="Chemistry";
                    Intent intent = new Intent(MainTutorListing_Activity.this, DisplayTutors.class);
                    intent.putExtra("message", message4);
                    startActivity(intent);
                }
                if (position == 4) {
                    String message4="Physics";
                    Intent intent = new Intent(MainTutorListing_Activity.this, DisplayTutors.class);
                    intent.putExtra("message", message4);
                    startActivity(intent);
                }
            }
        });

    }

    protected boolean isLocationEnabled(){
        String le = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(le);
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            return false;
        } else {
            return true;
        }
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

                // Success Stuff here
                buildGoogleApiClient();
            } else {
                // Failure Stuff
            }
        }

    }


    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setInterval(10000); // Update location every ten seconds

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

        }
        //updateUI();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());

        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        getAddress(latitude,longitude);
        //updateUI();


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

    //void updateUI() {
       /*txtOutputLatitude.setText("Latitude --> " + lat);
        txtOutputLongitude.setText("Longitude --> " +lon);
        mCurrentAddress.setText("Current Address --> " + add);*/
    //tvlocation.setText("Current Loc:" +add);

    //}

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size()>0) {
                Address obj = addresses.get(0);
                //add = obj.getAddressLine(0);
                //add = add + "\n" + obj.getCountryName();
                //add = add + "\n" + obj.getCountryCode();
                //add = add + "\n" + obj.getAdminArea();
                //add = add + "\n" + obj.getPostalCode();
                add = obj.getLocality();
                //add=obj.getLocality();
                Toast.makeText(this,"Current Location Set", Toast.LENGTH_SHORT).show();
                studentlocshow.setText("Searching tutors in: "+add);


            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;

    }
}


