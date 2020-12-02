package worldofzuul;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

public class ViewManager {

    public int HEIGHT = 800;
    public int WIDTH = 800;

    public AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private AnimationTimer gameTimer;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;

    public HashMap<String, Background> backgrounds;
    final File dir = new File("Graphics/Backgrounds");
    public PlayerGraphics player;

    public ViewManager(){
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

        // Loads rest of needed images


        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, HEIGHT, WIDTH);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        mainPane.setBackground(backgrounds.get("Market"));
        mainPane.getChildren().add(player.img);

    }

    public void createGameLoop(){
        gameTimer = new AnimationTimer(){

            @Override
            public void handle(long l) {
                // Game loop
                move();


            }
        };
        gameTimer.start();
    }

    private void move(){
        if (movingLeft){
            // Border check (Dependent on player size)
            if (player.getXPos() > -player.XLEFTOFFSET){
                player.setXPos(player.getXPos() - player.MOVEMENTSPEED);
            }
        }
        if (movingRight){
            // Border check (Dependent on player size)
            if (player.getXPos() < WIDTH - player.XRIGHTOFFSET){
                player.setXPos(player.getXPos() + player.MOVEMENTSPEED);
            }
        }
        if (movingUp){
            // Border check (Dependent on player size)
            if (player.getYPos() > -player.YOFFSET){
                player.setYPos(player.getYPos() - player.MOVEMENTSPEED);
            }
        }
        if (movingDown){
            // Border check (Dependent on player size)
            if (player.getYPos() < HEIGHT - player.imgHeight){
                player.setYPos(player.getYPos() + player.MOVEMENTSPEED);
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
