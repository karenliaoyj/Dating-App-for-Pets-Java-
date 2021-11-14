package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatRoom extends SceneController{
    private SceneHelper.MessageContainer messageContainer;

    @FXML
    private Label lb_otherschat;
    @FXML
    private Label lb_userchat;
    @FXML
    private TextArea ta_text;
    @FXML
    private Button button_send;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int receiverID = DBConnection.getMatchPeople(event,messageContainer.userID);
                String content = ta_text.getText();
                boolean sendContentSuccess = DBConnection.writeText(event, messageContainer.userID, receiverID, content);


            }
        });

    }
    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }
}
