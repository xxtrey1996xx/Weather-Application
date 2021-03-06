package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../mainMenu.fxml"));
        primaryStage.setTitle("Current Weather");
        primaryStage.setScene(new Scene(root, 774, 411));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
