package com.app.service;

import com.app.controller.InsCompaniesController;
import com.app.entity.InsCompaniesEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsCompaniesService {
    private InsCompaniesController insCompaniesController;

    public InsCompaniesService() {
        insCompaniesController = new InsCompaniesController();
    }
    public InsCompaniesEntity insert(InsCompaniesEntity insCompanies) {
        return InsCompaniesController.insert(insCompanies);
    }
    public InsCompaniesEntity delete(InsCompaniesEntity insCompanies) {
        return InsCompaniesController.delete(insCompanies);
    }
    public InsCompaniesEntity update(InsCompaniesEntity insCompanies) {
        return InsCompaniesController.update(insCompanies);
    }
    public InsCompaniesEntity getById(InsCompaniesEntity insCompanies) {
        return InsCompaniesController.getById(insCompanies);
    }
    public InsCompaniesEntity vis(InsCompaniesEntity insCompanies) {
        return InsCompaniesController.vis(insCompanies);
    }

    public List<InsCompaniesEntity> getAll() {
        return insCompaniesController.getAll();
    }
    public List<InsCompaniesEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return insCompaniesController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return insCompaniesController.getByName(criteria);
    }
}
