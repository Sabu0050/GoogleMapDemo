package com.sabu.googlemapdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //public Places[] places = new Places[5];
    LocationManager locationManager;
    LocationListener locationListener;
    public double lat,lon;

    public void startListening(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        }

    }
    public void updateLocation(Location location){
        Log.i("My Lovation Info",location.toString());
        /*latituteText = (TextView) findViewById(R.id.latituteTextView);
        longtituteText = (TextView) findViewById(R.id.longtituteTextView4);
        latituteText.setText(""+location.getLatitude());
        longtituteText.setText(""+location.getLongitude());*/
        lat = location.getLatitude();
        lon = location.getLongitude();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*places[0] = new Places("Shonir Akhra",23.7077233,90.4462659);
        places[1] = new Places("Botanical Garden",23.7171484,90.3490898);
        places[2] = new Places("Ahsan Monjil",23.7085661,90.4038006);
        places[3] = new Places("Lalbagh Fort",23.719546,90.3859046);
        places[4] = new Places("Baldha Garden",23.7170022,90.417119);*/

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if(Build.VERSION.SDK_INT < 22){
            startListening();
        }else {
            if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }else{
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(location != null) {

                    updateLocation(location);
                }

            }
        }

    }

    /*
    Shonir Akhra - 23.7077233,90.4462659
    Botanical Garden - 23.7171484,90.3490898
    Ahsan Monjil  23.7085661,90.4038006
    Lalbagh Fort - 23.719546,90.3859046
    Baldha Garden - 23.7170022,90.417119*/

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
        // Add a marker in Sydney and move the camera


        /*for(Places aPlace: this.places){

            LatLng myLoc = new LatLng(aPlace.latitute,aPlace.longtitute);
            mMap.addMarker(new MarkerOptions().position(myLoc).title(aPlace.name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,13));
        }*/

        LatLng ourPlace = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(ourPlace).title("Marker in Our Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ourPlace,15));
    }
}
