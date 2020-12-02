package worldofzuul;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerGraphics {
    public final int MOVEMENTSPEED = 3;

    public final int YOFFSET = 5;
    public final int XLEFTOFFSET = 35;
    public final int XRIGHTOFFSET;

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
        XRIGHTOFFSET = imgWidth - 35;
    }

    public void setXPos(int value){
        img.setLayoutX(value);
    }
    public void setYPos(int value){
        img.setLayoutY(value);
    }
    public int getXPos(){
        return (int)img.getLayoutX();
    }
    public int getYPos(){
        return (int)img.getLayoutY();
    }
}
