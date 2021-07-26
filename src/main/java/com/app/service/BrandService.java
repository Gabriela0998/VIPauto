package com.app.service;

import com.app.controller.BrandController;
import com.app.entity.BrandEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrandService {
    private BrandController brandController;

    public BrandService() {
        brandController = new BrandController();
    }
    public BrandEntity insert(BrandEntity brand) {
        return BrandController.insert(brand);
    }
    public BrandEntity update(BrandEntity brand) {
        return BrandController.update(brand);
    }
    public BrandEntity delete(BrandEntity brand) {
        return BrandController.delete(brand);
    }
    public BrandEntity getById(BrandEntity brand) {
        return BrandController.getById(brand);
    }
    public BrandEntity vis(BrandEntity brand) {
        return BrandController.vis(brand);
    }
    public Integer getExistingBrandId(String majorName) {
        return brandController.getExistingBrandId(majorName);
    }

    public List<BrandEntity> getAll() {
        return brandController.getAll();
    }
    public List<BrandEntity> getAll(int id) {
        return brandController.getAll(id);
    }
    public List<BrandEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return brandController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return brandController.getByName(criteria);
    }
}

