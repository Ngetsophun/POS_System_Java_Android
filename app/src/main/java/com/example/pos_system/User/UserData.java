package com.example.pos_system.User;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tbUser")
public class UserData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    String name;
    @ColumnInfo
    String password;

    @ColumnInfo
    String gender;

    @ColumnInfo
    String address;

    @ColumnInfo
    String role;

    @ColumnInfo
    String dob;
    @ColumnInfo
    String permission;

    @ColumnInfo
    String image;

    @ColumnInfo
    String date;


    public UserData() {
    }

    public UserData( String name, String password, String gender, String address, String role, String dob, String permission, String image, String date) {

        this.name = name;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.role = role;
        this.dob = dob;
        this.permission = permission;
        this.image = image;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
