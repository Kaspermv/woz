package worldofzuul;

public class TownHall extends Room{

    public Inventory inventory = new Inventory();

    public TownHall(String description, String secondDescription, boolean hasPrice){
        super(description, secondDescription, hasPrice);

        for (int i = 0; i < 5; i++) {
            inventory.addItem(new Item("Road-upgrade", "Upgrades dirt road to asphalt road", 75));
        }
    }

    @Override
    public String toString(){
        return inventory.toString();
    }
}
