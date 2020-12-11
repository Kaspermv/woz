package worldofzuul;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser 
{
    // Holds all the command words (The enums and their string value)
    private CommandWords commands;
    // The scanner object used for text input
    private Scanner reader;

    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    // This takes a text input from the terminal with a scanner, outputs a parsed command object that the game can use
    // This should maybe be split into two 'takeInput' and 'inputToCommand' functions for clarity
    public Command getCommand() 
    {
        // Pre-defines variables for the input
        String inputLine;
        String word1 = null;
        String word2 = null;
        // Shows the player that an input is needed.
        // Should be sent to the GUI
        System.out.print("> "); 
        // takes an input as a string
        inputLine = reader.nextLine();

        // Takes a string to parse
        Scanner tokenizer = new Scanner(inputLine);
        // The Scanner object splits the string for every space ' ' by default, which is what we use
        // Checks if the input has a word, and stores the first word
        if(tokenizer.hasNext()) {
            // The next functions is called the first time, assigning the first word to the string variable
            word1 = tokenizer.next();
            // Checks to see if the input has a second word, and assigns the variable for it if true
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next(); 
            }
        }
        // Returns a command object made from the input
        // The getCommandWord function takes the action input string and returns the appropriate Enum
        // If the first word is null, no input was given and the Enum becomes the UNKNOWN, as well as if the word doesn't match an Enum
        // The second word is also null if no input is given
        return new Command(commands.getCommandWord(word1), word2);
    }
    // Shows all the commands (Enum actions) available
    // Might change to GUI later
    public void showCommands()
    {
        commands.showAll();
    }
}
