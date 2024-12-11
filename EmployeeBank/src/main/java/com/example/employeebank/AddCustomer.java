package com.example.employeebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCustomer {
    @FXML
    private Button addbtn,cancelbtn;
    @FXML
    private TextField fName,mName,lName,occupation,phone,city,dBirth;
    @FXML
    Stage stage;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label result;
    private static String source;
    DataBase connectnow=new DataBase();
    public void cancelOnAction(ActionEvent e) {
        stage = (Stage) anchorPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
        try {
            Scene old = new Scene(fxmlLoader.load(), 800, 700);
            stage.setScene(old);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addCustomer(ActionEvent e) {
        String existanceQuery = "SELECT fname, mname, ltname, city, mobileno, occupation, dob FROM customer WHERE " +
                "fname=? AND mname=? AND ltname=? AND city=? AND mobileno=? AND occupation=? AND dob=?";

        String insertQuery = "INSERT INTO customer (fname, mname, ltname, city, mobileno, occupation, dob) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement existanceStatement = connection.prepareStatement(existanceQuery)) {

            existanceStatement.setString(1, fName.getText());
            existanceStatement.setString(2, mName.getText());
            existanceStatement.setString(3, lName.getText());
            existanceStatement.setString(4, city.getText());
            existanceStatement.setString(5, phone.getText());
            existanceStatement.setString(6, occupation.getText());
            existanceStatement.setString(7, dBirth.getText());

            ResultSet resultSet = existanceStatement.executeQuery();

            if (resultSet.next()) {
                result.setText("Failed, already exists");
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, fName.getText());
                    insertStatement.setString(2, mName.getText());
                    insertStatement.setString(3, lName.getText());
                    insertStatement.setString(4, city.getText());
                    insertStatement.setString(5, phone.getText());
                    insertStatement.setString(6, occupation.getText());
                    insertStatement.setString(7, dBirth.getText());

                    int rowsAffected = insertStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        result.setText("Add success");
                    } else {
                        result.setText("Failed to add customer");
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error in adding");
            ex.printStackTrace();
        }
    }
    public void setSource(String s){
        this.source=s;
    }
}
