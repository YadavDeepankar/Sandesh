package com.help.sandesh.User;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.help.sandesh.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetrieveMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    FirebaseDatabase database;
    DatabaseReference userLocationsRef;
    private boolean isPermission;
    private LocationManager locationManager;
    private LocationManager mLocationManager;
    LatLng cloc;
    String state,Service,PhoneNo;
    Map<String, Marker> mNamedMarkers = new HashMap<String, Marker>();
    public ArrayList<String> rtstype=new ArrayList<String>();
    public ArrayList<String> rtname=new ArrayList<String>();
    public ArrayList<String> rtphno=new ArrayList<String>();
    public ArrayList<String> rtuid=new ArrayList<String>();


    ChildEventListener markerUpdateListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            String key = dataSnapshot.getKey();
//            Log.d(TAG, "Adding location for '" + key + "'");

            Double lng = dataSnapshot.child("Location").child("longitude").getValue(Double.class);
            Double lat = dataSnapshot.child("Location").child("latitude").getValue(Double.class);
            LatLng location = new LatLng(lat, lng);

            Marker marker = mNamedMarkers.get(key);

            if (marker == null) {
                MarkerOptions options = getMarkerOptions(key);
                marker = mMap.addMarker(options.position(location));
                mNamedMarkers.put(key, marker);
            } else {
                // This marker-already-exists section should never be called in this listener's normal use, but is here to handle edge cases quietly.
                // TODO: Confirm if marker title/snippet needs updating.
                marker.setPosition(location);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            String key = dataSnapshot.getKey();
//            Log.d(TAG, "Location for '" + key + "' was updated.");

            Double lng = dataSnapshot.child("Location").child("longitude").getValue(Double.class);
            Double lat = dataSnapshot.child("Location").child("latitude").getValue(Double.class);
            LatLng location = new LatLng(lat, lng);

            Marker marker = mNamedMarkers.get(key);

            if (marker == null) {

                // This null-handling section should never be called in this listener's normal use, but is here to handle edge cases quietly.
//                Log.d(TAG, "Expected existing marker for '" + key + "', but one was not found. Added now.");
                MarkerOptions options = getMarkerOptions(key); // TODO: Read data from database for this marker (e.g. Name, Driver, Vehicle type)
                marker = mMap.addMarker(options.position(location));
                mNamedMarkers.put(key, marker);
            } else {
                // TODO: Confirm if marker title/snippet needs updating.
                marker.setPosition(location);
            }
        }

        /**
         * Removes the marker from its GoogleMap instance
         * @param dataSnapshot  The removed data
         */
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
//            Log.d(TAG, "Location for '" + key + "' was removed.");

            Marker marker = mNamedMarkers.get(key);
            if (marker != null)
                marker.remove();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            // Unused
//            Log.d(TAG, "Priority for '" + dataSnapshot.getKey() "' was changed.");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
//            Log.w(TAG, "markerUpdateListener:onCancelled", databaseError.toException());
            Toast.makeText(RetrieveMapActivity.this, "Failed to load location markers.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_map);

        Bundle bundle = getIntent().getExtras();
        state = bundle.getString("state");
        if (requestSinglePermission()) {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            checkLocation();
        }
    }

    private boolean checkLocation() {

        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();

    }

    private boolean isLocationEnabled() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                    }


                }).check();

        return isPermission;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(20.5937, 78.9629));
        mMap.moveCamera(point);

        database = FirebaseDatabase.getInstance();
        userLocationsRef = database.getReference("DriverAvail").child(state);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        retrievedata(state);
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("DriverAvail" + "/" + state)) {
                    userLocationsRef.addChildEventListener(markerUpdateListener);
                } else {
                    Toast.makeText(RetrieveMapActivity.this, "No services found in " +state.toUpperCase()+ " at this moment\n Please try after some time", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RetrieveMapActivity.this, "NO SERVICE AVAILABLE IN YOUR AREA AT THIS MOMENT, TRY AFTER SOME TIME", Toast.LENGTH_LONG).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            int count = 0;
            @Override
            public boolean onMarkerClick(Marker marker) {
                count = count + 1;

                float[] result = new float[3];
                double distance;
                //speed in meter/sec
                double speedd = 11.11;
                double timme;

                String MarkerId;
                MarkerId = marker.getTitle();

                Location.distanceBetween(cloc.latitude, cloc.longitude,
                        marker.getPosition().latitude, marker.getPosition().longitude, result);
                distance = (double) result[0];
                distance = distance + (distance * 0.25);
                timme = (distance / speedd);

                final AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveMapActivity.this);

                int ppos=rtuid.indexOf(marker.getTitle());

                builder.setTitle(rtstype.get(ppos)).setCancelable(false);
                builder.setMessage(
                        "Distance : " + convertTokm(distance) + "\n" +
                                "Duration : " + convertTohhmm(timme) + "\n\n" +
                                "Name : "+rtname.get(ppos)+"\n"+
                                "Contact : " +rtphno.get(ppos));
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return false;
            }
        });

    }

    public void retrievedata(String alst){
        rtuid.clear();
        rtstype.clear();
        rtname.clear();
        rtphno.clear();
        FirebaseDatabase.getInstance().getReference("DriverAvail").child(alst)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String uuiid=dataSnapshot.getKey();
                        rtuid.add(uuiid);
                        int pos=rtuid.indexOf(uuiid);
                        rtstype.add(pos,(String) dataSnapshot.child("Service").getValue());
                        getdetails(uuiid,pos);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void getdetails(String suid, final int spos) {
        FirebaseDatabase.getInstance().getReference("Driver").child(suid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rtname.add(spos,(String) dataSnapshot.child("FullName").getValue());
                rtphno.add(spos,(String) dataSnapshot.child("Phno").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String convertTohhmm(double timme) {
        int dd,hh,mm,ss;
        dd= (int) (timme/84600);
        timme=timme-(84600*dd);
        hh=(int) (timme/3600);
        timme=timme-(3600*hh);
        mm=(int) (timme/60);
        ss= (int) (timme%60);
        return dd+" D, "+hh+" H, "+mm+" m, "+ss+" s";
    }

    public String convertTokm(double distance) {
        int km= (int) (distance/1000);
        int mtrs= (int) (distance%1000);
        return km+" km and "+mtrs+" m.";
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private MarkerOptions getMarkerOptions(String key) {


        // TODO: Read data from database for the given marker (e.g. Name, Driver, Vehicle type)
        return new MarkerOptions().title(key).icon(BitmapDescriptorFactory.fromResource(R.drawable.helpinghand));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        cloc=latLng;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13F));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
