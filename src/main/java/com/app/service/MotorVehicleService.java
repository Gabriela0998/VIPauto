package com.app.service;

import com.app.controller.MotorVehicleController;
import com.app.entity.MotorVehicleEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotorVehicleService {
    private static MotorVehicleController motorVehicleController;

    public MotorVehicleService() {
        motorVehicleController = new MotorVehicleController();
    }
    public MotorVehicleEntity insert(MotorVehicleEntity motorVehicle) {
        return MotorVehicleController.insert(motorVehicle);
    }
    public MotorVehicleEntity update(MotorVehicleEntity motorVehicle) {
        return MotorVehicleController.update(motorVehicle);
    }
    public MotorVehicleEntity delete(MotorVehicleEntity motorVehicle) {
        return MotorVehicleController.delete(motorVehicle);
    }
    public MotorVehicleEntity getById(MotorVehicleEntity motorVehicle) {
        return MotorVehicleController.getById(motorVehicle);
    }
    public MotorVehicleEntity vis(MotorVehicleEntity motorVehicle) {
        return MotorVehicleController.vis(motorVehicle);
    }
    public static Integer getExistingMotorVehicleIdCat(String comboName) {
        return motorVehicleController.getExistingMotorVehicleIdCat(comboName);
    }
    public Integer getExistingMotorVehicleIdType(String comboName) {
        return motorVehicleController.getExistingMotorVehicleIdType(comboName);
    }
    public Integer getExistingMotorVehicleIdModel(String comboName) {
        return motorVehicleController.getExistingMotorVehicleIdModel(comboName);
    }
    public static Integer getExistingMotorVehicleIdBrand(String comboName) {
        return motorVehicleController.getExistingMotorVehicleIdBrand(comboName);
    }
    public Integer getExistingMotorVehicleIdFuel(String comboName) {
        return motorVehicleController.getExistingMotorVehicleIdFuel(comboName);
    }

    public List<MotorVehicleEntity> getAll() {
        return motorVehicleController.getAll();
    }
    public List<MotorVehicleEntity> getAll(Integer idBrand, Integer idCat, Integer idModel) {
        return motorVehicleController.getAll(idBrand, idCat, idModel);
    }
    public List<MotorVehicleEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return motorVehicleController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return motorVehicleController.getByName(criteria);
    }
}

