package com.codecool.vm;

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

    public void clearMap(Map<VerifiedCoins, Integer> map){
        map = new HashMap<>();
    }

}
