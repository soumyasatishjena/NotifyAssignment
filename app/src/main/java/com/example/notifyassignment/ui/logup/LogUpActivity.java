package com.example.notifyassignment.ui.logup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.notifyassignment.sharedpreference.SessionKey;
import com.example.notifyassignment.sharedpreference.SessionManager;
import com.example.notifyassignment.utility.MainContractInterface;
import com.example.notifyassignment.R;
import com.example.notifyassignment.databinding.ActivityLogUpBinding;
import com.example.notifyassignment.ui.login.LogInActivity;
import com.google.android.material.snackbar.Snackbar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.notifyassignment.utility.Utility.hideSoftKeyBoard;

public class LogUpActivity extends AppCompatActivity implements MainContractInterface.SignUpView, View.OnClickListener {

    private ActivityLogUpBinding binding;
    private LogUpPresenter logUpPresenter;
    private View view;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_up);
        logUpPresenter = new LogUpPresenter(this);
        sessionManager = new SessionManager(this);
        intiView();
    }

    private void intiView() {
        binding.textSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.view = v;
        hideSoftKeyBoard(view, this);
        if(v.getId() == R.id.textSignUp){
            binding.textSignUp.setClickable(false);
            binding.progressBarLogUp.setVisibility(VISIBLE);
            sendDataToRegisterUser();
        }
    }

    private void sendDataToRegisterUser() {
        String firstName = binding.editRegisterName.getText().toString().trim();
        String lastName = binding.editRegisterLastName.getText().toString().trim();
        String number = binding.editRegisterPhoneNumber.getText().toString().trim();
        String email = binding.editRegisterEmailId.getText().toString().trim();
        String password = binding.editRegisterPassword.getText().toString().trim();
        String cnfPassword = binding.editRegisterCnfPassword.getText().toString().trim();
        String lat = sessionManager.getString(SessionKey.KEY_LATITUDE);
        String lon = sessionManager.getString(SessionKey.KEY_LONGITUDE);
        logUpPresenter.registerUserData(firstName, lastName,  number, email, password, cnfPassword,
                lat, lon, this);
    }

    @Override
    public void onRegistrationDone(String response, boolean status) {
        if(response.equals("Pass") && status){
            startActivity(new Intent(this, LogInActivity
                    .class));
            finish();
        }else {
            Snackbar.make(view, response, Snackbar.LENGTH_SHORT).show();
        }
        binding.textSignUp.setClickable(true);
        binding.progressBarLogUp.setVisibility(GONE);
    }
}