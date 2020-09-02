package com.example.notifyassignment.ui.login;

import android.content.Context;

import com.example.notifyassignment.utility.MainContractInterface;
import com.example.notifyassignment.database.DatabaseAdapter;
import com.example.notifyassignment.pojo.UserData;

import java.util.ArrayList;

import static com.example.notifyassignment.utility.Utility.checkEmailFormat;
import static com.example.notifyassignment.utility.Utility.checkPasswordFormat;


public class LogInModel implements MainContractInterface.SignInModel {

    private LogInPresenter logInPresenter;

    LogInModel(LogInPresenter logInPresenter) {
        this.logInPresenter = logInPresenter;
    }

    @Override
    public void authenticateUser(String emailId, String password, Context context) {
        String emailStatus = checkEmailFormat(emailId);
        String pwd = checkPasswordFormat(context,  password);
        if(emailStatus.equals("Pass")) {
            if(pwd.equals("Pass")) {
                DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
                databaseAdapter.open();
                ArrayList<UserData> userDataList = databaseAdapter.
                            getUserDataDetails("FetchData", emailId);
                databaseAdapter.close();
                    if(userDataList.size()>0) {
                        if(userDataList.get(0).getUserPassword().equals(password)) {
                            logInPresenter.onAuthenticationDone(userDataList.get(0), "Pass",
                                    true);
                        }else
                            logInPresenter.onAuthenticationDone(null,
                                    "Password Mismatch, Enter Correct Password.",
                                    true);
                    }else
                        logInPresenter.onAuthenticationDone(null,
                                "No User Exist, Register to Proceed SignIn.", false);


            }else
                logInPresenter.onAuthenticationDone(null, pwd, false);
        } else
            logInPresenter.onAuthenticationDone(null, emailStatus,false);
    }
}
