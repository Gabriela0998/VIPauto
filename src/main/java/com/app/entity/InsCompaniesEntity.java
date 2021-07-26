package com.app.entity;

import com.app.view.InsCompanies;

public class InsCompaniesEntity {
    private Integer id;
    private String name;
    private String phone;
    private String extra;
    private int id_myData;

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

    public void visible(String nameTF, String idTF, String phoneTF, String extraTF) {
        InsCompanies.visible(nameTF,idTF,phoneTF,extraTF);
    }

    public int getId_myData() {
        return id_myData;
    }

    public void setId_myData(int id_myData) {
        this.id_myData = id_myData;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
