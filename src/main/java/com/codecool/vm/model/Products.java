package com.codecool.vm.model;

public enum Products {

    COLA(100),
    CHIPS(50),
    CANDY(65);

    private int price;

    Products(int price){
        this.price = price;
    }

    public int getPrice(){
        return this.price;
    }

}
