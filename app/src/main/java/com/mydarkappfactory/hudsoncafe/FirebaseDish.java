package com.mydarkappfactory.hudsoncafe;

/**
 * Created by dragonslayer on 25/12/17.
 */

public class FirebaseDish {
    private String name;
    private int quantity;
    private int price;

    public FirebaseDish(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public FirebaseDish() {

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
