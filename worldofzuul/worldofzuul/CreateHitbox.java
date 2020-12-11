package worldofzuul;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class CreateHitbox {

    // Static method for creating a rectangle, used as hitboxes, from an ImageView object
    public static Rectangle imageView(ImageView sourceImgView){
        Rectangle hitboxRect = new Rectangle(sourceImgView.getLayoutX(),sourceImgView.getLayoutY(), sourceImgView.getImage().getWidth(),sourceImgView.getImage().getHeight());
        return hitboxRect;
    }
}
