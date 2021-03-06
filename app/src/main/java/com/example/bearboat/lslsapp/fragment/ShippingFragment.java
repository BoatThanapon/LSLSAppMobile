package com.example.bearboat.lslsapp.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
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
import com.example.bearboat.lslsapp.tool.Validator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bearboat.lslsapp.tool.UserInterfaceUtils.showToast;

public class ShippingFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "ShippingFragment";

    private String SHIPPING_COMPLETE = "SHIPPING_COMPLETE";
    private String SHIPPING_INCOMPLETE = "SHIPPING_INCOMPLETE";

    private Job job;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FloatingActionButton fabDetail, fabUpdate;
    private Shipping shipping;
    private APIService mAPIService;
    private AlertDialog.Builder builder;
    private SweetAlertDialog pDialog;
    private Context mContext;
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shipping, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        mContext = getContext();

        getActivity().setTitle(getResources().getString(R.string.action_bar_jobs_sub));
        builder = new AlertDialog.Builder(getContext());

        Bundle mBundle = getArguments();
        job = (Job) mBundle.getSerializable("JOB_ASSIGNMENT");

        if (Validator.isConnected(mContext)) {
            getShippingDetail();
        } else {
            showToast(mContext, getString(R.string.connection_failed));
        }

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
        LatLng origin;
        Double lat = Double.valueOf((MySharedPreference.getPref(MySharedPreference.CURRENT_LATITUBE, getContext())));
        Double lng = Double.valueOf(MySharedPreference.getPref(MySharedPreference.CURRENT_LONGTITUBE, getContext()));

        if (lat != null && lng != null) {
            origin = new LatLng(lat, lng);

        } else {
            origin = new LatLng(18.7657827, 98.9842099);
        }

        List<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();
        LatLng destination = new LatLng(job.getLatitudeDesJob(), job.getLongitudeDesJob());
        MarkerOptions originMarker = new MarkerOptions().position(origin).title("You're HERE!");
        MarkerOptions destinationMarker = new MarkerOptions().position(destination).title("Destination");
        markerOptionsList.add(originMarker);
        markerOptionsList.add(destinationMarker);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : markerOptionsList) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        // offset from edges of the map in pixels
        int padding = 150;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.addMarker(originMarker).showInfoWindow();
        mMap.addMarker(destinationMarker);
        mMap.animateCamera(cu);

        GoogleDirection.withServerKey(getString(R.string.server_key))
                .from(origin)
                .to(destination)
                .unit(Unit.METRIC)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();

                        if (direction.isOK()) {

                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                            for (PolylineOptions polylineOption : polylineOptionList) {
                                mMap.addPolyline(polylineOption);
                            }
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        showToast(getContext(), "Failed");
                    }
                });

    }

    private void showUpdateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater(null);
        View dialogView = inflater.inflate(R.layout.alert_update_shipping, null);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.setTitle("Select Shipping Status:");
        final Button btnComplete = dialogView.findViewById(R.id.btnComplete);
        final Button btnIncomplete = dialogView.findViewById(R.id.btnIncomplete);

        btnComplete.setOnClickListener(this);
        btnIncomplete.setOnClickListener(this);

        alertDialog.show();
    }

    private void showShippingNoteDialog(final Boolean isShippingComplete) {
        if (alertDialog != null) alertDialog.dismiss();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater(null);
        View dialogView = inflater.inflate(R.layout.dialog_shipping_note, null);
        builder.setView(dialogView);
        final EditText etReceiverName = dialogView.findViewById(R.id.etReceiverName);
        final EditText etShippingNote = dialogView.findViewById(R.id.etShippingNote);

        if (isShippingComplete) {
            etReceiverName.setVisibility(View.VISIBLE);
            etShippingNote.setVisibility(View.GONE);
        } else {
            etReceiverName.setVisibility(View.GONE);
            etShippingNote.setVisibility(View.VISIBLE);
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String shippingDetail = isShippingComplete ?
                        etReceiverName.getText().toString().trim() :
                        etShippingNote.getText().toString().trim();
                showComfirmationDialog(isShippingComplete, shippingDetail);
            }
        });

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.setTitle("Enter Shipping Note Detail");
        alertDialog.show();
    }

    private void updateStatusShipping(Boolean isShippingComplete, String shippingDetail) {

        mAPIService = ApiUtils.getAPIService();

        if (isShippingComplete) {
            shipping.setStatusOfTransportation(true);
            shipping.setReceiverName(shippingDetail);
        } else {
            shipping.setStatusOfTransportation(false);
            shipping.setShippingNote(shippingDetail);
        }
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
        } else if (view.getId() == R.id.btnComplete) {
            Boolean isShippingComplete = true;
            showShippingNoteDialog(isShippingComplete);
        } else if (view.getId() == R.id.btnIncomplete) {
            Boolean isShippingComplete = false;
            showShippingNoteDialog(isShippingComplete);
        }
    }

    private void showShippingDetialDialog() {

        String statusShipping = shipping.getStatusOfTransportation() ? "COMPLETE" : "INCOMPLETE";

        String msg = String.format(getString(R.string.title_shipping_id), shipping.getShippingId());
        msg += String.format(getString(R.string.title_date_transport), shipping.getDateOfTransportation());
        msg += String.format(getString(R.string.title_product_name), shipping.getProductName());
        msg += String.format(getString(R.string.title_start_point), shipping.getStartingPoint());
        msg += String.format(getString(R.string.title_destination), shipping.getDestination());
        msg += String.format(getString(R.string.title_employer), shipping.getEmployer());
        msg += String.format(getString(R.string.title_status), statusShipping);
        if (shipping.getShippingNote() != null)
            msg += String.format(getString(R.string.title_shipping_note), shipping.getShippingNote());
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

    private void showComfirmationDialog(final Boolean isShippingComplete, final String shippingDetail){

        String statusShipping = isShippingComplete ? "COMPLETE" : "INCOMPLETE";
        String shippingDetailTitle = isShippingComplete ?  getString(R.string.title_receiver_name) : getString(R.string.title_shipping_note);

        String msg = String.format(getString(R.string.title_shipping_id), shipping.getShippingId());
        msg += String.format(getString(R.string.title_product_name), shipping.getProductName());
        msg += String.format(getString(R.string.title_status), statusShipping);
        msg += String.format(shippingDetailTitle, shippingDetail);
        Spanned showMsg = Html.fromHtml(msg);

        builder.setTitle("Comfirm");
        builder.setMessage(showMsg);
        builder.setPositiveButton("Comfirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Validator.isConnected(mContext)) {
                    updateStatusShipping(isShippingComplete, shippingDetail);
                } else {
                    showToast(mContext, getString(R.string.connection_failed));
                }
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

    @Override
    public void onPause() {
        super.onPause();
        dismissProgressDialog();
    }
}
