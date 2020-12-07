package worldofzuul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent window = new AnchorPane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(window, 300, 275));
        primaryStage.show();
    }
//Creates a new instance of Game
    public static void main(String[] args) {
        launch(args);
        Game start = new Game();
        start.play();

    }
}
