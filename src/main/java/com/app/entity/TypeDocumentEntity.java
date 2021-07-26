package com.app.entity;

import com.app.view.TypeDocument;

public class TypeDocumentEntity {
    private Integer id;
    private String name;
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

    public void visible(String nameTF, String idTF) {
        TypeDocument.visible(nameTF,idTF);
    }

    public int getId_myData() {
        return id_myData;
    }

    public void setId_myData(int id_myData) {
        this.id_myData = id_myData;
    }
}
