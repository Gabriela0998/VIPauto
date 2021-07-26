package com.app.service;

import com.app.controller.TypeDocumentController;
import com.app.entity.TypeDocumentEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeDocumentService {
    private TypeDocumentController typeDocumentController;

    public TypeDocumentService() {
        typeDocumentController = new TypeDocumentController();
    }
    public TypeDocumentEntity insert(TypeDocumentEntity typeDocument) {
        return TypeDocumentController.insert(typeDocument);
    }
    public TypeDocumentEntity delete(TypeDocumentEntity typeDocument) {
        return TypeDocumentController.delete(typeDocument);
    }
    public TypeDocumentEntity update(TypeDocumentEntity typeDocument) {
        return TypeDocumentController.update(typeDocument);
    }
    public TypeDocumentEntity getById(TypeDocumentEntity typeDocument) {
        return TypeDocumentController.getById(typeDocument);
    }
    public TypeDocumentEntity vis(TypeDocumentEntity typeDocument) {
        return TypeDocumentController.vis(typeDocument);
    }

    public List<TypeDocumentEntity> getAll() {
        return typeDocumentController.getAll();
    }
    public List<TypeDocumentEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return typeDocumentController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return typeDocumentController.getByName(criteria);
    }
}
