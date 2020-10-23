package worldofzuul;
import java.util.HashMap;


public class CommandWords
{
    // This is a container for all the commands (enums) that are in the game.
    // Having this object, makes the list of commands, and the custom functionality, available everywhere

    // A HashMap of the string and enum of the different commands, e.g. <"go",GO>
    private HashMap<String, CommandWord> validCommands;

    // Instantiated on startup from Parser, which is instantiated from Game
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        // For all the Enums/Actions, except UNKNOWN, they are put into the HashMap as values with the string version as key, making it usable in the parser
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }
    // Takes a string input like "go" and returns it's Enum value, specified in the validCommands HashMap
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        // If the input string isn't a key in the HashMap, then it isn't a valid command/action, and therefore returns the UNKNOWN Enum
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
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
        for(String command : validCommands.keySet()) {
            // Should be sent to the GUI
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
