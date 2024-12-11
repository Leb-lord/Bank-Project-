package com.example.employeebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanManager {
    @FXML
    private TextField customerID, loan, branch;
    @FXML
    private Label loanAmount;
    DataBase connection = new DataBase();
    private static String customer;
    private Stage stage;
    @FXML
    AnchorPane anchorpane;

    public void cancelOnAction(ActionEvent e) {
        stage = (Stage) anchorpane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Scene old = new Scene(fxmlLoader.load(), 513, 400);
            stage.setScene(old);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void checkLoan() {
        customer = customerID.getText();
        String query = "SELECT SUM(loan_amount) AS totalLoan FROM loan WHERE custid = ?";
        try (Connection connectnow = connection.connectToDatabase();
             PreparedStatement preparedStatement = connectnow.prepareStatement(query)) {
            preparedStatement.setString(1, customer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                loanAmount.setVisible(true);
                if (resultSet.next()) {
                    int totalLoan = resultSet.getInt("totalLoan");
                    loanAmount.setText("Total Loan= " + totalLoan+"$");
                } else {
                    loanAmount.setText("No loans found for Customer ID " + customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addLoan() {
        customer = customerID.getText();
        String checkQuery = "SELECT * FROM loan WHERE custid = ? AND bid = ?";
        String updateQuery = "UPDATE loan SET loan_amount = loan_amount + ? WHERE custid = ? AND bid = ?";
        String insertQuery = "INSERT INTO loan (custid, bid, loan_amount) VALUES (?, ?, ?)";

        try (Connection connectnow = connection.connectToDatabase()) {
            // Check if the customer ID exists in the loan table
            try (PreparedStatement checkStatement = connectnow.prepareStatement(checkQuery)) {
                checkStatement.setString(1, customer);
                checkStatement.setString(2, branch.getText());

                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // Customer ID exists, perform UPDATE operation
                    try (PreparedStatement updateStatement = connectnow.prepareStatement(updateQuery)) {
                        double loanAmountValue = Double.parseDouble(loan.getText());
                        updateStatement.setDouble(1, loanAmountValue);
                        updateStatement.setString(2, customer);
                        updateStatement.setString(3, branch.getText());

                        int rowsAffected = updateStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                    "Update loan for " + customer + "?");
                            alert.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                    "Failed to update loan for " + customer);
                            alert.show();
                        }
                    }
                } else {
                    // Customer ID does not exist, perform INSERT operation
                    try (PreparedStatement insertStatement = connectnow.prepareStatement(insertQuery)) {
                        double loanAmountValue = Double.parseDouble(loan.getText());
                        insertStatement.setString(1, customer);
                        insertStatement.setString(2, branch.getText());
                        insertStatement.setDouble(3, loanAmountValue);

                        int rowsAffected = insertStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                    "Add new loan for " + customer + "?");
                            alert.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                    "Failed to add new loan for " + customer);
                            alert.show();
                        }
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Invalid loan amount. Please enter a valid number.");
            alert.show();
        }
    }

}
