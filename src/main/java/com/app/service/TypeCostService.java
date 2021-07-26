package com.app.service;

import com.app.controller.TypeCostController;
import com.app.entity.TypeCostEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeCostService {
    private TypeCostController typeCostController;

    public TypeCostService() {
        typeCostController = new TypeCostController();
    }
    public TypeCostEntity insert(TypeCostEntity typeCost) {
        return TypeCostController.insert(typeCost);
    }
    public TypeCostEntity delete(TypeCostEntity typeCost) {
        return TypeCostController.delete(typeCost);
    }
    public TypeCostEntity update(TypeCostEntity typeCost) {
        return TypeCostController.update(typeCost);
    }
    public TypeCostEntity getById(TypeCostEntity typeCost) {
        return TypeCostController.getById(typeCost);
    }
    public TypeCostEntity vis(TypeCostEntity typeCost) {
        return TypeCostController.vis(typeCost);
    }

    public List<TypeCostEntity> getAll() {
        return typeCostController.getAll();
    }
    public List<TypeCostEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return typeCostController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return typeCostController.getByName(criteria);
    }
}
