package com.example.notifyassignment.ui.login;

import android.content.Context;

import com.example.notifyassignment.utility.MainContractInterface;
import com.example.notifyassignment.pojo.UserData;

public class LogInPresenter implements MainContractInterface.SignInPresenter {

    private MainContractInterface.SignInView signInView;
    private LogInModel logInModel;

    LogInPresenter(MainContractInterface.SignInView signInView) {
        this.logInModel = new LogInModel(this);
        this.signInView = signInView;
    }

    @Override
    public void authenticateUser(String emailId, String password, Context context) {
        logInModel.authenticateUser(emailId, password, context);
    }

    @Override
    public void onAuthenticationDone(UserData listData, String response, boolean status){
        signInView.onAuthenticationDone(listData, response, status);
    }
}
