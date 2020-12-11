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
        // Instantiates the game view
        ViewManager manager = new ViewManager();
        // Creates the key listeners for user input
        manager.createKeyListeners();
        // Creates the main game loop
        manager.createGameLoop();
        // Sets the primary stage of the application to the viewManager stage
        primaryStage = manager.getMainStage();
        // Sets the window title
        primaryStage.setTitle("World of Slum");
        // Final update of canvas
        primaryStage.show();
    }
// Launches the game on startup
    public static void main(String[] args) {
        launch(args);
    }

}
