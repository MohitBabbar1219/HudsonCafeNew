package com.mydarkappfactory.hudsoncafe;

/**
 * Created by dragonslayer on 26/12/17.
 */

public class QRCodeData {

    String email, headCount;

    public QRCodeData(String email, String headCount) {
        this.email = email;
        this.headCount = headCount;
    }

    public QRCodeData() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadCount() {
        return headCount;
    }

    public void setHeadCount(String headCount) {
        this.headCount = headCount;
    }
}
