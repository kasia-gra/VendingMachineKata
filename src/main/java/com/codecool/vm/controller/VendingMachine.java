package com.codecool.vm.controller;

import com.codecool.vm.Helper;
import com.codecool.vm.model.Coin;
import com.codecool.vm.model.Products;
import com.codecool.vm.model.VerifiedCoins;
import com.codecool.vm.view.Menu;

import java.util.*;

public class VendingMachine {

    private Map<Products, Integer> availableProducts = new HashMap<>();
    private Map<VerifiedCoins, Integer> userBudget = new HashMap<>();
    private Map<VerifiedCoins, Integer> coinsInVendingMachine = new HashMap<>();
    private Menu menu;
    Helper helper = new Helper();

    public VendingMachine(Menu menu) {
        this.menu = menu;
        addCoinsToVendingMachine(0,0,0);
        addProductsToVendingMachine(0, 0, 0);
    }

    public void addCoinsToVendingMachine(int quarters, int dimes, int nickles){
        coinsInVendingMachine.put(VerifiedCoins.QUARTER, quarters);
        coinsInVendingMachine.put(VerifiedCoins.DIME, dimes);
        coinsInVendingMachine.put(VerifiedCoins.NICKLE, nickles);
    }
    
    public void addProductsToVendingMachine(int cola, int chips, int candy){
        availableProducts.put(Products.COLA, cola);
        availableProducts.put(Products.CHIPS, chips);
        availableProducts.put(Products.CANDY, candy);
    }


    public void showMenu(Scanner scanner) {
        while (true) {
            menu.displayMessage("INSERT COINS | " + formatBudgetValue() + " | " + currentState());
            String userChoice = menu.getCoin(scanner);
            if (userChoice.equals("B")) {
                Products chosenProduct = menu.chooseProduct(scanner);
                if (availableProducts.get(chosenProduct) == 0) {
                    menu.displayMessage("SOLD OUT");
                } else {
                    buyProduct(chosenProduct);
                }
            }
            else if (userChoice.equals("R")){
                menu.displayMessage("RETURNING | " + formatBudgetValue());
                userBudget = new HashMap<>();
            }
            else {
                addCoinsToBudget(userChoice);
            }
        }
    }


    public void buyProduct(Products chosenProduct) {
        if (availableProducts.get(chosenProduct) == 0) {
            menu.displayMessage("SOLD OUT");
            return;
        }
        if (getBudgetValue() < chosenProduct.getPrice()) {
            menu.displayMessage("NOT ENOUGH BUDGET");
            return;
        }
        int dueChange = getBudgetValue() - chosenProduct.getPrice();
        Map<VerifiedCoins, Integer> calculatedChange = getChange(dueChange);
        if (calculatedChange == null) {
            menu.displayMessage("EXACT CHANGE ONLY");
            return;
        }
        updateVendingMachineData(chosenProduct, calculatedChange);
    }


    private Map<VerifiedCoins, Integer> getChange(int change) {
        if (change == 0) {
            return new HashMap<>();
        }
        Map<VerifiedCoins, Integer> currentSolution;
        List<Map<VerifiedCoins, Integer>> solutionsForEachNumber = new ArrayList<>();
        initializeListWithNulls(solutionsForEachNumber, change);
        for (int partialChange = 1; partialChange < change + 1; partialChange++) {
            for (VerifiedCoins coin : coinsInVendingMachine.keySet()) {
                if (coin.getValue() <= partialChange) {
                    currentSolution = consolidateResults(solutionsForEachNumber.get(partialChange - coin.getValue()), coin);
                    getOptimalSolution(currentSolution, solutionsForEachNumber, partialChange);
                }
            }
        }
        return solutionsForEachNumber.get(change);
    }

    private void getOptimalSolution(Map<VerifiedCoins, Integer> currentSolution, List<Map<VerifiedCoins, Integer>> solutionsForEachNumber, int i) {
        if (solutionsForEachNumber.get(i) != null && currentSolution != null) {
            if (totalCoins(solutionsForEachNumber.get(i)) < totalCoins(currentSolution)) {
                currentSolution = solutionsForEachNumber.get(i);
            }
        }
        if (currentSolution != null) {
            solutionsForEachNumber.set(i, currentSolution);
        }
    }


    private int totalCoins(Map<VerifiedCoins, Integer> solution) {
        return solution.values().stream().reduce(0, (consVal, val) -> consVal + val);
    }

    private Map<VerifiedCoins, Integer> consolidateResults(Map<VerifiedCoins, Integer> optimalSolution, VerifiedCoins coin) {
        if (optimalSolution == null) {
            return null;
        }
        Map<VerifiedCoins, Integer> currentSolution = new HashMap<>();
        currentSolution.put(coin, 1);
        helper.addMoney(currentSolution, optimalSolution);
        if (!hasEnoughCoins(currentSolution)) return null;
        return currentSolution;
    }


    private boolean hasEnoughCoins(Map<VerifiedCoins, Integer> optimalChange){
        for (Map.Entry<VerifiedCoins, Integer> entry : optimalChange.entrySet()){
            int coinsInUserBudget = (userBudget.containsKey(entry.getKey())) ? userBudget.get(entry.getKey()) : 0;
            if (entry.getValue() > coinsInVendingMachine.get(entry.getKey()) + coinsInUserBudget) {
                return false;
            }
        }
        return true;
    }


    private void initializeListWithNulls(List<Map<VerifiedCoins, Integer>> solutionsForEachNumber, int change) {
        solutionsForEachNumber.add(0, new HashMap<>());
        for (int index = 1; index <= change; index++) {
            solutionsForEachNumber.add(index, null);
        }
    }


    public Optional<VerifiedCoins> insertCoinsByName(String name) {
        Coin coin = new Coin();
        coin.assignSize(name);
        return Arrays.stream(VerifiedCoins.values()).filter(val -> val.getWeight() == coin.getWeight() && val.getRadius() == coin.getRadius())
                .findFirst();
    }


    public void addCoinsToBudget(String name)  {
        Optional<VerifiedCoins> insertedCoin = insertCoinsByName(name);
        if (insertedCoin.isEmpty() || insertedCoin.get().equals(VerifiedCoins.PENNY)) {
            menu.displayMessage("ERROR");
        } else {
            int coinsCount = (userBudget.containsKey(insertedCoin.get())) ? userBudget.get(insertedCoin.get()) : 0;
            userBudget.put(insertedCoin.get(), coinsCount + 1);
        }
    }


    public Map<VerifiedCoins, Integer> getUserBudget() {
        return userBudget;
    }

    public int getBudgetValue() {
        return userBudget.entrySet()
                .stream()
                .reduce(0, (partialResult, entry) -> partialResult + (entry.getKey().getValue() * entry.getValue()), Integer::sum);
    }


    private void updateVendingMachineData(Products chosenProduct, Map<VerifiedCoins, Integer> calculatedChange) {
        helper.addMoney(coinsInVendingMachine, userBudget);
        helper.removeMoney(coinsInVendingMachine, calculatedChange);
        userBudget = new HashMap<>();
        takeOutProduct(chosenProduct);
    }


    private void takeOutProduct(Products chosenProduct) {
        int currentProductQty = availableProducts.get(chosenProduct);
        availableProducts.put(chosenProduct, currentProductQty - 1);
    }

    public String formatBudgetValue() {
        return helper.convertCentsToDollars(getBudgetValue());
    }

    private String currentState(){
        return  helper.formatProductsDisplay(availableProducts) +  helper.formatMoneyDisplay(coinsInVendingMachine);
    }

}
