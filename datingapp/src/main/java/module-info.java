module com.yenju.datingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.yenju.datingapp to javafx.fxml;
    exports com.yenju.datingapp;
}