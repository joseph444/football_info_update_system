package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Controller;
import models.Teams;
import models.Tournaments;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

import static models.teams.*;

public class CreateTeams implements Initializable {

    ArrayList<Teams> teamDetails = new ArrayList<Teams>();
    Tournaments tournament;
    Controller instance = Controller.getInstance();

    private int TeamFlag =1;
    @FXML
    Label teamNo = new Label("Team Details for 1st Team"),validation = new Label();
    @FXML
    Button previous = new Button(),next = new Button();
    @FXML
    TextField teamName = new TextField(),
              teamInit = new TextField(),
              capName  = new TextField(),
              coachName = new TextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tournament = instance.getTournament_id();
        previousLogic();
        teamNoLogic();
    }


    @FXML
    public void Next(ActionEvent event){

        try {
            if(teamName.getText().isBlank()||teamInit.getText().isBlank()||capName.getText().isBlank()||coachName.getText().isBlank()){
                validation.setText("All Fields are Necessary");
                sleep(100);
                validation.setText("");
            }else{
                Teams tmp = new Teams(TeamFlag,teamName.getText(),teamInit.getText(),coachName.getText(),tournament,capName.getText());
              try{
                  teamDetails.set(TeamFlag-1,tmp);
              }catch(Exception e){
                  teamDetails.add(tmp);
              }
                System.out.println(teamDetails.toString());
                if(tournament.getNo_of_teams()!=TeamFlag){
                    TeamFlag++;
                    setTextFieldDetails("","","","");
                    teamNoLogic();
                    previousLogic();
                }else{
                    addAllTeams();

                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void Previous(ActionEvent e){
        try {
            --TeamFlag;
            Teams t =teamDetails.get(TeamFlag-1);
            previousLogic();
            teamNoLogic();
            setTextFieldDetails(t.name,t.initials,t.captain_name,t.coach_name);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }






    private void previousLogic(){
        if(TeamFlag==1){
            previous.setVisible(false);
            previous.setDisable(true);
        }else{
            previous.setVisible(true);
            previous.setDisable(false);
        }

        teamNoLogic();
    }

    private void teamNoLogic(){
        teamNo.setText("Team Details for "+TeamFlag+"th Team");
    }

    private void addAllTeams(){
        for (Teams tmp : teamDetails) {
            insert(tmp.name, tmp.initials, tmp.coach_name, tmp.tournament.id, tmp.captain_name);
        }

        try{
            Parent root = FXMLLoader.load(getClass().getResource("../screens/main.fxml"));
            Stage Window = (Stage)teamInit.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTextFieldDetails(String name,String initial,String captain_name,String coachName){
        teamName.setText(name);
        teamInit.setText(initial);
        capName.setText(captain_name);
        this.coachName.setText(coachName);
    }
}
