package com.example.notifyassignment.utility;

import android.content.Context;

import com.example.notifyassignment.pojo.UserData;



public interface MainContractInterface {

    interface SignInView {
        void onAuthenticationDone(UserData listData, String response, boolean status);
    }

    interface SignInModel{
        void authenticateUser(String emailId, String password, Context context);
    }

    interface SignInPresenter {
        void authenticateUser(String emailId, String password, Context context);
        void onAuthenticationDone(UserData listData, String response, boolean status);
    }


    interface SignUpView {
        void onRegistrationDone(String response, boolean status);
    }

    interface SignUpModel{
        void registerUserData(String firstName, String lastName,  String userNumber,
                              String userEmailId, String userPassword, String userConfirmPassword,
                              String lat, String lon, Context context);
    }

    interface SignUpPresenter {
        void registerUserData(String firstName, String lastName,  String userNumber,
                              String userEmailId, String userPassword, String userConfirmPassword,
                              String lat, String lon, Context context);
        void onRegistrationDone(String response, boolean status);
    }
}
