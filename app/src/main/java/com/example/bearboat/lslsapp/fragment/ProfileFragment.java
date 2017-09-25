package com.example.bearboat.lslsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.activity.LoginActivity;
import com.example.bearboat.lslsapp.activity.MainActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private final String PREF_NAME = "MY_PREF";
    private Button btnLogout;

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

            getContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
