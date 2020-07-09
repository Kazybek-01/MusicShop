package com.example.mymusic.models;

import java.io.Serializable;

public class Order implements Serializable {
    private String userName;
    private String goodName;
    private int goodQuantity;
    private double goodPrice;

    public Order(String userName, String goodName, int goodQuantity, double goodPrice) {
        this.userName = userName;
        this.goodName = goodName;
        this.goodQuantity = goodQuantity;
        this.goodPrice = goodPrice;
    }
    public Order (){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getGoodQuantity() {
        return goodQuantity;
    }

    public void setGoodQuantity(int goodQuantity) {
        this.goodQuantity = goodQuantity;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userName='" + userName + '\'' +
                ", goodName='" + goodName + '\'' +
                ", goodQuantity=" + goodQuantity +
                ", goodPrice=" + goodPrice +
                '}';
    }
}
