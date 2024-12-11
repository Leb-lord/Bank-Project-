package com.example.employeebank;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class allEmployee {
    private final SimpleStringProperty empid;
    private final SimpleStringProperty bid;
    private final SimpleStringProperty username;  // Corrected property name
    private final SimpleStringProperty occupation;

    public allEmployee(String empid, String bid, String username, String occupation) {
        this.empid = new SimpleStringProperty(empid);
        this.bid = new SimpleStringProperty(bid);
        this.username = new SimpleStringProperty(username);
        this.occupation = new SimpleStringProperty(occupation);
    }

    public String getEmpid() {
        return empid.get();
    }

    public String getBid() {
        return bid.get();
    }

    public String getUsername() {  // Corrected method name
        return username.get();
    }

    public String getOccupation() {
        return occupation.get();
    }
}

