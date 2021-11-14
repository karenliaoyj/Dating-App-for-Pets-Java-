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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    // to do : photo upload

    public static boolean sighUpUser(ActionEvent event, String username, String password, String gender, String photoName){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM user WHERE username = ?" );
            psCheckUserExist.setString( 1, username);
            resultSet = psCheckUserExist.executeQuery();

            if(resultSet.isBeforeFirst()){ //user exits
                System.out.println("User already exists");
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO user (username, password, gender,photoName) VALUES (?, ?, ?,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, gender);
                psInsert.setString(4,photoName);
                psInsert.executeUpdate();
                return true;
            }

        }catch (Exception e ) {
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
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }
        return false;
    }

    public static int logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultset = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
            preparedStatement = connection.prepareStatement("SELECT password, userID FROM user WHERE username = ?");
            preparedStatement.setString(1,username);
            resultset = preparedStatement.executeQuery();

            if(!resultset.isBeforeFirst()){
                System.out.println("user not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            }else{
                while(resultset.next()){
                    String retrivedPassword = resultset.getString("password");
                    int retrivedUserID = resultset.getInt("userID");
                    if(retrivedPassword.equals(password)){
                        return retrivedUserID;

                    }else{
                        return -1;

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
        return -1;
    }

    public static boolean matchLogic(ActionEvent event,String toy, String color, String activity, int userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
            preparedStatement = connection.prepareStatement("UPDATE user SET toy=? ,color=?, activity=? WHERE userID=?;");
            preparedStatement.setString(1, toy);
            preparedStatement.setString(2, color);
            preparedStatement.setString(3, activity);
            preparedStatement.setInt(4, userID);
            int count = preparedStatement.executeUpdate();
            if (count <= 0) {
                System.out.println("user not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return false;
        }
    }


        public static boolean writeText(ActionEvent event, int userID, int receiverID, String text){
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultset = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
                preparedStatement = connection.prepareStatement("INSERT INTO chatRecord  (senter, receiver,content, time) VALUES (?,?,?,NOW())");
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, receiverID);
                preparedStatement.setString(3, text);
                int count = preparedStatement.executeUpdate();
                if (count <= 0) {
                    System.out.println("cannot send message");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("cannot send message");
                    alert.show();
                    return false;
                } else {
                    return true;

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultset != null) {
                    try {
                        resultset.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }



        public static int getMatchPeople(ActionEvent event, int userID) {
            Connection connection = null;
            PreparedStatement preparedStatement =null;
            ResultSet resultset = null;
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaFx", "root", "karen87930");
                preparedStatement = connection.prepareStatement("SELECT chatppl from user where userID = ?");
                preparedStatement.setInt(1,userID);
                resultset = preparedStatement.executeQuery();

                if(!resultset.isBeforeFirst()){
                    System.out.println("user not found in the database");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided credentials are incorrect");
                    alert.show();
                }else {
                    while (resultset.next()) {
                        int retrivedChatPpl = resultset.getInt("chatppl");
                        return retrivedChatPpl;

                    }
                    return -1;
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
            return -1;

        }
    }




