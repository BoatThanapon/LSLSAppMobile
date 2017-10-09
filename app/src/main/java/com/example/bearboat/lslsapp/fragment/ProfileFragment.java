package com.example.bearboat.lslsapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.bearboat.lslsapp.tool.UserInterfaceUtils;
import com.example.bearboat.lslsapp.tool.Validator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.bearboat.lslsapp.tool.UserInterfaceUtils.showToast;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private Button btnLogout;
    private ProgressDialog progressDialog;
    private TextView tvName, tvTruckId, tvAddress;
    private APIService mAPIService;
    private TruckDriver truckDriver;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initInstances(rootView);
        initListener();
        return rootView;
    }

    private void initInstances(View rootView) {
        mContext = getContext();

        btnLogout = rootView.findViewById(R.id.btnLogout);
        tvAddress = rootView.findViewById(R.id.tvAddress);
        tvName = rootView.findViewById(R.id.tvName);
        tvTruckId = rootView.findViewById(R.id.tvTruckId);

        String truckDriverId = MySharedPreference.getPref(MySharedPreference.TRUCK_DRIVER_ID,
                mContext);

        if (Validator.isConnected(mContext)) {
            getTruckDriverInfo(truckDriverId);
        } else {
            showToast(mContext, getString(R.string.connection_failed));
        }
    }

    private void getTruckDriverInfo(String truckDriverId) {

        mAPIService = ApiUtils.getAPIService();
        showProgressDialog();
        mAPIService.getTruckDriverInfo(truckDriverId).enqueue(new Callback<TruckDriver>() {
            @Override
            public void onResponse(Call<TruckDriver> call, Response<TruckDriver> response) {

                if (response.isSuccessful()) {

                    truckDriver = response.body();
                    onSuccess(truckDriver);

                } else {

                    showToast(mContext, getString(R.string.on_failure));
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TruckDriver> call, Throwable t) {
                showToast(mContext, getString(R.string.on_failure));
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
//        outState.putSerializable("TRUCK_DRIVER", truckDriver);
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

            MySharedPreference.clearPref(mContext);
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            UserInterfaceUtils.showToast(mContext, "Logged out successfully!");
        }
    }

    private void onSuccess(TruckDriver truckDriver){

        tvName.setText(truckDriver.getTruckDriverFullname());
        tvTruckId.setText(truckDriver.getTruckId());
        tvAddress.setText("Your Driving License: " +
                truckDriver.getTruckDriverDriverLicenseId()
                + "\n" + truckDriver.getTruckDriverAddress());

        dismissProgressDialog();
    }

    public void showProgressDialog() {

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }


    private void dismissProgressDialog() {

        progressDialog.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissProgressDialog();
    }
}
