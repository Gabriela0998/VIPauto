package com.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String fname;
    private String lname;
    private String password;
    private String admin;
    private Integer A0;
    private Boolean A1;
    private Boolean A2;
    private Boolean A3;
    private Boolean A4;
    private Boolean A5;
    private Boolean A6;
    private Boolean A7;
    private Boolean A8;
    private Boolean A9;
    private Boolean A10;
    private String extra;

    private int type;
    private int id_type;

    public UserEntity(){

    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Integer getA0() {
        return A0;
    }

    public void setA0(Integer a0) {
        A0 = a0;
    }

    public Boolean getA1() {
        return A1;
    }

    public void setA1(Boolean a1) {
        A1 = a1;
    }

    public Boolean getA2() {
        return A2;
    }

    public void setA2(Boolean a2) {
        A2 = a2;
    }

    public Boolean getA3() {
        return A3;
    }

    public void setA3(Boolean a3) {
        A3 = a3;
    }

    public Boolean getA4() {
        return A4;
    }

    public void setA4(Boolean a4) {
        A4 = a4;
    }

    public Boolean getA5() {
        return A5;
    }

    public void setA5(Boolean a5) {
        A5 = a5;
    }

    public Boolean getA6() {
        return A6;
    }

    public void setA6(Boolean a6) {
        A6 = a6;
    }

    public Boolean getA7() {
        return A7;
    }

    public void setA7(Boolean a7) {
        A7 = a7;
    }

    public Boolean getA8() {
        return A8;
    }

    public void setA8(Boolean a8) {
        A8 = a8;
    }

    public Boolean getA9() {
        return A9;
    }

    public void setA9(Boolean a9) {
        A9 = a9;
    }

    public Boolean getA10() {
        return A10;
    }

    public void setA10(Boolean a10) {
        A10 = a10;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }
}
