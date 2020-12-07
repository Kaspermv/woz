package worldofzuul;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class CreateHitbox {

    public static Rectangle imageView(ImageView sourceImgView){
        Rectangle hitboxRect = new Rectangle(sourceImgView.getLayoutX(),sourceImgView.getLayoutY(), sourceImgView.getImage().getWidth(),sourceImgView.getImage().getHeight());
        hitboxRect.setFill(Color.color(0,0,0,0.2));
        return hitboxRect;
    }
}
