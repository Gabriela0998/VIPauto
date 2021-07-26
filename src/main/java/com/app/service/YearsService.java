package com.app.service;

import com.app.controller.YearController;
import com.app.entity.YearEntity;

import java.util.List;

public class YearsService {

    private YearController yearController;

    public List<YearEntity> getAll() {
        return yearController.getAll();
    }
}
