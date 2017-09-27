package com.example.bearboat.lslsapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.adapter.JobAdapter;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.Job;
import com.example.bearboat.lslsapp.model.TruckDriver;
import com.example.bearboat.lslsapp.tool.MySharedPreference;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class JobsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "JobsFragment";
    private RecyclerView recyclerView;
    private APIService mAPIService;
    private JobAdapter adapter;
    private List<Job> jobsList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static JobsFragment newInstance() {
        JobsFragment fragment = new JobsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void getJobsList(String truckDriverId) {

        mAPIService = ApiUtils.getAPIService();
        mAPIService.getJobsList(truckDriverId).enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {

                if (response.isSuccessful()) {

                    jobsList = response.body();

                    adapter = new JobAdapter(jobsList, getActivity());
                    recyclerView.setAdapter(adapter);

                } else {

                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void initInstances(View rootView) {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        String truckDriverId = MySharedPreference.getPref(MySharedPreference.TRUCK_DRIVER_ID,
                getContext());

        getJobsList(truckDriverId);
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
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }


}
