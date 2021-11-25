package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHelper {
    public static class MessageContainer {
        public MessageContainer(){

        }
        public String username;
        public String title;
        public int userID;
        public int chatppl;
        //add attributes if needed
    }

    public static void changeScene(ActionEvent event, String fxmlFile , MessageContainer messageContainer){
        Parent root = null;

        if(messageContainer != null ){
            try {
                FXMLLoader loader = new FXMLLoader(DBConnection.class.getResource(fxmlFile));
                root = loader.load();
                SceneController controller = loader.getController();
                controller.setMessage(messageContainer);
            }catch(IOException e){
                e.printStackTrace();;
            }
        }else{
            try{
                root = FXMLLoader.load(DBConnection.class.getResource(fxmlFile));
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(messageContainer.title);
        stage.setScene(new Scene(root,  600, 400));
        stage.show();
    }
}
