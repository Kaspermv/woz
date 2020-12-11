package worldofzuul;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


public class ViewManager {

    // Width and height of the game window, not the entire window
    public int GAMEHEIGHT = 800;
    public int WIDTH = 800;

    // X location of the buttons in the menu
    final private int buttonX = 420;

    // If true, the hitboxes will be rendered as semi-transparent, as opposed to transparent
    public boolean debugMode = false;

    public AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private AnimationTimer gameTimer;

    // Values used for movement
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;

    // 'E' key, used for interacting in the environment
    private boolean actionKey;

    // Exit hitboxes
    private Rectangle upRect;
    private Rectangle downRect;
    private Rectangle leftRect;
    private Rectangle rightRect;

    // Various images and their hitboxes
    private ImageView bubble;
    private ImageView textbox;
    private ImageView dataFrame;
    private Text dataText;
    private ImageView sign;
    private Rectangle signRect;
    private Rectangle bedRect;
    private ImageView bed;
    private Rectangle townHallRect;

    // Player hitbox
    public Rectangle playerRect;

    // More images and the text object
    private Text bottomTextBox;
    private ImageView buyButtonImage;
    private ImageView buyButtonPressedImage;
    private ImageView sleepPressedButtonImage;
    private ImageView sleepButtonImage;
    private ImageView useButtonImage;
    private ImageView usePressedButtonImage;

    // A hashmap of all the background images, stored as Background objects
    public HashMap<String, Background> backgrounds;
    // A File object, directed to the folder where the background images are
    final File dir = new File("Graphics/Backgrounds");

    // Values used for the displaying of the player inventory
    private GridPane inventoryPane;
    private ImageView inventoryBackground;
    private Image deed;
    private final double vGap = 4;
    private final double hGap = 4;

    // A player object, that has values related to the visuals of the player
    public PlayerGraphics player;

    // The game logic
    public Game game;

    public ViewManager() {
        // Instantiating the game and player graphics objects
        game = new Game();
        player = new PlayerGraphics();
        // Display player on top (lower is on top, 0 is default)
        player.img.setViewOrder(-1);
        // Loads background images into a hashmap
        backgrounds = new HashMap<>();
        try {
            for (final File imgFile : dir.listFiles()) {
                Image tempImage = new Image(imgFile.getPath());
                BackgroundImage tempBackgroundImage = new BackgroundImage(tempImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(WIDTH, GAMEHEIGHT, false, false, true, false));
                Background tempBackground = new Background(tempBackgroundImage);
                // Gets the name of the png image and uses it as the key for storing the related image in the hashmap
                String name = imgFile.getName().substring(0, imgFile.getName().lastIndexOf("."));
                backgrounds.put(name, tempBackground);
            }
            // Load other images
            textbox = new ImageView(new Image("Graphics/Tekst boks.png"));
            sign = new ImageView(new Image("Graphics/Skilt.png", 170, 150, true, false));
            bubble = new ImageView(new Image("Graphics/Bubble.png"));
            buyButtonImage = new ImageView(new Image("Graphics/Buy.png"));
            buyButtonPressedImage = new ImageView(new Image("Graphics/Buy pressed.png"));
            useButtonImage = new ImageView(new Image("Graphics/Use.png"));
            usePressedButtonImage = new ImageView(new Image("Graphics/Use pressed.png"));
            dataFrame = new ImageView(new Image("Graphics/Data frame.png"));
            bed = new ImageView(new Image("Graphics/Bed.png"));
            sleepButtonImage = new ImageView(new Image("Graphics/Sleep.png"));
            sleepPressedButtonImage = new ImageView(new Image("Graphics/Sleep pressed.png"));
            inventoryBackground = new ImageView(new Image("Graphics/Inventory background.png"));
            deed = new Image("Graphics/Skøde.png");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to load images.");
        }

        // Bed setup
        bed.setLayoutX(600);
        bed.setLayoutY(450);
        bedRect = CreateHitbox.imageView(bed);
        if (debugMode) {
            bedRect.setFill(Color.color(0, 0, 0, 0.2));
        } else {
            bedRect.setFill(Color.color(0, 0, 0, 0));
        }

        // Sign setup
        sign.setLayoutX(620);
        sign.setLayoutY(500);
        signRect = CreateHitbox.imageView(sign);
        if (debugMode) {
            signRect.setFill(Color.color(0, 0, 0, 0.2));
        } else {
            signRect.setFill(Color.color(0, 0, 0, 0));
        }

        // Townhall setup
        townHallRect = new Rectangle(200,100,400,150);
        if (debugMode){
            townHallRect.setFill(Color.color(0,0,0,0.2));
        } else townHallRect.setFill(Color.color(0,0,0,0.0));

        // Textbox setup
        textbox.setY(800);
        textbox.setX(0);
        bottomTextBox = new Text(textbox.getX() + 25, textbox.getY() + 35, game.currentRoom.getDescription());

        // Data frame setup
        dataFrame.setLayoutX(0);
        dataFrame.setLayoutY(0);
        dataFrame.setViewOrder(-2);
        dataText = new Text(8, 18, MessageFormat.format("Balance: ${0,number,integer} Life quality: {1,number,integer} Income pr day: ${2,number,integer}", game.player.balance, game.player.lifeQuality, game.player.income));
        dataText.setFont(new Font(12.5));
        dataText.setViewOrder(-3);

        // Creation of buttons in seperate methods for easier reading because they use handlers for the Mouse Events
        createBuyButton();
        createSleepButton();
        createUseButton();

        // Instantiating the window ect.
        inventoryPane = new GridPane();
        mainPane = new AnchorPane(inventoryPane);
        mainScene = new Scene(mainPane, WIDTH, GAMEHEIGHT + textbox.getImage().getHeight());
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        // Setup inventory
        // The values have been fine tuned by the pixel, hence the odd numbers
        updateInventory(game.player.inventory.getInventoryMap());
        inventoryPane.setVgap(vGap);
        inventoryPane.setHgap(hGap);
        inventoryPane.setLayoutX(608);
        inventoryPane.setLayoutY(GAMEHEIGHT + 45);
        inventoryPane.setViewOrder(-2);
        inventoryPane.setGridLinesVisible(debugMode);
        inventoryBackground.setLayoutX(604);
        inventoryBackground.setLayoutY(GAMEHEIGHT + 20);
        inventoryBackground.setViewOrder(-1);

        // Generate exit rects
        upRect = new Rectangle(300, 0, 200, 5);
        upRect.setFill(Color.GRAY);
        downRect = new Rectangle(300, GAMEHEIGHT - 5, 200, 5);
        downRect.setFill(Color.GRAY);
        rightRect = new Rectangle(WIDTH - 5, 300, 5, 200);
        rightRect.setFill(Color.GRAY);
        leftRect = new Rectangle(0, 300, 5, 200);
        leftRect.setFill(Color.GRAY);

        // Adds the initial nodes to the scene (AnchorPane)
        createExits();
        mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        mainPane.getChildren().addAll(player.img, textbox, dataFrame, dataText, bedRect, bed, inventoryBackground);

        // Creating player hitbox
        playerRect = new Rectangle();
        if (debugMode) {
            playerRect.setFill(Color.color(0, 0, 0, 0.2));
        } else {
            playerRect.setFill(Color.color(0, 0, 0, 0));
        }
        playerRect.setX(400 + 40);
        playerRect.setY(400 + 3);
        playerRect.setHeight(125);
        playerRect.setWidth(50);
        // Adding player hitbox separately, due to position origin resetting
        mainPane.getChildren().add(playerRect);
    }
    // Used to calculate delta time between frames, for a consistent movementspeed, across different monitors with different refresh rates
    long prevTime;

    // Game loop
    public void createGameLoop() {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                // Moves the player dependent on delta time
                move(System.currentTimeMillis() - prevTime);

                // Checks for collision with exits
                if (mainPane.getChildren().contains(upRect) && playerRect.intersects(upRect.getLayoutBounds())) {
                    changeRoom("up");
                }
                if (mainPane.getChildren().contains(downRect) && playerRect.intersects(downRect.getLayoutBounds())) {
                    changeRoom("down");
                }
                if (mainPane.getChildren().contains(leftRect) && playerRect.intersects(leftRect.getLayoutBounds())) {
                    changeRoom("left");
                }
                if (mainPane.getChildren().contains(rightRect) && playerRect.intersects(rightRect.getLayoutBounds())) {
                    changeRoom("right");
                }

                // Check for sign collision
                if (mainPane.getChildren().contains(signRect) && playerRect.intersects(signRect.getLayoutBounds())) {
                    // Show the bubble
                    showBubble();

                    if (actionKey) {
                        // Checks if the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            buyMenu();
                        }
                    }
                } else if (mainPane.getChildren().contains(bedRect) && playerRect.intersects(bedRect.getLayoutBounds())) {
                    // Show the bubble
                    showBubble();

                    if (actionKey) {
                        // Checks if the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            sleepMenu();
                        }
                    }
                } else if (mainPane.getChildren().contains(townHallRect) && playerRect.intersects(townHallRect.getLayoutBounds()) && !game.townHall.inventory.getInventoryMap().isEmpty()) {
                    // Show the bubble
                    showBubble();

                    if (actionKey) {
                        // Checks if the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            townhallMenu();
                        }
                    }

                } else if (game.currentRoom.description.equals("outside on a dirt road.") && !game.player.inventory.getInventoryMap().isEmpty() && game.currentRoom.currentLevel != game.currentRoom.maxLevel) {
                    //show the bubble
                    showBubble();

                    if (actionKey) {
                        // Checks if the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            useMenu();
                        }
                    }


                } else if (mainPane.getChildren().contains(bubble)) {
                    // Removes all the menu options if no collision is detected anymore
                    mainPane.getChildren().removeAll(bubble, bottomTextBox, buyButtonImage, buyButtonPressedImage, sleepButtonImage, sleepPressedButtonImage, useButtonImage, usePressedButtonImage);
                    buyButtonPressedImage.setViewOrder(0);
                    sleepPressedButtonImage.setViewOrder(0);
                    usePressedButtonImage.setViewOrder(0);
                }
                prevTime = System.currentTimeMillis();
            }
        };
        gameTimer.start();
    }

    public void updateInventory(HashMap<Integer, Item> inventory) {
        // When updating the inventory view, it's first cleared, so no duplicate errors occur
        inventoryPane.getChildren().clear();
        int number = 0;
        // The calculated width and height of the individual item cell
        double cellWidth = (164 - 2 * vGap) / 3;
        double cellHeight = (110 - hGap - 20) / 2;

        // Loop over the given hashmap of items
        for (Map.Entry<Integer, Item> ded : inventory.entrySet()) {
            // Creates a new ImageView object for each item, because the same image cannot be added more than once
            ImageView deedImage = new ImageView(deed);
            deedImage.setFitWidth(cellWidth);
            deedImage.setFitHeight(cellHeight);
            deedImage.setPreserveRatio(true);

            Pane pane = new Pane();

            pane.getChildren().add(deedImage);
            pane.setPrefSize(cellWidth, cellHeight);
            // Adds the image to the inventory grid pane at the calculated row and column
            inventoryPane.add(deedImage, number%3, Math.floorDiv(number,3));
            number++;
        }

    }

    // Shows a bubble that indicates an interaction is possible
    public void showBubble() {
        // Only add the image if it's not already been added
        if (!mainPane.getChildren().contains(bubble)) {
            mainPane.getChildren().add(bubble);
        }
        // Update the location of the bubble to be right above the players head (called every frame the bubble is active)
        bubble.setLayoutX(playerRect.getX() + playerRect.getWidth() / 2 - bubble.getImage().getWidth() / 2);
        bubble.setLayoutY(playerRect.getY() - bubble.getImage().getHeight() - 5);
    }

    // Shows text in the textbox and a button for sleeping
    public void sleepMenu() {
        bottomTextBox.setText("Sleep here for a nights rest, and gain income.");
        bottomTextBox.setFont(new Font(14));

        mainPane.getChildren().addAll(bottomTextBox, sleepPressedButtonImage, sleepButtonImage);
    }

    // Shows text in the textbox and a button for buying an item in the town hall
    public void townhallMenu(){
        bottomTextBox.setText("You can buy this item to upgrade your town:\n" + game.townHall.inventory.getItem("Road-upgrade") + "\nPress BUY to buy one upgrade form.\nYou can buy up to 5.");
        bottomTextBox.setFont(new Font(14));
        bottomTextBox.setWrappingWidth(380);

        mainPane.getChildren().addAll(bottomTextBox, buyButtonPressedImage, buyButtonImage);
    }

    // Shows text in the textbox and a button for buying a property or upgrading it
    public void buyMenu() {
        bottomTextBox.setText(game.currentRoom.getDescription());
        bottomTextBox.setFont(new Font(14));
        bottomTextBox.setWrappingWidth(380);

        mainPane.getChildren().addAll(bottomTextBox, buyButtonPressedImage, buyButtonImage);
    }

    // Shows text in the textbox and a button for using an item on a dirt road
    public void useMenu() {
        bottomTextBox.setText("Do you want to use a deed to upgrade the dirt road?");
        bottomTextBox.setFont(new Font(14));
        bottomTextBox.setWrappingWidth(380);

        mainPane.getChildren().addAll(bottomTextBox, usePressedButtonImage, useButtonImage);
    }

    // Create the sleep button and the Mouse Handlers for the button
    public void createSleepButton() {
        // Display the button
        sleepButtonImage.setLayoutX(buttonX);
        sleepButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - sleepButtonImage.getImage().getHeight() / 2);
        sleepPressedButtonImage.setLayoutX(buttonX);
        sleepPressedButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - sleepPressedButtonImage.getImage().getHeight() / 2);

        // When the button image is pressed, the pressed image is displayed on top
        sleepButtonImage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sleepPressedButtonImage.setViewOrder(-1);
                event.consume();
            }
        });
        // When the button is released, the sleep action is performed in the game logic
        sleepButtonImage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sleepPressedButtonImage.setViewOrder(0);
                // If the player is able to sleep, they do, and the appropriate message is displayed in the text box
                if (game.player.isCanSleep()) {
                    game.processCommand(new Command(Action.SLEEP, null));
                    // The data at the top of the screen is re-drawn
                    dataText.setText(MessageFormat.format("Balance: ${0,number,integer} Life quality: {1,number,integer} Income pr day: ${2,number,integer}", game.player.balance, game.player.lifeQuality, game.player.income));
                    bottomTextBox.setText("You wake up on day " + game.day + ", check your bank account.");
                } else {
                    bottomTextBox.setText("You are not tired. Go do something.");
                }
                event.consume();
            }
        });
    }

    // Create the use button and the Mouse Handlers for the button
    public void createUseButton() {
        // Display the button
        useButtonImage.setLayoutX(400);
        useButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - useButtonImage.getImage().getHeight() / 2);
        usePressedButtonImage.setLayoutX(400);
        usePressedButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - usePressedButtonImage.getImage().getHeight() / 2);

        // When the button image is pressed, the pressed image is displayed on top
        useButtonImage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                usePressedButtonImage.setViewOrder(-1);
                event.consume();
            }
        });
        // When the button is released, the use action is performed in the game logic
        useButtonImage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                usePressedButtonImage.setViewOrder(0);
                game.processCommand(new Command(Action.USE, "Road-upgrade"));
                // The inventory visuals are updated
                updateInventory(game.player.inventory.getInventoryMap());
                // The background is re-picked and drawn
                chooseBackground();

                event.consume();
            }
        });
    }

    // Create the use button and the Mouse Handlers for the button
    public void createBuyButton() {
        // Display the button
        buyButtonImage.setLayoutX(buttonX);
        buyButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - buyButtonImage.getImage().getHeight() / 2);
        buyButtonPressedImage.setLayoutX(buttonX);
        buyButtonPressedImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - buyButtonPressedImage.getImage().getHeight() / 2);

        // When the button image is pressed, the pressed image is displayed on top
        buyButtonImage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buyButtonPressedImage.setViewOrder(-1);
                event.consume();
            }
        });
        // When the button is released, the buy action is performed in the game logic
        buyButtonImage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buyButtonPressedImage.setViewOrder(0);

                // If the player is in the town hall they try to buy an item, else they try to buy real-estate
                if (game.currentRoom.name == "Town hall"){
                    // If the player can buy the item, they do
                    if (game.player.getBalance() > game.townHall.inventory.getItem("Road-upgrade").getPrice()){
                        // The game logic is ran, so the player gets an item
                        game.processCommand(new Command(Action.BUY, "Road-upgrade"));
                        bottomTextBox.setText("You bought an item, you can use it on a dirt road.");
                        // The inventory visuals are updated
                        updateInventory(game.player.inventory.getInventoryMap());
                    } else bottomTextBox.setText("You dont have enough money.");

                } else{
                    // If the player can buy the real-estate they do
                    if (game.player.getBalance() > game.currentRoom.getPrice()){
                        // The game logic is ran, so the room is upgraded
                        game.processCommand(new Command(Action.BUY, null));
                        // The background is re-picked and drawn
                        chooseBackground();
                        // If the room can be upgraded further, the ned description is displayed
                        bottomTextBox.setText(game.currentRoom.getDescription());
                    } else bottomTextBox.setText("You dont have enough money.");
                }
                // This button changes the players stats, so it's re-drawn
                dataText.setText(MessageFormat.format("Balance: ${0,number,integer} Life quality: {1,number,integer} Income pr day: ${2,number,integer}", game.player.balance, game.player.lifeQuality, game.player.income));
                event.consume();
            }
        });
    }

    // Adds the currently needed exit hitboxes for the current room
    public void createExits() {
        switch (game.currentRoom.exitLocations) {
            case "up":
                mainPane.getChildren().removeAll(upRect, downRect, leftRect, rightRect);
                mainPane.getChildren().addAll(upRect);
                break;
            case "down":
                mainPane.getChildren().removeAll(upRect, downRect, leftRect, rightRect);
                mainPane.getChildren().add(downRect);
                break;
            case "right":
                mainPane.getChildren().removeAll(upRect, downRect, leftRect, rightRect);
                mainPane.getChildren().add(rightRect);
                break;
            case "all":
                mainPane.getChildren().removeAll(upRect, downRect, leftRect, rightRect);
                mainPane.getChildren().addAll(upRect, downRect, leftRect, rightRect);
                break;
        }
    }

    // Changes the current room in logic and visually
    public void changeRoom(String direction) {
        // The logic is ran
        game.processCommand(new Command(Action.GO, direction));

        // The correct background is drawn
        chooseBackground();

        // The custom interactive hitboxes are added if needed
        if (game.currentRoom.name == "Home") {
            mainPane.getChildren().addAll(bedRect, bed);
        } else if (game.currentRoom.name == "Town hall") {
            mainPane.getChildren().add(townHallRect);
        } else {
            mainPane.getChildren().removeAll(bedRect, bed,townHallRect);
        }

        // Reset player position
        if (direction == "up") {
            playerRect.setY(GAMEHEIGHT - playerRect.getHeight() - 10);
            player.setYPos(GAMEHEIGHT - playerRect.getHeight() - 10);
        } else if (direction == "down") {
            playerRect.setY(10);
            player.setYPos(10);
        } else if (direction == "left") {
            playerRect.setX(WIDTH - playerRect.getWidth() - 10);
            player.setXPos(WIDTH - playerRect.getWidth() - 13);
        } else if (direction == "right") {
            playerRect.setX(playerRect.getWidth() + 10);
            player.setXPos(playerRect.getWidth() + 7);
        }
        // Redo the exits for the current room
        createExits();
    }

    // Changes the current background is displayed, based on the currentRoom value in game
    public void chooseBackground() {
        // Choose which background and sign to display
        if (!game.currentRoom.hasPrice && game.currentRoom.currentLevel != game.currentRoom.maxLevel) {
            // Dirt road
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            mainPane.getChildren().removeAll(sign, signRect);
        } else if (!game.currentRoom.hasPrice && game.currentRoom.currentLevel == game.currentRoom.maxLevel) {
            // Upgraded road
            if (game.currentRoom.description.equals("outside on a dirt road.")) {
                mainPane.setBackground(backgrounds.get("Asphalt road"));
            } else{
                mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            }
            mainPane.getChildren().removeAll(sign, signRect);
        } else if (game.currentRoom.currentLevel == 0) {
            // Empty plots of land. Not housing and power plant
            if (game.currentRoom.name == "Housing" || game.currentRoom.name == "Powerplant") {
                mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            } else if (game.currentRoom.exitLocations == "up") {
                mainPane.setBackground(backgrounds.get("Plot down"));
            } else if (game.currentRoom.exitLocations == "down") {
                mainPane.setBackground(backgrounds.get("Plot up"));
            }
            // Show sign for upgrades
            if (!mainPane.getChildren().contains(sign)) {
                mainPane.getChildren().addAll(sign, signRect);
            }

        } else if (game.currentRoom.currentLevel != game.currentRoom.maxLevel) {
            // Not fully upgraded places
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            if (!mainPane.getChildren().contains(sign)) {
                mainPane.getChildren().addAll(sign, signRect);
            }
        } else {
            // Fully upgraded places and others
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            mainPane.getChildren().removeAll(sign, signRect);
        }
    }

    public void move(double deltaTime) {
        if (movingLeft) {
            // Border check left (Dependent on player size)
            if (playerRect.getX() > 0) {
                player.setXPos(player.getXPos() - player.MOVEMENTSPEED * deltaTime);
                playerRect.setX(playerRect.getX() - player.MOVEMENTSPEED * deltaTime);
            }
        }
        if (movingRight) {
            // Border check right (Dependent on player size)
            if (playerRect.getX() < WIDTH - playerRect.getWidth()) {
                player.setXPos(player.getXPos() + player.MOVEMENTSPEED * deltaTime);
                playerRect.setX(playerRect.getX() + player.MOVEMENTSPEED * deltaTime);
            }
        }
        if (movingUp) {
            // Border check top (Dependent on player size)
            if (playerRect.getY() > 0) {
                player.setYPos(player.getYPos() - player.MOVEMENTSPEED * deltaTime);
                playerRect.setY(playerRect.getY() - player.MOVEMENTSPEED * deltaTime);
            }
        }
        if (movingDown) {
            // Border check bottom (Dependent on player size)
            if (playerRect.getY() < GAMEHEIGHT - playerRect.getHeight()) {
                player.setYPos(player.getYPos() + player.MOVEMENTSPEED * deltaTime);
                playerRect.setY(playerRect.getY() + player.MOVEMENTSPEED * deltaTime);
            }
        }
    }

    // Creates an always active key listener that sets values to true/false depending on the activation of the WASD keys or E
    public void createKeyListeners() {
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // Movement
                if (keyEvent.getCode() == KeyCode.A) {
                    movingLeft = true;
                } else if (keyEvent.getCode() == KeyCode.D) {
                    movingRight = true;
                } else if (keyEvent.getCode() == KeyCode.W) {
                    movingUp = true;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    movingDown = true;
                }

                // Interaction
                if (keyEvent.getCode() == KeyCode.E) {
                    actionKey = true;
                }
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    movingLeft = false;
                } else if (keyEvent.getCode() == KeyCode.D) {
                    movingRight = false;
                } else if (keyEvent.getCode() == KeyCode.W) {
                    movingUp = false;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    movingDown = false;
                }

                // Interaction
                if (keyEvent.getCode() == KeyCode.E) {
                    actionKey = false;
                }
            }
        });
    }

    // Returns the stage thats being created in this class, for use in the Start class
    public Stage getMainStage() {
        return mainStage;
    }
}
