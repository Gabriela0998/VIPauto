package com.app.service;

import com.app.controller.TypeController;
import com.app.entity.TypeEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeService {
    private TypeController typeController;

    public TypeService() {
        typeController = new TypeController();
    }
    public TypeEntity insert(TypeEntity type) {
        return TypeController.insert(type);
    }
    public TypeEntity update(TypeEntity type) {
        return TypeController.update(type);
    }
    public TypeEntity delete(TypeEntity type) {
        return TypeController.delete(type);
    }
    public TypeEntity getById(TypeEntity type) {
        return TypeController.getById(type);
    }
    public TypeEntity vis(TypeEntity type) {
        return TypeController.vis(type);
    }
    public Integer getExistingTypeId(String majorName) {
        return typeController.getExistingTypeId(majorName);
    }

    public List<TypeEntity> getAll() {
        return typeController.getAll();
    }
    public List<TypeEntity> getAll(int id) {
        return typeController.getAll(id);
    }
    public List<TypeEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return typeController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return typeController.getByName(criteria);
    }
}

