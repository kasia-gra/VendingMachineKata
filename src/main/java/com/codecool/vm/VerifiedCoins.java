package com.codecool.vm;

import java.util.Arrays;

public enum VerifiedCoins {

    PENNY("penny", 1.01, 1.01, 1),
    NICKLE("nickle", 1.05, 1.05, 5),
    DIME("dime", 1.1, 1.1,  10),
    QUARTER("quarter", 1.25, 1.25, 25);


    private String name;
    private double weight;
    private double radius;
    private int value;



    VerifiedCoins(String name, double weight, double radius, int value){
        this.name = name;
        this.weight = weight;
        this.radius = radius;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public double getRadius() {
        return radius;
    }


}
