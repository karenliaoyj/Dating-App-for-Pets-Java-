package com.yenju.datingapp;

import javafx.fxml.Initializable;

public abstract class SceneController implements Initializable {
    public abstract void setMessage(SceneHelper.MessageContainer messageContainer);
}
