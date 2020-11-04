package worldofzuul;

public enum Action
{
    // The Enum object doesn't hold all the Enums in one object. When it is instantiated, it has one of the several values

    // This is the Enum object that is all the different actions or commands the player can use. There will be more, with the more features that are added
    GO("go"), BUY("buy"), QUIT("quit"), HELP("help"), UNKNOWN("?"),
    STATUS("status"),INVENTORY("inventory"), SLEEP("sleep");
    // This holds the individual string version of the Enum, "go", "help" etc.
    private String actionString;

    // The string is passed to the individual object automatically when instantiated
    Action(String actionString)
    {
        this.actionString = actionString;
    }
    // Returns the string value of the individual Enum
    public String toString()
    {
        return actionString;
    }
}
