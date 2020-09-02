package com.example.notifyassignment.sharedpreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    /**
     * shared preference local data base use to store small value;
     */
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_KEY = "notify.preferences";
    private SharedPreferences.Editor editor;

   @SuppressLint("CommitPrefEdits")
   public SessionManager (Context context){
       sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
       editor = sharedPreferences.edit();
   }

   public void setString (String key, String value){
       editor.putString(key, value);
       editor.apply();
   }

   public String getString (String key){
       return sharedPreferences.getString(key, "No Data!!");
   }

   public void setInt (String key, int value){
       editor.putInt(key, value);
       editor.apply();
   }

   public int getInt (String key){
       return sharedPreferences.getInt(key, -1);
   }

   public void setBoolean (String key, Boolean value){
       editor.putBoolean(key, value);
       editor.apply();
   }

   public boolean getBoolean (String key){
       return sharedPreferences.getBoolean(key, false);
   }

    public void clearSession(){
        editor.clear();
    }
}
