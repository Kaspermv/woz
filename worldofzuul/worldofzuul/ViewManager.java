package worldofzuul;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.HashMap;

public class ViewManager {

    public int GAMEHEIGHT = 800;
    public int WIDTH = 800;

    public Pane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private AnimationTimer gameTimer;

    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;

    // Exit hitboxes
    private Rectangle upRect;
    private Rectangle downRect;
    private Rectangle leftRect;
    private Rectangle rightRect;

    private ImageView textbox;

    public Rectangle playerRect;

    public HashMap<String, Background> backgrounds;
    final File dir = new File("Graphics/Backgrounds");

    public PlayerGraphics player;

    public Game game;

    public ViewManager(){
        game = new Game();
        player = new PlayerGraphics();
        // Loads background images into a hashmap
        backgrounds = new HashMap<>();
        try{
            for(final File imgFile : dir.listFiles()) {
                Image tempImage = new Image(imgFile.getPath());
                BackgroundImage tempBackgroundImage = new BackgroundImage(tempImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(WIDTH, GAMEHEIGHT,false, false,true,false));
                Background tempBackground = new Background(tempBackgroundImage);
                String name = imgFile.getName().substring(0, imgFile.getName().lastIndexOf("."));
                backgrounds.put(name, tempBackground);
            }
            // Load other images
            textbox = new ImageView(new Image("Graphics/Tekst boks.png"));

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Unable to load images.");
        }

        textbox.setY(800);
        textbox.setX(0);

        mainPane = new Pane();
        mainScene = new Scene(mainPane, WIDTH, GAMEHEIGHT +160);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        // Generate exit rects
        upRect = new Rectangle(300,0,200,5);
        upRect.setFill(Color.GRAY);
        downRect = new Rectangle(300, GAMEHEIGHT -5,200,5);
        downRect.setFill(Color.GRAY);
        rightRect = new Rectangle(WIDTH-5,300,5,200);
        rightRect.setFill(Color.GRAY);
        leftRect = new Rectangle(0,300,5,200);
        leftRect.setFill(Color.GRAY);

        createExits();
        mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        mainPane.getChildren().addAll(player.img, textbox);

        // Creating player hitbox
        playerRect = new Rectangle();
        playerRect.setFill(Color.color(0,0,0,0.2));

        playerRect.setX(400 + 40);
        playerRect.setY(400 + 3);
        playerRect.setHeight(125);
        playerRect.setWidth(50);

        mainPane.getChildren().add(playerRect);

    }
    // Rectangles need size of 5
    public void createGameLoop(){
        gameTimer = new AnimationTimer(){

            @Override
            public void handle(long l) {
                // Game loop

                move();

                // Checks for collision with exits
                if (mainPane.getChildren().contains(upRect) && playerRect.intersects(upRect.getLayoutBounds())){
                    changeRoom("up");
                }
                if (mainPane.getChildren().contains(downRect) && playerRect.intersects(downRect.getLayoutBounds())){
                    changeRoom("down");
                }
                if (mainPane.getChildren().contains(leftRect) && playerRect.intersects(leftRect.getLayoutBounds())){
                    changeRoom("left");
                }
                if (mainPane.getChildren().contains(rightRect) && playerRect.intersects(rightRect.getLayoutBounds())){
                    changeRoom("right");
                }
            }
        };
        gameTimer.start();
    }

    public void createExits(){
        switch (game.currentRoom.exitLocations){
            case "up":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                mainPane.getChildren().addAll(upRect);
                break;
            case "down":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                mainPane.getChildren().add(downRect);
                break;
            case "right":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                mainPane.getChildren().add(rightRect);
                break;
            case "all":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                mainPane.getChildren().addAll(upRect,downRect,leftRect,rightRect);
                break;
        }
    }

    public void changeRoom(String direction){
        game.processCommand(new Command(Action.GO, direction));

        chooseBackground();

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

    public void chooseBackground(){
        // Choose which background to display
        if (!game.currentRoom.hasPrice && game.currentRoom.currentLevel != game.currentRoom.maxLevel) {
            // Dirt road
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        }else  if (!game.currentRoom.hasPrice && game.currentRoom.currentLevel == game.currentRoom.maxLevel) {
            // Upgraded road NEEDS NEW GRAPHICS
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        } else if (game.currentRoom.currentLevel == 0) {
            // Empty plots of land. Not housing
            if (game.currentRoom.name == "Housing") {
                mainPane.setBackground(backgrounds.get(game.currentRoom.name));
            } else if (game.currentRoom.exitLocations == "up"){
                mainPane.setBackground(backgrounds.get("Plot down"));
            } else if (game.currentRoom.exitLocations == "down"){
                mainPane.setBackground(backgrounds.get("Plot up"));
            }
        } else if (game.currentRoom.currentLevel != game.currentRoom.maxLevel) {
            // Not fully upgraded places
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        } else {
            // Fully upgraded places and others
            mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        }
    }

    public void move(){
        if (movingLeft){
            // Border check left (Dependent on player size)
            if (playerRect.getX() > 0){
                player.setXPos(player.getXPos() - player.MOVEMENTSPEED);
                playerRect.setX(playerRect.getX()- player.MOVEMENTSPEED);
            }
        }
        if (movingRight){
            // Border check right (Dependent on player size)
            if (playerRect.getX() < WIDTH - playerRect.getWidth()){
                player.setXPos(player.getXPos() + player.MOVEMENTSPEED);
                playerRect.setX(playerRect.getX() + player.MOVEMENTSPEED);
            }
        }
        if (movingUp){
            // Border check top (Dependent on player size)
            if (playerRect.getY() > 0){
                player.setYPos(player.getYPos() - player.MOVEMENTSPEED);
                playerRect.setY(playerRect.getY()- player.MOVEMENTSPEED);
            }
        }
        if (movingDown){
            // Border check bottom (Dependent on player size)
            if (playerRect.getY() < GAMEHEIGHT - playerRect.getHeight()){
                player.setYPos(player.getYPos() + player.MOVEMENTSPEED);
                playerRect.setY(playerRect.getY() + player.MOVEMENTSPEED);
            }
        }
    }

    public void createKeyListeners(){
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A){
                    movingLeft = true;
                } else if (keyEvent.getCode() == KeyCode.D){
                    movingRight = true;
                } else if (keyEvent.getCode() == KeyCode.W){
                    movingUp = true;
                } else if (keyEvent.getCode() == KeyCode.S){
                    movingDown = true;
                }
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A){
                    movingLeft = false;
                } else if (keyEvent.getCode() == KeyCode.D){
                    movingRight = false;
                } else if (keyEvent.getCode() == KeyCode.W){
                    movingUp = false;
                } else if (keyEvent.getCode() == KeyCode.S){
                    movingDown = false;
                }
            }
        });
    }

    public Stage getMainStage() {
        return mainStage;
    }
}
