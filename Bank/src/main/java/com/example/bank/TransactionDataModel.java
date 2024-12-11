package com.example.bank;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionDataModel {
    private final SimpleObjectProperty<String> tnumber;
    private final SimpleObjectProperty<String> acnumber;
    private final SimpleStringProperty dot;
    private final SimpleStringProperty medium_of_transaction;
    private final SimpleStringProperty transaction_type;
    private final SimpleObjectProperty<String> transaction_amount;

    public TransactionDataModel(String tnumber, String acnumber, String dot,
                                String medium_of_transaction, String transaction_type, String transaction_amount) {
        this.tnumber = new SimpleObjectProperty<>(tnumber);
        this.acnumber = new SimpleObjectProperty<>(acnumber);
        this.dot = new SimpleStringProperty(dot);
        this.medium_of_transaction = new SimpleStringProperty(medium_of_transaction);
        this.transaction_type = new SimpleStringProperty(transaction_type);
        this.transaction_amount = new SimpleObjectProperty<>(transaction_amount);
    }

    // Getter methods
    public String getTnumber() {
        return tnumber.get();
    }

    public String getAcnumber() {
        return acnumber.get();
    }

    public String getDot() {
        return dot.get();
    }

    public String getMedium_of_transaction() {
        return medium_of_transaction.get();
    }

    public String getTransaction_type() {
        return transaction_type.get();
    }

    public String getTransaction_amount() {
        return transaction_amount.get();
    }
}
