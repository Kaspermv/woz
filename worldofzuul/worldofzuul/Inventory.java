package worldofzuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private HashMap<Integer, Item> inventoryMap;


    public Inventory() {
        inventoryMap = new HashMap<Integer, Item>();
    }

    public void addItem(Item item) {
        inventoryMap.put(item.getId(), item);
    }

    public void removeItem(Item item) {
        inventoryMap.remove(item.getId(), item);
    }


    public Item getItem(String input) {
        if (!inventoryMap.isEmpty()) {
            if (isNumeric(input)) {
                return inventoryMap.get(Integer.parseInt(input));
            } else
                for (Map.Entry<Integer, Item> items: inventoryMap.entrySet()){
                    if (items.getValue().getName().equals(input)){
                        return inventoryMap.get(items.getKey());
                    }
                }
        } return null;
    }

    public Boolean isEmpty() {
        if (inventoryMap.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String s = "";
        for (Map.Entry<Integer, Item> item: inventoryMap.entrySet()) {
             s += item.getValue().toString() + "\n";
        }
        return s;
    }


    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

    }

    public HashMap<Integer, Item> getInventoryMap() {
        return inventoryMap;
    }
}
