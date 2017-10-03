package com.example.bearboat.lslsapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DateSorter;
import android.widget.TextView;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.adapter.JobAdapter;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.Job;
import com.example.bearboat.lslsapp.tool.MySharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bearboat.lslsapp.tool.UserInterfaceUtils.showToast;

public class JobsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "JobsFragment";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String truckDriverId;
    private JobAdapter adapter;
    private APIService mAPIService;
    private SweetAlertDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        TextView tvProjectName = rootView.findViewById(R.id.tvProjectName);
        tvProjectName.setText(Html.fromHtml("<b>L</b>ogical <b>S</b>upporting <b>L</b>ogistic <b>S</b>ystem"));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        truckDriverId = MySharedPreference.getPref(MySharedPreference.TRUCK_DRIVER_ID,
                getContext());

        getJobsList(truckDriverId, false);
    }

    private void getJobsList(String truckDriverId, Boolean isPullToRefresh) {

        mAPIService = ApiUtils.getAPIService();
        if (!isPullToRefresh) showProgressDialog();

        mAPIService.getJobsList(truckDriverId).enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {

                if (response.isSuccessful()) {

                    onSuccess(response.body());

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
            public void onFailure(Call<List<Job>> call, Throwable t) {
                dismissProgressDialog();
                showToast(getContext(), getString(R.string.on_failure));
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void onSuccess(List<Job> jobsList) {

        List<Job> filteredJob = new ArrayList<Job>();

        // sort by status
        for (Job each : jobsList) {
            if (each.getJobAssignmentStatus() == true) filteredJob.add(each);
        }
        jobsList.removeAll(filteredJob);

        // sort by date
        Collections.sort(jobsList, new Comparator<Job>() {
            public int compare(Job o1, Job o2) {
                if (o1.getJobAssignmentDate() == null || o2.getJobAssignmentDate() == null)
                    return 0;
                return o1.getJobAssignmentDate().compareTo(o2.getJobAssignmentDate());
            }
        });

        adapter = new JobAdapter(jobsList, getActivity());

        if (adapter.getItemCount() != 0) {
            recyclerView.setAdapter(adapter);
        } else {
            swipeRefreshLayout.setVisibility(View.GONE);
        }
        dismissProgressDialog();
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getJobsList(truckDriverId, true);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
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