package controllers;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.Controller;
import models.Tournaments;;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static models.connection.delete;
import static models.tournaments.*;

public class ListTournaments implements Initializable {
    @FXML
    ListView <VBox>listTournament = new ListView<VBox>();
    ObservableList<VBox> items = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        all();

        for(int index: getElements().keySet()){
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(10,10,10,10));
            vBox.setSpacing(10);
            vBox.setId(""+getElements().get(index).getId());

            Label label = new Label(getElements().get(index).getName());
            label.setFont(Font.font("Arial", FontWeight.BOLD,30));

            Label label1 = new Label("Time of Starting :"+getElements().get(index).getTts());
            label1.setFont(Font.font("Arial", FontWeight.BOLD,20));

            Label label2 = new Label("Time of Ending :"+getElements().get(index).getTte());
            label2.setFont(Font.font("Arial", FontWeight.BOLD,20));

            Label label3 = new Label("Number of Teams :"+getElements().get(index).getNo_of_teams());
            label1.setFont(Font.font("Arial", FontWeight.BOLD,20));

            Button Tbtn = new Button("Teams");
            Tbtn.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent actionEvent) {
                    Controller state = Controller.getInstance();
                    state.setTournament_id(index);
                    gotoListMatches();

                }
            });


            Button Mbtn = new Button("Matches");
            Mbtn.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent actionEvent) {
                    Controller state = Controller.getInstance();
                    state.setTournament_id(index);
                    gotoListMatches();

                }
            });
            Button Fbtn = new Button("Mark as Finished");
            Fbtn.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent actionEvent) {
                    Tournaments t =getElements().get(index);
                    update(t.id,t.name,t.tts,t.tte,t.no_of_teams,1);
                    t.hasEnded=true;
                    getElements().put(t.id,t);
                    vBox.getChildren().remove(Fbtn);
                }
            });

            Button Dbtn = new Button("Delete");
            Dbtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        delete(getTableName(),index);
                        getElements().remove(index);
                        listTournament.getItems().remove(vBox);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            });

            vBox.getChildren().add(label);
            vBox.getChildren().add(label1);
            vBox.getChildren().add(label2);
            vBox.getChildren().add(label3);
            vBox.getChildren().add(Mbtn);
            vBox.getChildren().add(Tbtn);
            if(!getElements().get(index).hasEnded){
                vBox.getChildren().add(Fbtn);
            }
            vBox.getChildren().add(Dbtn);

            this.listTournament.getItems().add(vBox);

        }

    }


    private void gotoListMatches(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_matches.fxml"));
            Stage Window = (Stage)listTournament.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/main.fxml"));
            Stage Window = (Stage)listTournament.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void gotoListTeams(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_teams.fxml"));
            Stage Window = (Stage)listTournament.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
