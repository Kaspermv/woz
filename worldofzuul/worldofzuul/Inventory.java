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


    /*
        public Item getItem(String name) {
            if (!inventoryMap.isEmpty()) {
                return
            }
        }


     */
    public Item getItem(String id) {
        if (!inventoryMap.isEmpty()) {
            if (isNumeric(id)) {
                return inventoryMap.get(Integer.parseInt(id));
            } else
                return inventoryMap.get(id);

        } else return null;
    }

    public Boolean isEmpty() {
        if (inventoryMap.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String s ="";
        for (Map.Entry<Integer, Item> items: inventoryMap.entrySet()) {
             s += items.toString() + "\n";
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
}
