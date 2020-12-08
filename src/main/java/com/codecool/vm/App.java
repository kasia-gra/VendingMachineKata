package com.codecool.vm;

import java.util.Scanner;

public class App {

    public static void main(String [] args) throws InterruptedException {
        VendingMachine vm = new VendingMachine(new Menu());
        vm.addCoinsToVendingMachine(1, 3, 0);
        vm.addProductsToVendingMachine(1, 1, 1);
        Scanner scanner = new Scanner(System.in);
        vm.showMenu(scanner);
    }
}
