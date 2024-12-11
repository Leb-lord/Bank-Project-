package com.example.bank;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataModel {
    private final StringProperty costumer;
    private final StringProperty aod;
    private final StringProperty atype;
    private final StringProperty astatus;
    private final StringProperty openingBalance;

    public DataModel(String costumer, String aod, String atype, String astatus, String openingBalance) {
        this.costumer = new SimpleStringProperty(costumer);
        this.aod = new SimpleStringProperty(aod);
        this.atype = new SimpleStringProperty(atype);
        this.astatus = new SimpleStringProperty(astatus);
        this.openingBalance = new SimpleStringProperty(openingBalance);
    }

    public String getCostumer() {
        return costumer.get();
    }

    public StringProperty costumerProperty() {
        return costumer;
    }

    public String getAod() {
        return aod.get();
    }

    public StringProperty aodProperty() {
        return aod;
    }

    public String getAtype() {
        return atype.get();
    }

    public StringProperty atypeProperty() {
        return atype;
    }

    public String getAstatus() {
        return astatus.get();
    }

    public StringProperty astatusProperty() {
        return astatus;
    }

    public String getOpeningBalance() {
        return openingBalance.get();
    }

    public StringProperty openingBalanceProperty() {
        return openingBalance;
    }
}
