package com.example.employeebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddEmployee {
    @FXML
    private TextField username,branch,occupation,password;
    private Stage stage;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label result;
    DataBase connectnow=new DataBase();

    public void cancelOnAction() {
        stage = (Stage) anchorPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manager.fxml"));
        try {
            Scene old = new Scene(fxmlLoader.load(), 800, 700);
            stage.setScene(old);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void addEmployee() {
        String query = "INSERT INTO Employee (bid, username, password, occupation) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branch.getText());     // Assuming branch is an input field
            preparedStatement.setString(2, username.getText());      // Assuming custid is an input field
            preparedStatement.setString(3, password.getText());   // Assuming password is an input field
            preparedStatement.setString(4, occupation.getText()); // Assuming occupation is an input field

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                result.setText("Add Success");
            } else {
                result.setText("Failed to add employee. Check your input.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
