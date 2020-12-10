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

    public int GAMEHEIGHT = 800;
    public int WIDTH = 800;

    public boolean debugMode = false;

    public AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private AnimationTimer gameTimer;

    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;

    private boolean actionKey;

    // Exit hitboxes
    private Rectangle upRect;
    private Rectangle downRect;
    private Rectangle leftRect;
    private Rectangle rightRect;

    private ImageView bubble;
    private ImageView textbox;
    private ImageView dataFrame;
    private Text dataText;
    private ImageView sign;
    private Rectangle signRect;
    private Rectangle bedRect;
    private ImageView bed;

    public Rectangle playerRect;

    private Text bottomTextBox;
    private ImageView buyButtonImage;
    private ImageView buyButtonPressedImage;
    private ImageView sleepPressedButtonImage;
    private ImageView sleepButtonImage;
    private ImageView useButtonImage;
    private ImageView usePressedButtonImage;

    public HashMap<String, Background> backgrounds;
    final File dir = new File("Graphics/Backgrounds");

    private GridPane inventoryPane;
    private ImageView inventoryBackground;
    private Image deed;
    private final double vGap = 4;
    private final double hGap = 4;

    public PlayerGraphics player;

    public Game game;

    public ViewManager() {
        game = new Game();
        player = new PlayerGraphics();
        // Display player on top
        player.img.setViewOrder(-1);
        game.player.inventory.addItem(new Item("Road-upgrade", "Upgrades dirt road to asphalt road", 75, 1));
        game.player.inventory.addItem(new Item("Road-upgrade", "Upgrades dirt road to asphalt road", 75, 2));
        game.player.inventory.addItem(new Item("Road-upgrade", "Upgrades dirt road to asphalt road", 75, 3));
        game.player.inventory.addItem(new Item("Road-upgrade", "Upgrades dirt road to asphalt road", 75, 4));
        // Loads background images into a hashmap
        backgrounds = new HashMap<>();
        try {
            for (final File imgFile : dir.listFiles()) {
                Image tempImage = new Image(imgFile.getPath());
                BackgroundImage tempBackgroundImage = new BackgroundImage(tempImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(WIDTH, GAMEHEIGHT, false, false, true, false));
                Background tempBackground = new Background(tempBackgroundImage);
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

        createBuyButton();
        createSleepButton();
        createUseButton();

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
        inventoryPane.setGridLinesVisible(true);
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

        mainPane.getChildren().add(playerRect);
    }



    long prevTime;

    // Game loop
    public void createGameLoop() {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long l) {

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
                        // Checks is the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            buyMenu();
                        }
                    }
                } else if (mainPane.getChildren().contains(bedRect) && playerRect.intersects(bedRect.getLayoutBounds())) {
                    // Show the bubble
                    showBubble();

                    if (actionKey) {
                        // Checks is the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            sleepMenu();
                        }
                    }

                } else if (game.currentRoom.description.equals("outside on a dirt road.") && !game.player.inventory.getInventoryMap().isEmpty()) {
                    //show the bubble
                    showBubble();

                    if (actionKey) {
                        // Checks if the textbox has already been added, since this will be true as long as you hold action key
                        if (!mainPane.getChildren().contains(bottomTextBox)) {
                            useMenu();
                        }
                    }


                } else if (mainPane.getChildren().contains(bubble)) {
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
        int i = 0;
        int j = 0;
        //ImageView deedImage = new ImageView(deed);

        double cellWidth = (164 - 2 * vGap) / 3;
        double cellHeight = (110 - hGap - 20) / 2;
        for (Map.Entry<Integer, Item> ded : inventory.entrySet()) {
            ImageView deedImage = new ImageView(deed);
            deedImage.setFitWidth(cellWidth);
            deedImage.setFitHeight(cellHeight);
            deedImage.setPreserveRatio(true);

            Pane pane = new Pane();
            pane.getChildren().add(deedImage);
            pane.setPrefSize(cellWidth, cellHeight);


            inventoryPane.add(deedImage, j, i);

            if (j < 2) {
                j++;
            } else if (j <= 2) {
                j = 0;
                i++;
            } else if (i > 1) {
                System.out.println("what");
            }


        }

    }

    public void showBubble() {
        if (!mainPane.getChildren().contains(bubble)) {
            mainPane.getChildren().add(bubble);
        }
        bubble.setLayoutX(playerRect.getX() + playerRect.getWidth() / 2 - bubble.getImage().getWidth() / 2);
        bubble.setLayoutY(playerRect.getY() - bubble.getImage().getHeight() - 5);
    }

    public void sleepMenu() {
        bottomTextBox.setText("Sleep here for a nights rest, and gain income.");
        bottomTextBox.setFont(new Font(14));

        mainPane.getChildren().addAll(bottomTextBox, sleepPressedButtonImage, sleepButtonImage);
    }

    public void buyMenu() {
        bottomTextBox.setText(game.currentRoom.getDescription());
        bottomTextBox.setFont(new Font(14));
        bottomTextBox.setWrappingWidth(380);

        mainPane.getChildren().addAll(bottomTextBox, buyButtonPressedImage, buyButtonImage);
    }

    public void useMenu() {
        bottomTextBox.setText("Do you want to use a deed to upgrade the dirt road?");
        bottomTextBox.setFont(new Font(14));
        bottomTextBox.setWrappingWidth(380);

        mainPane.getChildren().addAll(bottomTextBox, usePressedButtonImage, useButtonImage);
    }

    public void createSleepButton() {
        sleepButtonImage.setLayoutX(400);
        sleepButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - sleepButtonImage.getImage().getHeight() / 2);
        sleepPressedButtonImage.setLayoutX(400);
        sleepPressedButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - sleepPressedButtonImage.getImage().getHeight() / 2);

        sleepButtonImage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sleepPressedButtonImage.setViewOrder(-1);
                event.consume();
            }
        });
        sleepButtonImage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sleepPressedButtonImage.setViewOrder(0);
                if (game.player.isCanSleep()) {
                    game.processCommand(new Command(Action.SLEEP, null));
                    dataText.setText(MessageFormat.format("Balance: ${0,number,integer} Life quality: {1,number,integer} Income pr day: ${2,number,integer}", game.player.balance, game.player.lifeQuality, game.player.income));
                    bottomTextBox.setText("You wake up on day " + game.day + ", check your bank account.");
                } else {
                    bottomTextBox.setText("You are not tired. Go do something.");
                }
                event.consume();
            }
        });
    }

    public void createUseButton() {
        useButtonImage.setLayoutX(400);
        useButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - useButtonImage.getImage().getHeight() / 2);
        usePressedButtonImage.setLayoutX(400);
        usePressedButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - usePressedButtonImage.getImage().getHeight() / 2);

        useButtonImage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                usePressedButtonImage.setViewOrder(-1);
                event.consume();
            }
        });
        useButtonImage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                usePressedButtonImage.setViewOrder(0);
                game.processCommand(new Command(Action.USE, "Road-upgrade"));
                updateInventory(game.player.inventory.getInventoryMap());
                chooseBackground();

                event.consume();
            }
        });
    }




    public void createBuyButton() {
        buyButtonImage.setLayoutX(400);
        buyButtonImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - buyButtonImage.getImage().getHeight() / 2);
        buyButtonPressedImage.setLayoutX(400);
        buyButtonPressedImage.setLayoutY(GAMEHEIGHT + textbox.getImage().getHeight() / 2 - buyButtonPressedImage.getImage().getHeight() / 2);

        buyButtonImage.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buyButtonPressedImage.setViewOrder(-1);
                event.consume();
            }
        });
        buyButtonImage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buyButtonPressedImage.setViewOrder(0);
                game.processCommand(new Command(Action.BUY, null));
                dataText.setText(MessageFormat.format("Balance: ${0,number,integer} Life quality: {1,number,integer} Income pr day: ${2,number,integer}", game.player.balance, game.player.lifeQuality, game.player.income));
                chooseBackground();
                bottomTextBox.setText(game.currentRoom.getDescription());
                event.consume();
            }
        });
    }

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

    public void changeRoom(String direction) {
        game.processCommand(new Command(Action.GO, direction));

        chooseBackground();

        if (game.currentRoom.name != "Home") {
            mainPane.getChildren().removeAll(bedRect, bed);
        } else {
            mainPane.getChildren().addAll(bedRect, bed);
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
        createExits();
    }

    public void chooseBackground() {
        // Choose which background and sign to display
        if (!game.currentRoom.hasPrice && game.currentRoom.currentLevel != game.currentRoom.maxLevel) {
            // Dirt road
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            mainPane.getChildren().removeAll(sign, signRect);
        } else if (!game.currentRoom.hasPrice && game.currentRoom.currentLevel == game.currentRoom.maxLevel) {
            // Upgraded road NEEDS NEW GRAPHICS
            mainPane.setBackground(backgrounds.get("Asphalt road"));
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

    public Stage getMainStage() {
        return mainStage;
    }
}
