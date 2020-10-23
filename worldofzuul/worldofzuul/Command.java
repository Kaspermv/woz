/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two parts: a CommandWord and a string
 * (for example, if the command was "take map", then the two parts
 * are TAKE and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

package worldofzuul;

public class Command
{
    // This is a container for the parsed input of commands, so it contains the action and description as enum and string respectively
    // e.g. GO and "east"

    // This is the enum, which is the action in the given text command
    private CommandWord commandWord;
    // This is the second word in the given text command, this would be the direction
    private String secondWord;
    // This takes an Enum and a second word as string and sets them to the variables
    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    // Returns the Enum of this instance of a command. e.g. GO
    public CommandWord getCommandWord()
    {
        return commandWord;
    }
    // Returns the second word of this instance of a command, which is a string. e.g. "east"
    public String getSecondWord()
    {
        return secondWord;
    }
    // Returns true if this command has a second word, no matter what it is
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

