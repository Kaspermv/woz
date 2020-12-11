package worldofzuul;

public class Item {
    private String name;
    private String description;
    private int price;
    private int id;

    //constructs an item
    public Item(String name, String description, int price, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }


    //returns item in string form
    @Override
    public String toString() {
        return name + ". " + description + ". Price: $" + price;
    }

    //returns item name
    public String getName() {
        return name;
    }

    //returns item price
    public int getPrice() {
        return price;
    }

    //returns  item id
    public int getId() {
        return id;
    }
}
