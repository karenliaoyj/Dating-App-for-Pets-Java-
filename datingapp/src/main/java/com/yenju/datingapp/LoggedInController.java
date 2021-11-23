package com.yenju.datingapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class LoggedInController  extends SceneController {
    private SceneHelper.MessageContainer messageContainer;

    @FXML
    private Button buttonLogOut;   //the log out button
    @FXML
    private Button button_chat;
    @FXML
    private Button button_choose;
    @FXML
    private Button button_check;
    @FXML
    private Button button_match;
    @FXML
    Label welcomeLabel;
    @FXML
    Label introWordLabel;

    @Override
    public void initialize (URL location, ResourceBundle resources) {

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerLogOut = new SceneHelper.MessageContainer();
                messageContainerLogOut.title = "Log In!";
                messageContainerLogOut.username = null;
                SceneHelper.changeScene(event, "LogInPage.fxml", messageContainer);
            }
        });
        // 跳轉到chat room
        button_chat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerNext = new SceneHelper.MessageContainer();
                messageContainerNext.title = "Chat Room";
                messageContainerNext.username =null;
                messageContainerNext.userID = messageContainer.userID;
                UserProfile currentUser = new UserProfile();
                int chatppl = DBConnection.getMatchPeople(messageContainer.userID);
                if(chatppl != 0){
                    SceneHelper.changeScene(event, "chatRoom.fxml",messageContainer );
                }else{
                    System.out.println("not matched user cannot chat");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Match before you chat");
                    alert.show();
                }

            }
        });
        // the pick friend button
        button_choose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerNext = new SceneHelper.MessageContainer();
                messageContainerNext.title = "Choose Friend";
                messageContainerNext.username =null;
                messageContainerNext.userID = messageContainer.userID;
                SceneHelper.changeScene(event, "likeOrDislike.fxml",messageContainer );

            }
        });

        button_check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int matchedUserID = MatchAlgorithm.matchUser(messageContainer.userID);
                DBConnection.updateMatched(matchedUserID, messageContainer.userID);

            }
        });

        button_match.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerNext = new SceneHelper.MessageContainer();
                messageContainerNext.title = "Choose Friend";
                messageContainerNext.username =null;
                messageContainerNext.userID = messageContainer.userID;
                SceneHelper.changeScene(event, "matchLogic.fxml",messageContainer );
            }
        });







    }
    private void setUserInformation(){
        welcomeLabel.setText("Welcome");
        introWordLabel.setText("Find your pet a friend!");
    }

    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
        //this.setUserInformation(this.messageContainer.username);
    }

}
