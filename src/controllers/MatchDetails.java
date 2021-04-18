package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import main.Controller;
import models.Matches;


import static models.matches.*;
import static models.connection.*;

public class MatchDetails implements Initializable {
    @FXML
    Label t1 = new Label() , t2 = new Label() , t1g = new Label() , t2g = new Label();

    @FXML
    Button delete = new Button(), start = new Button(), end = new Button(), inc1 = new Button(), inc2 = new Button();

    private Controller state = Controller.getInstance();
    private Matches match = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        match = state.getMatchDetails();
        System.out.println(match);
        t1.setText(match.t1.name !=null?match.t1.name:"");
        t2.setText(match.t2.name!=null?match.t2.name:"");

        t1g.setText(match.t1_goals+"");
        t2g.setText(match.t2_goals+"");

        if(match.hasEnded){
            start.setDisable(true);
            start.setVisible(false);

            end.setDisable(true);
            end.setVisible(false);

            inc1.setDisable(true);
            inc1.setVisible(false);

            inc2.setDisable(true);
            inc2.setVisible(false);


        }else{
            if(match.hasStarted){
                start.setDisable(true);
                start.setVisible(false);

                end.setDisable(false);
                end.setVisible(true);

                inc1.setDisable(false);
                inc1.setVisible(true);

                inc2.setDisable(false);
                inc2.setVisible(true);
            }else{
                inc1.setDisable(true);
                inc1.setVisible(false);

                inc2.setDisable(true);
                inc2.setVisible(false);
                end.setDisable(true);
                end.setVisible(false);
            }
        }
    }


    @FXML
    public void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_tournaments.fxml"));
            Stage Window = (Stage)t1.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void Start(ActionEvent event){
        try{
            update(match.id,1,match.hasEnded?1:0, match.t1_goals, match.t2_goals);
            start.setDisable(true);
            start.setVisible(false);

            end.setDisable(false);
            end.setVisible(true);

            inc1.setDisable(false);
            inc1.setVisible(true);

            inc2.setDisable(false);
            inc2.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void End(ActionEvent event){
        try {
            update(match.id,match.hasStarted?1:0,1, match.t1_goals, match.t2_goals);
            start.setDisable(true);
            start.setVisible(false);

            end.setDisable(true);
            end.setVisible(false);

            inc1.setDisable(true);
            inc1.setVisible(false);

            inc2.setDisable(true);
            inc2.setVisible(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void Inc1(ActionEvent event){
        try {
            update(match.id,match.hasStarted?1:0,match.hasEnded?1:0, ++match.t1_goals, match.t2_goals);
            t1g.setText(match.t1_goals+"");
            t2g.setText(match.t2_goals+"");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void Inc2(ActionEvent event){
        try {
            update(match.id,match.hasStarted?1:0,match.hasEnded?1:0, match.t1_goals, ++match.t2_goals);
            t1g.setText(match.t1_goals+"");
            t2g.setText(match.t2_goals+"");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void Delete(ActionEvent event){
        try {
            delete(getTableName(),match.id);
            getElements().remove(match.id);
            back(event);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
