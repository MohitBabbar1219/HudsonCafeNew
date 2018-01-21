package com.mydarkappfactory.hudsoncafe;

import android.database.Cursor;

/**
 * Created by Mohit on 10/8/2017.
 */

public class Dish {

    private String name, description;
    private int price, quantity, imageResId;
    private float rating;
    public static final String LOREM_IPSUM = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

    public Dish() {
    }

    public Dish(String name, String description, int price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = 0;
        this.rating = 4;
    }

    public Dish(Cursor cursor) {
        this.name = cursor.getString(1);
        this.description = cursor.getString(2);
        this.price = cursor.getInt(3);
        this.rating = cursor.getInt(4);
        this.quantity = cursor.getInt(5);
        this.imageResId = cursor.getInt(6);
    }

    public Dish(Dish dish) {
        this.name = dish.getName();
        this.description = dish.getDescription();
        this.price = dish.getPrice();
        this.rating = dish.getRating();
        this.quantity = dish.getQuantity();
        this.imageResId = dish.getImageResId();
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getRating() {
        return rating;
    }
}
