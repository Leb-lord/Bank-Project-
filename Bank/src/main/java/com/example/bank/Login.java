package com.example.bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class Login {
    private static String costumer;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button cancel;
    @FXML
    private Button login;
    @FXML
    private Label loginMessage;
    private Stage stage;

    @FXML
    private BorderPane border;

    public void cancelOnAction(ActionEvent e) {
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void setLoginMessage(ActionEvent e) {
        if (!txtUser.getText().isBlank() && !txtPassword.getText().isBlank()) {
            if (checkValidation()) {
                setCostumer(txtUser.getText());
                loginMessage.setText("Welcome!");
                Stage stage1= (Stage) border.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-user.fxml"));
                try {
                    Scene userScene = new Scene(fxmlLoader.load(), 800, 700);
                    stage1.setScene(userScene);
                    stage1.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                loginMessage.setText("Your Login Failed, Check UserName Or Pass!");
            }
        } else {
            loginMessage.setText("Please enter both username and password.");
        }
    }

    public boolean checkValidation() {
        DataBase connectnow = new DataBase();
        String query = "SELECT * FROM account WHERE custid = ? AND pass = ?";
        boolean isAuthenticated = false;

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, txtUser.getText());
            preparedStatement.setString(2, txtPassword.getText());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isAuthenticated = resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }
    public void setCostumer(String c)
    {
        this.costumer=c;

    }
    public String getCostumer(String c){
        c=this.costumer;
        return c;
    }
}
