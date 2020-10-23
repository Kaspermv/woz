package worldofzuul;

public enum CommandWord
{
    // This Enum object doesn't hold all the Enums in one object. When it is instantiated, it has one of the several values

    // This is the Enum object that is all the different actions or commands the player can use. There will be more, with the more features that are added
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");
    // This holds the individual string version of the Enum, "go", "help" etc.
    private String commandString;

    // The string is passed to the individual object automatically when instantiated
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    // Returns the string value of the individual Enum
    public String toString()
    {
        return commandString;
    }
}
