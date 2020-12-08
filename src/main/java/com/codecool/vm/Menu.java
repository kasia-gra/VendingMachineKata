package com.codecool.vm;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public Products chooseProduct(Scanner scanner){
        Products chosenProduct = null;
        while ( chosenProduct == null) {
            System.out.println("What would you like to buy: " +
                    "1 - COLA: $1.00 | 2 - CHIPS: $0.50 | 3 CANDY: $0.65");
            String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        chosenProduct = Products.COLA;
                        break;
                    case "2":
                        chosenProduct = Products.CHIPS;
                        break;
                    case "3":
                        chosenProduct = Products.CANDY;
                        break;
                    default:
                        System.out.println("Please select from available options");
                }
        }
        return chosenProduct;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String getCoin(Scanner scanner){
        return scanner.nextLine();
    }
}
