package com.example.notifyassignment.ui.logup;

import android.content.Context;

import com.example.notifyassignment.utility.MainContractInterface;
import com.example.notifyassignment.sharedpreference.SessionKey;
import com.example.notifyassignment.sharedpreference.SessionManager;
import com.example.notifyassignment.database.DatabaseAdapter;
import com.example.notifyassignment.pojo.UserData;

import java.util.ArrayList;

import static com.example.notifyassignment.utility.Utility.checkConfirmPassword;
import static com.example.notifyassignment.utility.Utility.checkEmailFormat;
import static com.example.notifyassignment.utility.Utility.checkNumberFormat;
import static com.example.notifyassignment.utility.Utility.checkPasswordFormat;


public class LogUpModel implements MainContractInterface.SignUpModel {

    private LogUpPresenter presenter;

    LogUpModel(LogUpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void registerUserData(String firstName, String lastName,  String userNumber,
                                 String userEmailId, String userPassword, String userConfirmPassword,
                                 String lat, String lon, Context context) {
        if(!firstName.isEmpty()){
            if(!lastName.isEmpty()) {
                String numStatus = checkNumberFormat(userNumber);
                String emailIdStatus = checkEmailFormat(userEmailId);
                String passwordStatus = checkPasswordFormat(context, userPassword);
                String cnfPasswordStatus = checkPasswordFormat(context, userConfirmPassword);
                if (numStatus.equals("Pass")) {
                    if (emailIdStatus.equals("Pass")) {
                        if (passwordStatus.equals("Pass")) {
                            if (cnfPasswordStatus.equals("Pass")) {
                                String cnfPwdStatus = checkConfirmPassword(userPassword,
                                        userConfirmPassword);
                                if (cnfPwdStatus.equals("Pass")) {
                                    checkIsUserExist(context, firstName, lastName, userNumber, userEmailId,
                                            userConfirmPassword, lat, lon);
                                } else
                                    presenter.onRegistrationDone(cnfPwdStatus, false);
                            } else
                                presenter.onRegistrationDone(cnfPasswordStatus, false);
                        } else
                            presenter.onRegistrationDone(passwordStatus, false);
                    } else
                        presenter.onRegistrationDone(emailIdStatus, false);
                } else
                    presenter.onRegistrationDone(numStatus, false);
            }else
                presenter.onRegistrationDone("Last Name Cannot be Blank", false);
        }else
            presenter.onRegistrationDone("Enter User Name, field cannot be blank.",
                false);
    }

    private void checkIsUserExist(Context context, String firstName, String lastName,
                                  String userNumber, String userEmailId, String userConfirmPassword,
                                  String lat, String lon){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
        databaseAdapter.open();
        ArrayList<UserData> userExistList = databaseAdapter.
                getUserDataDetails("SearchData", userEmailId);
        databaseAdapter.close();
        if(userExistList.size()>0){
            presenter.onRegistrationDone("User Already exist.. " +
                    "Please Continue with new Email Id to Register.", false);
        }else {
            SessionManager sessionManager = new SessionManager(context);
            int getIdNum = sessionManager.getInt(SessionKey.KEY_USER_ID_COUNT);
            if (getIdNum == -1) {
                getIdNum = 1;
            } else {
                getIdNum = getIdNum + 1;
            }
            sessionManager.setInt(SessionKey.KEY_USER_ID_COUNT, getIdNum);
            UserData userData = new UserData(getIdNum, firstName, userNumber, userConfirmPassword,
                    userEmailId,  lastName, lat, lon);
            storeDataToDatabase(context, userData);
            presenter.onRegistrationDone("Pass", true);
        }
    }

    private void storeDataToDatabase(Context context, UserData userData){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
        databaseAdapter.open();
        databaseAdapter.addUserData(userData);
        databaseAdapter.close();
    }

}
