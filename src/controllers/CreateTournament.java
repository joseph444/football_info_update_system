package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static models.tournaments.*;
import static models.connection.*;

public class CreateTournament implements Initializable {
    @FXML
    TextField nameField;
    @FXML
    DatePicker ttsField,tteField;
    @FXML
    Slider noOfTeamsField;
    @FXML
    Label sliderLabel = new Label("4"),validation = new Label("");

    @FXML
    public void next(ActionEvent event){
        validation.setVisible(true);
        String Name = nameField.getText();
        String TTS = ttsField.getEditor().getText();
        String TTE = tteField.getEditor().getText();
        int NO_OF_TEAMS = (int)noOfTeamsField.getValue();

        if(Name.isBlank()){
            System.out.println("TUDUM");
            validation.setText("Name is required");

        }
        else if(TTS.isBlank()){
            System.out.println("TUDUM0");
            validation.setText("Start Date is required");

        }
        else if(TTE.isBlank()){
            System.out.println("TUDUM1");
            validation.setText("End Date is required");

        }
        else{
            System.out.println("TUDUM2");
            insert(Name,TTS,TTE,NO_OF_TEAMS);
            
            gotoNext();

        }
    }

    @FXML
    public void cancel(ActionEvent event){
        back(event);
    }


    @FXML
    public void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../screens/main.fxml"));
            Stage Window = (Stage)nameField.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        noOfTeamsField.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sliderLabel.setText(""+((int)noOfTeamsField.getValue()));
            }
        });
    }

    private void gotoNext(){
        all();
        int Index = 0;
       
        try {
            for(int index:getElements().keySet()){
                Index =index;
                System.out.println(getElements().get(index));
            }
            Controller state = Controller.getInstance();
            state.setTournament_id(Index);
            Parent root = FXMLLoader.load(getClass().getResource("../screens/create_teams.fxml"));
            Stage Window = (Stage)sliderLabel.getScene().getWindow();
            Scene s = new Scene(root, 500, 800);
            s.getStylesheets().add("main/app.css");
            Window.setScene(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
