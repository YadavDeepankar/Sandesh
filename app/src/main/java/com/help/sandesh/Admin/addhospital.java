package com.help.sandesh.Admin;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.Manifest;
import android.content.pm.PackageManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.help.sandesh.User.LocationHelper;
import com.help.sandesh.R;
import com.help.sandesh.User.hospHelper;

import java.io.IOException;
import java.util.List;

public class addhospital extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public LatLng mkloc;
    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhospital);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("CovidLabs");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
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
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void searchLocation(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText1);
        EditText loccity = (EditText) findViewById(R.id.editText2);
        String location = locationSearch.getText().toString().trim();
        String locationcity = loccity.getText().toString().trim();
        List<Address> addressList = null;

        if (location != null || !location.equals("")||locationcity != null || !locationcity.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location+" "+locationcity, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            mMap.clear();

            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(location).draggable(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap.addCircle(new CircleOptions().center(latLng).radius(1000).strokeWidth(5).clickable(false).visible(true));
            mkloc=new LatLng(address.getLatitude(),address.getLongitude());
//            Toast.makeText(getApplicationContext(),locationSearch +"\n"+mkloc.latitude+" "+mkloc.longitude,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "location does not exists", Toast.LENGTH_LONG).show();
            startActivity(new Intent(addhospital.this,addhospital.class));
        }
    }

    public void addtofirebase(View v) {

        final EditText locname = (EditText) findViewById(R.id.editText1);
        final EditText loccity = (EditText) findViewById(R.id.editText2);
        final EditText locdesc = (EditText) findViewById(R.id.editText3);
        final EditText locphno = (EditText) findViewById(R.id.editText4);
        final String lname = locname.getText().toString();
        final String lcity = loccity.getText().toString();
        final String ldesc = locdesc.getText().toString().trim();
        final String lphno = locphno.getText().toString();
        final String luid = databaseReference.push().getKey();

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                Toast.makeText(addhospital.this, "marker drag started", Toast.LENGTH_SHORT).show();
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                Toast.makeText(addhospital.this, "marker drag ended", Toast.LENGTH_SHORT).show();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                mkloc=new LatLng(arg0.getPosition().latitude,arg0.getPosition().longitude);
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
            }
        });

        Double stlng=mkloc.longitude;
        Double stlat=mkloc.latitude;
        LocationHelper st=new LocationHelper(stlng,stlat);

        if (checkempty(lname,lcity,ldesc,lphno))
        {
            hospHelper hh=new hospHelper(lname,lcity,getsStatename(lname,lcity),ldesc,lphno,st);
            databaseReference.child(luid).setValue(hh).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    locname.setText("");
                    loccity.setText("");
                    locdesc.setText("");
                    locphno.setText("");
                    mMap.clear();
                    Toast.makeText(addhospital.this, "hospital"+" inserted ", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else Toast.makeText(addhospital.this, "Some data is missing", Toast.LENGTH_SHORT).show();
    }

    private boolean checkempty(String lname, String lcity, String ldesc, String lphno) {
        if (!lname.isEmpty()&&!lcity.isEmpty()&&!ldesc.isEmpty()&&!lphno.isEmpty()&&lphno.length()==10)
        {
            return true;
        }
        else return false;
    }

    public String getsStatename(String llname,String llcity){
        List<Address> addressList = null;
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(llname+" "+llcity, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            return address.getAdminArea();
    }

}