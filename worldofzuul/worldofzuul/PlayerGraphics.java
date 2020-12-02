package worldofzuul;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerGraphics {
    public final int MOVEMENTSPEED = 3;

    public int imgHeight;
    public int imgWidth;

    public ImageView img;

    public PlayerGraphics(){
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
