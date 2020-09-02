package com.example.notifyassignment.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notifyassignment.R;
import com.example.notifyassignment.databinding.ActivityHomeBinding;
import com.example.notifyassignment.receiver.MyAlarmNotify;
import com.example.notifyassignment.sharedpreference.SessionKey;
import com.example.notifyassignment.sharedpreference.SessionManager;
import com.example.notifyassignment.ui.login.LogInActivity;
import java.util.Calendar;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.notifyassignment.sharedpreference.SessionKey.KEY_IS_USER_SIGN_IN;
import static com.example.notifyassignment.sharedpreference.SessionKey.KEY_USER_ID_COUNT;
import static com.example.notifyassignment.utility.Utility.pickDate;
import static com.example.notifyassignment.utility.Utility.pickTime;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private ActivityHomeBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        sessionManager = new SessionManager(this);
        binding.textAlarm.setOnClickListener(this);
        binding.topBarHome.homeLogOut.setOnClickListener(this);
        binding.topBarHome.textInitials.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.homeLogOut:
                binding.progressBarHome.setVisibility(VISIBLE);
                new Handler().postDelayed(this::logOutData, 1500);
                binding.progressBarHome.setVisibility(GONE);
                break;
            case R.id.homeNameText:
            case R.id.textInitials:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.textAlarm:
                pickDate(this, this);
                break;
        }
    }

    private void logOutData(){
        int idCount = sessionManager.getInt(KEY_USER_ID_COUNT);
        String lat = sessionManager.getString(SessionKey.KEY_LONGITUDE);
        String lon = sessionManager.getString(SessionKey.KEY_LATITUDE);
        sessionManager.clearSession();
        sessionManager.setInt(KEY_USER_ID_COUNT, idCount);
        sessionManager.setString(SessionKey.KEY_LATITUDE, lat);
        sessionManager.setString(SessionKey.KEY_LONGITUDE, lon);
        sessionManager.setBoolean(KEY_IS_USER_SIGN_IN, false);
        startActivity(new Intent(this, LogInActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (view.isShown()) {

            Calendar datetime = Calendar.getInstance();
            Calendar calendarInstance = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);
            if (datetime.getTimeInMillis() > calendarInstance.getTimeInMillis()) {
                @SuppressLint("DefaultLocale")
                String timeFormat = String.format("%02d:%02d %s", hourOfDay == 0 ? 12 : hourOfDay,
                    minute, hourOfDay < 12 ? "am" : "pm");
                Toast.makeText(this, timeFormat, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            view.getHour(), view.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            view.getCurrentHour(), view.getCurrentMinute(), 0);
                }
                setAlarm(calendar.getTimeInMillis());
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Time Select Future " +
                        "Time No Past Time", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (view.isShown()) {
            month = month+1;
            String expectedDate = dayOfMonth+"-"+month+"-"+year;
            Toast.makeText(this, expectedDate, Toast.LENGTH_SHORT).show();
            pickTime(this, this);
        }
    }

    private void setAlarm(long time) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarmNotify.class);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent mPendingIntent = PendingIntent
                .getBroadcast(this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, mPendingIntent);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
}