package com.samansepahvand.calculateexpensesnew.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = Account.TABLE_NAME)
public class Account extends Model {

    public static final String TABLE_NAME = "Account";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String KEY_FORGET = "keyForget";


    @Column(name = FIRST_NAME)
    String  firstName;

    @Column(name = LAST_NAME)
    String lastName;

    @Column(name = USER_NAME)
    String  userName;

    @Column(name = PASSWORD)
    String  password;

    @Column(name = KEY_FORGET)
    String  keyForget;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyForget() {
        return keyForget;
    }

    public void setKeyForget(String keyForget) {
        this.keyForget = keyForget;
    }
}
