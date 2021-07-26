package com.app.service;

import com.app.controller.FirmsController;
import com.app.entity.FirmsEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirmsService {
    public FirmsEntity getById(FirmsEntity firms) {
        return FirmsController.getById(firms);
    }
    public FirmsEntity update(FirmsEntity firms) {
        return FirmsController.update(firms);
    }
    public FirmsEntity delete(FirmsEntity firms) {
        return FirmsController.delete(firms);
    }
    public FirmsEntity vis(FirmsEntity firms) {
        return FirmsController.vis(firms);
    }
    public static List<FirmsEntity> getAll() {
        return FirmsController.getAll();
    }
    public static List<FirmsEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return FirmsController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return getAll();
        }

        return FirmsController.getByName(criteria);
    }

}
