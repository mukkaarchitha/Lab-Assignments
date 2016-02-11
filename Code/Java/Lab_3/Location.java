package com.example.architha.lab3ase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Location extendsextends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


public Bitmap bM;

private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
private GoogleMap goomap;
private GoogleApiClient GoogleAPIclient;
private LocationRequest locRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        GoogleAPIclient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
        bM=getIntent().getParcelableExtra("ImageObject");

        locRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(10 * 1000)
        .setFastestInterval(1 * 1000);
            }
        });
    }

@Override
protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        GoogleAPIclient.connect();
        }
@Override
protected void onPause() {
        super.onPause();
        if (GoogleAPIclient.isConnected()) {
        LocationServices.FusedLocationApi.removeLocationUpdates(GoogleAPIclient, this);
        GoogleAPIclient.disconnect();
        }
        }
private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (goomap == null) {
        // Try to obtain the map from the SupportMapFragment.
        goomap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location))
        .getMap();
        // Check if we were successful in obtaining the map.
        if (goomap != null) {
        setUpMap();
        }
        }
        }
private void setUpMap() {
        goomap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }
private void handleNewLocation(Location location) {
        String straddr=markerPosition(location);
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        MarkerOptions options = new MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.fromBitmap(bm))
        .title(straddr);
        CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(currentLatitude, currentLongitude));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
        goomap.addMarker(options);
        goomap.moveCamera(center);
        goomap.animateCamera(zoom);
        }
public String markerPosition(Location location)
        {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        String address = "";
        Geocoder geoCoder= new Geocoder(this, Locale.getDefault());
        try {
        List<Address> addressList = geoCoder
        .getFromLocation(currentLatitude, currentLongitude, 1);
        if (addressList != null) {
        android.location.Address returnedAddress = addressList.get(0);
        StringBuilder returnedAddrress = new StringBuilder("");
        for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
        returnedAddrress
        .append(returnedAddress.getAddressLine(i)).append(
        "\n");
        }
        address = returnedAddrress.toString();
        } else {
        Log.d("Error", "Address null!");
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        return address;
        }
@Override
public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
        }
        Location loc = LocationServices.FusedLocationApi.getLastLocation(GoogleAPIclient);
        if (loc == null) {
        LocationServices.FusedLocationApi.requestLocationUpdates(GoogleAPIclient, locRequest, this);
        }
        else {
        handleNewLocation(loc);
        }
        }

@Override
public void onConnectionSuspended(int i) {

        }

@Override
public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
        try {
        connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (IntentSender.SendIntentException e) {
        e.printStackTrace();
        }
        } else {
        Log.d("Error","Connection failed");
        }
        }
@Override
public void onLocationChanged(Location location) {
        handleNewLocation(location);
        }
}
