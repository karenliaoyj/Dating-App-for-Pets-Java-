package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignUpController implements Initializable {

    @FXML
    private Button button_signup;
    @FXML
    private Button button_login;

    @FXML
    private RadioButton rb_male;
    @FXML
    private RadioButton rb_female;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ToggleGroup toggleGroup = new ToggleGroup(); //one click at a time
        rb_male.setToggleGroup(toggleGroup);
        rb_female.setToggleGroup(toggleGroup);

        rb_male.setSelected(true);


        button_signup.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText(); //get male or female

                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DBConnection.sighUpUser(event, tf_username.getText(),tf_password.getText(),toggleName);
                }else{
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information");
                    alert.show();
                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                DBConnection.changeScene(event, "Login.fxml",null,null);
            }
        });
    }
}
