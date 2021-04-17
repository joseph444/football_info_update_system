package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import static models.connection.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../screens/main.fxml"));
        primaryStage.setTitle("Football Info");
        Scene s = new Scene(root, 500, 800);
        s.getStylesheets().add("main/app.css");
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        connect();
        launch(args);
        disconnect();
    }
}
