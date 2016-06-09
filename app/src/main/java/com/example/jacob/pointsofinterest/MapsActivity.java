package com.example.jacob.pointsofinterest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;

import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    String message;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Location cLocation;
    LatLng cLatLng;
    LatLng mLatLng;
    Marker cMarker;
    Context mContext;

    //markers
    LatLng lwit;
    LatLng spaceNeedle;
    LatLng pacificScienceCenter;
    LatLng empMuseum;
    LatLng centuryLinkField;
    LatLng woodlandParkZoo;
    LatLng fremontTroll;
    LatLng safecoField;
    LatLng internationalDistrict;
    LatLng spookedInSeattle;
    LatLng undergroundTour;
    LatLng ferrisWheel;
    LatLng pikesPlace;
    LatLng gumWall;
    LatLng seattleLibrary;
    LatLng paramountTheatre;
    LatLng fifthAvenueTheatre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        message = intent.getStringExtra("cameraStart");
        mContext = getApplicationContext();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

        fillMarkers();

        Double lat = Double.parseDouble(message.substring(0, 9));
        Double lng = Double.parseDouble(message.substring(10, message.length() - 1));
        LatLng startingCamera = new LatLng(lat, lng);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingCamera, 16));


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                cMarker = marker;
                cLatLng = marker.getPosition();
                cLocation = new Location(LocationManager.GPS_PROVIDER);
                cLocation.setLatitude(cLatLng.latitude);
                cLocation.setLongitude(cLatLng.longitude);
            }
        });
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
            @Override
        public boolean onMyLocationButtonClick() {
                if (cLocation != null && mLastLocation != null && cMarker != null) {
                    if (mLastLocation.distanceTo(cLocation) < 500) {

                        SharedPreferences myPrefs = mContext.getSharedPreferences(cMarker.getTitle(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.putBoolean("visited", true);
                        editor.apply();
                        fillMarkers();
                    }
                }
                    return false;
                }

        });
    }

    void fillMarkers() {

        try {
            mMap.clear();
            InputStream in = this.getResources().openRawResource(R.raw.points_of_interest);
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                double latitude;
                double longitude;
                while (reader.readLine() != null) {
                    str = reader.readLine();
                    latitude = Double.parseDouble(reader.readLine());
                    longitude = Double.parseDouble(reader.readLine());
                    LatLng latLng = new LatLng(latitude, longitude);
                    SharedPreferences myPrefs = this.getSharedPreferences(str, Context.MODE_PRIVATE);
                    Boolean visited = myPrefs.getBoolean("visited", false);
                    if (visited)
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str).snippet("Click InfoWindow then MyLocation to CheckIn").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    else
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str).snippet("Visited").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                if (mLatLng != null) {
                    SharedPreferences myPrefs = this.getSharedPreferences("My Location", Context.MODE_PRIVATE);
                    Boolean visited = myPrefs.getBoolean("visited", false);
                    if (visited)
                        mMap.addMarker(new MarkerOptions().position(mLatLng).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    else
                        mMap.addMarker(new MarkerOptions().position(mLatLng).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
            }
        } catch (Throwable t) {

            Toast

                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

        /*lwit = new LatLng(47.705307, -122.167171);
        mMap.addMarker(new MarkerOptions().position(lwit).title("Lake Washington Institute of Technology"));

        pacificScienceCenter = new LatLng(47.619851, -122.352165);
        mMap.addMarker(new MarkerOptions().position(pacificScienceCenter).title("Pacific Science Center"));

        spaceNeedle = new LatLng(47.620579, -122.349277);
        mMap.addMarker(new MarkerOptions().position(spaceNeedle).title("Space Needle"));

        empMuseum = new LatLng(47.621714, -122.348103);
        mMap.addMarker(new MarkerOptions().position(empMuseum).title("EMP Museum"));

        centuryLinkField = new LatLng(47.595268, -122.331629);
        mMap.addMarker(new MarkerOptions().position(centuryLinkField).title("Century Link Field"));

        woodlandParkZoo = new LatLng(47.669069, -122.350926);
        mMap.addMarker(new MarkerOptions().position(woodlandParkZoo).title("Woodland Park Zoo"));

        fremontTroll = new LatLng(47.651243, -122.347366);
        mMap.addMarker(new MarkerOptions().position(fremontTroll).title("Fremont Troll"));

        safecoField = new LatLng(47.591617, -122.332378);
        mMap.addMarker(new MarkerOptions().position(safecoField).title("Safeco Field"));

        internationalDistrict = new LatLng(47.598366, -122.327342);
        mMap.addMarker(new MarkerOptions().position(internationalDistrict).title("International District"));

        spookedInSeattle = new LatLng(47.602889, -122.334090);
        mMap.addMarker(new MarkerOptions().position(spookedInSeattle).title("Spooked In Seattle"));

        undergroundTour = new LatLng(47.602525, -122.333633);
        mMap.addMarker(new MarkerOptions().position(undergroundTour).title("Seattle Underground Tour"));

        ferrisWheel = new LatLng(47.606218, -122.342652);
        mMap.addMarker(new MarkerOptions().position(ferrisWheel).title("Seattle Great Ferris Wheel"));

        pikesPlace = new LatLng(47.608960, -122.340602);
        mMap.addMarker(new MarkerOptions().position(pikesPlace).title("Pikes Place Market"));

        gumWall = new LatLng(47.608360, -122.340250);
        mMap.addMarker(new MarkerOptions().position(gumWall).title("Gum Wall"));

        seattleLibrary = new LatLng(47.608191, -122.332538);
        mMap.addMarker(new MarkerOptions().position(seattleLibrary).title("Seattle Public Library"));

        paramountTheatre = new LatLng(47.614005, -122.331478);
        mMap.addMarker(new MarkerOptions().position(paramountTheatre).title("Paramount Theatre"));

        fifthAvenueTheatre = new LatLng(47.609445, -122.333828);
        mMap.addMarker(new MarkerOptions().position(fifthAvenueTheatre).title("5th Avenue Theatre"));*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                SharedPreferences myPrefs = this.getSharedPreferences("My Location", Context.MODE_PRIVATE);
                Boolean visited = myPrefs.getBoolean("visited", false);
                if (visited)
                    mMap.addMarker(new MarkerOptions().position(mLatLng).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                else
                    mMap.addMarker(new MarkerOptions().position(mLatLng).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}


