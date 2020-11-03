package worldofzuul;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    public int balance = 1000;
    public int lifeQuality = 0;
    public int income = 0;

    public Game() 
    {
        createRooms();
        parser = new Parser();
    }


    private void createRooms()
    {
        // Declares all the rooms in the game
        Room home, dirtRoad1, dirtRoad2, dirtRoad3, dirtRoad4, dirtRoad5, city, bank, townHall, powerplant, windmills,
                housing, park, hospital, waterPlant, waterTreatmentPlant, school, sportsFacility, market;

        // Creates all the rooms, and sets their description.
        home = new Room("in your home.", "in your home");
        dirtRoad1 = new Room("outside on a dirt road.", "outside on an asphalt road");
        dirtRoad2 = new Room("outside on a dirt road.", "outside on an asphalt road");
        dirtRoad3 = new Room("outside on a dirt road.", "outside on an asphalt road");
        dirtRoad4 = new Room("outside on a dirt road.", "outside on an asphalt road");
        dirtRoad5 = new Room("outside on a dirt road.", "outside on an asphalt road");
        city = new Room("in the big city.", "in the big city.");
        bank = new Room("in the bank.", "in the bank.");
        townHall = new Room("in the town hall, there is a terminal.", "in the town hall, there is a terminal.");
        powerplant = new Room("at the powerplant.", "at the powerplant.");
        housing = new Room("in the housing area.", "in the housing area.");
        park = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a park.", "at the park.");
        hospital = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a hospital.", "at the hospital");
        waterPlant = new Room("at the water plant.", "at the water plant.");
        waterTreatmentPlant = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a water treatment plant.", "at the water treatment plant");
        school = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a school.", "at the school");
        sportsFacility = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a sports facility.", "at the sports facility");
        windmills = new Room("on an empty plot of land.\n" +
                "This would make a great spot for some windmills.", "at the windmill park");
        market = new Room("on an empty plot of land.\n" +
                "This would make a great spot for a market.", "at the market");

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
        powerplant.setCurrentLevel(1);
        powerplant.setPricePerLevel(100);
        powerplant.setPayPerLevel(30);

        housing.setMaxLevel(5);
        housing.setCurrentLevel(1);
        housing.setPricePerLevel(75);
        housing.setQualityPerLevel(50);
        housing.setPayPerLevel(50);

        park.setMaxLevel(1);
        park.setPricePerLevel(175);
        park.setQualityPerLevel(100);

        hospital.setMaxLevel(1);
        hospital.setPricePerLevel(300);
        hospital.setQualityPerLevel(150);
        hospital.setPayPerLevel(150);

        waterPlant.setMaxLevel(4);
        waterPlant.setCurrentLevel(1);
        waterPlant.setPricePerLevel(100);
        waterPlant.setQualityPerLevel(20);

        waterTreatmentPlant.setMaxLevel(1);
        waterTreatmentPlant.setPricePerLevel(150);
        waterTreatmentPlant.setQualityPerLevel(60);

        school.setMaxLevel(4);
        school.setQualityPerLevel(75);
        school.setPricePerLevel(125);

        sportsFacility.setMaxLevel(1);
        sportsFacility.setPricePerLevel(325);
        sportsFacility.setQualityPerLevel(400);

        windmills.setMaxLevel(3);
        windmills.setPricePerLevel(100);
        windmills.setPayPerLevel(50);
        windmills.setQualityPerLevel(20);

        market.setMaxLevel(3);
        market.setPayPerLevel(125);
        market.setPricePerLevel(150);
        market.setQualityPerLevel(50);

        // Sets start room
        currentRoom = home;
    }

    public void play() 
    {
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
        }
        // If the game is over, this message shows
        System.out.println("Thank you for playing.  Good bye.");
    }

    // Prints a welcome message and shows the 'help' command
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Slum!");
        System.out.println("World of Slum is a new, learning adventure game.");
        System.out.println("Type '" + Action.HELP + "' if you need help.");
        System.out.println();
        // Gives the initial message of where the player is at game start
        System.out.println(currentRoom.getLongDescription());
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
            printHelp();
        }
        // The GO command calls the goRoom function, with the given command
        else if (commandWord == Action.GO) {
            goRoom(command);
        }
        else if (commandWord == Action.BUY){
            if (command.hasSecondWord()){
                // Buys item from vendor
            } else if (balance >= currentRoom.getPrice() && currentRoom.buyable()){
                // Buys room if the player has enough money and it isn't max level. NEEDS LIFEQUALITY CHECK
                try {
                    currentRoom.buy();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("Nothing was bought");
            }
        }
        else if (commandWord == Action.STATUS){
            printStatus(command);
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
        System.out.println("You are drunk");
        System.out.println("You don't know what to do");
        System.out.println("You are " + currentRoom.getShortDescription());
        System.out.println();
        System.out.println("Your available command words are:");
        // This shows the available commands, 'go', 'help' and so on
        parser.showCommands();
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
            System.out.println(currentRoom.getLongDescription());
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
