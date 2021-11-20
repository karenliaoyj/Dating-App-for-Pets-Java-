package com.yenju.datingapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class MatchLogicController extends SceneController  {
    private SceneHelper.MessageContainer messageContainer;

    @FXML
    private RadioButton rb_ball;
    @FXML
    private RadioButton rb_stuffedToy;
    @FXML
    private RadioButton rb_black;
    @FXML
    private RadioButton rb_white;
    @FXML
    private RadioButton rb_play;
    @FXML
    private RadioButton rb_sleep;
    @FXML
    private Button button_go;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ToggleGroup toggleGroupToy = new ToggleGroup(); //one click at a time
        rb_ball.setToggleGroup(toggleGroupToy);
        rb_stuffedToy.setToggleGroup(toggleGroupToy);
        rb_ball.setSelected(true);
        
        ToggleGroup toggleGroupColor = new ToggleGroup();
        rb_black.setToggleGroup(toggleGroupColor);
        rb_white.setToggleGroup(toggleGroupColor);
        rb_black.setSelected(true);

        ToggleGroup toggleGroupActivity = new ToggleGroup();
        rb_sleep.setToggleGroup(toggleGroupActivity);
        rb_play.setToggleGroup(toggleGroupActivity);
        rb_play.setSelected(true);

        button_go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleToy = ((RadioButton) toggleGroupToy.getSelectedToggle()).getText();
                String toggleColor = ((RadioButton) toggleGroupColor.getSelectedToggle()).getText();
                String toggleActivity = ((RadioButton) toggleGroupActivity.getSelectedToggle()).getText();

                boolean success = DBConnection.matchLogic(event, toggleToy,toggleColor,toggleActivity,messageContainer.userID);
                SceneHelper.MessageContainer messageContainerNext = new SceneHelper.MessageContainer();
                messageContainerNext.title = "Choose Friend";
                messageContainerNext.username =null;
                messageContainerNext.userID = messageContainer.userID;
                SceneHelper.changeScene(event, "likeOrDislike.fxml",messageContainer );


            }
        });





    }
    public void setMessage(SceneHelper.MessageContainer messageContainer) {
        this.messageContainer = messageContainer;
    }
}
