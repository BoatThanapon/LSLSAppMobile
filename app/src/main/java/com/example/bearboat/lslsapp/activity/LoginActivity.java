package com.example.bearboat.lslsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.manager.APIService;
import com.example.bearboat.lslsapp.manager.ApiUtils;
import com.example.bearboat.lslsapp.model.LoginStatus;
import com.example.bearboat.lslsapp.tool.MySharedPreference;
import com.example.bearboat.lslsapp.tool.Validator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bearboat.lslsapp.tool.UserInterfaceUtils.showToast;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "LoginActivity";

    private Button btnLogin;
    private TextView tvLoginTitle;
    private EditText etUsername, etPassword;

    private Context mContext;
    private APIService mAPIService;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();

        checkLoggedIn();

        initInstance();
        initListener();
    }

    private void checkLoggedIn() {

        Boolean isActiveOverHalfHour = Validator.isActiveOverHalfHour(mContext);
        Boolean isLoggedIn = MySharedPreference.getPref(MySharedPreference.TRUCK_DRIVER_ID, mContext) != null;

        Log.i(TAG, "checkLoggedIn: " + isActiveOverHalfHour + " " + isLoggedIn);

        if (!isActiveOverHalfHour){
            if (isLoggedIn){
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void initInstance() {

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvLoginTitle = (TextView) findViewById(R.id.tvLoginTitle);

        tvLoginTitle.setText(Html.fromHtml("<b>L</b>ogical <b>S</b>upporting <b>L</b>ogistic <b>S</b>ystem"));
    }

    private void initListener() {
        btnLogin.setOnClickListener(this);
        tvLoginTitle.setOnClickListener(this);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    btnLogin.performClick();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnLogin) {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (Validator.isStringEmpty(username)
                    && Validator.isStringEmpty(password)) {

                showToast(mContext, getString(R.string.empty_username_password));

            } else {
                if (!Validator.isUsernameValid(username)) {

                    showToast(mContext, getString(R.string.invalid_username));

                } else if (!Validator.isPasswordValid(password)) {

                    showToast(mContext, getString(R.string.invalid_password));

                } else {

                    if (Validator.isConnected(mContext)) {
                        login(username, password);
                    } else {
                        showToast(mContext, getString(R.string.connection_failed));
                    }

                }
            }

        } else if (view.getId() == R.id.tvLoginTitle) {
            etUsername.setText("drivera");
            etPassword.setText("driver");
        }
    }

    private void login(String username, String password) {
        mAPIService = ApiUtils.getAPIService();
        showProgressDialog();
        mAPIService.checkLogin(username, password).enqueue(new Callback<LoginStatus>() {

            @Override
            public void onResponse(Call<LoginStatus> call, Response<LoginStatus> response) {

                if (response.isSuccessful()) {
                    onSuccess(response.body());

                } else {
                    dismissProgressDialog();
                    showToast(mContext, getString(R.string.on_failure));
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginStatus> call, Throwable t) {
                dismissProgressDialog();
                showToast(mContext, getString(R.string.on_failure));
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void onSuccess(LoginStatus loginStatus) {
        if (loginStatus.getStatus() == 1) {

            MySharedPreference.putPref(MySharedPreference.TRUCK_DRIVER_ID,
                    loginStatus.getTruckDriverId().toString(),
                    mContext);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    dismissProgressDialog();
                }
            }, 1000);

        } else {

            showToast(mContext, getString(R.string.invalid_credential));
            dismissProgressDialog();
        }
    }

    private void showProgressDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText(getString(R.string.log_in_loading));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}