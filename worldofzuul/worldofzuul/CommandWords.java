package worldofzuul;
import java.util.HashMap;


public class CommandWords
{
    // This is a container for all the commands (enums) that are in the game.
    // Having this object, makes the list of commands, and the custom functionality, available everywhere

    // A HashMap of the string and enum of the different commands, e.g. <"go",GO>
    private HashMap<String, Action> validCommands;

    // Instantiated on startup from Parser, which is instantiated from Game
    public CommandWords()
    {
        validCommands = new HashMap<String, Action>();
        // For all the Enums/Actions, except UNKNOWN, they are put into the HashMap as values with the string version as key, making it usable in the parser
        for(Action action : Action.values()) {
            if(action != Action.UNKNOWN) {
                validCommands.put(action.toString(), action);
            }
        }
    }
    // Takes a string input like "go" and returns it's Enum value, specified in the validCommands HashMap
    public Action getCommandWord(String commandWord)
    {
        Action action = validCommands.get(commandWord);
        // If the input string isn't a key in the HashMap, then it isn't a valid command/action, and therefore returns the UNKNOWN Enum
        if(action != null) {
            return action;
        }
        else {
            return Action.UNKNOWN;
        }
    }
    // Unused boolean function to check if an input commands first word is a valid command
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
    // Prints all the keys of the valid commands, which is the string versions, on the same line. "go", "help" etc.
    public void showAll() 
    {
        for(String action : validCommands.keySet()) {
            // Should be sent to the GUI
            System.out.print(action + "  ");
        }
        System.out.println();
    }
}
