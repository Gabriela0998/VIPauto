package com.app.service;

import com.app.controller.PeopleController;
import com.app.entity.PeopleEntity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeopleService {
    private static PeopleController peopleController;

    public PeopleService() {
        peopleController = new PeopleController();
    }
    public PeopleEntity insert(PeopleEntity people) {
        return PeopleController.insert(people);
    }
    public PeopleEntity update(PeopleEntity people) {
        return PeopleController.update(people);
    }
    public PeopleEntity delete(PeopleEntity people) {
        return PeopleController.delete(people);
    }
    public PeopleEntity getById(PeopleEntity people) {
        return PeopleController.getById(people);
    }
    public PeopleEntity vis(PeopleEntity people) {
        return PeopleController.vis(people);
    }

    public static Integer getExistingFirmsId(String majorName) {
        return PeopleController.getExistingFirmsId(majorName);
    }
    public List<PeopleEntity> getAll() {
        return peopleController.getAll();
    }
    public List<PeopleEntity> getSearched(String criteria) {
        String regEx = "[0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(criteria);

        if (matcher.find() == true) {
            return peopleController.getByCode(criteria);
        } else if (criteria.equals("")) {
            return this.getAll();
        }

        return peopleController.getByName(criteria);
    }
}

