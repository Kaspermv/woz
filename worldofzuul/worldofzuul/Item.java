package worldofzuul;

public class Item {
    private String name;
    private String description;
    private int price;

    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name: " + name + ". Description: " + description + ". Price: $" + price;
    }

    public String getName(){
        return name;
    }

    public int getPrice() {
        return price;
    }
}