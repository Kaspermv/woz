package worldofzuul;

public class TownHall extends Room{

    public Inventory inventory = new Inventory();

    //constructs a Town Hall which is a room with an inventory
    public TownHall(String name, String description, String secondDescription, boolean hasPrice, String exits){
        super(name, description, secondDescription, hasPrice, exits);

        for (int i = 0; i < 5; i++) {
            inventory.addItem(new Item("Road-upgrade", "Upgrades dirt road to asphalt road", 75, i));
        }
    }

    @Override
    public String toString(){
        return inventory.toString();
    }
}
