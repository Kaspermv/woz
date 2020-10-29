package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 
{
    // This is the description for displaying where the player is, e.g. "in the bedroom".
    private String description;
    // This stores all the adjecent rooms that the player can access, with the direction to the room.
    private HashMap<String, Room> exits;

    private int currentLevel = 1, maxLevel = 1;
    private int price;

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    // Returns true if this room can be bought/upgraded
    public Boolean buyable(){
        return (currentLevel < maxLevel);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void buy(){
        currentLevel++;
    }

    // Getters and setters for currentLevel and maxLevel
    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    // This is used on game startup, for setting the exit(s) of the reffered room.
    public void setExit(String direction, Room neighbor) 
    {
        // e.g. Classroom("east",hallway) would mean the hallway is to the east from the classroom.
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

    // Returns a string on two lines, that says where the player is, and the exits available.
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    // Returns a string that consists of all the exits from the room.
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    // Returns the room object that matches the direction given, relative to this room (instance). null if it doesnt exist
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

