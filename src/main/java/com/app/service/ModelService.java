package com.app.service;

import com.app.controller.ModelController;
import com.app.entity.ModelEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelService {
    private ModelController modelController;

    public ModelService() {
        modelController = new ModelController();
    }
    public ModelEntity insert(ModelEntity model) {
        return ModelController.insert(model);
    }
    public ModelEntity update(ModelEntity model) {
        return ModelController.update(model);
    }
    public ModelEntity delete(ModelEntity model) {
        return ModelController.delete(model);
    }
    public ModelEntity getById(ModelEntity model) {
        return ModelController.getById(model);
    }
    public ModelEntity vis(ModelEntity model) {
        return ModelController.vis(model);
    }
    public Integer getExistingModelId(String modelName) {
        return modelController.getExistingModelId(modelName);
    }

    public List<ModelEntity> getAll() {
        return modelController.getAll();
    }
    public List<ModelEntity> getAll(int idBrand) {
        return modelController.getAll(idBrand);
    }
    public List<ModelEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return modelController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return modelController.getByName(criteria);
    }
}

