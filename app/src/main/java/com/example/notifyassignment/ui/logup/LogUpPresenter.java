package com.example.notifyassignment.ui.logup;

import android.content.Context;

import com.example.notifyassignment.utility.MainContractInterface;


public class LogUpPresenter implements MainContractInterface.SignUpPresenter {

    private MainContractInterface.SignUpView signUpView;
    private LogUpModel logUpModel;

    LogUpPresenter(MainContractInterface.SignUpView signUpView) {
        this.logUpModel = new LogUpModel(this);
        this.signUpView = signUpView;
    }

    @Override
    public void registerUserData(String firstName, String lastName,  String userNumber,
                                 String userEmailId, String userPassword, String userConfirmPassword,
                                 String lat, String lon, Context context) {
        logUpModel.registerUserData(firstName, lastName,  userNumber, userEmailId, userPassword,
                userConfirmPassword, lat, lon,  context);
    }

    @Override
    public void onRegistrationDone(String response, boolean status) {
        signUpView.onRegistrationDone(response, status);
    }
}
