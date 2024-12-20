package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import objects.Enemy;
import objects.Item;
import objects.Player;

public class Randomizer {

    private static final Random RANDOM = new Random();

    private Randomizer() {
        // Private constructor to prevent instantiation
    }

    public static int generateExp(Enemy enemy, Player player)
    {
        int exp = 0;
        int atkDiff = enemy.getAtk() - player.getAtk();
        if (atkDiff > 0)
        {
            exp += generateInt(1, 3) + atkDiff;
        }
        else
        {
            exp += atkDiff;
        }

        int hpDiff = (enemy.getMaxHP()/2) - player.getCurrHP();
        if (hpDiff > 0)
        {
            exp += generateInt(10, 15);
        }

        int lvlDiff = enemy.getLevel() - player.getLevel();
        if (lvlDiff > 0)
        {
            exp += lvlDiff * 5;
        }
        else
        {
            exp += lvlDiff * 8;
        }

        exp += enemy.getMaxHP()/4 + (enemy.getAtk() * 2) + (3 * enemy.getLevel());
        

        if (exp < 1)
        {
            return 1;
        }
        else{
            return exp;
        }
    }

    public static int generateInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }
        return RANDOM.nextInt((max - min) + 1) + min;
    }


    public static double generateDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }
        return min + (RANDOM.nextDouble() * (max - min));
    }

    public static boolean generateBoolean() {
        return RANDOM.nextBoolean();
    }


    public static <T> T generateFromArray(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        int index = generateInt(0, array.length - 1);
        return array[index];
    }

    // Function that returns a HashMap of Item and its count
    public static HashMap<String, Integer> getItemCountMap(ArrayList<Item> itemList) {
        HashMap<String, Integer> itemCountMap = new HashMap<>();
        
        // Iterate through the ArrayList and populate the HashMap
        for (Item item : itemList) {
            if (itemCountMap.containsKey(item.getName())) {
                // Item exists, increment the count
                itemCountMap.put(item.getName(), itemCountMap.get(item.getName()) + 1);
            } else {
                // Item doesn't exist, add it with a count of 1
                itemCountMap.put(item.getName(), 1);
            }
        }
        
        return itemCountMap;
    }

    public static Item rollItem(HashMap<Item, Float> items) {
        float roll = (float) generateDouble(0, 1);

        float cumulativeWeight = 0;
        for (Map.Entry<Item, Float> entry : items.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (roll < cumulativeWeight) {
                return entry.getKey(); 
            }
        }

        return null;
    }

    public static int forbidZero(int value)
    {
        if (value < 1)
        {
            return 1;
        }
        return value;
    }
}
