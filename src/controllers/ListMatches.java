package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Controller;
import models.Matches;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static models.matches.*;
import static models.connection.*;


public class ListMatches implements Initializable {

    private final Controller state = Controller.getInstance();
    private final boolean hasEnded = state.getTournament_id() == null || state.getTournament_id().hasEnded;
    @FXML
    Circle circle = new Circle();
    @FXML
    Label addLabel = new Label();
    @FXML
    ListView<VBox> listMatches = new ListView<VBox>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String[] Columns = new String[0];
            StringBuilder query = select(getTableName(), Columns);
            where(query,"tournament_id","=",""+ Objects.requireNonNull(state.getTournament_id()).id+"");
            executeQuery(query.toString());
            if(hasEnded){
                circle.setVisible(false);
                addLabel.setVisible(false);
            }

            for(int i:getElements().keySet()){
                listMatches.getItems().add(createVBox(getElements().get(i)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public void newMatch(MouseEvent  event){
        if(!hasEnded){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../screens/create_matches.fxml"));
                Stage Window = (Stage)circle.getScene().getWindow();
                Scene s = new Scene(root, 500, 800);
                s.getStylesheets().add("main/app.css");
                Window.setScene(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/list_tournaments.fxml"));
            Stage Window = (Stage)circle.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private VBox createVBox(Matches match){
        VBox parent = new VBox();
        parent.setSpacing(10);
        parent.setPadding(new Insets(10,10,10,10));

        parent.setId(""+match.id);

        Label team_1_name,team_2_name,team_1_score,team_2_score;
        team_1_name = new Label();
        team_1_name.setAlignment(Pos.CENTER);
        team_1_name.setFont(Font.font("Arial",22));
        team_1_name.setText(match.t1.name);

        team_2_name = new Label();
        team_2_name.setAlignment(Pos.CENTER);
        team_2_name.setFont(Font.font("Arial",22));
        team_2_name.setText(match.t2.name);


        team_1_score =  new Label();
        team_1_score.setAlignment(Pos.CENTER);
        team_1_score.setFont(Font.font("Arial",20));
        team_1_score.setText(""+match.t1_goals);

        team_2_score =  new Label();
        team_2_score.setAlignment(Pos.CENTER);
        team_2_score.setFont(Font.font("Arial",20));
        team_2_score.setText(""+match.t2_goals);

        HBox    hBox = new HBox(),
                scoreBox = new HBox();




        hBox.setAlignment(Pos.CENTER);
        scoreBox.setAlignment(Pos.CENTER);


        hBox.getChildren().add(team_1_name);
        hBox.getChildren().add(new Label("  -   "));
        hBox.getChildren().add(team_2_name);

        scoreBox.getChildren().add(team_1_score);
        scoreBox.getChildren().add(new Label("  -   "));
        scoreBox.getChildren().add(team_2_score);


        Button button = new Button("Details");
        button.setPadding(new Insets(12,12,12,12));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                state.setMatchDetails(match);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../screens/match_details.fxml"));
                    Stage window = (Stage)circle.getScene().getWindow();
                    Scene s = new Scene(root,500,800);
                    s.getStylesheets().add("main/app.css");

                    window.setScene(s);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        parent.getChildren().add(hBox);
        parent.getChildren().add(scoreBox);
        parent.getChildren().add(button);
        return parent ;
    }

}
