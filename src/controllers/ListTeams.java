package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Teams;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import main.Controller;

public class ListTeams implements Initializable {
    @FXML
    ListView<VBox> listTeams = new ListView<VBox>();

    Map<Integer, Teams> teams = new HashMap<Integer, Teams>() ;
    Controller state = Controller.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teams = state.getAllTeams();
        for(int i: teams.keySet()){
            listTeams.getItems().add(createVbox(teams.get(i)));
        }
    }

    @FXML
    public void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_tournaments.fxml"));
            Stage Window = (Stage)listTeams.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private VBox createVbox(Teams team){
        VBox parent = new VBox();
        Label team_name = new Label(team.name);
        team_name.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR,24));
        Label captain_name = new Label("Captain : "+team.captain_name);
        captain_name.setFont(Font.font("System",FontWeight.BOLD,20));
        Label coach_name = new Label("Coach : "+team.coach_name);
        coach_name.setFont(Font.font("System",FontWeight.BOLD,20));

        parent.setPadding(new Insets(15,15,15,15));
        parent.setAlignment(Pos.CENTER);
        parent.getChildren().add(team_name);
        parent.getChildren().add(captain_name);
        parent.getChildren().add(coach_name);
        return parent;
    }
}
