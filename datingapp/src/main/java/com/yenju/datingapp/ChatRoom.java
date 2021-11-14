package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int receiverID;
    private void syncMessage(){
        ArrayList<String > senterContent = DBConnection.getChatContent( messageContainer.userID, receiverID);
        ArrayList<String > receiverContent = DBConnection.getChatContent(receiverID,messageContainer.userID);
        String senderContentLine = String.join("\n", senterContent);
        String receiverContentLine = String.join("\n", receiverContent);
        lb_userchat.setText(senderContentLine);
        lb_otherschat.setText(receiverContentLine);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String content = ta_text.getText();
                boolean sendContentSuccess = DBConnection.writeText(event, messageContainer.userID, receiverID, content);
                Alert alert;
                if(sendContentSuccess){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("message sent!");
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("send message failed!");
                }
                alert.show();
                syncMessage();
                ta_text.setText("");

            }
        });

    }
    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
        receiverID = DBConnection.getMatchPeople(messageContainer.userID);
        syncMessage();
    }
}
