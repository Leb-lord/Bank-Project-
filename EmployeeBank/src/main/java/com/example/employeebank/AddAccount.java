package com.example.employeebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAccount {
    @FXML
    private Button addbtn,cancelbtn;
    @FXML
    private TextField custId,bId,aType,aStatus,oBalance;
    @FXML
    Stage stage;
    @FXML
    AnchorPane anchorPane;
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

    public void addAccount(ActionEvent e){
        String query="INSERT INTO account (custid,bid,opening_balance,aod,atype,astatus,pass) VALUES(?,?,?,?,?,?,'123') ";
        try(Connection connection=connectnow.connectToDatabase();
            PreparedStatement preparedStatement=connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1,custId.getText());
            preparedStatement.setString(2,bId.getText());
            preparedStatement.setString(3,oBalance.getText());
            preparedStatement.setString(4,date());
            preparedStatement.setString(5,aType.getText());
            preparedStatement.setString(6,aStatus.getText());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                result.setText("Add is done");
                System.out.println("Update successful. Rows affected: " + rowsAffected);
            } else {
                result.setText("Failed, customer didnt exist");
                System.out.println("No rows affected. Check your query.");
            }


        }
        catch (SQLException ex){
            result.setText("Failed,something wrong");
            ex.printStackTrace();
        }
    }
    private void showAlert(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
