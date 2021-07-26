package com.app.entity;

import java.sql.Date;

public class ThirdPartyLiabilityEntity {
    private Integer id;
    private Integer id_cost;
    private Integer numberDeposits;
    private Double amount;
    private Date date_from;
    private Date date_to;
    private String policyNumber;
    private Double amount1;
    private Date date1;
    private Double amount2;
    private Date date2;
    private Double amount3;
    private Date date3;
    private Double amount4;
    private Date date4;
    private String extra;

    private int id_myData;

    public ThirdPartyLiabilityEntity(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId_myData() {
        return id_myData;
    }

    public void setId_myData(int id_myData) {
        this.id_myData = id_myData;
    }

    public Integer getId_cost() {
        return id_cost;
    }

    public void setId_cost(Integer id_cost) {
        this.id_cost = id_cost;
    }

    public Integer getNumberDeposits() {
        return numberDeposits;
    }

    public void setNumberDeposits(Integer numberDeposits) {
        this.numberDeposits = numberDeposits;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Double getAmount1() {
        return amount1;
    }

    public void setAmount1(Double amount1) {
        this.amount1 = amount1;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Double getAmount2() {
        return amount2;
    }

    public void setAmount2(Double amount2) {
        this.amount2 = amount2;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Double getAmount3() {
        return amount3;
    }

    public void setAmount3(Double amount3) {
        this.amount3 = amount3;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Double getAmount4() {
        return amount4;
    }

    public void setAmount4(Double amount4) {
        this.amount4 = amount4;
    }

    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
