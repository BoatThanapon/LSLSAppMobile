package com.example.bearboat.lslsapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.manager.APIService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapsFragment";
    private GoogleMap mMap;
    private APIService mAPIService;
    private double lat, lng;
    private ProfileFragment mFragment;
    private Bundle mBundle;
    private SupportMapFragment mapFragment;
    LocationManager locationManager;
    String provider;
    private Button btnSendLocation;
    private Toast toast;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
//        mMapView = (MapView) rootView.findViewById(R.id.mapView);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnSendLocation = rootView.findViewById(R.id.btnSendLocation);
        btnSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Location Sent!");
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng chiangMai = new LatLng(18.78832, 98.98532);
        mMap.addMarker(new MarkerOptions().position(chiangMai).title("Current Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chiangMai, 14));

    }

    private void showToast(String text) {

        toast = Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
