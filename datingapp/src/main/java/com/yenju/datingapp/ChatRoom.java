package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    @FXML
    private Button button_logout;
    @FXML
    private Button button_back;
    @FXML
    private Button button_download;

    private int receiverID;
    private String senderContentLine;
    private String receiverContentLine;
    private void syncMessage(){
        ArrayList<String > senterContent = DBConnection.getChatContent( messageContainer.userID, receiverID);
        ArrayList<String > receiverContent = DBConnection.getChatContent(receiverID,messageContainer.userID);
        senderContentLine = String.join("\n", senterContent);
        receiverContentLine = String.join("\n", receiverContent);
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

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerLogOut = new SceneHelper.MessageContainer();
                messageContainerLogOut.title = "Log In!";
                messageContainerLogOut.username = null;
                SceneHelper.changeScene(event, "LogInPage.fxml", messageContainer);
            }
        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerNext = new SceneHelper.MessageContainer();
                messageContainerNext.title = "Logged In";
                messageContainerNext.username =null;
                messageContainerNext.userID = messageContainer.userID;
                SceneHelper.changeScene(event, "LoggedIn.fxml",messageContainer );
            }
        });

        button_download.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                Window window = ((Node) (event.getSource())).getScene().getWindow();
                File file = fileChooser.showSaveDialog(window);

                if (file != null) {
                    Path path = file.getAbsoluteFile().toPath();
                    String content = senderContentLine+"\n"+receiverContentLine;
                    try (final BufferedWriter writer = Files.newBufferedWriter(path,
                            StandardCharsets.UTF_8, StandardOpenOption.CREATE);)
                    {
                        writer.write(content);
                        writer.flush();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }

            }
        });

    }
    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
        receiverID = DBConnection.getMatchPeople(messageContainer.userID);
        syncMessage();
    }
}
