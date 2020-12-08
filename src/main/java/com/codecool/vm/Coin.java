package com.codecool.vm;

public class Coin {

    Coin(double weight, double radius){
        this.weight = weight;
        this.radius = radius;
    }

    Coin(){

    }

    public double getWeight() {
        return weight;
    }

    public double getRadius() {
        return radius;
    }
    double weight;

    double radius;


    public void assignSize(String name){
        switch (name){
            case "penny":
                this.weight = this.radius = 1.01;
                break;
            case "nickle" : this.weight = this.radius = 1.05;
                break;
            case "dime" : this.weight = this.radius = 1.1;
                break;
            case "quarter" : this.weight = this.radius = 1.25;
                break;
            default: this.weight = this.radius = 0;
        }
    }


}
