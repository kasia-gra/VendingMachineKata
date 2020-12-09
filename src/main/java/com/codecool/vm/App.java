package com.codecool.vm;

import com.codecool.vm.controller.VendingMachine;
import com.codecool.vm.view.Menu;

import java.util.Scanner;

public class App {

    public static void main(String [] args) throws InterruptedException {
        VendingMachine vm = new VendingMachine(new Menu());
        vm.addCoinsToVendingMachine(1, 1, 0);
        vm.addProductsToVendingMachine(1, 1, 1);
        Scanner scanner = new Scanner(System.in);
        vm.showMenu(scanner);
    }
}
