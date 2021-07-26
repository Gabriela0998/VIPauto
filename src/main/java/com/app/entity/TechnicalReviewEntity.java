package com.app.entity;

import java.sql.Date;

public class TechnicalReviewEntity {
    private Integer id;
    private Integer id_cost;
    private Double amount;
    private Date date_from;
    private Date date_to;
    private String service;
    private String extra;

    private int id_myData;

    public TechnicalReviewEntity(){}
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
