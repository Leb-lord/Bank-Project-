package com.example.employeebank;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransactionDataModel {
    private final StringProperty tnumber;
    private final StringProperty custid;
    private final StringProperty acnumber;
    private final StringProperty dot;
    private final StringProperty medium_of_transaction;
    private final StringProperty transaction_type;
    private final StringProperty transaction_amount;

    public TransactionDataModel(
            String tnumber,
            String custid,
            String acnumber,
            String dot,
            String medium_of_transaction,
            String transaction_type,
            String transaction_amount) {
        this.tnumber = new SimpleStringProperty(tnumber);
        this.custid = new SimpleStringProperty(custid);
        this.acnumber = new SimpleStringProperty(acnumber);
        this.dot = new SimpleStringProperty(dot);
        this.medium_of_transaction = new SimpleStringProperty(medium_of_transaction);
        this.transaction_type = new SimpleStringProperty(transaction_type);
        this.transaction_amount = new SimpleStringProperty(transaction_amount);
    }

    public String getTnumber() {
        return tnumber.get();
    }

    public StringProperty tnumberProperty() {
        return tnumber;
    }

    public String getCustid() {
        return custid.get();
    }

    public StringProperty custidProperty() {
        return custid;
    }

    public String getAcnumber() {
        return acnumber.get();
    }

    public StringProperty acnumberProperty() {
        return acnumber;
    }

    public String getDot() {
        return dot.get();
    }

    public StringProperty dotProperty() {
        return dot;
    }

    public String getMedium_of_transaction() {
        return medium_of_transaction.get();
    }

    public StringProperty medium_of_transactionProperty() {
        return medium_of_transaction;
    }

    public String getTransaction_type() {
        return transaction_type.get();
    }

    public StringProperty transaction_typeProperty() {
        return transaction_type;
    }

    public String getTransaction_amount() {
        return transaction_amount.get();
    }

    public StringProperty transaction_amountProperty() {
        return transaction_amount;
    }
}
