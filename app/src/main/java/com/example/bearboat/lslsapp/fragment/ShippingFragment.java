package com.example.bearboat.lslsapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.adapter.JobAdapter;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.Job;
import com.example.bearboat.lslsapp.model.LoginStatus;
import com.example.bearboat.lslsapp.model.Shipping;
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
    private APIService mAPIService;
    private AlertDialog.Builder builder;
    private Shipping shipping;

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

        builder = new AlertDialog.Builder(getContext());

        Log.i(TAG, "initInstances: " + job.getShippingId());

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

            getShippingDetail();

        } else if (view.getId() == R.id.fabUpdate) {
            showUpdateDialog();
        }
    }

    private void showUpdateDialog() {

        builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater(null);
        View dialogView = inflater.inflate(R.layout.alert_update_shipping, null);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        final EditText etReceiverName = dialogView.findViewById(R.id.etReceiverName);
        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                updateStatusShipping(etReceiverName.getText().toString());
            }
        });

        alertDialog.show();
    }

    private void updateStatusShipping(String receiverName) {

        mAPIService = ApiUtils.getAPIService();

        shipping = new Shipping();
        shipping.setReceiverName(receiverName);

        mAPIService.updateStatusShipping(job.getShippingId().toString(), shipping).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {

                    onUpdateStatusSuccess(response.body());

                } else {
                    UserInterfaceUtils.showToast(getContext(), "Failed!");

                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                UserInterfaceUtils.showToast(getContext(), "Failed!");
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void onUpdateStatusSuccess(Boolean isSuccess) {

        if (isSuccess) UserInterfaceUtils.showToast(getContext(), "Updated successfully!");
        else UserInterfaceUtils.showToast(getContext(), "Failed!");
    }

    private void getShippingDetail() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getShippingInfo(String.valueOf(job.getShippingId())).enqueue(new Callback<Shipping>() {
            @Override
            public void onResponse(Call<Shipping> call, Response<Shipping> response) {
                if (response.isSuccessful()) {

                    onShippingDetailSuccess(response.body());

                } else {
                    UserInterfaceUtils.showToast(getContext(), "Failed!");

                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Shipping> call, Throwable t) {
                UserInterfaceUtils.showToast(getContext(), "Failed!");
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void onShippingDetailSuccess(Shipping shipping) {

        String statusShipping = shipping.getStatusOfTransportation() ? "DONE" : "In Process";

        String msg = String.format(getString(R.string.title_shipping_id), shipping.getShippingId());
        msg += String.format(getString(R.string.title_date_transport), shipping.getDateOfTransportation());
        msg += String.format(getString(R.string.title_product_name), shipping.getProductName());
        msg += String.format(getString(R.string.title_start_point), shipping.getStartingPoint());
        msg += String.format(getString(R.string.title_destination), shipping.getDestination());
        msg += String.format(getString(R.string.title_employer), shipping.getEmployer());
        msg += String.format(getString(R.string.title_status), statusShipping);
        Spanned showMsg = Html.fromHtml(msg);

        Log.i(TAG, "onSuccess: " + shipping.getDestination());
        builder.setTitle("Shipping Detail");
        builder.setMessage(showMsg);
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (builder != null) dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
