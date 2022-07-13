package com.example.youmie.utils;

import java.lang.reflect.Field;

public class Host {

    private String username;
    private String placeName;
    private String foodType;
    private String price;
    private String description;

    public Host(String username, String placeName, String foodType, String price, String description) {
        this.username = username;
        this.placeName = placeName;
        this.foodType = foodType;
        this.price = price;
        this.description = description;
    }

    public boolean checkNull() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields()) {
            if (f.get(this) == null) {
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
