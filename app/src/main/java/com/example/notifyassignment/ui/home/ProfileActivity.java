package com.example.notifyassignment.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.notifyassignment.R;
import com.example.notifyassignment.databinding.ActivityProfileBinding;
import com.example.notifyassignment.sharedpreference.SessionKey;
import com.example.notifyassignment.sharedpreference.SessionManager;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProfileBinding binding;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        sessionManager = new SessionManager(this);
        binding.backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backButton){
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        setView();
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        String name  = sessionManager.getString(SessionKey.KEY_USER_FIRST_NAME);
        String last = sessionManager.getString(SessionKey.KEY_USER_LAST_NAME);
        String initial = String.valueOf(name.charAt(0))+ last.charAt(0);
        String lat = sessionManager.getString(SessionKey.KEY_LONGITUDE);
        String lon = sessionManager.getString(SessionKey.KEY_LATITUDE);
        binding.textInitials.setText(initial);
        binding.textUserName.setText(name + " " + last);
        binding.textUserLongitude.setText(lat.substring(0,5));
        binding.textUserLatitude.setText(lon.substring(0, 5));
        binding.textUserEmail.setText(sessionManager.getString(SessionKey.KEY_USER_EMAIL));
        binding.textUserNum.setText(sessionManager.getString(SessionKey.KEY_USER_MOBILE));
    }
}