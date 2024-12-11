package com.example.employeebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Import statements...

public class Transaction {
    @FXML
    private TextField custid, amount, anumber, mtrans;
    @FXML
    private Label result;
    @FXML
    private ChoiceBox<String> transaction = new ChoiceBox<>();
    @FXML
    private AnchorPane anchor;

    private final DataBase connectnow = new DataBase();
    private Stage stage;
    private static String source;

    @FXML
    public void initialize() {
        transaction.getItems().addAll("Deposit", "Withdrawal");
        transaction.setValue("Deposit");
    }

    @FXML
    public void cancel(ActionEvent e) {
        stage = (Stage) anchor.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
        try {
            Scene old = new Scene(fxmlLoader.load(), 800, 700);
            stage.setScene(old);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void transferMoney() {
        String date = date();
        String query = "exec deposit_withdrawal @account=?, @dot=?, @medium_trans=?, @trans_type=?, @trans_amount=?";

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, anumber.getText());
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, mtrans.getText());
            preparedStatement.setString(4, transaction.getValue());
            preparedStatement.setString(5, amount.getText());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == -1) {
                result.setText("Add success");
            } else {
                result.setText("Failed, check the customer id");
            }

        } catch (SQLException e) {
            result.setText("Error: " + e.getMessage());
        }
    }

    private String date() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }
    public void setSource(String s){
        this.source=s;
    }
}
