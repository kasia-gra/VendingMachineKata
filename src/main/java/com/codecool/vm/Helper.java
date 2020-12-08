package com.codecool.vm;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public void addMoney(Map<VerifiedCoins, Integer> originalMap, Map<VerifiedCoins, Integer> mapToAdd) {
        for (Map.Entry<VerifiedCoins, Integer> addedEntry : mapToAdd.entrySet()) {
            VerifiedCoins addedCoin = addedEntry.getKey();
            int addedValue = addedEntry.getValue();
            int updatedValue = (originalMap.containsKey(addedCoin)) ? addedValue + originalMap.get(addedCoin) : addedValue;
            originalMap.put(addedCoin, updatedValue);
        }
    }

    public void removeMoney(Map<VerifiedCoins, Integer> originalMap, Map<VerifiedCoins, Integer> mapToRemove){
        for (Map.Entry<VerifiedCoins, Integer> removedEntry : mapToRemove.entrySet()) {
            VerifiedCoins coinToRemove = removedEntry.getKey();
            int valueToRemove = removedEntry.getValue();;
            originalMap.put(coinToRemove, originalMap.get(coinToRemove) - valueToRemove);
        }
    }


    public String convertCentsToDollars(int priceInCents) {
        return "$" + String.format("%.2f", priceInCents / 100.00);
    }

    public String formatProductsDisplay(Map<Products, Integer> originalMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("* PRODUCTS: ");
        for (Map.Entry <Products, Integer> entry : originalMap.entrySet()) {
            sb.append(" " +entry.getKey() + ": " + entry.getValue() + " ");
        }
        return sb.toString();
    }

    public String formatMoneyDisplay(Map<VerifiedCoins, Integer> originalMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("* MONEY");
        for (Map.Entry <VerifiedCoins, Integer> entry : originalMap.entrySet()) {
            sb.append(" " + entry.getKey() + " (" + entry.getKey().getValue() + ") " + ": " + entry.getValue() + " ");
        }
        return sb.toString();
    }

}
