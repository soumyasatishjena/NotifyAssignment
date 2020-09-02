package com.example.notifyassignment.utility;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;


public class Utility {

    public static long UPDATE_INTERVAL = 10 * 1000;
    public static long FASTEST_INTERVAL = 2000;


    public static void hideSoftKeyBoard(View view, Context context) {
        if (view != null && context != null) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && imm.isActive())
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String checkNumberFormat(String number) {
        String failReason = "Pass";
        if (number.isEmpty())
            failReason = "Please enter mobile number, field cannot be blank.";
        else if (!number.matches("\\d+"))
            failReason = "Supports mobile numbers only from 0 to 9, Please correct "+
                    "\"" + number + "\"" +".";
        else if (number.startsWith("0"))
            failReason = "Please remove \"0\" from starting of "+"\"" + number + "\"" +
                    ". We supports only local mobile numbers.";
        else if (number.contains(" "))
            failReason = "Mobile number contains a space. Please retype valid mobile number.";
        else if (number.length()!=10)
            failReason ="\"" + number + "\"" + " Mobile number should be 10 digit.";

        return failReason;
    }

    public static String checkEmailFormat(String email) {
        String failReason = "Pass";
        if (email.isEmpty())
            failReason = "Please enter an email, field cannot be empty.";
        else if (email.length() <= 7 || email.contains(" "))
            failReason = "\"" + email + "\"" + " is invalid Email address.";
        else if (!email.contains(".") || !email.contains("@"))
            failReason = "\"" + email + "\"" + " is invalid email format.";
        return failReason;
    }


    public static String checkPasswordFormat(Context context, String password) {
        String failReason = "Pass";
        if ((password.length() <= 7))
            failReason = "Password length should be more than 7 words.";
        else if (!passwordContainsSpecialChars(password))
            failReason = "context.getString(R.string.pass_hint)";
        return failReason;

    }

    private static boolean passwordContainsSpecialChars(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }

    public static String checkConfirmPassword(String password, String cnfPassword){
        String failReason = "Pass";
        if(password.isEmpty() || cnfPassword.isEmpty())
            failReason = "Please enter all field";
        else if(!cnfPassword.equals(password))
            failReason = "New Password and Confirm password mismatch";
        return failReason;
    }

    public static void pickDate(Context context,
                                DatePickerDialog.OnDateSetListener onDateSetListener) {
        final Calendar c = Calendar.getInstance();
        int myYear = c.get(Calendar.YEAR);
        int myMonth = (c.get(Calendar.MONTH));
        int myDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener,
                myYear, myMonth, myDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static void pickTime(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        final Calendar calendarTime = Calendar.getInstance();
        int mHour = calendarTime.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendarTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, onTimeSetListener,
                mHour, mMinute, false);
        timePickerDialog.show();
    }
}
