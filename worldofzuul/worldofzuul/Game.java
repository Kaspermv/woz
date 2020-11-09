package worldofzuul;

import com.sun.security.jgss.GSSUtil;

import java.sql.SQLOutput;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private boolean canSleep = false;
    public int balance = 1000;
    public int lifeQuality = 100;
    public int income = 0;
    public Inventory inventory;
    public int day = 0;
    public static int roomsFinished = 0;

    // Declares all the rooms in the game
    public Room home, dirtRoad1, dirtRoad2, dirtRoad3, dirtRoad4, dirtRoad5, city, bank, powerplant, windmills,
            housing, park, hospital, waterPlant, waterTreatmentPlant, school, sportsFacility, market;

    public TownHall townHall;

    public Game() 
    {
        inventory = new Inventory();
        createRooms();
        parser = new Parser();
    }


    private void createRooms()
    {


        // Creates all the rooms, and sets their description.
        home = new Room("in your home.", "in your home", false);
        dirtRoad1 = new Room("outside on a dirt road.", "outside on an asphalt road", false);
        dirtRoad2 = new Room("outside on a dirt road.", "outside on an asphalt road", false);
        dirtRoad3 = new Room("outside on a dirt road.", "outside on an asphalt road", false);
        dirtRoad4 = new Room("outside on a dirt road.", "outside on an asphalt road", false);
        dirtRoad5 = new Room("outside on a dirt road.", "outside on an asphalt road", false);
        city = new Room("in the big city.", "in the big city.", false);
        bank = new Room("in the bank.", "in the bank.", false);
        townHall = new TownHall("in the town hall.", "in the town hall.", false);
        powerplant = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a powerplant.", "at the powerplant.", true);
        housing = new Room("in the housing area.", "in the housing area.", true);
        park = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a park.", "at the park.", true);
        hospital = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a hospital.", "at the hospital", true);
        waterPlant = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a waterplant.", "at the water plant.", true);
        waterTreatmentPlant = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a water treatment plant.", "at the water treatment plant", true);
        school = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a school.", "at the school", true);
        sportsFacility = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a sports facility.", "at the sports facility", true);
        windmills = new Room("on an empty plot of land.\n" +
                "This would make a great spot for some windmills.", "at the windmill park", true);
        market = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a market.", "at the market", true);

        // Sets all the exits for each room, by giving the direction to the room
        home.setExit("up", dirtRoad1);

        dirtRoad1.setExit("down", home);
        dirtRoad1.setExit("right", dirtRoad2);
        dirtRoad1.setExit("up", housing);
        dirtRoad1.setExit("left", city);

        city.setExit("down", powerplant);
        city.setExit("right", dirtRoad1);
        city.setExit("up", townHall);
        city.setExit("left", bank);

        housing.setExit("down", dirtRoad1);

        townHall.setExit("down", city);

        powerplant.setExit("up", city);

        bank.setExit("right", city);

        dirtRoad2.setExit("up", park);
        dirtRoad2.setExit("down", hospital);
        dirtRoad2.setExit("left", dirtRoad1);
        dirtRoad2.setExit("right", dirtRoad3);

        park.setExit("down", dirtRoad2);

        hospital.setExit("up", dirtRoad2);

        dirtRoad3.setExit("down", waterTreatmentPlant);
        dirtRoad3.setExit("right", dirtRoad4);
        dirtRoad3.setExit("up", waterPlant);
        dirtRoad3.setExit("left", dirtRoad2);

        waterTreatmentPlant.setExit("up", dirtRoad3);

        waterPlant.setExit("down", dirtRoad3);

        dirtRoad4.setExit("up", school);
        dirtRoad4.setExit("down", sportsFacility);
        dirtRoad4.setExit("left", dirtRoad3);
        dirtRoad4.setExit("right", dirtRoad5);

        sportsFacility.setExit("up", dirtRoad4);
        
        school.setExit("down", dirtRoad4);

        dirtRoad5.setExit("up", market);
        dirtRoad5.setExit("left", dirtRoad4);
        dirtRoad5.setExit("down", windmills);

        market.setExit("down", dirtRoad5);

        windmills.setExit("up", dirtRoad5);

        // Setting prices and other values

        dirtRoad1.setMaxLevel(1);
        dirtRoad1.setQualityPerLevel(10);

        dirtRoad2.setMaxLevel(1);
        dirtRoad2.setQualityPerLevel(10);

        dirtRoad3.setMaxLevel(1);
        dirtRoad3.setQualityPerLevel(10);

        dirtRoad4.setMaxLevel(1);
        dirtRoad4.setQualityPerLevel(10);

        dirtRoad5.setMaxLevel(1);
        dirtRoad5.setQualityPerLevel(10);

        powerplant.setMaxLevel(5);
        powerplant.setPricePerLevel(100);
        powerplant.setPayPerLevel(30);
        powerplant.setQualityRequirementPerLevel(20);

        housing.setMaxLevel(5);
        housing.setPricePerLevel(75);
        housing.setQualityPerLevel(50);
        housing.setPayPerLevel(50);
        housing.setQualityRequirementPerLevel(10);

        park.setMaxLevel(1);
        park.setPricePerLevel(175);
        park.setQualityPerLevel(100);

        hospital.setMaxLevel(1);
        hospital.setPricePerLevel(300);
        hospital.setQualityPerLevel(150);
        hospital.setPayPerLevel(150);

        waterPlant.setMaxLevel(4);
        waterPlant.setPricePerLevel(100);
        waterPlant.setQualityPerLevel(20);
        waterPlant.setQualityRequirementPerLevel(15);

        waterTreatmentPlant.setMaxLevel(1);
        waterTreatmentPlant.setPricePerLevel(150);
        waterTreatmentPlant.setQualityPerLevel(60);
        waterTreatmentPlant.setQualityRequirementPerLevel(20);

        school.setMaxLevel(4);
        school.setQualityPerLevel(75);
        school.setPricePerLevel(125);
        school.setQualityRequirementPerLevel(20);

        sportsFacility.setMaxLevel(1);
        sportsFacility.setPricePerLevel(325);
        sportsFacility.setQualityPerLevel(400);

        windmills.setMaxLevel(3);
        windmills.setPricePerLevel(100);
        windmills.setPayPerLevel(50);
        windmills.setQualityPerLevel(20);
        windmills.setQualityRequirementPerLevel(10);

        market.setMaxLevel(3);
        market.setPayPerLevel(125);
        market.setPricePerLevel(150);
        market.setQualityPerLevel(50);
        market.setQualityRequirementPerLevel(15);

        // Sets start room
        currentRoom = home;
    }

    public void play() 
    {
        System.out.println(Action.GO.toString());
        // Prints the welcome message
        printWelcome();

        // If true, the game ends
        boolean finished = false;

        // This is the game loop
        while (! finished) {
            // Basicly, the parser takes an input and formats it to a command
            Command command = parser.getCommand();
            // This is the game itself, that returns true if the game is completed
            finished = processCommand(command);
            if (roomsFinished == 15) {
                System.out.println("Congratulations - you won");
                System.out.println("It took you " + day + " days");
                finished = true;
            }
        }
        // If the game is over, this message shows
        System.out.println("Thank you for playing.  Goodbye.");
    }

    // Prints a welcome message and shows the 'help' command
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Slum!");
        System.out.println("World of Slum is a new, learning adventure game.");
        System.out.println("The goal is to upgrade all places to their max level in the fewest amount of days.");
        System.out.println("You have a bit of money from the start which you can use to buy upgrades and items.");
        System.out.println("Some upgrades requires you to have gained some amount of life quality for your town in order to be able to buy them.");
        System.out.println("Type '" + Action.HELP + "' if you need help.");
        System.out.println();
        // Gives the initial message of where the player is at game start
        System.out.println(currentRoom.getDescription());
    }

    // This performs the main game actions, which takes a command from the parser
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        // Stores the enum from the command in a variable
        Action commandWord = command.getCommandWord();

        // If the enum is the UNKNOWN, which means it isn't one of the pre-made commands, it ends the game turn
        if(commandWord == Action.UNKNOWN) {
            // This should be sent to the GUI handler in the future
            System.out.println("I don't know what you mean...");
            return false;
        }
        // The help command
        if (commandWord == Action.HELP) {
            if (command.hasSecondWord()) {
                switch (command.getSecondWord()) {
                    case "go":
                        System.out.println("Type go followed by one of the available directions to move through the map. ex: \"go up\"");
                        System.out.println(currentRoom.getExitString());
                        break;
                    case "buy":
                        System.out.println("Type buy followed by the name of the item you want to purchase. ex: \"buy Road-upgrade\"");
                        System.out.println("Hint: items can be bought at the town hall");
                        break;
                    case "quit":
                        System.out.println("Type quit to exit the game - your progress will be lost");
                        break;
                    case "sleep":
                        System.out.println("Type sleep to let a day go by - this pays out your daily income");
                        System.out.println("You can only sleep at home");
                        break;
                    case "use":
                        System.out.println("Type use followed by the name of the item you want to use");
                        System.out.println("Items can only be used in specific rooms");
                        break;
                    case "status":
                        System.out.println("Type status to check your balance, life quality and current income");
                        break;
                    default:
                        System.out.println("Thats not a viable command");
                }
            } else { printHelp(); }

        }
        // The GO command calls the goRoom function, with the given command
        else if (commandWord == Action.GO) {
            goRoom(command);
        }
        else if (commandWord == Action.BUY){
            if (command.hasSecondWord() && currentRoom == townHall){
                // Buys item from vendor
                Item item = townHall.inventory.getItem(command.getSecondWord());
                if (item == null){
                    System.out.println("No item by that name.");
                    return false;
                }
                if (balance > item.getPrice()) {
                    inventory.addItem(item);
                    townHall.inventory.removeItem(item);
                    canSleep = true;
                    balance -= item.getPrice();

                    System.out.println("You bought the item");
                    System.out.println("You can buy these items to upgrade your town:");
                    System.out.println(townHall.toString());
                    System.out.println("To buy an item, type BUY and the name of the item.");
                }
            } else if (balance >= currentRoom.getPrice() && currentRoom.buyable(lifeQuality) && !command.hasSecondWord()){
                // Buys room if the player has enough money and it isn't max level. HAS LIFEQUALITY CHECK
                balance -= currentRoom.getPrice();
                income += currentRoom.getPayPerLevel();
                lifeQuality += currentRoom.getQualityPerLevel();

                currentRoom.buy();
                canSleep = true;
                System.out.println(currentRoom.getDescription());
            } else {
                System.out.println("Nothing was bought");
            }
        }
        else if (commandWord == Action.STATUS){
            printStatus(command);
        }

        else if (commandWord == Action.INVENTORY){
            if (!inventory.isEmpty()) {
                System.out.println(inventory.toString());
            } else{
                System.out.println("Inventory is empty.");
            }
        }

        else if (commandWord == Action.SLEEP){
            if (command.hasSecondWord()) {
                System.out.println("Sleep command doesn't take a second argument");
            } else
            {
                if (currentRoom == home) {
                    if (canSleep) {
                        //go to sleep
                        System.out.println("Going to sleep");
                        balance += getIncome();
                        day++;
                        System.out.println("You wake up on day " + day + " and check your bank account. Balance: " + balance);
                        canSleep = false;
                    } else {
                        System.out.println("You are not tired. Go do something.");
                    }
                } else {
                    System.out.println("You can't sleep here");
                }
            }
        } else if (commandWord == Action.USE){
            if (command.hasSecondWord()){
                if (currentRoom == dirtRoad1 || currentRoom == dirtRoad2 || currentRoom == dirtRoad3 || currentRoom == dirtRoad4 || currentRoom == dirtRoad5) {
                    // Upgrades a dirt road, using an item
                    Item item = inventory.getItem(command.getSecondWord());
                    if (item == null) {
                        System.out.println("No item by that name.");
                        return false;
                    }
                    inventory.removeItem(item);
                    canSleep = true;

                    currentRoom.buy();
                    lifeQuality += currentRoom.getQuality();
                    System.out.println("You upgraded the road.");
                    System.out.println(currentRoom.getDescription());
                } else{
                    System.out.println("You can't use that here.");
                }
            } else {
                System.out.println("You cant do that.");
            }
        }


        // Sets the quit condition to true, if the correct quit command is the input
        else if (commandWord == Action.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }
    // The help message to be displayed when the 'help' command is given
    private void printHelp() 
    {
        // All this should be sent through the GUI in the future
        System.out.println("You are " + currentRoom.getDescription());
        System.out.println();
        System.out.println("Your available command words are:");
        // This shows the available commands, 'go', 'help' and so on
        parser.showCommands();
        System.out.println();
        System.out.println("To get more info on a specific command, type help followed by the command. ex: \"help use\"");
    }
    // The function that moves the player, given a valid command
    private void goRoom(Command command) 
    {
        // The 'go' command requires a direction. This checks to see if it does, not if it's valid
        if(!command.hasSecondWord()) {
            // Should be sent to the GUI
            System.out.println("Go where?");
            return;
        }
        // Stores the direction given
        String direction = command.getSecondWord();
        // Stores the room object that was pointed to with a direction, in the command
        Room nextRoom = currentRoom.getExit(direction);

        // If there is no road in the given direction, or that direction isn't set as an exit, the player doesn't move
        if (nextRoom == null) {
            // Should be sent to the GUI
            System.out.println("This is a dead end!");
        }
        else {
            // Changes the current that the player is in, and displays the new current room and exits
            currentRoom = nextRoom;
            // Should be sent to the GUI
            System.out.println(currentRoom.getDescription());
            if (currentRoom == townHall){
                System.out.println();
                System.out.println("You can buy these items to upgrade your town:");
                System.out.println(townHall.toString());
                System.out.println("To buy an item, type BUY and the name of the item.");
            }
        }
    }


    public void printStatus (Command command){
        if (command.hasSecondWord()){
            System.out.println("Status command doesn't take an argument");
        } else {
            System.out.println("Balance: $" + getBalance() + " | Life quality: " + getLifeQuality() + " | Income: $" + getIncome() + " per day");
        }
    }
    // Returns true if the 'quit' command is given, and only contains that command
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    public int getBalance() {
        return balance;
    }

    public int getIncome() {
        return income;
    }

    public int getLifeQuality() {
        return lifeQuality;
    }

}
