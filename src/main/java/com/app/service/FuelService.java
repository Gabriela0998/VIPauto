package com.app.service;

import com.app.controller.FuelController;
import com.app.entity.FuelEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuelService {
    private FuelController fuelController;

    public FuelService() {
        fuelController = new FuelController();
    }
    public FuelEntity insert(FuelEntity fuel) {
        return FuelController.insert(fuel);
    }
    public FuelEntity delete(FuelEntity fuel) {
        return FuelController.delete(fuel);
    }
    public FuelEntity update(FuelEntity fuel) {
        return FuelController.update(fuel);
    }
    public FuelEntity getById(FuelEntity fuel) {
        return FuelController.getById(fuel);
    }
    public FuelEntity vis(FuelEntity fuel) {
        return FuelController.vis(fuel);
    }

    public List<FuelEntity> getAll() {
        return fuelController.getAll();
    }
    public List<FuelEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return fuelController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return fuelController.getByName(criteria);
    }
}
