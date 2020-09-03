package com.example.notifyassignment.pojo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


public class UserData implements Parcelable {

    private Context context;

    public UserData(Context context) {
        this.context = context;
    }

    private int tableIndex;
    private int userId;
    private String userFirstName;
    private String userNumber;
    private String userPassword;
    private String userEmail;
    private String userLastName;
    private String latitude;
    private String longitude;

    public UserData(int userId, String userFirstName, String userNumber, String userPassword,
                    String userEmail, String userLastName, String latitude, String longitude) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userNumber = userNumber;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userLastName = userLastName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected UserData(Parcel in) {
        tableIndex = in.readInt();
        userId = in.readInt();
        userFirstName = in.readString();
        userNumber = in.readString();
        userPassword = in.readString();
        userEmail = in.readString();
        userLastName = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableIndex);
        dest.writeInt(userId);
        dest.writeString(userFirstName);
        dest.writeString(userNumber);
        dest.writeString(userPassword);
        dest.writeString(userEmail);
        dest.writeString(userLastName);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
