package com.example.employeebank;

public class allAccount {
    private String custid;
    private String aod;
    private int opening_Balance;
    private String bid;
    private String astatus;

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public void setAod(String aod) {
        this.aod = aod;
    }

    public void setOpeningBalance(int openingBalance) {
        this.opening_Balance = openingBalance;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setAstatus(String astatus) {
        this.astatus = astatus;
    }

    public String getCustid() {
        return custid;
    }

    public String getAod() {
        return aod;
    }

    public int getOpeningBalance() {
        return opening_Balance;
    }

    public String getBid() {
        return bid;
    }

    public String getAstatus() {
        return astatus;
    }

    public allAccount(String custid, String aod, int openingBalance, String bid, String astatus) {
        this.custid = custid;
        this.aod = aod;
        this.opening_Balance = openingBalance;
        this.bid = bid;
        this.astatus = astatus;
    }

    // ... Getters and Setters
}
