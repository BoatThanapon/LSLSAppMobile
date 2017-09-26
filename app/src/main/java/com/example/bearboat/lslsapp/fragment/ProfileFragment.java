package com.example.bearboat.lslsapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.activity.LoginActivity;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.TruckDriver;
import com.example.bearboat.lslsapp.tool.MySharedPreference;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private Button btnLogout;
    private TextView tvName, tvTruckId, tvAddress;
    private APIService mAPIService;
    private ProgressDialog progressDialog;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView);
        initListener();
        return rootView;
    }

    private void initInstances(View rootView) {

        btnLogout = rootView.findViewById(R.id.btnLogout);
        tvAddress = rootView.findViewById(R.id.tvAddress);
        tvName = rootView.findViewById(R.id.tvName);
        tvTruckId = rootView.findViewById(R.id.tvTruckId);

        String truckDriverId = MySharedPreference.getPref(MySharedPreference.TRUCK_DRIVER_ID,
                getContext());

        if (MySharedPreference.getPref(MySharedPreference.TRUCK_ID, getContext()) != null &&
                MySharedPreference.getPref(MySharedPreference.TRUCK_NAME, getContext()) != null &&
                MySharedPreference.getPref(MySharedPreference.TRUCK_ADDRESS, getContext()) != null)
        {
            tvTruckId.setText(MySharedPreference.getPref(MySharedPreference.TRUCK_ID, getContext()));
            tvName.setText(MySharedPreference.getPref(MySharedPreference.TRUCK_NAME, getContext()));
            tvAddress.setText(MySharedPreference.getPref(MySharedPreference.TRUCK_ADDRESS, getContext()));

        } else {

            showProgressDialog();
            getTruckDriverInfo(truckDriverId);
        }

    }

    private void getTruckDriverInfo(String truckDriverId) {

        mAPIService = ApiUtils.getAPIService();
        mAPIService.GetTruckDriverInfo(truckDriverId).enqueue(new Callback<TruckDriver>() {
            @Override
            public void onResponse(Call<TruckDriver> call, Response<TruckDriver> response) {

                if (response.isSuccessful()) {

                    TruckDriver truckDriver = response.body();
                    onSuccess(truckDriver);

                } else {

                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TruckDriver> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });

    }

    private void initListener() {

        btnLogout.setOnClickListener(this);
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
    public void onClick(View view) {

        if (view.getId() == R.id.btnLogout){

            MySharedPreference.clearPref(getContext());
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void onSuccess(TruckDriver truckDriver){

        tvName.setText(truckDriver.getTruckDriverFullname());
        tvTruckId.setText(truckDriver.getTruckId());
        tvAddress.setText(truckDriver.getTruckDriverAddress());

        MySharedPreference.putPref(MySharedPreference.TRUCK_NAME,
                truckDriver.getTruckDriverFullname(),
                getContext());

        MySharedPreference.putPref(MySharedPreference.TRUCK_ID,
                truckDriver.getTruckId(),
                getContext());

        MySharedPreference.putPref(MySharedPreference.TRUCK_ADDRESS,
                truckDriver.getTruckDriverAddress(),
                getContext());

        dismissProgressDialog();
    }

    public void showProgressDialog() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }


    private void dismissProgressDialog() {

        progressDialog.dismiss();
    }
}
