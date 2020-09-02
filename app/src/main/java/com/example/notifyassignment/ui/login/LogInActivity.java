package com.example.notifyassignment.ui.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.notifyassignment.utility.MainContractInterface;
import com.example.notifyassignment.R;
import com.example.notifyassignment.sharedpreference.SessionKey;
import com.example.notifyassignment.sharedpreference.SessionManager;
import com.example.notifyassignment.databinding.ActivityLogInBinding;
import com.example.notifyassignment.pojo.UserData;
import com.example.notifyassignment.ui.home.HomeActivity;
import com.example.notifyassignment.ui.logup.LogUpActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.snackbar.Snackbar;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.notifyassignment.utility.Utility.FASTEST_INTERVAL;
import static com.example.notifyassignment.utility.Utility.UPDATE_INTERVAL;
import static com.example.notifyassignment.utility.Utility.hideSoftKeyBoard;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener,
        MainContractInterface.SignInView, LocationListener {

    private ActivityLogInBinding binding;
    private LogInPresenter logInPresenter;
    private SessionManager sessionManager;
    private View view;
    private LocationRequest mLocationRequest;
    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in);
        logInPresenter = new LogInPresenter(this);
        sessionManager = new SessionManager(this);
        intiView();
        setView();
    }

        private void intiView() {
        binding.textSignIn.setOnClickListener(this);
        binding.textSignUp.setOnClickListener(this);
        binding.imageSignUp.setOnClickListener(this);
    }

    private void setView() {
        String data = getResources().getString(R.string.sign_up_text);
        SpannableString spannableString=  new SpannableString(data);
        spannableString.setSpan(new RelativeSizeSpan(1.8f), 17,25, 0);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)),
                17, 25, 0);
        binding.textSignUp.setText(spannableString);
    }

    @Override
    protected void onResume() {
        getLastLocation();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        this.view = v;
        hideSoftKeyBoard(view, this);
        switch (v.getId()){
        case R.id.textSignUp:
        case R.id.imageSignUp:
            openActivity(LogUpActivity.class);
        break;
        case R.id.textSignIn:
        binding.progressBarLogIn.setVisibility(VISIBLE);
        sendDataToValidateUser();
        break;
        }
    }

    private void openActivity(Class activityName){
        startActivity(new Intent(this, activityName));
        finish();
    }

        private void sendDataToValidateUser(){
        String email = binding.editUserEmailId.getText().toString().trim();
        String password = binding.editUserPassword.getText().toString().trim();
        logInPresenter.authenticateUser(email, password, this);
    }

        @Override
        public void onAuthenticationDone(UserData listData, String response, boolean status) {
        if(listData!= null && response.equals("Pass") && status){
        sessionManager.setString(SessionKey.KEY_USER_ID,
        String.valueOf(listData.getUserId()));
        sessionManager.setString(SessionKey.KEY_USER_FIRST_NAME, listData.getUserFirstName());
        sessionManager.setString(SessionKey.KEY_USER_LAST_NAME, listData.getUserLastName());
        sessionManager.setString(SessionKey.KEY_USER_MOBILE, listData.getUserNumber());
        sessionManager.setString(SessionKey.KEY_USER_EMAIL, listData.getUserEmail());
        sessionManager.setString(SessionKey.KEY_USER_PASSWORD, listData.getUserPassword());
        sessionManager.setString(SessionKey.KEY_LATITUDE, listData.getLatitude());
        sessionManager.setString(SessionKey.KEY_LONGITUDE, listData.getLongitude());
        sessionManager.setBoolean(SessionKey.KEY_IS_USER_SIGN_IN, true);
        openActivity(HomeActivity.class);
        } else {
        Snackbar.make(view, response, Snackbar.LENGTH_SHORT).show();
        }
        binding.progressBarLogIn.setVisibility(GONE);
    }


    public void getLastLocation() {
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            turnOnGps();
            return;
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null)
                        LogInActivity.this.onLocationChanged(location);
                    else
                        turnOnGps();

                })
                .addOnFailureListener(e -> Toast.makeText(LogInActivity.this,
                        "Error trying to get last GPS location", Toast.LENGTH_SHORT).show());
    }

    private void turnOnGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    startActivity(new
                            Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    startLocationUpdates();
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                }, Looper.myLooper());
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        storeValue();
    }


    private void storeValue() {
        if(sessionManager.getString(SessionKey.KEY_LATITUDE).equals("No Data!!") &&
        sessionManager.getString(SessionKey.KEY_LONGITUDE).equals("No Data!!")) {
            sessionManager.setString(SessionKey.KEY_LATITUDE, String.valueOf(lat));
            sessionManager.setString(SessionKey.KEY_LONGITUDE, String.valueOf(lon));
        }else if(!String.valueOf(lat).equals(sessionManager.getString(SessionKey.KEY_LATITUDE)) &&
                !String.valueOf(lon).equals(sessionManager.getString(SessionKey.KEY_LONGITUDE))){
            sessionManager.setString(SessionKey.KEY_LATITUDE, String.valueOf(lat));
            sessionManager.setString(SessionKey.KEY_LONGITUDE, String.valueOf(lon));
        }
    }
}