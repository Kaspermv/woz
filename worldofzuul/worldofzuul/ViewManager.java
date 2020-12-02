package worldofzuul;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.shape.Rectangle;
import java.io.File;
import java.util.HashMap;

public class ViewManager {

    public int HEIGHT = 800;
    public int WIDTH = 800;

    public Pane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private AnimationTimer gameTimer;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private Rectangle upRect;
    private Rectangle downRect;
    private Rectangle leftRect;
    private Rectangle rightRect;
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
                BackgroundImage tempBackgroundImage = new BackgroundImage(tempImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(WIDTH, HEIGHT,false, false,false,true));
                Background tempBackground = new Background(tempBackgroundImage);
                String name = imgFile.getName().substring(0, imgFile.getName().indexOf("."));
                System.out.println(name);
                backgrounds.put(name, tempBackground);
            }
        } catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
            System.out.println("Unable to load images.");
        }

        mainPane = new Pane();
        mainScene = new Scene(mainPane, HEIGHT, WIDTH);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createExits();
        mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        mainPane.getChildren().addAll(player.img);

        // Creating player hitbox
        playerRect = new Rectangle();
        playerRect.setFill(Color.color(0,0,0,0.2));

        playerRect.setX(400 + 40);
        playerRect.setY(400 + 3);
        playerRect.setHeight(125);
        playerRect.setWidth(50);

        mainPane.getChildren().addAll(playerRect);

    }
    // Rectangles need size of 5
    public void createGameLoop(){
        gameTimer = new AnimationTimer(){

            @Override
            public void handle(long l) {
                // Game loop
                move();
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
                upRect = new Rectangle(300,0,200,5);
                upRect.setFill(Color.GRAY);
                mainPane.getChildren().addAll(upRect);
                break;
            case "down":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                downRect = new Rectangle(300,HEIGHT-5,200,5);
                downRect.setFill(Color.GRAY);
                mainPane.getChildren().add(downRect);
                break;
            case "right":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                rightRect = new Rectangle(WIDTH-5,300,5,200);
                rightRect.setFill(Color.GRAY);
                mainPane.getChildren().add(rightRect);
                break;
            case "all":
                mainPane.getChildren().removeAll(upRect,downRect,leftRect,rightRect);
                upRect = new Rectangle(300,0,200,5);
                upRect.setFill(Color.GRAY);
                downRect = new Rectangle(300,HEIGHT-5,200,5);
                downRect.setFill(Color.GRAY);
                rightRect = new Rectangle(WIDTH-5,300,5,200);
                rightRect.setFill(Color.GRAY);
                leftRect = new Rectangle(0,300,5,200);
                leftRect.setFill(Color.GRAY);
                mainPane.getChildren().addAll(upRect,downRect,leftRect,rightRect);
                break;
        }
    }

    public void changeRoom(String direction){
        game.processCommand(new Command(Action.GO, direction));
        mainPane.setBackground(backgrounds.get(game.currentRoom.name));
        if (direction == "up") {
            playerRect.setY(HEIGHT - playerRect.getHeight() - 10);
            player.setYPos(HEIGHT - playerRect.getHeight() - 10);
        } else if (direction == "down") {
            playerRect.setY(10);
            player.setYPos(10);
        } else if (direction == "left") {
            playerRect.setX(WIDTH - playerRect.getWidth() - 10);
            player.setXPos(WIDTH - playerRect.getWidth() - 10);
        } else if (direction == "right") {
            playerRect.setX(playerRect.getWidth() + 10);
            player.setXPos(playerRect.getWidth() + 10);
        }
        createExits();
    }

    private void move(){
        if (movingLeft){
            // Border check (Dependent on player size)
            if (playerRect.getX() > 0){
                player.setXPos(player.getXPos() - player.MOVEMENTSPEED);
                playerRect.setX(playerRect.getX()- player.MOVEMENTSPEED);
            }
        }
        if (movingRight){
            // Border check (Dependent on player size)
            if (playerRect.getX() < WIDTH - playerRect.getWidth()){
                player.setXPos(player.getXPos() + player.MOVEMENTSPEED);
                playerRect.setX(playerRect.getX() + player.MOVEMENTSPEED);
            }
        }
        if (movingUp){
            // Border check (Dependent on player size)
            if (playerRect.getY() > 0){
                player.setYPos(player.getYPos() - player.MOVEMENTSPEED);
                playerRect.setY(playerRect.getY()- player.MOVEMENTSPEED);
            }
        }
        if (movingDown){
            // Border check (Dependent on player size)
            if (playerRect.getY() < HEIGHT - playerRect.getHeight()){
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
