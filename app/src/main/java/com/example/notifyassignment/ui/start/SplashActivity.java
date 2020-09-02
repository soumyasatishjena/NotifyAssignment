package com.example.notifyassignment.ui.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.notifyassignment.R;
import com.example.notifyassignment.sharedpreference.SessionManager;
import com.example.notifyassignment.ui.home.HomeActivity;
import static com.example.notifyassignment.sharedpreference.SessionKey.KEY_IS_USER_SIGN_IN;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SessionManager sessionManager = new SessionManager(this);
        new Handler().postDelayed(() -> {
            if(sessionManager.getBoolean(KEY_IS_USER_SIGN_IN))
                openActivity(HomeActivity.class);
            else
                openActivity(LocationEnabledActivity.class);
        }, 1500);
    }

    private void openActivity(Class activityName){
        startActivity(new Intent(this, activityName));
        finish();
    }
}