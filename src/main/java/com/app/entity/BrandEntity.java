package com.app.entity;

public class BrandEntity {
    private Integer id;
    private String name;
    private Integer id_catAuto;

    private int id_myData;

    public BrandEntity(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_catAuto() {
        return id_catAuto;
    }

    public void setId_catAuto(Integer id_catAuto) {
        this.id_catAuto = id_catAuto;
    }

    public int getId_myData() {
        return id_myData;
    }

    public void setId_myData(int id_myData) {
        this.id_myData = id_myData;
    }
}
