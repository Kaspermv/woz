package worldofzuul;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory;


    public Inventory() {
        inventory = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public Boolean isEmpty(){
        if (inventory.size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String value = "";
        for (Item item : inventory) {
            value += item.toString() + "\n";
        }
        return value;
    }
}
