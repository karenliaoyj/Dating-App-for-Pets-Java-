package com.yenju.datingapp;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DBConnection {

    public static void changeScene(ActionEvent event, String fxmlFile , String title, String username){
        Parent root = null;

        if(username != null ){
            try {
                FXMLLoader loader = new FXMLLoader(DBConnection.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
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
        stage.setTitle(title);
        stage.setScene(new Scene(root,  600, 400));
        stage.show();
    }
    // to do : photo upload
    public static void sighUpUser(ActionEvent event, String username, String password, String gender){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection(" jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM user username = ?" );
            psCheckUserExist.setString( 1, username);
            resultSet = psCheckUserExist.executeQuery();

            if(resultSet.isBeforeFirst()){ //user exits
                System.out.println("User already exists");
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO user(username,password,gender");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,gender);
                psInsert.executeUpdate();

                changeScene(event, "logIn.fxml", "Log In!", username);
            }

        }catch (SQLException e ){
            e.printStackTrace();

        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExist != null){
                try{
                    psCheckUserExist.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultset = null;
        try{
            connection = DriverManager.getConnection(" jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
            preparedStatement = connection.prepareStatement("SELECT password, gender FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            resultset = preparedStatement.executeQuery();

            if(resultset.isBeforeFirst()){
                System.out.println("user no founr in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            }else{
                while(resultset.next()){
                    String retrivedPassword = resultset.getString("password");
                    String retrivesGender = resultset.getString("gender");
                    if(retrivedPassword.equals(password)){
                        changeScene(event, "logIn.fxml", "Welcome", username);
                    }else{
                        System.out.println("Password did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect");
                        alert.show();

                    }
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultset != null){
                try{
                    resultset.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
