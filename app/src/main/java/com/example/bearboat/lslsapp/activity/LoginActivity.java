package com.example.bearboat.lslsapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.LoginStatus;
import com.example.bearboat.lslsapp.tool.MySharedPreference;
import com.example.bearboat.lslsapp.tool.UserInterfaceUtils;
import com.example.bearboat.lslsapp.tool.Validator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "LoginActivity";

    private TextView tvLoginTitle;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private RelativeLayout rlLogin;

    private ProgressDialog progressDialog;
    private APIService mAPIService;

    private Context mContext;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (MySharedPreference.getPref(MySharedPreference.TRUCK_DRIVER_ID, getApplicationContext()) != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        initInstance();
        initListener();
    }


    private void initInstance() {

        mContext = getApplicationContext();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvLoginTitle = (TextView) findViewById(R.id.tvLoginTitle);
        rlLogin = (RelativeLayout) findViewById(R.id.rlLogin);

        tvLoginTitle.setText(Html.fromHtml("<b>L</b>ogical <b>S</b>upporting <b>L</b>ogistic <b>S</b>ystem"));
    }


    private void initListener() {

        btnLogin.setOnClickListener(this);
        tvLoginTitle.setOnClickListener(this);
    }


    private void login(String username, String password) {

        mAPIService = ApiUtils.getAPIService();
        mAPIService.checkLogin(username, password).enqueue(new Callback<LoginStatus>() {

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
        if (loginStatus.getStatus() == 1) {

            Log.i(TAG, "onSuccess: " + loginStatus.getTruckDriverId());

            MySharedPreference.putPref(MySharedPreference.TRUCK_DRIVER_ID,
                    loginStatus.getTruckDriverId().toString(),
                    getApplicationContext());

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    dismissProgressDialog();
                }
            }, 1500);

        } else {

            Snackbar snackbar = Snackbar.make(rlLogin,
                    getResources().getString(R.string.invalid_credential),
                    Snackbar.LENGTH_LONG);
            snackbar.show();
            dismissProgressDialog();
        }

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

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnLogin) {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (Validator.isStringEmpty(username)
                    && Validator.isStringEmpty(password)) {

                UserInterfaceUtils.showToast(mContext, "Username or Password is required");

            } else {
                if (Validator.isUsernameValid(username)) {

                    UserInterfaceUtils.showToast(mContext, "Username is invalid");

                } else if (Validator.isPasswordValid(password)) {

                    UserInterfaceUtils.showToast(mContext, "Password is invalid");

                } else {

                    login(username, password);
                }
            }

        } else if (view.getId() == R.id.tvLoginTitle) {
            etUsername.setText("drivera");
            etPassword.setText("driver");
        }
    }
}