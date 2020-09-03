package com.example.notifyassignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.notifyassignment.pojo.UserData;

import java.util.ArrayList;

public class DatabaseAdapter {
    /*************
     * Database Name
     ************/
    static final String DATABASE_NAME = "notify.db";

    /****
     * Database Version (Increase one if want to also upgrade your database)
     ****/
    static final int DATABASE_VERSION = 1;

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;


    public DatabaseAdapter (Context context){
        this.context = context;
        dataBaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        try {
            sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        } catch (Exception se) {
            Toast.makeText(context, se.toString(), Toast.LENGTH_SHORT).show();
            try {
                sqLiteDatabase = dataBaseHelper.getReadableDatabase();
            } catch (Exception se1) {
                Toast.makeText(context, se1.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void close() {
        dataBaseHelper.close();
    }


    /************
     * Table Fields  column name
     * "  );";
     ************/

    private static final String KEY_DB_ID = "_id";

    // user data
    private static final String KEY_DB_USER_ID= "user_id";
    private static final String KEY_DB_USER_NUMBER = "user_number";
    private static final String KEY_DB_USER_EMAIL = "user_email";
    private static final String KEY_DB_USER_PASSWORD = "user_password";
    private static final String KEY_DB_USER_FIRST_NAME = "user_first_name";
    private static final String KEY_DB_USER_LAST_NAME = "user_last_name";
    private static final String KEY_DB_LATITUDE = "user_latitude";
    private static final String KEY_DB_LONGITUDE = "user_longitude";

    /**
     * Table names
     */
    private static final String TABLE_USER_DATA = "table_user_data";

    /**
     * Table Column
     */
    static final String CREATE_TABLE_USER_DATA = "create table if not exists " + TABLE_USER_DATA +
            "( " +
            "_id integer primary key autoincrement, " +
            "user_id integer, " +
            "user_first_name text, " +
            "user_last_name text, " +
            "user_number text, " +
            "user_email text, " +
            "user_password text, "  +
            "user_latitude text, " +
            "user_longitude text " +
            "  );";


    public void addUserData( UserData userData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DB_USER_ID, userData.getUserId());
        contentValues.put(KEY_DB_USER_FIRST_NAME, userData.getUserFirstName());
        contentValues.put(KEY_DB_USER_LAST_NAME, userData.getUserLastName());
        contentValues.put(KEY_DB_USER_NUMBER, userData.getUserNumber());
        contentValues.put(KEY_DB_USER_EMAIL, userData.getUserEmail());
        contentValues.put(KEY_DB_USER_PASSWORD, userData.getUserPassword());
        contentValues.put(KEY_DB_LATITUDE, userData.getLatitude());
        contentValues.put(KEY_DB_LONGITUDE, userData.getLongitude());
        sqLiteDatabase.insert(TABLE_USER_DATA, null, contentValues);
    }


    public ArrayList<UserData> getUserDataDetails(String action, String userEmailId){
        ArrayList<UserData> getUserDetails = new ArrayList<>();
        if(action.isEmpty() && userEmailId.isEmpty() ){
            return getUserDetails;
        }
        Cursor cursor = null;
        switch (action){
            case "FetchData":
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_USER_DATA
                        + " where " + KEY_DB_USER_EMAIL + " =?", new String[]{userEmailId});
                break;
            case "SearchData":
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_USER_DATA +
                        " where EXISTS ( SELECT "+ KEY_DB_USER_EMAIL + " where "
                        + KEY_DB_USER_EMAIL + " =? )", new String[]{userEmailId});
                break;
        }
        if(cursor!=null){
            boolean element = cursor.moveToFirst();
            try {
                while (element) {
                    UserData userData = new UserData(context);
                    userData.setTableIndex
                            (Integer.parseInt(cursor.getString
                                    (cursor.getColumnIndex(KEY_DB_ID))));
                    userData.setUserId
                            (Integer.parseInt(cursor.getString
                                    (cursor.getColumnIndex(KEY_DB_USER_ID))));
                    userData.setUserFirstName
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_USER_FIRST_NAME)));
                    userData.setUserLastName
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_USER_LAST_NAME)));
                    userData.setUserNumber
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_USER_NUMBER)));
                    userData.setUserEmail
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_USER_EMAIL)));
                    userData.setUserPassword
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_USER_PASSWORD)));
                    userData.setLatitude
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_LATITUDE)));
                    userData.setLongitude
                            (cursor.getString(cursor.getColumnIndex(KEY_DB_LONGITUDE)));
                    getUserDetails.add(userData);
                    element = cursor.moveToNext();
                } cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getUserDetails;
    }
}
