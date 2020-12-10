package worldofzuul;

public class Item {
    private String name;
    private String description;
    private int price;
    private int id;

    public Item(String name, String description, int price, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    @Override
    public String toString() {
        return name + ". " + description + ". Price: $" + price;
    }

    public String getName(){
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }




    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
