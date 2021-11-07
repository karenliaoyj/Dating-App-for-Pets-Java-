package com.yenju.datingapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class LoggedInController implements Initializable{

    @FXML
    private Button buttonLogOut;   //the log out button

    @FXML
    Label welcomeLabel;
    @FXML
    Label introWordLabel;

    @Override
    public void initialize (URL location, ResourceBundle resources) {

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBConnection.changeScene(event, "com/yenju/datingapp/LogInPage.fxml", "Log in!", "null");
            }
        });
    }
    public void setUserInformation(String username){
        welcomeLabel.setText("Welcome"+username);
        introWordLabel.setText("Find your pet a friend!");
    }



}
