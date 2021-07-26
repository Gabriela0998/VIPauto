package com.app.controller;

import com.app.entity.YearEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class YearController {
    public YearController(){

    }

    public List<YearEntity> getAll() {
        List<YearEntity> yearsEntities = new ArrayList<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxYear = calendar.get(Calendar.YEAR);
        int minYear = 1960;
                while (minYear<=maxYear) {
                    YearEntity yearEntity = new YearEntity();
                    yearEntity.setName(String.valueOf(minYear));
                    yearsEntities.add(yearEntity);
                    minYear = minYear+1;
                }


        return yearsEntities;
    }
}
