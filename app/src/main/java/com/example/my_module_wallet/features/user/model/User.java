package com.example.my_module_wallet.features.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String firstName;
    private final String firstLastname;
    private final String username;
    private final String password;

    public User(int id, String firstName, String firstLastname, String username, String password) {
        this.firstName = firstName;
        this.firstLastname = firstLastname;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    protected User(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        firstLastname = in.readString();
        username = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFirstLastname() {
        return firstLastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(firstLastname);
        dest.writeString(username);
        dest.writeString(password);
    }
}
