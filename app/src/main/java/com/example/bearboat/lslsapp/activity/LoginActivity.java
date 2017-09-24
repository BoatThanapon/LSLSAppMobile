package com.example.bearboat.lslsapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.TruckDriver;
import com.example.bearboat.lslsapp.tool.Validator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private Toast toast;
    private Intent intent;
    private ProgressDialog progressDialog;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initInstance();
        initListener();
    }

    private void initInstance() {

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);

    }

    private void initListener() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if ( Validator.isStringEmpty(username)
                        && Validator.isStringEmpty(password)) {
                    showToast("Username or Password is required");

                } else {
                    if ( Validator.isUsernameValid(username) ) {
                        showToast("Username is invalid");

                    } else if ( Validator.isPasswordValid(password) ) {
                        showToast("Password is invalid");

                    } else {
                        showToast("Login Success!");
                        login(username, password);

                    }
                }
            }
        });
    }

    private void login(String username, String password) {

        mAPIService = ApiUtils.getAPIService();
        mAPIService.GetTruckDrivers().enqueue(new Callback<TruckDriver>() {
            @Override
            public void onResponse(Call<TruckDriver> call, Response<TruckDriver> response) {

                if (response.isSuccessful()) {
                    showProgressDialog();
                    onSuccess(response.body());

                } else {
                    try {
                        showToast("resoponse is unsuccessful");
                        Log.i(TAG, "onResponse: " + response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<TruckDriver> call, Throwable t) {
                showToast("onFailure");
                Log.i(TAG, "onFailure: " + t.toString());

            }
        });
    }

    private void onSuccess(TruckDriver truckDriver){

        Log.i(TAG, "onSuccess: " + truckDriver.getTruckDriverFullname());
        Log.i(TAG, "onSuccess: " + truckDriver.getTruckDriverEmail());
        Log.i(TAG, "onSuccess: " + truckDriver.getTruckDriverAddress());
        Log.i(TAG, "onSuccess: " + truckDriver.getTruckDriverUsername());

        intent = new Intent(this, MainActivity.class);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                dismissProgressDialog();
            }
        }, 2000);


    }

    private void showToast(String text){

        toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }
}