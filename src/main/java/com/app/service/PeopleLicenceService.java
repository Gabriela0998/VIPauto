package com.app.service;

import com.app.controller.PeopleLicenceController;
import com.app.entity.PeopleLicenceEntity;

public class PeopleLicenceService {
    private static PeopleLicenceController peopleLicenceController;//int idPeople,idPeople,

    public PeopleLicenceService() {
        peopleLicenceController = new PeopleLicenceController();
    }
    public static PeopleLicenceEntity getAll( int idLicence, int idPeople) {
        return PeopleLicenceController.getAll(idLicence, idPeople);
    }
    public static PeopleLicenceEntity delete(int idPeople) {
        return PeopleLicenceController.delete(idPeople);
    }
    /*public List<BrandEntity> getAll(int id) {
        return peopleLicenceController.getAll(id);
    }*/
}

