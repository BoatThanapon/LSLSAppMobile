package com.example.bearboat.lslsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bearboat.lslsapp.data.remote.APIService;
import com.example.bearboat.lslsapp.data.remote.ApiUtils;
import com.example.bearboat.lslsapp.models.TruckDriver;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lengthUsername = usernameEditText.getText().toString().trim().length();
                int lengthPassword = passwordEditText.getText().toString().trim().length();

                if (lengthUsername == 0 && lengthPassword == 0) {
                    toast = Toast.makeText(getApplicationContext(),
                            " Username or Password is required",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (lengthUsername <= 3 || lengthUsername >= 15) {
                        toast = Toast.makeText(getApplicationContext(),
                                " Username is invalid",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (lengthPassword <= 3 || lengthPassword >= 15) {
                        toast = Toast.makeText(getApplicationContext(),
                                " Password is invalid",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        SendLogin();
                    }
                }
            }
        });

    }

    public void SendLogin() {
        APIService mAPIService;
        mAPIService = ApiUtils.getAPIService();
        mAPIService.GetTruckDrivers().enqueue(new Callback<TruckDriver>() {
            @Override
            public void onResponse(Call<TruckDriver> call, Response<TruckDriver> response) {
                if (response.isSuccessful()) {
                    Log.i("Success", "post submitted to API." + response.body().toString());
                } else if (response.code() == 400) {
                    try {
                        Log.v("Error code 400",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<TruckDriver> call, Throwable t) {
                Log.e("Failure", t.toString());
            }
        });
    }

}
