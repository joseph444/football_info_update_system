package controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    @FXML
    Button createT,listT;

    @FXML
    public void createt(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/create_tournament.fxml"));
            Stage Window = (Stage)createT.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void listt(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_tournaments.fxml"));
            Stage Window = (Stage)createT.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
