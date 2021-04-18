package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import main.Controller;
import models.Teams;

import static models.matches.*;

public class CreateMatches implements Initializable {
    @FXML
     ChoiceBox<String> T1 = new ChoiceBox<String>();
    @FXML
     ChoiceBox<String> T2 = new ChoiceBox<String>();
    @FXML
    Button create = new Button();

    Controller state = Controller.getInstance();

    private ObservableList<String> mainList =FXCollections.observableArrayList();
    private ArrayList<Teams> teamList = new ArrayList<Teams>();
    private Teams team1 = null,team2 = null;
    Map<Integer, Teams> teams = new HashMap<Integer, Teams>() ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            teams = state.getAllTeams();
            for(int i: teams.keySet()){
                teamList.add(teams.get(i));
                mainList.add(teams.get(i).name);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        T1.getItems().addAll(mainList);
        T1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                team1 = teamList.get(t1.intValue());
            }
        });
        T2.getItems().addAll(mainList);
        T2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                team2 = teamList.get(t1.intValue());
            }
        });
    }

    @FXML
    public void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_tournaments.fxml"));
            Stage Window = (Stage)create.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void Create(ActionEvent event){
        System.out.println(team1);
        System.out.println(team2);
        if(team1 !=null){
            if(team2 != null){
                if(team1!=team2){
                    insert(state.getTournament_id().id, team1.id, team2.id);
                    back(event);
                }
            }
        }
    }


}
