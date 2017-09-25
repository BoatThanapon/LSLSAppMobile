package com.example.bearboat.lslsapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.bearboat.lslsapp.model.LoginStatus;
import com.example.bearboat.lslsapp.tool.Validator;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private final String PREF_NAME = "MY_PREF";
    private final String TRUCK_ID = "TRUCK_ID";

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private Toast toast;

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

                if (Validator.isStringEmpty(username)
                        && Validator.isStringEmpty(password)) {

                    showToast("Username or Password is required");

                } else {
                    if (Validator.isUsernameValid(username)) {

                        showToast("Username is invalid");

                    } else if (Validator.isPasswordValid(password)) {

                        showToast("Password is invalid");

                    } else {

                        login(username, password);
                    }
                }
            }
        });
    }


    private void login(String username, String password) {

        mAPIService = ApiUtils.getAPIService();
        mAPIService.CheckLogin(username, password).enqueue(new Callback<LoginStatus>() {

            @Override
            public void onResponse(Call<LoginStatus> call, Response<LoginStatus> response) {

                if (response.isSuccessful()) {

                    showProgressDialog();

                    LoginStatus loginStatus = response.body();
                    onSuccess(loginStatus);

                } else {

                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<LoginStatus> call, Throwable t) {

//                showToast("onFailure");
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }


    private void onSuccess(LoginStatus loginStatus) {

        // CHECK LOGIN FROM SERVER
        if (loginStatus.getStatus() == 1){

           Log.i(TAG, "onSuccess: " + loginStatus.getTruckDriverId());
            SharedPreferences prefs = this.getSharedPreferences(
                    PREF_NAME,
                    Context.MODE_PRIVATE);
            prefs.edit().putInt(TRUCK_ID, loginStatus.getTruckDriverId());

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    dismissProgressDialog();
                }
            }, 2000);

        } else {

            showToast(getResources().getString(R.string.invalid_credential));
            dismissProgressDialog();
        }

    }

    private void showToast(String text) {

        toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }


    private void dismissProgressDialog() {

        progressDialog.dismiss();
    }
}