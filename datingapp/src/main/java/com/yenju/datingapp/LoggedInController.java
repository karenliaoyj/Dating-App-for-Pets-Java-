package com.yenju.datingapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class LoggedInController  extends SceneController {
    private SceneHelper.MessageContainer messageContainer;

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
                SceneHelper.MessageContainer messageContainer = new SceneHelper.MessageContainer();
                messageContainer.title = "Log In!";
                messageContainer.username = null;
                SceneHelper.changeScene(event, "LogInPage.fxml", messageContainer);
            }
        });
    }
    private void setUserInformation(String username){
        welcomeLabel.setText("Welcome"+username);
        introWordLabel.setText("Find your pet a friend!");
    }

    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
        this.setUserInformation(this.messageContainer.username);
    }

}
