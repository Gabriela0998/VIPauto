package com.app.entity;

import java.util.Date;

public class PeopleEntity {
    private Integer id;
    private String name;
    private String fname;
    private String lname;
    private String egn;
    private String phone;
    private String mail;
    private Date dateBorn;
    private String address;
    private String addressSecond;
    private String bornCountry;
    private String text1;
    private Integer idFirms;
    private Integer typePerson;
    private Integer statusPerson;
    private Date dateC;
    private Date dateV;
    private String place;
    private String numberDoc;

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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressSecond() {
        return addressSecond;
    }

    public void setAddressSecond(String addressSecond) {
        this.addressSecond = addressSecond;
    }

    public String getBornCountry() {
        return bornCountry;
    }

    public void setBornCountry(String bornCountry) {
        this.bornCountry = bornCountry;
    }

    public Integer getIdFirms() {
        return idFirms;
    }

    public void setIdFirms(Integer idFirms) {
        this.idFirms = idFirms;
    }

    public Integer getTypePerson() {
        return typePerson;
    }

    public void setTypePerson(Integer typePerson) {
        this.typePerson = typePerson;
    }

    public Integer getStatusPerson() {
        return statusPerson;
    }

    public void setStatusPerson(Integer statusPerson) {
        this.statusPerson = statusPerson;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText1() {
        return text1;
    }

    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    public Date getDateV() {
        return dateV;
    }

    public void setDateV(Date dateV) {
        this.dateV = dateV;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNumberDoc() {
        return numberDoc;
    }

    public void setNumberDoc(String numberDoc) {
        this.numberDoc = numberDoc;
    }
}
