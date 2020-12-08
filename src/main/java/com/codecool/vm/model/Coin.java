package com.codecool.vm.model;

public class Coin {

    private double weight;

    private double radius;


    public Coin(){

    }

    public double getWeight() {
        return weight;
    }
    public double getRadius() {
        return radius;
    }


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
