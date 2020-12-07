package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 
{
    // This is the description for displaying where the player is, e.g. "in the bedroom".
    public String description;
    public String secondDescription;
    // This stores all the adjecent rooms that the player can access, with the direction to the room.
    public HashMap<String, Room> exits;
    public String exitLocations;
    public String name;

    public int currentLevel = 0, maxLevel = 0;
    public int pricePerLevel;
    public int qualityPerLevel;
    public int payPerLevel;
    public int qualityRequirementPerLevel = 0;
    public boolean hasPrice;

    public Room(String name, String description, String secondDescription, boolean hasPrice, String exitType)
    {
        this.description = description;
        this.secondDescription = secondDescription;
        this.hasPrice = hasPrice;
        this.exitLocations = exitType;
        this.name = name;
        exits = new HashMap<String, Room>();
    }

    // Returns true if this room can be bought/upgraded
    public Boolean buyable(int quality){
        return (currentLevel < maxLevel && quality > getQualityRequirement() && hasPrice);
    }

    public void buy(){
        currentLevel++;
        if (currentLevel == maxLevel) {
            Game.roomsFinished++;
        }
    }

    // Getters and setters for values


    public int getPayPerLevel() {
        return payPerLevel;
    }

    public void setPayPerLevel(int payPerLevel) {
        this.payPerLevel = payPerLevel;
    }

    public int getQuality() {
        return qualityPerLevel*currentLevel;
    }

    public int getQualityPerLevel() {
        return qualityPerLevel;
    }

    public void setQualityPerLevel(int qualityPerLevel) {
        this.qualityPerLevel = qualityPerLevel;
    }

    public void setPricePerLevel(int price) {
        this.pricePerLevel = price;
    }

    public int getPrice(){
        return pricePerLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setQualityRequirementPerLevel(int qualityRequirementPerLevel) {
        this.qualityRequirementPerLevel = qualityRequirementPerLevel;
    }

    public int getQualityRequirement() {
        return qualityRequirementPerLevel * currentLevel;
    }

    // This is used on game startup, for setting the exit(s) of the reffered room.
    public void setExit(String direction, Room neighbor) 
    {
        // e.g. Classroom("east",hallway) would mean the hallway is to the east from the classroom.
        exits.put(direction, neighbor);
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getDescription() {
        if (!hasPrice && currentLevel != maxLevel) {
            return "You are " + description + "\n"
                    + getExitString();
        }else  if (!hasPrice && currentLevel == maxLevel) {
            return "You are " + secondDescription + "\n"
                    +getExitString();
        } else if (currentLevel == 0) {
            return "You are " + description + " Level: " + currentLevel + "/" + maxLevel + "\n"
                    + "Price: " + getPrice() + ",-\n"
                    + "Lifequality required: " + getQualityRequirement() + "\n"
                    + "Current revenue: " + getPayPerLevel() * currentLevel + ",- Revenue after upgrade: " + getPayPerLevel() * (currentLevel + 1) + ",-\n"
                    + getExitString();
        } else if (currentLevel != maxLevel) {
            return "You are " + secondDescription + " Level: " + currentLevel + "/" + maxLevel + ".\n"
                    + "Price: " + getPrice() + ".\n"
                    + "Lifequality required: " + getQualityRequirement() + ".\n"
                    + "Current revenue: " + getPayPerLevel() * currentLevel + ",- Revenue after upgrade: " + getPayPerLevel() * (currentLevel + 1) + ",-\n"
                    + getExitString();
        } else {
            return "You are " + secondDescription + " Level: " + currentLevel + "/" + maxLevel + ".\n"
                    + "Current revenue: " + getPayPerLevel() * currentLevel
                    + getExitString();
        }
    }

    // Returns a string that consists of all the exits from the room.
    public String getExitString()
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

