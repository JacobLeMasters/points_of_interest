package com.example.jacob.pointsofinterest;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spaceNeedle, 16));


    }

    void fillMarkers(){

        lwit = new LatLng(47.705307, -122.167171);
        mMap.addMarker(new MarkerOptions().position(lwit).title("Marker for Lake Washington Institute of Technology"));

        pacificScienceCenter = new LatLng(47.619851, -122.352165);
        mMap.addMarker(new MarkerOptions().position(pacificScienceCenter).title("Marker for Pacific Science Center"));

        spaceNeedle = new LatLng(47.620579, -122.349277);
        mMap.addMarker(new MarkerOptions().position(spaceNeedle).title("Marker for Space Needle"));

        empMuseum = new LatLng(47.621714, -122.348103);
        mMap.addMarker(new MarkerOptions().position(empMuseum).title("Marker for EMP Museum"));

        centuryLinkField = new LatLng(47.595268, -122.331629);
        mMap.addMarker(new MarkerOptions().position(centuryLinkField).title("Marker for CenturyLink Field"));

        woodlandParkZoo = new LatLng(47.669069, -122.350926);
        mMap.addMarker(new MarkerOptions().position(woodlandParkZoo).title("Marker for Woodland Park Zoo"));

        fremontTroll = new LatLng(47.651243, -122.347366);
        mMap.addMarker(new MarkerOptions().position(fremontTroll).title("Marker for Fremont Troll"));

        safecoField = new LatLng(47.591617, -122.332378);
        mMap.addMarker(new MarkerOptions().position(safecoField).title("Marker for Safeco Field"));

        internationalDistrict = new LatLng(47.598366, -122.327342);
        mMap.addMarker(new MarkerOptions().position(internationalDistrict).title("Marker for International District"));

        spookedInSeattle = new LatLng(47.602889, -122.334090);
        mMap.addMarker(new MarkerOptions().position(spookedInSeattle).title("Marker for Spooked In Seattle"));

        undergroundTour = new LatLng(47.602525, -122.333633);
        mMap.addMarker(new MarkerOptions().position(undergroundTour).title("Marker for Seattle Underground Tour"));

        ferrisWheel = new LatLng(47.606218, -122.342652);
        mMap.addMarker(new MarkerOptions().position(ferrisWheel).title("Marker for Seattle Great Ferris Wheel"));

        pikesPlace = new LatLng(47.608960, -122.340602);
        mMap.addMarker(new MarkerOptions().position(pikesPlace).title("Marker for Pike's Place Market"));

        gumWall = new LatLng(47.608360, -122.340250);
        mMap.addMarker(new MarkerOptions().position(gumWall).title("Marker for Gum Wall"));

        seattleLibrary = new LatLng(47.608191, -122.332538);
        mMap.addMarker(new MarkerOptions().position(seattleLibrary).title("Marker for Seattle Public Library"));

        paramountTheatre = new LatLng(47.614005, -122.331478);
        mMap.addMarker(new MarkerOptions().position(paramountTheatre).title("Marker for Paramount Theatre"));

        fifthAvenueTheatre = new LatLng(47.609445, -122.333828);
        mMap.addMarker(new MarkerOptions().position(fifthAvenueTheatre).title("Marker for 5th Avenue Theatre"));
    }

}
