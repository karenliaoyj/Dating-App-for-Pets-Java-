package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SignUpController extends SceneController {
    private SceneHelper.MessageContainer messageContainer;

    @FXML
    private Button button_signup;
    @FXML
    private Button button_upload;

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
    Path src;
    Path dst;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ToggleGroup toggleGroup = new ToggleGroup(); //one click at a time
        rb_male.setToggleGroup(toggleGroup);
        rb_female.setToggleGroup(toggleGroup);

        rb_male.setSelected(true);


        button_signup.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event){
                try{
                    Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
                }catch (IOException e){
                    e.printStackTrace();
                }

                boolean signup;
                String userName = tf_username.getText();
                String password = tf_password.getText();
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText(); //get male or female
                String photoName = dst.toString();
                photoName = photoName.substring(photoName.lastIndexOf("/")+1);
                if(!userName.trim().isEmpty() && !password.trim().isEmpty()){
                    signup = DBConnection.sighUpUser(event, tf_username.getText(),tf_password.getText(),toggleName,photoName);
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

        button_upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Window window = ((Node) (event.getSource())).getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(window);
                src = file.getAbsoluteFile().toPath();
                Path currentRelativePath = Paths.get("");
                Path currentAbsolutePath = currentRelativePath.toAbsolutePath();
                String uuid = UUID.randomUUID().toString();
                dst = currentAbsolutePath.resolve(uuid+".jpg");



            }
        });
    }

    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }

}
