package com.example.employeebank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Manager {
    private static String costumer;
    @FXML
    private Button cancbtn, checkbtn,transbtn,depbtn,withdbtn;
    @FXML
    BorderPane border1;
    private Stage stage;
    @FXML
    TableView table;
    @FXML
    Label fullName;
    @FXML
    private TextField customerId;
    private Scene previousScene;

    private DataBase connectnow = new DataBase();

    public void cancelOnAction(ActionEvent e) {
        stage = (Stage) border1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Scene old = new Scene(fxmlLoader.load(), 513, 400);
            stage.setScene(old);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
        public void checkAccount(ActionEvent e) {
            costumer=customerId.getText();
            table.getColumns().clear();
            TableColumn<String, String> costId = new TableColumn<>("Costumer");
            TableColumn<String, String> oDate = new TableColumn<>("Creation Date");
            TableColumn<String, String> aType = new TableColumn<>("Type");
            TableColumn<String, String> aStatus = new TableColumn<>("Status");
            TableColumn<String, String> aBalance = new TableColumn<>("Balance");

            costId.setCellValueFactory(new PropertyValueFactory<>("costumer"));
            oDate.setCellValueFactory(new PropertyValueFactory<>("aod"));
            aType.setCellValueFactory(new PropertyValueFactory<>("atype"));
            aStatus.setCellValueFactory(new PropertyValueFactory<>("astatus"));
            aBalance.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));

            table.setEditable(true);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.getColumns().addAll(costId, oDate, aType, aStatus, aBalance);
            table.setVisible(true);
            fullName.setVisible(true);


            String query = "SELECT fname, mname, ltname FROM customer WHERE custid = ?";
            String query1 = "SELECT * FROM dbo.check_account(?)";
            ObservableList<DataModel> data = FXCollections.observableArrayList();

            try (Connection connection = connectnow.connectToDatabase();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {

                preparedStatement.setString(1, this.costumer);
                preparedStatement1.setString(1, this.costumer);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String firstName = resultSet.getString("fname");
                    String midName = resultSet.getString("mname");
                    String lastName = resultSet.getString("ltname");
                    fullName.setText(firstName + " " + midName + " " + lastName);
                }

                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while (resultSet1.next()) {
                    String cust = costumer;
                    String date = resultSet1.getString("aod");
                    String type = resultSet1.getString("atype");
                    String status = resultSet1.getString("astatus");
                    String balance = resultSet1.getString("opening_balance");

                    data.add(new DataModel(cust, date, type, status, balance));
                }

                table.setEditable(true);
                table.setItems(data);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        public void checkTrans(ActionEvent e) {
            costumer=customerId.getText();
            table.getColumns().clear();
            TableColumn<String, String> tnumber = new TableColumn<>("Transaction");
            TableColumn<String, String> custId = new TableColumn<>("Customer");
            TableColumn<String, String> anumber = new TableColumn<>("Account");
            TableColumn<String, String> date = new TableColumn<>("Date");
            TableColumn<String, String> mOfTransaction = new TableColumn<>("Medium Transaction");
            TableColumn<String, String> tType = new TableColumn<>("Type");
            TableColumn<String, String> tAmount = new TableColumn<>("Amount");

            tnumber.setCellValueFactory(new PropertyValueFactory<>("tnumber"));
            custId.setCellValueFactory(new PropertyValueFactory<>("custid"));
            anumber.setCellValueFactory(new PropertyValueFactory<>("acnumber"));
            date.setCellValueFactory(new PropertyValueFactory<>("dot"));
            mOfTransaction.setCellValueFactory(new PropertyValueFactory<>("medium_of_transaction"));
            tType.setCellValueFactory(new PropertyValueFactory<>("transaction_type"));
            tAmount.setCellValueFactory(new PropertyValueFactory<>("transaction_amount"));

            table.setEditable(true);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.getColumns().addAll(tnumber, custId, anumber, date, mOfTransaction, tType, tAmount);
            table.setVisible(true);
            fullName.setVisible(true);

            String query = "SELECT fname, mname, ltname FROM customer WHERE custid = ?";
            String query1 = "SELECT * FROM dbo.check_transaction(?)";
            ObservableList<TransactionDataModel> data = FXCollections.observableArrayList();

            try (Connection connection = connectnow.connectToDatabase();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {

                preparedStatement.setString(1, this.costumer);
                preparedStatement1.setString(1, this.costumer);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String firstName = resultSet.getString("fname");
                    String midName = resultSet.getString("mname");
                    String lastName = resultSet.getString("ltname");
                    fullName.setText(firstName + " " + midName + " " + lastName);
                }

                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    String transNumber = resultSet1.getString("tnumber");
                    String customer = costumer;
                    String Account = resultSet1.getString("acnumber");
                    String dot = resultSet1.getString("dot");
                    String mTransaction = resultSet1.getString("medium_of_transaction");
                    String tTransaction = resultSet1.getString("transaction_type");
                    String transaction_Amount = resultSet1.getString("transaction_amount");

                    data.add(new TransactionDataModel(transNumber,customer,Account,dot,mTransaction,
                            tTransaction,transaction_Amount));
                }

                table.setEditable(true);
                table.setItems(data);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

        public void transfer(ActionEvent e){
        Transaction transaction=new Transaction();
        transaction.setSource("manager.fxml");
            Stage stage1= (Stage) border1.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transaction.fxml"));
            try {
                Scene userScene = new Scene(fxmlLoader.load(), 513, 400);
                stage1.setScene(userScene);
                stage1.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void addCustomer(ActionEvent e){
            AddCustomer addCustomer=new AddCustomer();
            addCustomer.setSource("manager.fxml");
            Stage stage1= (Stage) border1.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_customer.fxml"));
            try {
                Scene userScene = new Scene(fxmlLoader.load(), 513, 400);
                stage1.setScene(userScene);
                stage1.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void addAccount(ActionEvent e){
            AddAccount addAccount=new AddAccount();
            addAccount.setSource("manager.fxml");
            Stage stage1= (Stage) border1.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-account.fxml"));
            try {
                Scene userScene = new Scene(fxmlLoader.load(), 513, 400);
                stage1.setScene(userScene);
                stage1.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    public void checkAll(ActionEvent e) {
        table.getColumns().clear();
        TableColumn<allAccount, String> acostId = new TableColumn<>("Costumer");
        TableColumn<allAccount, String> abranch = new TableColumn<>("Branch id");
        TableColumn<allAccount, Integer> abalance = new TableColumn<>("Balance");
        TableColumn<allAccount, String> adate = new TableColumn<>("Creation Date");
        TableColumn<allAccount, String> astatus = new TableColumn<>("Status");

        acostId.setCellValueFactory(new PropertyValueFactory<>("custid"));
        adate.setCellValueFactory(new PropertyValueFactory<>("aod"));
        abalance.setCellValueFactory(new PropertyValueFactory<>("openingBalance")); // Adjusted property name
        abranch.setCellValueFactory(new PropertyValueFactory<>("bid"));
        astatus.setCellValueFactory(new PropertyValueFactory<>("astatus"));

        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(acostId, adate, abalance, abranch, astatus);
        table.setVisible(true);

        ObservableList<allAccount> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM account";

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String cust = resultSet.getString("custid");
                String date = resultSet.getString("aod");
                int balance = resultSet.getInt("opening_balance");
                String brid = resultSet.getString("bid");
                String status = resultSet.getString("astatus");

                data.add(new allAccount(cust, date, balance, brid, status));
            }

            table.setEditable(true);
            table.setItems(data);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void deleteAccount() {
        costumer = customerId.getText();
        String query = "DELETE FROM account WHERE custid=?";

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this account?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                preparedStatement.setString(1, costumer);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    fullName.setText(" Account "+costumer+" is deleted");
                } else {
                    fullName.setText("Account deletion failed. Please check the customer ID.");
                }
            } else {
                // User canceled the deletion
                fullName.setText("Account deletion canceled.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployee(){
        Stage stage1= (Stage) border1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-employee.fxml"));
        try {
            Scene userScene = new Scene(fxmlLoader.load(), 513, 400);
            stage1.setScene(userScene);
            stage1.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void addEmployee() {
        String query = "INSERT INTO Employee (bid, username, password, occupation) VALUES (?, ?, ?, ?)() {
        table.getColumns().clear();

        TableColumn<allEmployee, String> eid = new TableColumn<>("ID");
        TableColumn<allEmployee, String> bid = new TableColumn<>("Branch ID");
        TableColumn<allEmployee, Integer> username = new TableColumn<>(" User Name");
        TableColumn<allEmployee, String> occupation = new TableColumn<>("Occupation");

        eid.setCellValueFactory(new PropertyValueFactory<>("empid"));
        bid.setCellValueFactory(new PropertyValueFactory<>("bid"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        occupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(eid, bid, username, occupation);

        ObservableList<allEmployee> data = FXCollections.observableArrayList();
        String query = "SELECT empid, username, bid, occupation FROM EMPLOYEE";

        try (Connection connection = connectnow.connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String eidValue = resultSet.getString("empid");
                String bidValue = resultSet.getString("bid");
                String usernameValue = resultSet.getString("username");
                String occValue = resultSet.getString("occupation");
                data.add(new allEmployee(eidValue, bidValue, usernameValue, occValue));
            }

            table.setItems(data);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}