package worldofzuul;

import com.sun.security.jgss.GSSUtil;

import java.sql.SQLOutput;

public class Game
{
    PlayerState player = new PlayerState();
    private Parser parser;
    //Variable storing the current room the player is in
    public Room currentRoom;
    //Variable for amount of days passed in game
    public int day = 0;
    //Variable to keep track of how many rooms ar finished
    public static int roomsFinished = 0;
    //Constant to check if player has finished game
    final private int roomsToFinish = 15;

    // Declares all the rooms in the game
    public Room home, dirtRoad1, dirtRoad2, dirtRoad3, dirtRoad4, dirtRoad5, city, bank, powerplant, windmills,
            housing, park, hospital, waterPlant, waterTreatmentPlant, school, sportsFacility, market;

    public TownHall townHall;


    public Game() {
        //Calls function for creating rooms
        createRooms();
        //adds a parser to the game
        parser = new Parser();
    }


    private void createRooms() {


        // Creates all the rooms, and sets their description.
        home = new Room("Home","in your home.", "in your home", false, "up");
        dirtRoad1 = new Room("Dirt road","outside on a dirt road.", "outside on an asphalt road", false, "all");
        dirtRoad2 = new Room("Dirt road2","outside on a dirt road.", "outside on an asphalt road", false, "all");
        dirtRoad3 = new Room("Dirt road3","outside on a dirt road.", "outside on an asphalt road", false, "all");
        dirtRoad4 = new Room("Dirt road","outside on a dirt road.", "outside on an asphalt road", false, "all");
        dirtRoad5 = new Room("Dirt road2","outside on a dirt road.", "outside on an asphalt road", false, "all");
        city = new Room("City","in the big city.", "in the big city.", false, "all");
        bank = new Room("Bank","in the bank.", "in the bank.", false, "right");
        townHall = new TownHall("Town hall","in the town hall.", "in the town hall.", false, "down");
        powerplant = new Room("Powerplant", "on an empty plot of land.\n" +
                "This would make a great spot for a powerplant.", "at the powerplant.", true, "up");
        housing = new Room("Housing","in the housing area.", "in the housing area.", true, "down");
        park = new Room("Park","on an empty plot of land.\n" +
                "This would make a great spot for a park.", "at the park.", true, "down");
        hospital = new Room("Hospital", "on an empty plot of land.\n" +
                "This would make a great spot for a hospital.", "at the hospital", true, "up");
        waterPlant = new Room("Water plant", "on an empty plot of land.\n" +
                "This would make a great spot for a waterplant.", "at the water plant.", true, "down");
        waterTreatmentPlant = new Room("Watertreatment facility","on an empty plot of land.\n" +
                "This would make a great spot for a water treatment plant.", "at the water treatment plant", true,"up");
        school = new Room("School", "on an empty plot of land.\n" +
                "This would make a great spot for a school.", "at the school", true, "down");
        sportsFacility = new Room("Sports facility","on an empty plot of land.\n" +
                "This would make a great spot for a sports facility.", "at the sports facility", true, "up");
        windmills = new Room("Windmills","on an empty plot of land.\n" +
                "This would make a great spot for some windmills.", "at the windmill park", true, "up");
        market = new Room("Market","on an empty plot of land.\n" +
                "This would make a great spot for a market.", "at the market", true, "down");

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

    // Prints a welcome message and shows the 'help' command
    private void printWelcome() {
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
    protected boolean processCommand(Command command) {
        boolean wantToQuit = false;

        // Stores the enum from the command in a variable
        Action commandWord = command.getCommandWord();

        switch (command.getCommandWord()) {
            case GO:
                goRoom(command);
                break;
            case BUY:
                if (command.hasSecondWord() && currentRoom == townHall) {
                    // Buys item from vendor
                    Item item = townHall.inventory.getItem(command.getSecondWord());
                    if (item == null) {
                        System.out.println("No item by that name.");
                        return false;
                    }
                    if (player.getBalance() > item.getPrice()) {
                        player.inventory.addItem(item);
                        townHall.inventory.removeItem(item);
                        player.setCanSleep(true);
                        player.setBalance(player.getBalance() - item.getPrice());

                        System.out.println("You bought the item");
                        System.out.println("You can buy these items to upgrade your town:");
                        System.out.println(townHall.toString());
                        System.out.println("To buy an item, type BUY and the name of the item.");
                        break;
                    }
                } else if (player.getBalance() >= currentRoom.getPrice() && currentRoom.buyable(player.getLifeQuality()) && !command.hasSecondWord()) {
                    // Buys room if the player has enough money and it isn't max level. HAS LIFEQUALITY CHECK
                    player.setBalance(player.getBalance() - currentRoom.getPrice());
                    player.setIncome(player.getIncome() + currentRoom.getPayPerLevel());
                    player.setLifeQuality(player.getLifeQuality() + currentRoom.getQualityPerLevel());

                    currentRoom.buy();
                    player.setCanSleep(true);
                    System.out.println(currentRoom.getDescription());
                    break;
                } else {
                    System.out.println("Nothing was bought");
                    break;
                }
            case UNKNOWN:
                // If the enum is the UNKNOWN, which means it isn't one of the pre-made commands, it ends the game turn
                // This should be sent to the GUI handler in the future
                System.out.println("I don't know what you mean...");
                return false;
            case HELP:
                // The help command
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
                    break;
                } else {
                    printHelp();
                    break;
                }
            case STATUS:
                printStatus(command);
                break;
            case INVENTORY:
                if (!player.inventory.isEmpty()) {
                    System.out.println(player.inventory.toString());
                    break;
                } else {
                    System.out.println("Inventory is empty.");
                    break;
                }
            case SLEEP:
                if (command.hasSecondWord()) {
                    System.out.println("Sleep command doesn't take a second argument");
                    break;
                } else {
                    if (currentRoom == home) {
                        if (player.isCanSleep()) {
                            //go to sleep
                            System.out.println("Going to sleep");
                            player.setBalance(player.getBalance() + player.getIncome());
                            day++;
                            System.out.println("You wake up on day " + day + " and check your bank account. Balance: " + player.getBalance());
                            player.setCanSleep(false);
                            break;
                        } else {
                            System.out.println("You are not tired. Go do something.");
                            break;
                        }
                    } else {
                        System.out.println("You can't sleep here");
                        break;
                    }
                }
            case USE:
                if (command.hasSecondWord()) {
                    if (currentRoom == dirtRoad1 || currentRoom == dirtRoad2 || currentRoom == dirtRoad3 || currentRoom == dirtRoad4 || currentRoom == dirtRoad5) {
                        // Upgrades a dirt road, using an item
                        Item item = player.inventory.getItem(command.getSecondWord());
                        if (item == null) {
                            System.out.println("No item by that name.");
                            return false;
                        }
                        player.inventory.removeItem(item);
                        player.setCanSleep(true);

                        currentRoom.buy();
                        player.lifeQuality += currentRoom.getQuality();
                        System.out.println("You upgraded the road.");
                        System.out.println(currentRoom.getDescription());
                        break;
                    } else {
                        System.out.println("You can't use that here.");
                        break;
                    }
                } else {
                    System.out.println("You cant do that.");
                    break;
                }
            case QUIT:
                // Sets the quit condition to true, if the correct quit command is the input
                wantToQuit = quit(command);

        }
        return false;
    }


    // The help message to be displayed when the 'help' command is given
    private void printHelp() {
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
    private void goRoom(Command command) {
        // The 'go' command requires a direction. This checks to see if it does, not if it's valid
        if (!command.hasSecondWord()) {
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
        } else {
            // Changes the current that the player is in, and displays the new current room and exits
            currentRoom = nextRoom;
            // Should be sent to the GUI
            System.out.println(currentRoom.getDescription());
            if (currentRoom == townHall) {
                System.out.println();
                System.out.println("You can buy these items to upgrade your town:");
                System.out.println(townHall.toString());
                System.out.println("To buy an item, type BUY and the name of the item.");
            }
        }
    }


    private void printStatus(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Status command doesn't take an argument");
        } else {
            System.out.println("Balance: $" + player.getBalance() + " | Life quality: " + player.getLifeQuality() + " | Income: $" + player.getIncome() + " per day");
        }
    }

    // Returns true if the 'quit' command is given, and only contains that command
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
