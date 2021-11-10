package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController extends SceneController {
    private SceneHelper.MessageContainer messageContainer;

    @FXML
    private Button button_signup;

    @FXML
    private RadioButton rb_male;
    @FXML
    private RadioButton rb_female;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Label lb_errors;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ToggleGroup toggleGroup = new ToggleGroup(); //one click at a time
        rb_male.setToggleGroup(toggleGroup);
        rb_female.setToggleGroup(toggleGroup);

        rb_male.setSelected(true);


        button_signup.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                boolean signup;
                String userName = tf_username.getText();
                String password = tf_password.getText();
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText(); //get male or female

                if(!userName.trim().isEmpty() && !password.trim().isEmpty()){
                    signup = DBConnection.sighUpUser(event, tf_username.getText(),tf_password.getText(),toggleName);
                    if(signup ){
                        SceneHelper.MessageContainer messageContainer = new SceneHelper.MessageContainer();
                        messageContainer.title = "Log In!";
                        messageContainer.username = userName;
                        SceneHelper.changeScene(event, "LogInPage.fxml", messageContainer);
                    }else{
                        lb_errors.setText("Please try again!");
                    }

                }else{
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information");
                    alert.show();
                }
            }
        });
    }

    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }

}
