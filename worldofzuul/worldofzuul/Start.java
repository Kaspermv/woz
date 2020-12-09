package worldofzuul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ViewManager manager = new ViewManager();
        manager.createKeyListeners();
        manager.createGameLoop();
        primaryStage = manager.getMainStage();
        primaryStage.setTitle("World of Slum");
        // Final update of canvas
        primaryStage.show();
    }
//Creates a new instance of Game
    public static void main(String[] args) {
        launch(args);
    }

}
