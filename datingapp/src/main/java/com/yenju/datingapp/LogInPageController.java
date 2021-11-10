package com.yenju.datingapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInPageController  extends SceneController {
    private SceneHelper.MessageContainer messageContainer;

    @FXML
    private Button button_login;
    @FXML
    private Button button_signup;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        button_login.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event){

                String userName = tf_username.getText();
                String passWord = tf_password.getText();
                boolean logInSuccess;
                logInSuccess = DBConnection.logInUser(event, userName, passWord);
                if(logInSuccess){
                    SceneHelper.MessageContainer messageContainer = new SceneHelper.MessageContainer();
                    messageContainer.title = "Welcome";
                    messageContainer.username = userName;
                    SceneHelper.changeScene(event, "loggedIn.fxml", messageContainer);
                }else{
                    System.out.println("Password did not match");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The username/password is incorrect");
                    alert.show();
                }
            }
        });

        button_signup.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                SceneHelper.MessageContainer messageContainer = new SceneHelper.MessageContainer();
                messageContainer.title = null;
                messageContainer.username = null;
                SceneHelper.changeScene(event, "SignUp.fxml", messageContainer);
            }
        });
    }

    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }

}
