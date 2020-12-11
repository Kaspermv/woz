package worldofzuul;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerGraphics {
    //sets the movement speed for the player graphic
    public final double MOVEMENTSPEED = 0.5;

    //variable for sixe of player
    public int imgHeight;
    public int imgWidth;

    //creates Imageview called img
    public ImageView img;

    //Constructs a PlayerGraphics object that can be used in a JavaFX application
    public PlayerGraphics(){
        //load the player graphic "Player.png" in the folder Graphics
        Image playerImg = new Image("Graphics/Player.png");
        img = new ImageView(playerImg);
        img.setSmooth(true);
        img.setCache(true);

        img.setLayoutX(400);
        img.setLayoutY(400);

        imgHeight = (int)playerImg.getHeight();
        imgWidth = (int)playerImg.getWidth();
    }


    public void setXPos(double value){
        img.setLayoutX(value-35);
    }
    public void setYPos(double value){
        img.setLayoutY(value-5);
    }
    public double getXPos(){
        return img.getLayoutX()+35;
    }
    public double getYPos(){
        return img.getLayoutY()+5;
    }
}
