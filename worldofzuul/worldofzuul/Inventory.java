package worldofzuul;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventoryList;


    public Inventory() {
        inventoryList = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        inventoryList.add(item);
    }

    public void removeItem(Item item) {
        inventoryList.remove(item);
    }

    public Item getItem(String name) {
        for (Item item : inventoryList){
            if (item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    public Boolean isEmpty(){
        if (inventoryList.size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String value = "";
        for (Item item : inventoryList) {
            value += item.toString() + "\n";
        }
        return value;
    }
}
