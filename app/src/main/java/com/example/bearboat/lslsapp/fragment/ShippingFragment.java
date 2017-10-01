package com.example.bearboat.lslsapp.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.adapter.JobAdapter;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.Job;
import com.example.bearboat.lslsapp.tool.MySharedPreference;
import com.example.bearboat.lslsapp.tool.UserInterfaceUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private static final String TAG = "ShippingFragment";
    private FloatingActionButton fabDetail, fabUpdate;
    private Job job;


    public static ShippingFragment newInstance() {
        ShippingFragment fragment = new ShippingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shipping, container, false);

        getActivity().setTitle(getResources().getString(R.string.action_bar_jobs_sub));

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        Bundle mBundle = getArguments();
        job = (Job) mBundle.getSerializable("JOB_ASSIGNMENT");

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fabDetail = rootView.findViewById(R.id.fabDetail);
        fabUpdate = rootView.findViewById(R.id.fabUpdate);

        fabDetail.setOnClickListener(this);
        fabUpdate.setOnClickListener(this);
    }

    /*
                 * Save Instance State Here
                 */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
        }

        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng chiangMai = new LatLng(job.getLatitudeDesJob(), job.getLongitudeDesJob());
        mMap.addMarker(new MarkerOptions().position(chiangMai).title("Current Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chiangMai, 14));
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fabDetail) {
            UserInterfaceUtils.showToast(getContext(), "Detail!!!");

        } else if (view.getId() == R.id.fabUpdate) {
            UserInterfaceUtils.showToast(getContext(), "Update!!!");
        }
    }
}
