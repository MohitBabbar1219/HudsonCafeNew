package com.mydarkappfactory.hudsoncafe;

/**
 * Created by dragonslayer on 25/12/17.
 */

public class Record {
    private String date, dishesOrdered;

    public Record() {
    }

    public Record(String date, String dishesOrdered) {

        this.date = date;
        this.dishesOrdered = dishesOrdered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDishesOrdered() {
        return dishesOrdered;
    }

    public void setDishesOrdered(String dishesOrdered) {
        this.dishesOrdered = dishesOrdered;
    }
}
