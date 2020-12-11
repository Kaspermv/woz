package worldofzuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//class to create inventory system
public class Inventory {

    //Inventory uses hash-map to store values
    private HashMap<Integer, Item> inventoryMap;

    //inventroy constructor
    public Inventory() {
        inventoryMap = new HashMap<Integer, Item>();
    }

    //method to add item to hashmap
    public void addItem(Item item) {
        inventoryMap.put(item.getId(), item);
    }

    //method to remove an item from hashmap
    public void removeItem(Item item) {
        inventoryMap.remove(item.getId(), item);
    }

    //Method to return item when called
    //takes an input as a string
    public Item getItem(String input) {
        //first checks if hashmap is empty. If empty, return null
        if (!inventoryMap.isEmpty()) {
            //checks if user input is numeric by using isNumeric method
            if (isNumeric(input)) {
                //returns an entry from the hashmap with the key corresponding to the key of the entry
                return inventoryMap.get(Integer.parseInt(input));
            } else
                //if user entry is not numeric, must be text and evaluated as a string
                //does a for each and returns the first item which name equals the user input
                for (Map.Entry<Integer, Item> items: inventoryMap.entrySet()){
                    if (items.getValue().getName().equals(input)){
                        return inventoryMap.get(items.getKey());
                    }
                }
        } return null;
    }

    //checks if hashmap is empty
    public Boolean isEmpty() {
        if (inventoryMap.size() == 0) {
            return true;
        }
        return false;
    }

    //to string method, to return each entry en text form
    @Override
    public String toString(){
        String s = "";
        for (Map.Entry<Integer, Item> item: inventoryMap.entrySet()) {
             s += item.getValue().toString() + "\n";
        }
        return s;
    }

    //method to check if function
    public static boolean isNumeric(String str) {
        //if string is empty return false
        if (str == null) {
            return false;
        }
        //checks if using, parseInt throws a Number Format Exception. If it does, return false because input is not numeric
        //if parse int does not throw an NFE, the input must be numeric and returns true
        try {
            Integer i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

    }

    //returns an inventory
    public HashMap<Integer, Item> getInventoryMap() {
        return inventoryMap;
    }
}
