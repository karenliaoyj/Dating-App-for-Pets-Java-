package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LikeOrDislikeController extends SceneController{
    private SceneHelper.MessageContainer messageContainer;
    @FXML
    private Button button_dislike;
    @FXML
    private Button button_like;
    @FXML
    private Button button_back;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_intro;
    @FXML
    private ImageView im_image;


    //private photo;
    //private userName;
    //private intro;
    private int receiverID;

    private Queue<UserProfile> notMatchedUser;

   @Override
    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;

        getUnMatched();
        showProfile();
    }

    private void getUnMatched(){
       if(notMatchedUser ==null){
           ArrayList unMatchedUser = DBConnection.getNotMatchedUser(messageContainer.userID);
           if(unMatchedUser != null && !unMatchedUser.isEmpty() ){
               notMatchedUser = new LinkedList<>(unMatchedUser);
           }
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("No more profile to show");
           alert.show();
       }


    }
    public boolean showProfile(){
       if(notMatchedUser == null){
           return false;
       }
       if(notMatchedUser.isEmpty()){
           return false;
       }
       UserProfile matchingUser = notMatchedUser.peek();
       receiverID  = matchingUser.userID;
       Path currentRelativePath = Paths.get("");
       Path currentAbsolutePath = currentRelativePath.toAbsolutePath();
       Path dst = currentAbsolutePath.resolve(matchingUser.photoName);
       Image profileImage = new Image("file:"+dst.toString());
       im_image.setImage(profileImage);
       lb_name.setText(matchingUser.userName);
       lb_intro.setText(matchingUser.intro);
       notMatchedUser.poll();
       return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // to do : button_dislike, button_ back
        button_like.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 boolean likeDislikeSuccess = DBConnection.chooseFriend(event, messageContainer.userID, receiverID, true);
                 Alert alert;
                 if(likeDislikeSuccess){
                     boolean show = showProfile();
                     if(!show){
                         alert = new Alert(Alert.AlertType.ERROR);
                         alert.setContentText("No more profile to show");
                         alert.show();
                     }
                 }else{
                     alert = new Alert(Alert.AlertType.ERROR);
                     alert.setContentText("like dislike failed");
                     alert.show();
                 }


             }
       });
        button_dislike.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean likeDislikeSuccess = DBConnection.chooseFriend(event, messageContainer.userID, receiverID, false);
                Alert alert;
                if(likeDislikeSuccess){
                    boolean show = showProfile();
                    if(!show){
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("No more profile to show");
                        alert.show();
                    }
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("like dislike failed");
                    alert.show();
                }

            }
        });


        button_back.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneHelper.MessageContainer messageContainerNext = new SceneHelper.MessageContainer();
                messageContainerNext.title = "Logged In";
                messageContainerNext.username =null;
                messageContainerNext.userID = messageContainer.userID;
                SceneHelper.changeScene(event, "LoggedIn.fxml",messageContainer );
            }
        }));

    }
}
