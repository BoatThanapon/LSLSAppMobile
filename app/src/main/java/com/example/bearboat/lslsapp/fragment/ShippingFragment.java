package com.example.bearboat.lslsapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.bearboat.lslsapp.activity.MainActivity;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bearboat.lslsapp.tool.UserInterfaceUtils.showToast;

public class ShippingFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "ShippingFragment";

    private Job job;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FloatingActionButton fabDetail, fabUpdate;
    private Shipping shipping;
    private APIService mAPIService;
    private AlertDialog.Builder builder;
    private SweetAlertDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shipping, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        getActivity().setTitle(getResources().getString(R.string.action_bar_jobs_sub));
        builder = new AlertDialog.Builder(getContext());

        Bundle mBundle = getArguments();
        job = (Job) mBundle.getSerializable("JOB_ASSIGNMENT");

        getShippingDetail();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fabDetail = rootView.findViewById(R.id.fabDetail);
        fabUpdate = rootView.findViewById(R.id.fabUpdate);

        fabDetail.setOnClickListener(this);
        fabUpdate.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng chiangMai = new LatLng(job.getLatitudeDesJob(), job.getLongitudeDesJob());
        mMap.addMarker(new MarkerOptions().position(chiangMai).title("Current Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chiangMai, 14));
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

        shipping.setStatusOfTransportation(true);
        shipping.setReceiverName(receiverName);
//        shipping.setShippingDocImage();

        showProgressDialog();
        mAPIService.updateStatusShipping(job.getShippingId().toString(), shipping).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.isSuccessful()) {
                    onUpdateStatusSuccess(response.body());

                } else {

                    getShippingDetail();
                    dismissProgressDialog();
                    showToast(getContext(), getString(R.string.on_failure));
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                getShippingDetail();
                dismissProgressDialog();
                showToast(getContext(), getString(R.string.on_failure));
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void onUpdateStatusSuccess(Boolean isSuccess) {

        if (isSuccess) {
            dismissProgressDialog();
            UserInterfaceUtils.showToast(getContext(), "Updated successfully!");
            fragmentJump();

        } else {
            getShippingDetail();
            dismissProgressDialog();
            UserInterfaceUtils.showToast(getContext(), getString(R.string.on_failure));
        }
    }

    private void getShippingDetail() {
        mAPIService = ApiUtils.getAPIService();

        showProgressDialog();
        mAPIService.getShippingInfo(String.valueOf(job.getShippingId())).enqueue(new Callback<Shipping>() {
            @Override
            public void onResponse(Call<Shipping> call, Response<Shipping> response) {

                if (response.isSuccessful()) {
                    onShippingDetailSuccess(response.body());

                } else {
                    dismissProgressDialog();
                    showToast(getContext(), getString(R.string.on_failure));
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Shipping> call, Throwable t) {

                dismissProgressDialog();
                showToast(getContext(), getString(R.string.on_failure));
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void onShippingDetailSuccess(Shipping shipping) {

        this.shipping = shipping;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fabDetail) {
            showShippingDetialDialog();

        } else if (view.getId() == R.id.fabUpdate) {
            showUpdateDialog();
        }
    }

    private void showShippingDetialDialog() {

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

    private void fragmentJump() {
        Fragment mFragment = new JobsFragment();

        FragmentManager fragmentManager = ((MainActivity) (getContext())).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentContainer2, mFragment);

        fragmentTransaction.commit();
    }

    private void showProgressDialog() {
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText(getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
