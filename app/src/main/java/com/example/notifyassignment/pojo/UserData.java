package com.example.notifyassignment.pojo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserData implements Parcelable {

    private Context context;

    public UserData(Context context) {
        this.context = context;
    }

    @Getter @Setter
    private int tableIndex;
    @Getter @Setter
    private int userId;
    @Getter @Setter
    private String userFirstName;
    @Getter @Setter
    private String userNumber;
    @Getter @Setter
    private String userPassword;
    @Getter @Setter
    private String userEmail;
    @Getter @Setter
    private String userLastName;
    @Getter @Setter
    private String latitude;
    @Getter @Setter
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

}
