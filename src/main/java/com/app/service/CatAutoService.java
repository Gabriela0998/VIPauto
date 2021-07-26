package com.app.service;

import com.app.controller.CatAutoController;
import com.app.entity.CatAutoEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatAutoService {
    private CatAutoController catAutoController;

    public CatAutoService() {
        catAutoController = new CatAutoController();
    }
    public CatAutoEntity insert(CatAutoEntity catAuto) {
        return CatAutoController.insert(catAuto);
    }
    public CatAutoEntity delete(CatAutoEntity catAuto) {
        return CatAutoController.delete(catAuto);
    }
    public CatAutoEntity update(CatAutoEntity catAuto) {
        return CatAutoController.update(catAuto);
    }
    public CatAutoEntity getById(CatAutoEntity catAuto) {
        return CatAutoController.getById(catAuto);
    }
    public CatAutoEntity vis(CatAutoEntity catAuto) {
        return CatAutoController.vis(catAuto);
    }

    public List<CatAutoEntity> getAll() {
        return catAutoController.getAll();
    }
    public List<CatAutoEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return catAutoController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return catAutoController.getByName(criteria);
    }
}
