package com.mydarkappfactory.hudsoncafe;

import java.util.ArrayList;

/**
 * Created by dragonslayer on 25/12/17.
 */

public class UserDetails {
    private String firstName, lastName, phoneNumber, emailAddress, password;
    private Boolean isFirstLogin;
    private int assignedTable;
    private ArrayList<Record> record;

    public UserDetails() {
    }

    public UserDetails(String firstName, String lastName, String phoneNumber, String emailAddress, String password, Boolean isFirstLogin, int assignedTable, ArrayList<Record> record) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
        this.isFirstLogin = isFirstLogin;
        this.assignedTable = assignedTable;
        this.record = record;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public int getAssignedTable() {
        return assignedTable;
    }

    public void setAssignedTable(int assignedTable) {
        this.assignedTable = assignedTable;
    }

    public ArrayList<Record> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<Record> record) {
        this.record = record;
    }
}
