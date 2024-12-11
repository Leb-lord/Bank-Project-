package com.example.employeebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtPass;
    @FXML
    private Button cancel,logIn;

    private Stage stage;
    @FXML
    private Label loginMessage;
    private String employee;
    @FXML
    private BorderPane border;
    @FXML
    private ChoiceBox<String> position = new ChoiceBox<>();
    public void cancelOnAction(ActionEvent e) {
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void initialize() {
            position.getItems().addAll("CUSTOMER_MANAGER", "ADMIN","LOAN_MANAGER");
        position.setValue("CUSTOMER_MANAGER");
    }

    public void checkId(){
        loginMessage.setVisible(true);
        if (!txtId.getText().isBlank() && !txtPass.getText().isBlank() && position.getValue() != null) {
            if (checkValidation(position.getValue())) {
                Stage stage1 = (Stage) border.getScene().getWindow();
                if ("CUSTOMER_MANAGER".equals(position.getValue())) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-employee.fxml"));
                    try {
                        Scene userScene = new Scene(fxmlLoader.load(), 800, 700);
                        stage1.setScene(userScene);
                        stage1.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if ("ADMIN".equals(position.getValue())) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manager.fxml"));
                    try {
                        Scene userScene = new Scene(fxmlLoader.load(), 800, 700);
                        stage1.setScene(userScene);
                        stage1.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loan-manager.fxml"));
                    try {
                        Scene userScene = new Scene(fxmlLoader.load(), 600, 500);
                        stage1.setScene(userScene);
                        stage1.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                loginMessage.setText("Your Login Failed, Check UserName Or Pass!");
            }
        } else {
            loginMessage.setText("Please enter both username and password.");
        }
    }


    private boolean checkValidation(String occupation) {
        DataBase connectnow = new DataBase();
        String query = "SELECT * FROM employee WHERE empid = ? AND password = ? AND occupation = ?";
        boolean isAuthenticated = false;

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, txtId.getText());
            preparedStatement.setString(2, txtPass.getText());
            preparedStatement.setString(3, occupation);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isAuthenticated = resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
//   isAuthenticated boolean isCustomerManager = checkValidation("CUSTOMER_MANAGER");


}
