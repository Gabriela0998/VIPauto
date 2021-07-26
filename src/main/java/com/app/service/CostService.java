package com.app.service;

import com.app.controller.CostController;
import com.app.entity.CostEntity;
import com.app.entity.ReferenceEntity;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CostService {
    private static CostController costController;

    public CostService() {
        costController = new CostController();
    }
    public CostEntity insert(CostEntity cost) {
        return CostController.insert(cost);
    }
    public CostEntity update(CostEntity cost) {
        return CostController.update(cost);
    }
    public CostEntity delete(CostEntity cost) {
        return CostController.delete(cost);
    }
    public CostEntity getById(CostEntity cost) {
        return CostController.getById(cost);
    }
    public CostEntity vis(CostEntity cost) {
        return CostController.vis(cost);
    }
    public static Integer getExistingCostIdCat(String comboName) {
        return CostController.getExistingCostIdCat(comboName);
    }
    public Integer getExistingCostIdType(String comboName) {
        return CostController.getExistingCostIdType(comboName);
    }
    public static Integer getExistingCostIdModel(String comboName) {
        return CostController.getExistingCostIdModel(comboName);
    }
    public Integer getExistingCostIdCar(String comboName) {
        return CostController.getExistingCostIdCar(comboName);
    }
    public static Integer getExistingCostIdBrand(String comboName) {
        return CostController.getExistingCostIdBrand(comboName);
    }
    public List<CostEntity> getAll() {
        return costController.getAll();
    }
    public List<ReferenceEntity> referencePrint(Date date_from, Date date_to) {
        return costController.referencePrint(date_from, date_to);
    }
    public List<CostEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return costController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return costController.getByName(criteria);
    }
   /* public List<ReferenceEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return costController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return costController.getByName(criteria);
    }*/
}

