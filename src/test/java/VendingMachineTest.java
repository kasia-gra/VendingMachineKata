import com.codecool.vm.view.Menu;
import com.codecool.vm.controller.VendingMachine;
import com.codecool.vm.model.VerifiedCoins;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class VendingMachineTest {

    VendingMachine vm = new VendingMachine(new Menu());


    @Test
    void addCoinsToBudget() throws InterruptedException {
        vm.addCoinsToBudget("nickle");
        vm.addCoinsToBudget("dime");
        vm.addCoinsToBudget("nickle");
        vm.addCoinsToBudget("quarter");
        assertAll(
                () -> assertEquals(1, vm.getUserBudget().get(VerifiedCoins.DIME)),
                () -> assertEquals(2, vm.getUserBudget().get(VerifiedCoins.NICKLE)),
                () -> assertEquals(1, vm.getUserBudget().get(VerifiedCoins.QUARTER))
        );
    }


    @Test
    void displayBudget() throws InterruptedException {
        addCoinsToBudget();
        String budget =  vm.formatBudgetValue();
        assertAll(
                () -> assertEquals("$0.45", budget)
        );
    }

    @Test
    void getOptimalChange() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        vm.addCoinsToVendingMachine(1, 12, 2);
        Map<VerifiedCoins, Integer> solution = new HashMap<>();
        solution.put(VerifiedCoins.QUARTER , 1);
        solution.put(VerifiedCoins.DIME , 2);
        solution.put(VerifiedCoins.NICKLE, 1);
        Method getChangeMethod = VendingMachine.class.getDeclaredMethod("getChange", int.class);
        getChangeMethod.setAccessible(true);
        assertEquals(solution, getChangeMethod.invoke(vm, 50));
    }


    @Test
    void getOptimalChangeIfNoCoinsInVendingMachineButUserInsertedEnoughCoins() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        vm.addCoinsToVendingMachine(0, 0, 0);
        Map<VerifiedCoins, Integer> solution = new HashMap<>();
        solution.put(VerifiedCoins.DIME , 1);
        vm.addCoinsToBudget("quarter");
        vm.addCoinsToBudget("nickle");
        vm.addCoinsToBudget("dime");
        Method getChangeMethod = VendingMachine.class.getDeclaredMethod("getChange", int.class);
        getChangeMethod.setAccessible(true);
        assertEquals(solution, getChangeMethod.invoke(vm, 10));
    }


    @Test
    void getOptimalChangeIfNoCoinsAvailable() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        vm.addCoinsToVendingMachine(0, 0, 0);
        Map<VerifiedCoins, Integer> solution = new HashMap<>();
        Method getChangeMethod = VendingMachine.class.getDeclaredMethod("getChange", int.class);
        getChangeMethod.setAccessible(true);
        assertEquals(null, getChangeMethod.invoke(vm, 10));
    }
}