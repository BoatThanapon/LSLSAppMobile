package com.example.bearboat.lslsapp.fragment;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.tool.UserInterfaceUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
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
    Marker marker;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        getActivity().setTitle(getResources().getString(R.string.action_bar_location));

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
//        mMapView = (MapView) rootView.findViewById(R.id.mapView);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

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

        marker = mMap.addMarker(new MarkerOptions().position(chiangMai).title("Share Location"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chiangMai, 14));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                marker.showInfoWindow();
            }
        }, 1000);

        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        UserInterfaceUtils.showToast(getContext(), "onInfoWindowClick: ");
    }
}
