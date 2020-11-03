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

    public void toString(ArrayList<Item> inventory){

        for (Item item : inventory) {
            System.out.println(item.toString());
        }
    }
}
